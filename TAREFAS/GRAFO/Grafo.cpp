#include <iostream>
#include "Grafo.h"

using namespace std;

int **Madj, **Md;
int i, j, n, OPC, inicio, fim, peso;

int main()
{
  Graph *G1 = new Graph();
  n = G1->Ent_Grafo(Md, "Dist5.txt");
  G1->Gera_Matriz_Adj(n, n, G1->dist);
  G1->grau = (int *)malloc(n * sizeof(int));
  G1->GrauG(n);

  do
  {
    system("cls");
    OPC = G1->Menu_Grafo();

    switch (OPC)
    {
    case 1:
      system("cls");
      G1->Mostra_Grafo();
      break;
    case 2:
      system("cls");
      G1->Imprime_Matriz(n, 2);
      break;
    case 3:
      system("cls");
      G1->Imprime_Matriz(n, 1);
      break;
    case 4:
      cout << " -" << endl;
      cout << " INSERINDO ARCO EM G(V,A) " << endl;
      cout << " -" << endl;
      cout << "Digite o inicio do arco: ";
      cin >> inicio;
      cout << "Digite o final do arco: ";
      cin >> fim;
      cout << "Digite o peso do arco " << inicio << " - " << fim << ": ";
      cin >> peso;
      G1->Insere_Arco(inicio, fim, peso);
      cout << " Arco " << inicio << " - " << fim << " inserido!" << endl;
      system("pause");
      break;
    case 5:
      cout << " -" << endl;
      cout << " REMOVENDO ARCO EM G(V,A) " << endl;
      cout << " -" << endl;
      cout << "Digite o inicio do arco: ";
      cin >> inicio;
      cout << "Digite o final do arco: ";
      cin >> fim;
      G1->Remove_Arco(inicio, fim);
      cout << " Arco " << inicio << " - " << fim << " removido!" << endl;
      system("pause");
      break;
    case 7:
      cout << " -" << endl;
      cout << " ALGORITMO DE DIJKSTRA EM G(V,A) " << endl;
      cout << " -" << endl;
      cout << "Digite o vertice inicial: ";
      cin >> inicio;
      G1->Dijkstra(n, inicio, G1->dist);
      system("pause");
      break;
    case 8:
      system("cls");
      G1->GrauG(n);
      G1->MostraGrauG(n);
      system("pause");
      break;
    case 9:
      system("cls");
      cout << "Encerrando o programa..." << endl;
      break;
    default:
      cout << "Opcao invalida! Tente novamente." << endl;
      system("pause");
      break;
    }
  } while (OPC != 9);
  delete G1;
  return 0;
}