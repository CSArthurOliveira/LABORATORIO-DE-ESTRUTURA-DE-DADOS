import java.util.Scanner;

class ArvoreMelhorada extends ArvoreBinaria {

    void buscarMaior() {
        try {
            No maior = buscarNoMaior(this.raiz);
            System.out.println("Maior valor: " + maior.valor);
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao buscar maior valor, arvore vazia!");
        }
    }

    private No buscarNoMaior(No raiz) {
        if (raiz == null) {
            throw new IllegalArgumentException();
        } else {
            if (raiz.direita != null) {
                raiz = buscarNoMaior(raiz.direita);
            }
        }
        return raiz;
    }

    @Override
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int operacao;
        do {
            limparTerminal();
            System.out.println("1 - Inserir");
            System.out.println("2 - Remover");
            System.out.println("3 - Percurso Pre-Ordem");
            System.out.println("4 - Percurso Em-Ordem");
            System.out.println("5 - Percurso Pos-Ordem");
            System.out.println("6 - Buscar Maior Valor");
            System.out.println("7 - Sair");
            System.out.print("Informe a operação desejada: ");
            operacao = scanner.nextInt();
            switch (operacao) {
                case 1:
                    limparTerminal();
                    System.out.print("Informe o valor que deseja inserir: ");
                    int valorParaInserir = scanner.nextInt();
                    inserir(valorParaInserir);
                    new Scanner(System.in).nextLine();
                    break;
                case 2:
                    limparTerminal();
                    System.out.print("Informe o valor que deseja remover: ");
                    int valorParaRemover = scanner.nextInt();
                    remover(valorParaRemover);
                    new Scanner(System.in).nextLine();
                    break;
                case 3:
                    limparTerminal();
                    percorrer(1);
                    new Scanner(System.in).nextLine();
                    break;
                case 4:
                    limparTerminal();
                    percorrer(2);
                    new Scanner(System.in).nextLine();
                    break;
                case 5:
                    limparTerminal();
                    percorrer(3);
                    new Scanner(System.in).nextLine();
                    break;
                case 6:
                    limparTerminal();
                    buscarMaior();
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