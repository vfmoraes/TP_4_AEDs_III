# TP_4_AEDs_III
Repositório do Trabalho Prático IV de Algoritmos e Estrutura de Dados III

#### Membros do grupo:
- Luís Augusto Lima de Oliveira
- Victor Ferraz de Moraes

# Relatório
## Implementações feitas pelo grupo

####  Criação da Classe Criptografia

* `byte[] toByteArray(byte[] byteArr, String chaveS)`
  * Recebe uma string e converte para um array de Bytes 

* `byte[] cifrar(byte[] byteArr, String chave)`
  * Recebe um array de bytes a ser codificado e uma chave, que será transformada em uma array de bytes também através da função `toByteArray(...)`. A função chamará primeiramente a cifra de Substituição (`cifrarSub(...)`) e logo em seguida, o seu retorno será utilizado como parâmetro para a cifra de Transposição (`cifrarTrans(...)`). Finalmente, o resultado final é retornado.
* `byte[] cifrarSub(byte[] byteArr, byte[] chave)`
  * Para cada byte contido no Array de bytes, será somado o valor deste Byte com o caracter correspondente da chave e depois feito um módulo em 256 para gerar a resultado da cifra.
* `byte[] cifrarTrans(byte[] byteArr, byte[] chave)`
  * Para cada byte contido no Array de bytes, ele será distribuído em uma matriz que terá a quantidade de colunas equivalente ao tamanho da chave. O resultado da codificação será feita em ordem alfabética, concatenando os elementos contidos nas colunas, em que cada coluna é um caracter da chave.


* `byte[] decifrarSub(byte[] byteArr, byte[] chave)`
  * Para cada byte contido no Array de bytes codificado, será subtraído do byte o caracter da chave correspondente e feito uma soma de 256, após realizar este cálculo, um módulo de 256 ocorre e por fim retornado o resultado.
* `byte[] decifrarTrans(byte[] byteArr, byte[] chave)`
  * Para cada byte contido no Array de bytes codificado, ele será distribuído em uma matriz que terá a quantidade de colunas equivalente ao tamanho da chave. O array de bytes será armazenado na matriz por colunas seguindo a ordem alfabética dos caracteres da chave. O resultado da decodificação será a leitura linear da matriz final.
* `byte[] decifrar(byte[] byteArr, String chave)`
  * Recebe um array de bytes a ser decodificado e uma chave, que será transformada em uma array de bytes também através da função `toByteArray(...)`. A função chamará primeiramente a decifragem para a cifra de Substituição (`decifrarSub(...)`) e logo em seguida, o seu retorno será utilizado como parâmetro para a decifragem para a cifra de Transposição (`decifrarTrans(...)`). Finalmente, o resultado final é retornado.

## Resultados

### Checklist

#### - Há uma função de cifragem em todas as classes de entidades, envolvendo pelo menos duas operações diferentes e usando uma chave criptográfica?

<pre> Sim, são utilizadas as cifras de Substituição (Cifra de Vigenère) e Transposição (Cifra de Colunas) para a realiazão da criptografia. A chave utilizada para a criptografia é diferente para cada entidade.</pre>

#### - Uma das operações de cifragem é baseada na substituição e a outra na transposição?

<pre> Sim, conforme dito acima. </pre>

#### - O trabalho está funcionando corretamente?

<pre> Sim, o trabalho funciona corretamente. </pre>

#### - O trabalho está completo?

<pre> Sim, o trabalho está completo. </pre>

#### - O trabalho é original e não a cópia de um trabalho de um colega?

<pre> Sim, o trabalho foi implemetado usando código desenvolvido pelo grupo no TP3 (Backup compactado)</pre>
