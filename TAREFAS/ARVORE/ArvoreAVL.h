#include <iostream>
#include <iomanip>

using namespace std;

//------------------------------------------------------------------------
//  			Estrutura de Dados para a �rvore AVL
//------------------------------------------------------------------------
typedef struct NO
{
    int info;
    int altura;
    struct NO *esq;
    struct NO *dir;
} *ArvAVL;

//------------------------------------------------------------------------
//   PROT�TIPOS: Fun��es para manipula��o da Arvore AVL
//------------------------------------------------------------------------
class ArvoreAVL
{
private:
    ArvAVL *raiz;

public:
    ArvoreAVL()
    {
        raiz = Inicializa_ArvAVL();
    }

    ~ArvoreAVL()
    {
        Libera_ArvAVL(raiz);
    }

    ArvAVL *Inicializa_ArvAVL()
    {
        ArvAVL *raiz = (ArvAVL *)malloc(sizeof(ArvAVL));
        if (raiz != NULL)
            *raiz = NULL;
        return raiz;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Libera mem�ria de um n� qualquer da �vore AVL
    //------------------------------------------------------------------------
    void Libera_NO(NO *no)
    {
        if (no == NULL)
            return;
        Libera_NO(no->esq);
        Libera_NO(no->dir);
        free(no);
        no = NULL;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Libera mem�ria da �vore AVL
    //------------------------------------------------------------------------
    void Libera_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return;
        Libera_NO(*raiz); // libera cada n�
        free(raiz);       // libera a raiz
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Calcula a altura de um n� qualquer da �vore AVL
    //------------------------------------------------------------------------
    int Altura_NO(NO *no)
    {
        if (no == NULL)
            return -1;
        else
            return no->altura;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Calcula a altura da �vore AVL
    //------------------------------------------------------------------------
    int Altura_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return 0;
        if (*raiz == NULL)
            return 0;
        int alt_esq = Altura_ArvAVL(&((*raiz)->esq));
        int alt_dir = Altura_ArvAVL(&((*raiz)->dir));
        if (alt_esq > alt_dir)
            return (alt_esq + 1);
        else
            return (alt_dir + 1);
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Calcula o fator de balanciamento de um n� da �vore AVL
    //------------------------------------------------------------------------
    int FatorBalance_NO(NO *no)
    {
        return labs(Altura_NO(no->esq) - Altura_NO(no->dir));
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Verifica de o valor de um n� � maior do que outro
    //------------------------------------------------------------------------
    int Maior(int x, int y)
    {
        if (x > y)
            return x;
        else
            return y;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Verifica de a �rvore est� vazia
    //------------------------------------------------------------------------

    int Vazia_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return 1;
        if (*raiz == NULL)
            return 1;
        return 0;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Calcula o n�mero de n�s da �vore AVL
    //------------------------------------------------------------------------

    int TotalNO_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return 0;
        if (*raiz == NULL)
            return 0;
        int alt_esq = TotalNO_ArvAVL(&((*raiz)->esq));
        int alt_dir = TotalNO_ArvAVL(&((*raiz)->dir));
        return (alt_esq + alt_dir + 1);
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Cabe�alho para o Relat�rio EmOrdem
    //------------------------------------------------------------------------
    void CabecalhoPreOrdem()
    {
        cout << "----------------" << endl;
        cout << "|INSERCAO - AVL|" << endl;
        cout << "----------------" << endl;
        cout << "|  NO | ALTURA |" << endl;
        cout << "----------------" << endl;
    }

    void Relatorio_Insercao(ArvAVL *raiz)
    {
        cout << "| " << setw(2) << (*raiz)->info << "  |\t " << setw(2) << Altura_NO(*raiz) << "    |" << endl;
        cout << "----------------" << endl;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Imprime a �vore AVL em Pr�-ordem
    //------------------------------------------------------------------------
    void PreOrdem_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return;
        if (*raiz != NULL)
        {
            cout << (*raiz)->info << " - ";
            PreOrdem_ArvAVL(&((*raiz)->esq));
            PreOrdem_ArvAVL(&((*raiz)->dir));
        }
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Cabe�alho para o Relat�rio para a �rvore AVL
    //------------------------------------------------------------------------
    void CabecalhoEmOrdem()
    {
        cout << "----------------------" << endl;
        cout << "|Relatorio Arvore AVL|" << endl;
        cout << "----------------------" << endl;
        cout << "|  NO | ALTURA | FB  |" << endl;
        cout << "----------------------" << endl;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Relat�rio para a �rvore AVL
    //------------------------------------------------------------------------
    void Relatorio_Balance(ArvAVL *raiz)
    {
        cout << "| " << setw(2) << (*raiz)->info << "  |\t " << setw(2) << Altura_NO(*raiz) << "    |" << setw(3) << FatorBalance_NO(*raiz) << "  |" << endl;
        cout << "----------------------" << endl;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Imprime a �vore AVL em ordem
    //------------------------------------------------------------------------
    void EmOrdem_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return;
        if (*raiz != NULL)
        {
            EmOrdem_ArvAVL(&((*raiz)->esq));
            cout << (*raiz)->info << " - ";
            EmOrdem_ArvAVL(&((*raiz)->dir));
        }
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Imprime a �vore AVL em P�s-ordem
    //------------------------------------------------------------------------
    void PosOrdem_ArvAVL(ArvAVL *raiz)
    {
        if (raiz == NULL)
            return;
        if (*raiz != NULL)
        {
            PosOrdem_ArvAVL(&((*raiz)->esq));
            PosOrdem_ArvAVL(&((*raiz)->dir));
            cout << (*raiz)->info << " - ";
        }
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Consulta um valor na �vore AVL
    //------------------------------------------------------------------------
    int Consulta_ArvAVL(ArvAVL *raiz, int valor)
    {
        if (raiz == NULL)
            return 0;
        struct NO *atual = *raiz;
        while (atual != NULL)
        {
            if (valor == atual->info)
            {
                return 1;
            }
            if (valor > atual->info)
                atual = atual->dir;
            else
                atual = atual->esq;
        }
        return 0;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Rotaciona os n�s da �vore AVL, Esquerda - Esquerda
    //------------------------------------------------------------------------
    void RotacaoEE(ArvAVL *raiz)
    {
        cout << "Rotacao Esquerda - Esquerda" << endl;
        NO *no;
        no = (*raiz)->esq;
        (*raiz)->esq = no->dir;
        no->dir = *raiz;
        (*raiz)->altura = Maior(Altura_NO((*raiz)->esq), Altura_NO((*raiz)->dir)) + 1;
        no->altura = Maior(Altura_NO(no->esq), (*raiz)->altura) + 1;
        *raiz = no;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Rotaciona os n�s da �vore AVL, Direita - Direita
    //------------------------------------------------------------------------
    void RotacaoDD(ArvAVL *raiz)
    {
        cout << "Rotacao Direita - Direita" << endl;
        NO *no;
        no = (*raiz)->dir;
        (*raiz)->dir = no->esq;
        no->esq = (*raiz);
        (*raiz)->altura = Maior(Altura_NO((*raiz)->esq), Altura_NO((*raiz)->dir)) + 1;
        no->altura = Maior(Altura_NO(no->dir), (*raiz)->altura) + 1;
        (*raiz) = no;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Rotaciona os n�s da �vore AVL, Esquerda - Direita
    //------------------------------------------------------------------------
    void RotacaoED(ArvAVL *raiz)
    {
        cout << "Rotacao Esquerda - Direita" << endl;
        RotacaoDD(&(*raiz)->esq);
        RotacaoEE(raiz);
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Rotaciona os n�s da �vore AVL, Direita - Esquerda
    //------------------------------------------------------------------------
    void RotacaoDE(ArvAVL *raiz)
    {
        cout << "Rotacao Direita - Esquerda" << endl;
        RotacaoEE(&(*raiz)->dir);
        RotacaoDD(raiz);
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Insere um n� na �vore AVL
    //------------------------------------------------------------------------

    int Insere_ArvAVL(ArvAVL *raiz, int valor)
    {
        int ok;
        if (*raiz == NULL)
        { // �rvore vazia ou n� folha
            NO *novo;
            novo = (NO *)malloc(sizeof(NO));
            if (novo == NULL)
                return 0;

            novo->info = valor;
            novo->altura = 0;
            novo->esq = NULL;
            novo->dir = NULL;
            *raiz = novo;
            return 1;
        }

        NO *atual = *raiz;
        if (valor < atual->info)
        {
            if ((ok = Insere_ArvAVL(&(atual->esq), valor)) == 1)
            {
                if (FatorBalance_NO(atual) >= 2)
                {
                    if (valor < (*raiz)->esq->info)
                    {
                        RotacaoEE(raiz);
                    }
                    else
                    {
                        RotacaoED(raiz);
                    }
                }
            }
        }
        else
        {
            if (valor > atual->info)
            {
                if ((ok = Insere_ArvAVL(&(atual->dir), valor)) == 1)
                {
                    if (FatorBalance_NO(atual) >= 2)
                    {
                        if ((*raiz)->dir->info < valor)
                        {
                            RotacaoDD(raiz);
                        }
                        else
                        {
                            RotacaoDE(raiz);
                        }
                    }
                }
            }
            else
            {
                printf("Valor duplicado!!\n");
                return 0;
            }
        }

        atual->altura = Maior(Altura_NO(atual->esq), Altura_NO(atual->dir)) + 1;

        return ok;
    }
    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Organiza os n�s da �vore AVL, menor - maior
    //------------------------------------------------------------------------
    NO *ProcuraMenor(NO *atual)
    {
        NO *no1 = atual;
        NO *no2 = atual->esq;
        while (no2 != NULL)
        {
            no1 = no2;
            no2 = no2->esq;
        }
        return no1;
    }

    //------------------------------------------------------------------------
    //   PROTOTIPA��O: Remove um n� na �vore AVL
    //------------------------------------------------------------------------
    int Remove_ArvAVL(ArvAVL *raiz, int valor)
    {
        if (*raiz == NULL)
        { // valor n�o existe
            cout << "valor nao existe!!" << endl;
            return 0;
        }
        int sai;
        if (valor < (*raiz)->info)
        {
            if ((sai = Remove_ArvAVL(&(*raiz)->esq, valor)) == 1)
            {
                if (FatorBalance_NO(*raiz) >= 2)
                {
                    if (Altura_NO((*raiz)->dir->esq) <= Altura_NO((*raiz)->dir->dir))
                        RotacaoDD(raiz);
                    else
                        RotacaoDE(raiz);
                }
            }
        }

        if ((*raiz)->info < valor)
        {
            if ((sai = Remove_ArvAVL(&(*raiz)->dir, valor)) == 1)
            {
                if (FatorBalance_NO(*raiz) >= 2)
                {
                    if (Altura_NO((*raiz)->esq->dir) <= Altura_NO((*raiz)->esq->esq))
                        RotacaoEE(raiz);
                    else
                        RotacaoED(raiz);
                }
            }
        }

        if ((*raiz)->info == valor)
        {
            if (((*raiz)->esq == NULL || (*raiz)->dir == NULL))
            { // n� tem 1 filho ou nenhum
                NO *oldNode = (*raiz);
                if ((*raiz)->esq != NULL)
                    *raiz = (*raiz)->esq;
                else
                    *raiz = (*raiz)->dir;
                free(oldNode);
            }
            else
            { // n� tem 2 filhos
                NO *temp = ProcuraMenor((*raiz)->dir);
                (*raiz)->info = temp->info;
                Remove_ArvAVL(&(*raiz)->dir, (*raiz)->info);
                if (FatorBalance_NO(*raiz) >= 2)
                {
                    if (Altura_NO((*raiz)->esq->dir) <= Altura_NO((*raiz)->esq->esq))
                        RotacaoEE(raiz);
                    else
                        RotacaoED(raiz);
                }
            }
            if (*raiz != NULL)
                (*raiz)->altura = Maior(Altura_NO((*raiz)->esq), Altura_NO((*raiz)->dir)) + 1;
            return 1;
        }

        (*raiz)->altura = Maior(Altura_NO((*raiz)->esq), Altura_NO((*raiz)->dir)) + 1;

        return sai;
    }

    void menu()
    {
        int opcao;
        do
        {
            cout << "-------- Menu Arvore AVL --------" << endl;
            cout << "1. Inserir elemento" << endl;
            cout << "2. Remover elemento" << endl;
            cout << "3. Consultar elemento" << endl;
            cout << "4. Imprimir em ordem" << endl;
            cout << "5. Imprimir em pre-ordem" << endl;
            cout << "6. Imprimir em pos-ordem" << endl;
            cout << "7. Altura da arvore" << endl;
            cout << "8. Total de nos na arvore" << endl;
            cout << "9. Verificar se a arvore esta vazia" << endl;
            cout << "0. Sair" << endl;
            cout << "Escolha uma opcao: ";
            cin >> opcao;
            switch (opcao)
            {
            case 1:
            {
                int valor;
                cout << "Digite o valor a ser inserido: ";
                cin >> valor;
                Insere_ArvAVL(raiz, valor);
                system("pause");
                break;
            }
            case 2:
            {
                int valor;
                cout << "Digite o valor a ser removido: ";
                cin >> valor;
                Remove_ArvAVL(raiz, valor);
                system("pause");
                break;
            }
            case 3:
            {
                int valor;
                cout << "Digite o valor a ser consultado: ";
                cin >> valor;
                if (Consulta_ArvAVL(raiz, valor))
                    cout << "Valor encontrado na arvore!" << endl;
                else
                    cout << "Valor nao encontrado na arvore!" << endl;
                system("pause");
                break;
            }
            case 4:
            {
                EmOrdem_ArvAVL(raiz);
                system("pause");
                break;
            }
            case 5:
            {
                PreOrdem_ArvAVL(raiz);
                system("pause");
                break;
            }
            case 6:
            {
                PosOrdem_ArvAVL(raiz);
                system("pause");
                break;
            }
            case 7:
            {
                cout << "Altura da Arvore: " << Altura_ArvAVL(raiz) << endl;
                system("pause");
                break;
            }
            case 8:
            {
                cout << "Total de Nos na Arvore: " << TotalNO_ArvAVL(raiz) << endl;
                system("pause");
                break;
            }
            case 9:
            {
                if (Vazia_ArvAVL(raiz))
                    cout << "A arvore esta vazia!" << endl;
                else
                    cout << "A arvore nao esta vazia!" << endl;
                system("pause");
                break;
            }
            case 0:
            {
                cout << "Encerrando..." << endl;
                break;
            }
            default:
            {
                cout << "Opcao invalida! Tente novamente." << endl;
                system("pause");
                break;
            }
            }
        } while (opcao != 0);
    }
};