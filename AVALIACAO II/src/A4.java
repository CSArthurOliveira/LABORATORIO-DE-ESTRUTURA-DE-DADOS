import java.awt.*;
import java.util.Scanner;
// no de arvore
class NoArvoreA4 {
    int chave;
    int contador;
    NoArvoreA4 esquerda, direita;

    public NoArvoreA4(int chave){
        this.chave = chave;
        this.contador = 1;
        this.esquerda = this.direita = null;
    }
}

class ArvoreA4 {
    protected NoArvoreA4 raiz;

    public void inserir(int chave){
        raiz = inserirRec(raiz, chave);
    }

    protected NoArvoreA4 inserirRec(NoArvoreA4 no, int valor){
        if (no==null){
            return new NoArvoreA4(valor);
        }

        if (valor < no.chave) {
            no.esquerda = inserirRec(no.esquerda, valor);
        }else if (valor > no.chave){
            no.direita = inserirRec(no.direita, valor);
        } else {
            no.contador++;
        }

        return no;
    }

    public void remover(int chave){
        raiz = removerRec(raiz, chave);
    }

    protected NoArvoreA4 removerRec(NoArvoreA4 no, int chave){
        if (no == null) return null;
        if (chave < no.chave) {
            no.esquerda = removerRec(no.esquerda, chave);
        }else if (chave > no.chave){
            no.direita = removerRec(no.direita, chave);
        } else {

            // encontrou o no a remover

            if (no.contador > 1){
                no.contador--;
                return no;
            }
            // remove o no normalmente se o contador == 1

            if (no.esquerda == null)
                return no.direita;
            else if (no.direita == null)
                return no.esquerda;
            // no com dois filhos (subistitui pelo menor da subarvore direita)
            no.chave = encontrarMenor(no.direita);
            no.direita = removerRec(no.direita, no.chave);
        }
        return no;
    }

    protected int encontrarMenor(NoArvoreA4 no){
        int menor = no.chave;
        while (no.esquerda != null) {
            menor = no.esquerda.chave;
            no = no.esquerda;
        }
        return menor;
    }

    public void exibirPreOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia!");
        }else {
            System.out.println("Elementos da arvore (valor(ocorrencias)): ");
            exibirPreOrdemRec(raiz);
            System.out.println();
        }
    }

    protected void exibirPreOrdemRec(NoArvoreA4 no) {
        if (no != null){
            System.out.print(no.chave + "(" + no.contador + ") ");
            exibirPreOrdemRec(no.esquerda);
            exibirPreOrdemRec(no.direita);
        }
    }

    public void exibirEmOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia!");
        }else {
            System.out.println("Elementos da arvore (valor(ocorrencias)): ");
            exibirEmOrdemRec(raiz);
            System.out.println();
        }
    }

    protected void exibirEmOrdemRec(NoArvoreA4 no) {
        if (no != null){
            exibirEmOrdemRec(no.esquerda);
            System.out.print(no.chave + "(" + no.contador + ") ");
            exibirEmOrdemRec(no.direita);
        }
    }

    public void exibirPosOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia!");
        }else {
            System.out.println("Elementos da arvore (valor(ocorrencias)): ");
            exibirPosOrdemRec(raiz);
            System.out.println();
        }
    }

    protected void exibirPosOrdemRec(NoArvoreA4 no) {
        if (no != null){
            exibirPosOrdemRec(no.esquerda);
            exibirPosOrdemRec(no.direita);
            System.out.print(no.chave + "(" + no.contador + ") ");
        }
    }
}

class Quest4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArvoreA4 arvore = new ArvoreA4();
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Inserir elemento");
            System.out.println("2 - Remover elemento");
            System.out.println("3 - Exibir arvore em Pre Ordem");
            System.out.println("4 - Exibir arvore em Ordem Simetrica");
            System.out.println("5 - Exibir arvore em Pos Ordem");
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
        new  Scanner(System.in).nextLine();
    }
}
