import java.util.Scanner;

class ArvoreMelhorada extends ArvoreBinaria {

    void buscar_maior() {
        try {
            No maior = buscando_maior(this.raiz);
            System.out.println("Maior valor: " + maior.valor);
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao buscar maior valor, arvore vazia!");
        }
    }

    private No buscando_maior(No raiz) {
        if (raiz == null) {
            throw new IllegalArgumentException();
        } else {
            if (raiz.direita != null) {
                raiz = buscando_maior(raiz.direita);
            }
        }

        return raiz;
    }

    @Override
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int operacao;
        do {
            limpar_terminal();
            System.out.println("1 - INSERIR");
            System.out.println("2 - REMOVER");
            System.out.println("3 - PERCURSO PRE ORDEM");
            System.out.println("4 - PERCURSO EM ORDEM");
            System.out.println("5 - PERCURSO POS ORDEM");
            System.out.println("6 - BUSCAR MAIOR VALOR");
            System.out.println("7 - SAIR");
            System.out.print("INFORME A OPERAÇÃO DESEJADA: ");
            operacao = scanner.nextInt();
            switch (operacao) {
                case 1:
                    limpar_terminal();
                    System.out.print("INFORME O VALOR QUE DESEJA INSERIR: ");
                    int para_inserir = scanner.nextInt();
                    inserir(para_inserir);
                    new Scanner(System.in).nextLine();
                    break;
                case 2:
                    limpar_terminal();
                    System.out.print("INFORME A VALOR QUE DESEJA REMOVER: ");
                    int para_remover = scanner.nextInt();
                    remover(para_remover);
                    new Scanner(System.in).nextLine();
                    break;
                case 3:
                    limpar_terminal();
                    percorrer(1);
                    new Scanner(System.in).nextLine();
                    break;
                case 4:
                    limpar_terminal();
                    percorrer(2);
                    new Scanner(System.in).nextLine();
                    break;
                case 5:
                    limpar_terminal();
                    percorrer(3);
                    new Scanner(System.in).nextLine();
                    break;
                case 6:
                    limpar_terminal();
                    buscar_maior();
                    new Scanner(System.in).nextLine();
                    break;
                case 7:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Operação inválida");
                    new Scanner(System.in).nextLine();
            }
        } while (operacao != 7);
    }
}
