import java.util.Scanner;

class Pessoa {
    String nome;
    char sexo;
    int idade;
    float peso;

    Pessoa(String nome, char sexo, int idade, float peso){

        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.peso = peso;
    }
}

//Nó da arvore
class NoArvore {
    Pessoa pessoa;
    NoArvore esquerda;
    NoArvore direita;

    NoArvore(Pessoa p) {
        this.pessoa = p;
        esquerda = null;
        direita = null;
    }
}

// No lista
class NoLista {
    Pessoa pessoa;
    NoLista prox;

    NoLista (Pessoa p) {
        this.pessoa = p;
        this.prox = null;
    }
}
// Classe da arvore binaria de busca
class ArvoreBinaria {
    NoArvore raiz;

    ArvoreBinaria() {
        raiz = null;
        System.out.println("Arvore inicializada!");
    }

    // Inserção na bst (ordem alfabetica)
    public void inserir(Pessoa p) {
        raiz = inserirRec(raiz, p);
    }

    private NoArvore inserirRec(NoArvore atual, Pessoa p) {
        if (atual == null) {
            return new NoArvore(p);
        }
        if (p.nome.compareToIgnoreCase(atual.pessoa.nome) < 0)
            atual.esquerda = inserirRec(atual.esquerda, p);
        else if (p.nome.compareToIgnoreCase(atual.pessoa.nome) > 0)
            atual.direita = inserirRec(atual.direita, p);
        else{
            System.out.println("Nome ja existente: "+ p.nome);
            new Scanner(System.in).nextLine();
        }


        return atual;
    }

    public void remover(String nomeAlvo) {
        try{
            raiz = removerRec(raiz, nomeAlvo);
            System.out.println("Pessoa removida com sucesso!");
        }catch(IllegalArgumentException exception){
            System.out.println("Erro ao remover!");
        }
    }

    private NoArvore removerRec(NoArvore atual, String alvo) {
            if(atual == null){
                throw new IllegalArgumentException();
            }else{
                if(alvo.compareToIgnoreCase(atual.pessoa.nome) < 0){
                    atual.esquerda = removerRec(atual.esquerda,alvo);
                }else if(alvo.compareToIgnoreCase(atual.pessoa.nome) > 0){
                    atual.direita = removerRec(atual.direita, alvo);
                }else{
                    if(atual.direita == null && atual.esquerda == null){
                        return null;
                    }else if(atual.esquerda == null){
                        return atual.direita;
                    }else if(atual.direita == null){
                        return atual.esquerda;
                    }else{
                        NoArvore sucessor = buscarMenor(atual.direita);
                        atual.pessoa = sucessor.pessoa;
                        atual.direita = removerRec(atual.direita, sucessor.pessoa.nome);
                    }
                }
            }

            return atual;
    }

    private NoArvore buscarMenor(NoArvore raiz) {
        NoArvore atual = raiz;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }
    // Geração das listas
    public void gerarListas(Lista homens, Lista mulheres) {
        percorrerArvore(raiz, homens, mulheres);
    }

    private void percorrerArvore(NoArvore no, Lista homens, Lista mulheres) {
        if (no != null){
            percorrerArvore(no.esquerda, homens, mulheres);

            if(no.pessoa.sexo == 'M' || no.pessoa.sexo == 'm')
                homens.inserirOrdenado(no.pessoa);
            else if (no.pessoa.sexo == 'F' || no.pessoa.sexo == 'f')
                mulheres.inserirOrdenado(no.pessoa);

            percorrerArvore(no.direita, homens, mulheres);
        }
    }

    //Exibir a arvore
    public void exibirPreOrdem(){
        exibirPreOrdemRec(raiz);
    }

    private void exibirPreOrdemRec(NoArvore no){
        if(no != null){
            System.out.println(no.pessoa.nome + "("+ no.pessoa.sexo +") Idade: "+ no.pessoa.idade + " Peso: "+ no.pessoa.peso);
            exibirEmOrdemRec(no.esquerda);
            exibirEmOrdemRec(no.direita);
        }
    }

    public void exibirEmOrdem(){
        exibirEmOrdemRec(raiz);
    }

    private void exibirEmOrdemRec(NoArvore no){
        if (no != null){
            exibirEmOrdemRec(no.esquerda);
            System.out.println(no.pessoa.nome + "("+ no.pessoa.sexo +") Idade: "+ no.pessoa.idade + " Peso: "+ no.pessoa.peso);
            exibirEmOrdemRec(no.direita);
        }
    }

    public void exibirPosOrdem(){
        exibirPosOrdemRec(raiz);
    }

    private void exibirPosOrdemRec(NoArvore no){
        if (no != null){
            exibirPosOrdemRec(no.esquerda);
            exibirPosOrdemRec(no.direita);
            System.out.println(no.pessoa.nome + "("+ no.pessoa.sexo +") Idade: "+ no.pessoa.idade + " Peso: "+ no.pessoa.peso);
        }
    }
}

// Lista encadeada
class Lista {
    NoLista inicio;

    public Lista() {
        inicio = null;
    }

    public void inserirOrdenado(Pessoa p) {
        NoLista novo = new NoLista(p);

        if (inicio == null || p.nome.compareToIgnoreCase(inicio.pessoa.nome) < 0) {
            novo.prox = inicio;
            inicio = novo;
        } else {
            NoLista atual = inicio;
            while (atual.prox != null && p.nome.compareToIgnoreCase(atual.prox.pessoa.nome) > 0){
                atual = atual.prox;
            }
            novo.prox = atual.prox;
            atual.prox = novo;
        }
    }
    public void exibir() {
        NoLista atual = inicio;
        while (atual != null) {
            System.out.println(atual.pessoa.nome + "("+ atual.pessoa.sexo + "), Idade: " +
                    atual.pessoa.idade + ", Peso: " + atual.pessoa.peso);
            atual = atual.prox;
        }
    }
}


class Quest3 {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            ArvoreBinaria arvore = new ArvoreBinaria();
            Lista homens = new Lista();
            Lista mulheres = new Lista();

            int opc;
            do {
                System.out.println("\n ---- MENU ----");
                System.out.println("1 - Inserir pessoa");
                System.out.println("2 - Remover pessoa");
                System.out.println("3 - Exibir arvore em Pre Ordem");
                System.out.println("4 - Exibir arvore em Ordem Simetrica");
                System.out.println("5 - Exibir arvore em Pos Ordem");
                System.out.println("6 - Gerar Lista de homens e mulheres");
                System.out.println("7 - Exibir lista de homens");
                System.out.println("8 - Exibir lista de mulheres");
                System.out.println("0 - Encerrar o programa");
                System.out.print("Opção: ");
                opc = sc.nextInt();
                sc.nextLine(); // limpar buffer

                switch (opc) {

                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Sexo (M/F): ");
                        char sexo = sc.nextLine().charAt(0);
                        System.out.print("Idade: ");
                        int idade = sc.nextInt();
                        System.out.print("Peso: ");
                        float peso = sc.nextFloat();
                        sc.nextLine(); // limpar buffer

                        Pessoa p = new Pessoa(nome, sexo, idade, peso);
                        arvore.inserir(p);
                        break;

                    case 2:
                        System.out.println("Digite o nome do pessoa que deseja remover: ");
                        String alvo = sc.nextLine();
                        arvore.remover(alvo);
                        esperarEnter(sc);
                        break;

                    case 3:
                        System.out.println("--- Arvore em Pre Ordem ---");
                        arvore.exibirPreOrdem();
                        esperarEnter(sc);
                        break;

                    case 4:
                        System.out.println("--- Arvore em Ordem Simetrica ---");
                        arvore.exibirEmOrdem();
                        esperarEnter(sc);
                        break;

                    case 5:
                        System.out.println("--- Arvore em Pos Ordem ---");
                        arvore.exibirPosOrdem();
                        esperarEnter(sc);
                        break;

                    case 6:
                        homens = new Lista();
                        mulheres = new Lista();
                        arvore.gerarListas(homens, mulheres);
                        System.out.println("Listas geradas com sucesso!");
                        esperarEnter(sc);
                        break;

                    case 7:
                        System.out.println("--- Lista Homens ---");
                        homens.exibir();
                        esperarEnter(sc);
                        break;

                    case 8:
                        System.out.println("--- Lista de Mulheres ---");
                        mulheres.exibir();
                        esperarEnter(sc);
                        break;

                    case 0:
                        System.out.println("Encerrando....");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        esperarEnter(sc);
                }
            } while (opc != 0);
            sc.close();
        }

        // Método auxiliar para pausar a execução
        private static void esperarEnter(Scanner sc) {
            System.out.println("\nPressione Enter para continuar...");
            sc.nextLine();
        }
    }

