import java.util.Scanner;

class APessoa {
    String nome;
    char sexo;
    int idade;
    float peso;

    public APessoa(String nome, char sexo, int idade, float peso) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s | Sexo: %c | Idade: %d | Peso: %.2f", nome, sexo, idade, peso);
    }
}

class NoAVL {
    APessoa pessoa;
    NoAVL esquerdo, direito;
    int altura;

    public NoAVL(APessoa p) {
        this.pessoa = p;
        this.esquerdo = this.direito = null;
        this.altura = 1; // novo nó tem altura 1
    }
}

class ArvoreAVL {
    private NoAVL raiz;

    // ===== INSERÇÃO =====
    public void inserir(APessoa p) {
        raiz = inserirRec(raiz, p);
    }

    private NoAVL inserirRec(NoAVL node, APessoa p) {
        if (node == null) {
            System.out.println("Inserido: " + p.nome);
            return new NoAVL(p);
        }

        int cmp = p.nome.compareToIgnoreCase(node.pessoa.nome);
        if (cmp < 0) {
            node.esquerdo = inserirRec(node.esquerdo, p);
        } else if (cmp > 0) {
            node.direito = inserirRec(node.direito, p);
        } else {
            System.out.println("Erro: nome já existente: " + p.nome);
            return node;
        }

        node.altura = 1 + Math.max(altura(node.esquerdo), altura(node.direito));
        int balance = fatorBalanceamento(node);

        // Tipos de rotação
        if (balance > 1 && p.nome.compareToIgnoreCase(node.esquerdo.pessoa.nome) < 0) {
            System.out.println("Rotação simples à direita (LL) em: " + node.pessoa.nome);
            return rotacaoDireita(node);
        }

        if (balance < -1 && p.nome.compareToIgnoreCase(node.direito.pessoa.nome) > 0) {
            System.out.println("Rotação simples à esquerda (RR) em: " + node.pessoa.nome);
            return rotacaoEsquerda(node);
        }

        if (balance > 1 && p.nome.compareToIgnoreCase(node.esquerdo.pessoa.nome) > 0) {
            System.out.println("Rotação dupla à direita (LR) em: " + node.pessoa.nome);
            node.esquerdo = rotacaoEsquerda(node.esquerdo);
            return rotacaoDireita(node);
        }

        if (balance < -1 && p.nome.compareToIgnoreCase(node.direito.pessoa.nome) < 0) {
            System.out.println("Rotação dupla à esquerda (RL) em: " + node.pessoa.nome);
            node.direito = rotacaoDireita(node.direito);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // ===== REMOÇÃO =====
    public void remover(String nome) {
        raiz = removerRec(raiz, nome);
    }

    private NoAVL removerRec(NoAVL node, String nome) {
        if (node == null) {
            System.out.println("Nome não encontrado: " + nome);
            return null;
        }

        int cmp = nome.compareToIgnoreCase(node.pessoa.nome);
        if (cmp < 0) {
            node.esquerdo = removerRec(node.esquerdo, nome);
        } else if (cmp > 0) {
            node.direito = removerRec(node.direito, nome);
        } else {
            System.out.println("Removendo: " + node.pessoa.nome);

            if (node.esquerdo == null || node.direito == null) {
                NoAVL temp = (node.esquerdo != null) ? node.esquerdo : node.direito;

                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                NoAVL temp = buscarMenorValor(node.direito);
                node.pessoa = temp.pessoa;
                node.direito = removerRec(node.direito, temp.pessoa.nome);
            }
        }

        if (node == null)
            return null;

        node.altura = 1 + Math.max(altura(node.esquerdo), altura(node.direito));
        int balance = fatorBalanceamento(node);

        if (balance > 1 && fatorBalanceamento(node.esquerdo) >= 0) {
            System.out.println("Rotação simples à direita (LL) em: " + node.pessoa.nome);
            return rotacaoDireita(node);
        }

        if (balance > 1 && fatorBalanceamento(node.esquerdo) < 0) {
            System.out.println("Rotação dupla à direita (LR) em: " + node.pessoa.nome);
            node.esquerdo = rotacaoEsquerda(node.esquerdo);
            return rotacaoDireita(node);
        }

        if (balance < -1 && fatorBalanceamento(node.direito) <= 0) {
            System.out.println("Rotação simples à esquerda (RR) em: " + node.pessoa.nome);
            return rotacaoEsquerda(node);
        }

        if (balance < -1 && fatorBalanceamento(node.direito) > 0) {
            System.out.println("Rotação dupla à esquerda (RL) em: " + node.pessoa.nome);
            node.direito = rotacaoDireita(node.direito);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // ===== LISTAGENS =====
    public void listarEmOrdem() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        System.out.println("--- Listagem Em-Ordem ---");
        listarEmOrdemRec(raiz);
        System.out.println("-------------------------");
    }

    public void listarPreOrdem() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        System.out.println("--- Listagem Pré-Ordem ---");
        listarPreOrdemRec(raiz);
        System.out.println("--------------------------");
    }

    public void listarPosOrdem() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        System.out.println("--- Listagem Pós-Ordem ---");
        listarPosOrdemRec(raiz);
        System.out.println("--------------------------");
    }

    private void listarEmOrdemRec(NoAVL node) {
        if (node != null) {
            listarEmOrdemRec(node.esquerdo);
            System.out.println(node.pessoa);
            listarEmOrdemRec(node.direito);
        }
    }

    private void listarPreOrdemRec(NoAVL node) {
        if (node != null) {
            System.out.println(node.pessoa);
            listarPreOrdemRec(node.esquerdo);
            listarPreOrdemRec(node.direito);
        }
    }

    private void listarPosOrdemRec(NoAVL node) {
        if (node != null) {
            listarPosOrdemRec(node.esquerdo);
            listarPosOrdemRec(node.direito);
            System.out.println(node.pessoa);
        }
    }

    // ===== CONSULTA =====
    public APessoa consultar(String nome) {
        NoAVL node = consultarRec(raiz, nome);
        return (node == null) ? null : node.pessoa;
    }

    private NoAVL consultarRec(NoAVL node, String nome) {
        if (node == null) return null;
        int cmp = nome.compareToIgnoreCase(node.pessoa.nome);
        if (cmp == 0) return node;
        if (cmp < 0) return consultarRec(node.esquerdo, nome);
        return consultarRec(node.direito, nome);
    }

    // ===== AUXILIARES =====
    private int altura(NoAVL node) {
        return (node == null) ? 0 : node.altura;
    }

    private int fatorBalanceamento(NoAVL node) {
        return (node == null) ? 0 : altura(node.esquerdo) - altura(node.direito);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;

        x.direito = y;
        y.esquerdo = T2;

        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;

        y.esquerdo = x;
        x.direito = T2;

        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;

        return y;
    }

    private NoAVL buscarMenorValor(NoAVL node) {
        NoAVL atual = node;
        while (atual.esquerdo != null)
            atual = atual.esquerdo;
        return atual;
    }
}

class QuestArvoreAVL {
    public static void main() {
        Scanner sc = new Scanner(System.in);
        ArvoreAVL arvore = new ArvoreAVL();
        int opcao;

        do {
            System.out.println("\n ----- MENU -----");
            System.out.println("1 - Inserir pessoa");
            System.out.println("2 - Listar Em-Ordem");
            System.out.println("3 - Listar Pré-Ordem");
            System.out.println("4 - Listar Pós-Ordem");
            System.out.println("5 - Remover pessoa (por nome)");
            System.out.println("6 - Consultar pessoa (por nome)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            while (!sc.hasNextInt()) {
                System.out.println("Por favor insira um número válido.");
                sc.next();
                System.out.print("Escolha: ");
            }
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Sexo (M/F): ");
                    char sexo = sc.nextLine().charAt(0);
                    System.out.print("Idade: ");
                    int idade = sc.nextInt();
                    System.out.print("Peso: ");
                    float peso = sc.nextFloat();
                    sc.nextLine();

                    arvore.inserir(new APessoa(nome, sexo, idade, peso));
                    pausar(sc);
                    break;

                case 2:
                    arvore.listarEmOrdem();
                    pausar(sc);
                    break;

                case 3:
                    arvore.listarPreOrdem();
                    pausar(sc);
                    break;

                case 4:
                    arvore.listarPosOrdem();
                    pausar(sc);
                    break;

                case 5:
                    System.out.print("Nome para remover: ");
                    String nomeRem = sc.nextLine();
                    arvore.remover(nomeRem);
                    pausar(sc);
                    break;

                case 6:
                    System.out.print("Nome para consultar: ");
                    String nomeConsultado = sc.nextLine();
                    APessoa res = arvore.consultar(nomeConsultado);
                    if (res != null)
                        System.out.println("Encontrado: " + res);
                    else
                        System.out.println("Pessoa não encontrada.");
                    pausar(sc);
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    pausar(sc);
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void pausar(Scanner sc) {
        System.out.println("\nPressione Enter para continuar...");
        sc.nextLine();
    }
}
