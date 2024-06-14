package aeds3;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Criptografia{
    public static byte[] toByteArray(String chaveS) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(chaveS);
        byte[] chave = baos.toByteArray();
        dos.close();
        baos.close();
        return chave;
    }

    public static byte[] cifrarSub(byte[] byteArr, byte[] chave){
        byte[] result = new byte[byteArr.length];
        for(int i = 0; i < byteArr.length; i++){
          result[i] = (byte) ((byteArr[i] + chave[i % chave.length]) % 256 );
        } 
        return result;
    }

    public static byte[] decifraSub(byte[] byteArr, byte[]chave){
        byte[] result = new byte[byteArr.length];
        for(int i = 0; i < byteArr.length; i++){
          result[i] = (byte) ((byteArr[i] - chave[i % chave.length] + 256) % 256);
        }
        return result;
    }


    public static boolean colunaValida(int[] posicoes, int posAtual, int tam){
        boolean ehValida = true;
        for(int i=0; i < tam; i++){
            if(posicoes[i] == posAtual){
                ehValida = false;
            }
        }
        return ehValida;
    }

    public static void calculoPosicoes(int[] posicoes, byte[] chave){
        for(int i=0; i<chave.length; i++){
            posicoes[i] = -1;
        }
        for(int i = 0; i < chave.length; i++){
            int posMenor = 0;
            while(!colunaValida(posicoes, posMenor, i))
                posMenor++;
            for(int j = posMenor+1; j < chave.length; j++){
                if(colunaValida(posicoes, j, i) && chave[j] < chave[posMenor]){
                        posMenor = j;
                }
            }
            posicoes[i] = posMenor;
        }
    }

    public static byte[] cifrarTrans(byte[] byteArr, byte[] chave){
        double testLinhas = byteArr.length/(double)chave.length;
        int qtdeLinhas = (int) Math.ceil(testLinhas);
        int qtdeColunas = chave.length;
        int[] posicoes = new int[chave.length];
        byte[][] columms = new byte[qtdeLinhas][qtdeColunas];
        byte[] result = new byte[byteArr.length];
        
        //------ preencher array de colunas -------//
        for(int i = 0; i < byteArr.length; i++){
          columms[i / qtdeColunas][i % qtdeColunas] = byteArr[i];
        }
        //------ identificar ordem das colunas -----//
        calculoPosicoes(posicoes, chave);
        
        for(int i : posicoes){
            System.out.println(i);
        }

        //----- preencher vetor resultado -------//
        int posArray = 0;
        for(int i = 0; i < posicoes.length; i++){
            //-- verificar até qual coluna será feita a iteração --//
            int tmp = (byteArr.length % chave.length) - 1;
            int lim = tmp < posicoes[i]  ? qtdeLinhas - 1 : qtdeLinhas;
            for(int j = 0; j < lim; j++){
              result[posArray++] = columms[j][posicoes[i]];
            }
        }

        return result;
    }

    public static byte[] decifrarTrans(byte[] byteArr, byte[]chave){
        double testLinhas = byteArr.length/(double)chave.length;
        int qtdeLinhas = (int) Math.ceil(testLinhas);
        int qtdeColunas = chave.length;
        int[] posicoes = new int[chave.length];
        byte[][] columms = new byte[qtdeLinhas][qtdeColunas];
        byte[] result = new byte[byteArr.length];

        //------ identificar ordem das colunas -----//
        calculoPosicoes(posicoes, chave);
        
        //----Preenche a coluna conforme a chave----//
        int posArray = 0;
        for(int i = 0; i < posicoes.length; i++){
            //-- verificar até qual coluna será feita a iteração --//
            int tmp = (byteArr.length % chave.length) - 1;
            int lim = tmp < posicoes[i]  ? qtdeLinhas - 1 : qtdeLinhas;
            for(int j = 0; j < lim; j++){
              columms[j][posicoes[i]] = byteArr[posArray++];
            }
        }
        
        posArray = 0;
        //preencher até a penúltima linha
        for(int i = 0; i < qtdeLinhas - 1; i++){
            for(int j = 0; j < qtdeColunas; j++){
                result[posArray++] = columms[i][j];
          }
        }
        //-- verificar até qual coluna será feita a iteração e preencher ultima linha--//
        int lim = (byteArr.length % chave.length);
        for(int j = 0; j < lim; j++){
          result[posArray++] = columms[qtdeLinhas-1][j];
        }
        
        return result;
    }

    /*
                   11 % 4 = 3
                   
      j a z z
      0 1 2 3 
    0 l u i s
    1 a u g u
    2 s t o
        
    */
    
    public static byte[] cifrar(byte[] byteArr, String chaveS) throws Exception{
        //---------- A partir da chave string, receber o array de bytes correspondente ----------//
        byte[] chave = toByteArray(chaveS);
        return cifrarTrans(cifrarSub(byteArr, chave), chave);    
        // return cifrarSub(byteArr, chave);
    }

    public static byte[] decifrar(byte[] byteArr, String chaveS) throws Exception{
        //---------- A partir da chave string, receber o array de bytes correspondente ----------//
        byte[] chave = toByteArray(chaveS);
        return decifraSub(decifrarTrans(byteArr, chave), chave);
    }

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