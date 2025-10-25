import java.util.Scanner;

class No {
    int valor;
    No esquerda;
    No direita;

    No() {

    }
}

class ArvoreBinaria {
    protected No raiz;

    public ArvoreBinaria() {
        System.out.println("Arvore inicializada!");
    }

    protected void inserir(int valor) {
        try {
            this.raiz = inserirNo(this.raiz, valor);
            System.out.println("Valor inserido com sucesso!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao inserir, valor duplicado!");
        }
    }

    private No inserirNo(No raiz, int valor) {
        if (raiz == null) {
            raiz = new No();
            raiz.valor = valor;
            return raiz;
        } else if (valor != raiz.valor) {
            if (valor < raiz.valor) {
                raiz.esquerda = inserirNo(raiz.esquerda, valor);
            } else {
                raiz.direita = inserirNo(raiz.direita, valor);
            }
        } else {
            throw new IllegalArgumentException();
        }

        return raiz;
    }

    protected void remover(int alvo) {
        try {
            this.raiz = removerNo(this.raiz, alvo);
            System.out.println("Valor removido com sucesso!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao remover, arvore vazia!");
        }
    }

    private No removerNo(No raiz, int alvo) {
        if (raiz == null) {
            throw new IllegalArgumentException();
        } else {
            if (alvo < raiz.valor) {
                raiz.esquerda = removerNo(raiz.esquerda, alvo);
            } else if (alvo > raiz.valor) {
                raiz.direita = removerNo(raiz.direita, alvo);
            } else {
                if (raiz.esquerda == null && raiz.direita == null) {
                    return null;
                } else if (raiz.esquerda == null) {
                    return raiz.direita;
                } else if (raiz.direita == null) {
                    return raiz.esquerda;
                } else {
                    No sucessor = buscarMenor(raiz.direita);
                    raiz.valor = sucessor.valor;
                    raiz.direita = removerNo(raiz.direita, sucessor.valor);
                }
            }
        }

        return raiz;
    }

    private void preOrdem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            System.out.print(raiz.valor + " ");
            preOrdem(raiz.esquerda);
            preOrdem(raiz.direita);
        }
    }

    private void emOrdem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            emOrdem(raiz.esquerda);
            System.out.print(raiz.valor + " ");
            emOrdem(raiz.direita);
        }
    }

    private void posOrdem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            posOrdem(raiz.esquerda);
            posOrdem(raiz.direita);
            System.out.print(raiz.valor + " ");
        }
    }

    protected void percorrer(int tipo) {
        if (this.raiz == null) {
            System.out.println("Falha ao percorrer, arvore vazia!");
        } else {
            switch (tipo) {
                case 1:
                    preOrdem(this.raiz);
                    break;
                case 2:
                    emOrdem(this.raiz);
                    break;
                case 3:
                    posOrdem(this.raiz);
                    break;
                default:
                    System.out.println("Percurso inválido!");
            }
        }
    }

    private No buscarMenor(No raiz) {
        while (raiz.esquerda != null) {
            raiz = raiz.esquerda;
        }
        return raiz;
    }

    protected void limparTerminal() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

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
            System.out.println("6 - Sair");
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
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Operação inválida");
                    new Scanner(System.in).nextLine();
            }
        } while (operacao != 6);
    }
}
