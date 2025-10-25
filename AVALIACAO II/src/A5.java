import java.util.Scanner;

class Node {
    int chave;
    int ocorrencias;
    Node esquerdo, direito;

    Node(int chave) {
        this.chave = chave;
        this.ocorrencias = 1;
        esquerdo = direito = null;
    }
}

class ArvoreBuscaBinaria {
    Node raiz;

    public void inserir(int chave) {
        raiz = inserirRec(raiz, chave);
    }

    private Node inserirRec(Node raiz, int chave) {
        if (raiz == null) {
            return new Node(chave);
        }
        if (chave < raiz.chave) {
            raiz.esquerdo = inserirRec(raiz.esquerdo, chave);
        } else if (chave > raiz.chave) {
            raiz.direito = inserirRec(raiz.direito, chave);
        } else {
            raiz.ocorrencias++;
        }
        return raiz;
    }

    public void remover(int chave) {
        raiz = removerRec(raiz, chave);
    }

    private Node removerRec(Node raiz, int chave) {
        if (raiz == null) return raiz;

        if (chave < raiz.chave) {
            raiz.esquerdo = removerRec(raiz.esquerdo, chave);
        } else if (chave > raiz.chave) {
            raiz.direito = removerRec(raiz.direito, chave);
        } else {
            if (raiz.ocorrencias > 1) {
                raiz.ocorrencias--;
                return raiz;
            }
            if (raiz.esquerdo == null)
                return raiz.direito;
            else if (raiz.direito == null)
                return raiz.esquerdo;

            Node temp = menorValor(raiz.direito);
            raiz.chave = temp.chave;
            raiz.ocorrencias = temp.ocorrencias;
            temp.ocorrencias = 1;

            raiz.direito = removerRec(raiz.direito, temp.chave);
        }
        return raiz;
    }

    private Node menorValor(Node node) {
        Node atual = node;
        while (atual.esquerdo != null)
            atual = atual.esquerdo;
        return atual;
    }

    public void exibirEmOrdem() {
        System.out.println("Arvore em ordem: ");
        emOrdem(raiz);
        System.out.println();
    }

    private void emOrdem(Node raiz) {
        if (raiz != null) {
            emOrdem(raiz.esquerdo);
            System.out.println(raiz.chave + "(" + raiz.ocorrencias + ")");
            emOrdem(raiz.direito);
        }
    }

    public int ehCheia() {
        return ehCheiaRec(raiz) ? 1 : 0;
    }

    private boolean ehCheiaRec(Node node) {
        if (node == null) return true;

        if (node.esquerdo == null && node.direito == null) return true;

        if (node.esquerdo != null && node.direito != null)
            return ehCheiaRec(node.esquerdo) && ehCheiaRec(node.direito);

        return false;
    }
}


class Quest5 {
    public static void main(String[] args) {
        ArvoreBuscaBinaria arvore = new ArvoreBuscaBinaria();
        Scanner sc = new Scanner(System.in);
        int opcao, valor;

        do {
            System.out.println("\n----- MENU -----");
            System.out.println("1 - Inserir elemento");
            System.out.println("2 - Remover elemento");
            System.out.println("3 - Exibir árvore (em ordem)");
            System.out.println("4 - Verificar se a árvore está cheia");
            System.out.println("0 - Encerrar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor para inserir: ");
                    valor = sc.nextInt();
                    arvore.inserir(valor);
                    System.out.println("Valor " + valor + " inserido com sucesso!");
                    pausar(sc);
                    break;

                case 2:
                    System.out.print("Digite o valor para remover: ");
                    valor = sc.nextInt();
                    arvore.remover(valor);
                    System.out.println("Valor " + valor + " removido (se existia na árvore).");
                    pausar(sc);
                    break;

                case 3:
                    System.out.println("--- Árvore em ordem ---");
                    arvore.exibirEmOrdem();
                    pausar(sc);
                    break;

                case 4:
                    if (arvore.ehCheia() == 1)
                        System.out.println("Árvore cheia!");
                    else
                        System.out.println("Árvore não cheia!");
                    pausar(sc);
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    pausar(sc);
            }
        } while (opcao != 0);

        sc.close();
    }

    // Método para pausar e dar tempo de visualizar a saída
    static void pausar(Scanner sc) {
        System.out.println("\nPressione Enter para continuar...");
        sc.nextLine();
    }
}
