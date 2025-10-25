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
        return nome + " (" + sexo + ") Idade: " + idade + " Peso: " + peso;
    }
}

class NoAVL {
    Pessoa pessoa;
    NoAVL esquerdo, direito;
    int altura;

    NoAVL(Pessoa p) {
        this.pessoa = p;
        this.esquerdo = this.direito = null;
        this.altura = 1; // novo nó tem altura 1
    }
}

class ArvoreAVL {
    private NoAVL raiz;

    // F1 - Inserção pública
    public void inserir(Pessoa p) {
        raiz = inserirRec(raiz, p);
    }

    // Inserção com balanceamento AVL
    private NoAVL inserirRec(NoAVL node, Pessoa p) {
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
            // nome já existe — não permite duplicatas
            System.out.println("Erro: nome já existente: " + p.nome);
            return node;
        }

        // Atualiza altura
        node.altura = 1 + Math.max(altura(node.esquerdo), altura(node.direito));

        // Verifica fator de balanceamento
        int balance = fatorBalanceamento(node);

        // Rotacoes e reequilíbrios
        // Caso Left Left
        if (balance > 1 && p.nome.compareToIgnoreCase(node.esquerdo.pessoa.nome) < 0)
            return rotacaoDireita(node);

        // Caso Right Right
        if (balance < -1 && p.nome.compareToIgnoreCase(node.direito.pessoa.nome) > 0)
            return rotacaoEsquerda(node);

        // Caso Left Right
        if (balance > 1 && p.nome.compareToIgnoreCase(node.esquerdo.pessoa.nome) > 0) {
            node.esquerdo = rotacaoEsquerda(node.esquerdo);
            return rotacaoDireita(node);
        }

        // Caso Right Left
        if (balance < -1 && p.nome.compareToIgnoreCase(node.direito.pessoa.nome) < 0) {
            node.direito = rotacaoDireita(node.direito);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // F3 - Remoção pública
    public void remover(String nome) {
        raiz = removerRec(raiz, nome);
    }

    private NoAVL removerRec(NoAVL node, String nome) {
        if (node == null) {
            System.out.println("Nome nao encontrado: " + nome);
            return null;
        }

        int cmp = nome.compareToIgnoreCase(node.pessoa.nome);
        if (cmp < 0) {
            node.esquerdo = removerRec(node.esquerdo, nome);
        } else if (cmp > 0) {
            node.direito = removerRec(node.direito, nome);
        } else {
            // nó encontrado
            System.out.println("Removendo: " + node.pessoa.nome);

            // caso com um ou nenhum filho
            if (node.esquerdo == null || node.direito == null) {
                NoAVL temp = (node.esquerdo != null) ? node.esquerdo : node.direito;

                if (temp == null) {
                    // sem filhos
                    node = null;
                } else {
                    // um filho
                    node = temp;
                }
            } else {
                // nó com dois filhos: obter sucessor (menor na subárvore direita)
                NoAVL temp = menorValor(node.direito);
                node.pessoa = temp.pessoa; // copia dados
                node.direito = removerRec(node.direito, temp.pessoa.nome);
            }
        }

        // Se a árvore ficou vazia
        if (node == null)
            return null;

        // atualiza altura
        node.altura = 1 + Math.max(altura(node.esquerdo), altura(node.direito));

        int balance = fatorBalanceamento(node);

        // Rebalancear
        // Left Left
        if (balance > 1 && fatorBalanceamento(node.esquerdo) >= 0)
            return rotacaoDireita(node);

        // Left Right
        if (balance > 1 && fatorBalanceamento(node.esquerdo) < 0) {
            node.esquerdo = rotacaoEsquerda(node.esquerdo);
            return rotacaoDireita(node);
        }

        // Right Right
        if (balance < -1 && fatorBalanceamento(node.direito) <= 0)
            return rotacaoEsquerda(node);

        // Right Left
        if (balance < -1 && fatorBalanceamento(node.direito) > 0) {
            node.direito = rotacaoDireita(node.direito);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // F2 - Listagem (Em-Ordem)
    public void listarEmOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia.");
            return;
        }
        System.out.println("--- Listagem Em-Ordem ---");
        listarEmOrdemRec(raiz);
        System.out.println();
    }

    private void listarEmOrdemRec(NoAVL node) {
        if (node != null) {
            listarEmOrdemRec(node.esquerdo);
            System.out.println(node.pessoa.toString());
            listarEmOrdemRec(node.direito);
        }
    }

    // F4 - Consulta por nome
    public Pessoa consultar(String nome) {
        NoAVL node = consultarRec(raiz, nome);
        if (node == null) return null;
        return node.pessoa;
    }

    private NoAVL consultarRec(NoAVL node, String nome) {
        if (node == null) return null;
        int cmp = nome.compareToIgnoreCase(node.pessoa.nome);
        if (cmp == 0) return node;
        if (cmp < 0) return consultarRec(node.esquerdo, nome);
        return consultarRec(node.direito, nome);
    }

    // auxiliares
    private int altura(NoAVL node) {
        return (node == null) ? 0 : node.altura;
    }

    private int fatorBalanceamento(NoAVL node) {
        return (node == null) ? 0 : altura(node.esquerdo) - altura(node.direito);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;

        // Rotacao
        x.direito = y;
        y.esquerdo = T2;

        // Atualiza alturas
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;

        return x; // nova raiz
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;

        // Rotacao
        y.esquerdo = x;
        x.direito = T2;

        // Atualiza alturas
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;

        return y; // nova raiz
    }

    private NoAVL menorValor(NoAVL node) {
        NoAVL atual = node;
        while (atual.esquerdo != null)
            atual = atual.esquerdo;
        return atual;
    }
}

class QuestArvoreAVL {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            ArvoreAVL arvore = new ArvoreAVL();
            int opcao;

            do {
                System.out.println("\n ----- MENU -----");
                System.out.println("1 - Inserir pessoa");
                System.out.println("2 - Listar (Em-Ordem)");
                System.out.println("3 - Remover pessoa (por nome)");
                System.out.println("4 - Consultar pessoa (por nome)");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine(); // limpar buffer

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

                        Pessoa p = new Pessoa(nome, sexo, idade, peso);
                        arvore.inserir(p);
                        System.out.println("Pessoa inserida com sucesso!");
                        pausar(sc);
                        break;

                    case 2:
                        System.out.println("--- Listagem em ordem ---");
                        arvore.listarEmOrdem();
                        pausar(sc);
                        break;

                    case 3:
                        System.out.print("Nome para remover: ");
                        String nomeRem = sc.nextLine();
                        arvore.remover(nomeRem);
                        System.out.println("Operação de remoção concluída!");
                        pausar(sc);
                        break;

                    case 4:
                        System.out.print("Nome para consultar: ");
                        String nomeCons = sc.nextLine();
                        Pessoa res = arvore.consultar(nomeCons);

                        if (res != null)
                            System.out.println("Encontrado: " + res.toString());
                        else
                            System.out.println("Pessoa não encontrada: " + nomeCons);

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

