package aeds3;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Criptografia{
    //=============== Abstração para transformar string para byteArray ===============//
    public static byte[] toByteArray(String chaveS) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(chaveS);
        byte[] chave = baos.toByteArray();
        dos.close();
        baos.close();
        return chave;
    }
    
    //=============== Cifrar/decifrar por substituição (Vigenère) ===============//
    //--- Cifrar ---//
    public static byte[] cifrarSub(byte[] byteArr, byte[] chave){
        byte[] result = new byte[byteArr.length];
        // Para cada byte original, aplicar a função (byte + chave) % 256 com o byte correspondente do array chave //
        for(int i = 0; i < byteArr.length; i++){
          result[i] = (byte) ((byteArr[i] + chave[i % chave.length]) % 256 );
        } 
        return result;
    }
    //--- Decifrar ---//
    public static byte[] decifraSub(byte[] byteArr, byte[]chave){
        byte[] result = new byte[byteArr.length];
        // Para cada byte original, aplicar a função ((byte - chave) + 256) % 256 com o byte correspondente do array chave //
        for(int i = 0; i < byteArr.length; i++){
          result[i] = (byte) ((byteArr[i] - chave[i % chave.length] + 256) % 256);
        }
        return result;
    }


    //=============== Cifrar/decifrar por transposição (cifra de colunas) ===============//
    //--- Abstração que verifica se a posicao atual já foi mapeada ou não ---//
    public static boolean colunaValida(int[] posicoes, int posAtual, int tam){
        boolean ehValida = true;
        for(int i=0; i < tam; i++){
            if(posicoes[i] == posAtual){
                ehValida = false;
            }
        }
        return ehValida;
    }
    //--- Abstração que armazena em posições a sequencia em que as colunas serão concatenadas ---//
    public static int[] calculoPosicoes(byte[] chave){
        int[] posicoes = new int[chave.length];
        // Inicializar array de posições com valores inválidos para evitar problemas na comparação //
        for(int i=0; i<chave.length; i++){
            posicoes[i] = -1;
        }
        
        for(int i = 0; i < chave.length; i++){
            int posMenor = 0;
            // Buscar primeira coluna válida //
            while(!colunaValida(posicoes, posMenor, i)) 
                posMenor++;
            // A partir da primeira coluna válida, verificar se ela é a menor posição válida //
            for(int j = posMenor+1; j < chave.length; j++){ 
                if(colunaValida(posicoes, j, i) && chave[j] < chave[posMenor]){
                        posMenor = j;
                }
            }
            posicoes[i] = posMenor;
        }
        return posicoes;
    }

    //--- Cifrar ---//
    public static byte[] cifrarTrans(byte[] byteArr, byte[] chave){
        double testLinhas = byteArr.length/(double)chave.length;
        int qtdeLinhas = (int) Math.ceil(testLinhas);
        int qtdeColunas = chave.length;
        int[] posicoes = null;
        byte[][] columms = new byte[qtdeLinhas][qtdeColunas];
        byte[] result = new byte[byteArr.length];
        
        // Preencher array de colunas //
        for(int i = 0; i < byteArr.length; i++){
          columms[i / qtdeColunas][i % qtdeColunas] = byteArr[i];
        }
        // Identificar ordem das colunas //
        posicoes = calculoPosicoes(chave);

        // Preencher vetor resultado //
        int posArray = 0;
        for(int i = 0; i < posicoes.length; i++){
            // Verificar até qual coluna será feita a iteração //
            int tmp = (byteArr.length % chave.length) - 1;
            int lim = tmp < posicoes[i]  ? qtdeLinhas - 1 : qtdeLinhas;
            for(int j = 0; j < lim; j++){
              result[posArray++] = columms[j][posicoes[i]];
            }
        }

        return result;
    }

    //--- Decifrar ---//
    public static byte[] decifrarTrans(byte[] byteArr, byte[]chave){
        double testLinhas = byteArr.length/(double)chave.length;
        int qtdeLinhas = (int) Math.ceil(testLinhas);
        int qtdeColunas = chave.length;
        int[] posicoes = new int[chave.length];
        byte[][] columms = new byte[qtdeLinhas][qtdeColunas];
        byte[] result = new byte[byteArr.length];

        // Identificar ordem das colunas //
        posicoes = calculoPosicoes(chave);
        
        // Preencher a coluna conforme a chave //
        int posArray = 0;
        for(int i = 0; i < posicoes.length; i++){
            // verificar até qual coluna será feita a iteração //
            int tmp = (byteArr.length % chave.length) - 1;
            int lim = tmp < posicoes[i]  ? qtdeLinhas - 1 : qtdeLinhas;
            for(int j = 0; j < lim; j++){
              columms[j][posicoes[i]] = byteArr[posArray++];
            }
        }
        
        posArray = 0;
        // Preencher até a penúltima linha //
        for(int i = 0; i < qtdeLinhas - 1; i++){
            for(int j = 0; j < qtdeColunas; j++){
                result[posArray++] = columms[i][j];
          }
        }
        // Verificar até qual coluna será feita a iteração e preencher ultima linha //
        int lim = (byteArr.length % chave.length);
        for(int j = 0; j < lim; j++){
          result[posArray++] = columms[qtdeLinhas-1][j];
        }
        
        return result;
    }
    
    public static byte[] cifrar(byte[] byteArr, String chaveS) throws Exception{
        // A partir da chave string, receber o array de bytes correspondente //
        byte[] chave = toByteArray(chaveS);
        // Cifrar a partir da cifra de substituição, em seguida a partir da cifra de transposição e retornar byte array resultante //
        return cifrarTrans(cifrarSub(byteArr, chave), chave);    
    }

    public static byte[] decifrar(byte[] byteArr, String chaveS) throws Exception{
        // A partir da chave string, receber o array de bytes correspondente //
        byte[] chave = toByteArray(chaveS);
        // Decifrar a partir da cifra de transposição, em seguida a partir da cifra de substituição e retornar byte array resultante //
        return decifraSub(decifrarTrans(byteArr, chave), chave);
    }

    // TESTES DA CLASSE COMO MAIN //
    // public static void main(String[] args) throws Exception{
    //     String teste = "Luís Augusto";
    //     byte[] bytes = toByteArray(teste);

    //     for(byte b : bytes){
    //         System.out.print(b + " ");
    //     }
    //     System.out.println("\n");

    //     bytes = cifrar(bytes, "ferraz");
    //     for(byte b : bytes){
    //         System.out.print(b + " ");
    //     }
    //     System.out.println("\n");

    //     bytes = decifrar(bytes, "ferraz");
    //     for(byte b : bytes){
    //         System.out.print(b + " ");
    //     }
    //     System.out.println("\n");
      
    // }
}
