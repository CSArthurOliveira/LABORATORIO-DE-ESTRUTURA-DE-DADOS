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
            this.raiz = inserindo(this.raiz, valor);
            System.out.println("Valor inserido com sucesso!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao inserir, Valor duplicado!");
        }
    }

    private No inserindo(No raiz, int valor) {
        if (raiz == null) {
            raiz = new No();
            raiz.valor = valor;
            return raiz;
        } else if (valor != raiz.valor) {
            if (valor < raiz.valor) {
                raiz.esquerda = inserindo(raiz.esquerda, valor);
            } else {
                raiz.direita = inserindo(raiz.direita, valor);
            }
        } else {
            throw new IllegalArgumentException();
        }

        return raiz;
    }


    protected void remover(int alvo) {
        try {
            this.raiz = removendo(this.raiz, alvo);
            System.out.println("Valor removido com sucesso!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Falha ao remover, arvore vazia!");
        }
    }

    private No removendo(No raiz, int alvo) {
        if (raiz == null) {
            throw new IllegalArgumentException();
        }

        if (alvo < raiz.valor) {
            raiz.esquerda = removendo(raiz.esquerda, alvo);
        } else if (alvo > raiz.valor) {
            raiz.direita = removendo(raiz.direita, alvo);
        } else {
            if (raiz.esquerda == null && raiz.direita == null) {
                return null;
            } else if (raiz.esquerda == null) {
                return raiz.direita;
            } else if (raiz.direita == null) {
                return raiz.esquerda;
            } else {
                No sucessor = buscar_menor(raiz.direita);
                raiz.valor = sucessor.valor;
                raiz.direita = removendo(raiz.direita, sucessor.valor);
            }
        }

        return raiz;
    }

    private void pre_ordem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            System.out.print(raiz.valor + " ");
            pre_ordem(raiz.esquerda);
            pre_ordem(raiz.direita);
        }
    }

    private void em_ordem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            em_ordem(raiz.esquerda);
            System.out.print(raiz.valor + " ");
            em_ordem(raiz.direita);
        }
    }


    private void pos_ordem(No raiz) {
        if (raiz == null) {
            return;
        } else {
            pos_ordem(raiz.esquerda);
            pos_ordem(raiz.direita);
            System.out.print(raiz.valor + " ");
        }
    }

    protected void percorrer(int tipo) {
        if (this.raiz == null) {
            System.out.println("Falha ao percorrer, arvore vazia!");
        } else {
            switch (tipo) {
                case 1:
                    pre_ordem(this.raiz);
                    break;
                case 2:
                    em_ordem(this.raiz);
                    break;
                case 3:
                    pos_ordem(this.raiz);
                    break;
                default:
                    System.out.println("Percurso inválido!");
            }
        }
    }

    private No buscar_menor(No raiz) {
        while (raiz.esquerda != null) {
            raiz = raiz.esquerda;
        }
        return raiz;
    }

    protected void limpar_terminal() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

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
            System.out.println("6 - SAIR");
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
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Operação inválida");
                    new Scanner(System.in).nextLine();
            }
        } while (operacao != 6);
    }
}
