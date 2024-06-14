
import java.io.File;
import java.util.Scanner;

import arquivos.ArquivoAutores;
import arquivos.ArquivoCategorias;
import arquivos.ArquivoLivros;
import entidades.Autor;
import entidades.Categoria;
import entidades.Livro;

public class Principal {

  private static Scanner console = new Scanner(System.in);

  public static void main(String[] args) {

    try {

      int opcao;
      do {
        System.out.println("\n\n\nBOOKAEDS 1.0"
                          +"------------"
                          +"\n> Início"
                          +"\n1) Categorias"
                          +"\n2) Autores"
                          +"\n3) Livros"
                          +"\n9) Reiniciar BD"
                          +"\n10) Backup"
                          +"\n0) Sair");
        
        System.out.print("\nOpção: ");
        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }

        switch (opcao) {
          case 1:
            (new MenuCategorias()).menu();
            break;
          case 2:
            (new MenuAutores()).menu();
            break;
          case 3:
            (new MenuLivros()).menu();
            break;
          case 4:

          case 9:
            preencherDados();
            break;
          case 10:
            (new MenuBackup()).menu();
          case 0:
            break;
          default:
            System.out.println("Opção inválida");
        }
      } while (opcao != 0);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void preencherDados() {
    try {
      new File("dados/categorias.db").delete();
      new File("dados/categorias.hash_d.db").delete();
      new File("dados/categorias.hash_c.db").delete();
      new File("dados/autores.db").delete();
      new File("dados/autores.hash_d.db").delete();
      new File("dados/autores.hash_c.db").delete();
      new File("dados/livros.db").delete();
      new File("dados/livros.hash_d.db").delete();
      new File("dados/livros.hash_c.db").delete();
      new File("dados/livros_isbn.hash_d.db").delete();
      new File("dados/livros_isbn.hash_c.db").delete();
      new File("dados/livros_categorias.btree.db").delete();
      new File("dados/dicionario.listainv.db").delete();
      new File("dados/blocos.listainv.db").delete();

      ArquivoLivros arqLivros = new ArquivoLivros();
      ArquivoCategorias arqCategorias = new ArquivoCategorias();
      ArquivoAutores arqAutores = new ArquivoAutores();

      arqCategorias.create(new Categoria("Romance"));
      arqCategorias.create(new Categoria("Educação"));
      arqCategorias.create(new Categoria("Sociologia"));
      arqCategorias.create(new Categoria("Policial"));
      arqCategorias.create(new Categoria("Aventura"));
      arqCategorias.create(new Categoria("Suspense"));

      arqAutores.create(new Autor("Homero"));
      arqAutores.create(new Autor("Lilian Bacich"));
      arqAutores.create(new Autor("Adolfo Tanzi Neto"));
      arqAutores.create(new Autor("Zygmunt Bauman"));
      arqAutores.create(new Autor("Plínio Dentzien"));
      arqAutores.create(new Autor("Ivan Izquierdo"));
      arqAutores.create(new Autor("Mariana Zapata"));

      arqLivros.create(new Livro("9788563560278", "Odisseia", 15.99F, 1));
      arqLivros.create(new Livro("9788584290483", "Ensino Híbrido", 39.90F, 2));
      arqLivros.create(new Livro("9786559790005", "Modernidade Líquida", 48.1F, 3));
      arqLivros.create(new Livro("9788582714911", "Memória", 55.58F, 1));
      arqLivros.create(new Livro("9786587150062", "Ciência da Computação", 48.9F, 1));
      arqLivros.create(new Livro("9786587150063", "Ciência de Dados", 48.9F, 1));
      arqLivros.create(new Livro("9786587150064", "Banco de Dados", 48.9F, 1));
      arqLivros.create(new Livro("9786587150065", "Engenharia de Computação", 48.9F, 1));
      arqLivros.create(new Livro("9786587150066", "Ciência e análise de Dados", 48.9F, 1));

      arqLivros.close();
      arqCategorias.close();
      arqAutores.close();

      System.out.println("Banco de dados reinicializado com dados de exemplo.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
