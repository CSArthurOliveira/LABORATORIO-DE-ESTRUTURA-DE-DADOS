import java.util.Scanner;

class ArvoreBuscaBinaria extends ArvoreA4 {

    public int eCheia() {
        try {
            return eCheiaRec(raiz) ? 1 : 0;
        } catch (NullPointerException exception) {
            throw new NullPointerException();
        }
    }

    private boolean eCheiaRec(NoArvoreA4 node) {
        if (this.raiz == null) throw new NullPointerException();

        if (node == null) return true;

        if (node.esquerda == null && node.direita == null) return true;

        if (node.esquerda != null && node.direita != null)
            return eCheiaRec(node.esquerda) && eCheiaRec(node.direita);

        return false;
    }
}

class Quest5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArvoreBuscaBinaria arvore = new ArvoreBuscaBinaria();
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Inserir elemento");
            System.out.println("2 - Remover elemento");
            System.out.println("3 - Exibir arvore em Pre Ordem");
            System.out.println("4 - Exibir arvore em Ordem Simetrica");
            System.out.println("5 - Exibir arvore em Pos Ordem");
            System.out.println("6 - Verificar se arvore e cheia");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = input.nextInt();
            input.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a inserir: ");
                    int valorInserir = input.nextInt();
                    arvore.inserir(valorInserir);
                    System.out.println("\nValor " + valorInserir + " inserido com sucesso!");
                    esperarEnter();
                    break;

                case 2:
                    System.out.print("Digite o valor a remover: ");
                    int valorRemover = input.nextInt();
                    arvore.remover(valorRemover);
                    System.out.println("\nValor " + valorRemover + " removido com sucesso!");
                    esperarEnter();
                    break;

                case 3:
                    System.out.println("--- Arvore em Pre Ordem ---");
                    arvore.exibirPreOrdem();
                    esperarEnter();
                    break;

                case 4:
                    System.out.println("--- Arvore em Ordem Simetrica ---");
                    arvore.exibirEmOrdem();
                    esperarEnter();
                    break;

                case 5:
                    System.out.println("--- Arvore em Pos Ordem ---");
                    arvore.exibirPosOrdem();
                    esperarEnter();
                    break;

                case 6:
                    try {
                        if (arvore.eCheia() == 1) {
                            System.out.println("Arvore cheia!");
                        } else {
                            System.out.println("Arvore nao e cheia!");
                        }
                    } catch (NullPointerException exception) {
                        System.out.println("Arvore vazia!");
                    }
                    esperarEnter();
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opcao invalida! Tente novamente.");
                    esperarEnter();
            }
        } while (opcao != 0);

        input.close();
    }

    // Método auxiliar para pausar a execução
    private static void esperarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }
}