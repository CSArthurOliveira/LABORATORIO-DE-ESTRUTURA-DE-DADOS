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
    private NoArvoreA4 raiz;

    public void inserir(int chave){
        raiz = inserirRec(raiz, chave);
    }

    private NoArvoreA4 inserirRec(NoArvoreA4 no, int chave){
        if (no==null){
            return new NoArvoreA4(chave);
        }

        if (chave < no.chave) {
            no.esquerda = inserirRec(no.esquerda, chave);
        }else if (chave > no.chave){
            no.direita = inserirRec(no.direita, chave);
        } else {
            no.contador++;
        }
        return no;
    }

    public void remover(int chave){
        raiz = removerRec(raiz, chave);
    }

    private NoArvoreA4 removerRec(NoArvoreA4 no, int chave){
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
    private int encontrarMenor(NoArvoreA4 no){
        int menor = no.chave;
        while (no.esquerda != null) {
            menor = no.esquerda.chave;
            no = no.esquerda;
        }
        return menor;
    }

    public void exibirEmOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia!");
        }else {
            System.out.println("Elementos da arvore (valor(ocorrencias)): ");
            exibirEmOrdem(raiz);
            System.out.println();
        }
    }

    private void exibirEmOrdem(NoArvoreA4 no) {
        if (no != null){
            exibirEmOrdem(no.esquerda);
            System.out.print(no.chave + "(" + no.contador + ") ");
            exibirEmOrdem(no.direita);
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
            System.out.println("3 - Exibir arvore em ordem");
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
                    System.out.println("--- Arvore em ordem ---");
                    arvore.exibirEmOrdem();
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
