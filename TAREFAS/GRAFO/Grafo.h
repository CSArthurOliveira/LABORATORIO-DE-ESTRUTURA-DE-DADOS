#include <iostream>

#define vertex int
#define inf 99999999

using namespace std;

class Graph
{
private:
	int V; // Número de vértices
	int A; // Número de arestas
public:
	int **adj;	// Matriz de adjacência
	int **dist; // Matriz de distâncias
	int *grau;	// Grau dos vértices
	//-------------------------------------------------------
	//         M�TODO CONSTRUTOR DA CLASSE GRAPH
	//-------------------------------------------------------
	Graph()
	{
		V = 0;
		A = 0;
		cout << "Grafo G(V,A) instanciado!" << endl;
	}
	//-------------------------------------------------------
	//         M�TODO DESTRUTOR DA CLASSE GRAPH
	//-------------------------------------------------------
	~Graph()
	{
		cout << "Grafo G(V,A) desalocado da memoria!" << endl;
	}

	//-------------------------------------------------------
	//    M�TODO GERADOR DE MATRIZ DE ADJAGENCIA DO GRAFO
	//-------------------------------------------------------
	void Gera_Matriz_Adj(int l, int c, int **Md)
	{
		adj = Aloca_MatrizInt(l, c, Md);
		for (vertex i = 0; i < l; i++)
			for (vertex j = 0; j < c; j++)
			{
				if (Md[i][j] > 0)
					adj[i][j] = 1;
				else
					adj[i][j] = 0;
			}
	}

	//-------------------------------------------------------
	//         M�TODO CONTADOR DE ARESTAS DO GRAFO
	//-------------------------------------------------------
	void NArestas(int l, int c)
	{
		int i, j, Na = 0;
		for (i = 0; i < l; i++)
			for (j = 0; j < c; j++)
				if ((i < j) && (adj[i][j] == 1))
					Na++;
		A = Na;
	}
	//-------------------------------------------------------
	//       M�TODO PARA INSERIR ARCO NO GRAFO
	//-------------------------------------------------------
	void Insere_Arco(vertex v, vertex w, int peso)
	{
		if (adj[v][w] == 0)
		{
			adj[v][w] = 1;
			adj[w][v] = 1;
			dist[v][w] = peso;
			dist[w][v] = peso;
			A++;
		}
	}

	//-------------------------------------------------------
	//       M�TODO PARA REMOVER ARCO NO GRAFO
	//-------------------------------------------------------
	void Remove_Arco(vertex v, vertex w)
	{
		if (adj[v][w] == 1)
		{
			adj[v][w] = 0;
			adj[w][v] = 0;
			dist[v][w] = inf;
			dist[w][v] = inf;
			A--;
		}
	}

	//-------------------------------------------------------
	//           M�TODO PARA EXIBIR O GRAFO
	//-------------------------------------------------------
	void Mostra_Grafo()
	{
		cout << "\t Grafo G(V,A)" << endl;
		cout << "\t Numero de Vertices: " << V << endl;
		cout << "\t Numero de Arestas: " << A << endl;
		cout << "\t --------------------------------------------" << endl;
		cout << "\t | VERTICE |            ARESTAS             |" << endl;
		cout << "\t --------------------------------------------" << endl;
		for (vertex v = 0; v < V; ++v)
		{
			cout << "\t |    " << v << "    |";
			for (vertex w = 0; w < V; ++w)
				if ((adj[v][w] == 1) && (v < w))
					cout << "  " << v << " - " << w << ",";
			cout << endl;
		}
		system("pause");
	}

	//-------------------------------------------------------
	//     M�TODO IMPRIMIR MATRIZ REPESENTATIVA DO GRAFO
	//-------------------------------------------------------
	void Imprime_Matriz(int n, int tipo)
	{
		int i, j;
		if (tipo == 1)
		{
			cout << "\t----------------------------------" << endl;
			cout << "\t   Matriz de Distancias de G(V,A) " << endl;
			cout << "\t----------------------------------" << endl;
			for (i = 0; i < n; i++)
			{
				if (i != 0)
					cout << endl;
				for (j = 0; j < n; j++)
					cout << "\t" << dist[i][j];
			}
			cout << endl;
		}
		else
		{
			cout << "\t----------------------------------" << endl;
			cout << "\t  Matriz de Adjacencia de G(V,A)  " << endl;
			cout << "\t----------------------------------" << endl;
			for (i = 0; i < n; i++)
			{
				if (i != 0)
					cout << endl;
				for (j = 0; j < n; j++)
					cout << "\t" << adj[i][j];
			}
			cout << endl;
		}
		system("pause");
	}

	//--------------------------------------------------------------------
	//         M�TODO LER UM GRAFO(MATRIZ DE DISTANCIAS) DO ARQUIVO
	//--------------------------------------------------------------------
	int Ent_Grafo(int **Md, const char Arq[])
	{
		int C, L, nv, Peso = 0;
		FILE *arquivo;
		arquivo = fopen(Arq, "r");
		if (!arquivo)
		{
			printf("O arquivo %s nao pode ser aberto", Arq);
			getchar();
			exit(1);
		}
		else
		{
			L = 0;
			C = 0;
			fscanf(arquivo, "%d", &nv);
			Md = Aloca_MatrizInt(nv, nv, Md);
			while (!feof(arquivo))
			{
				fscanf(arquivo, "%d", &Peso);
				if (Peso != -1) // -1 marca o fim do arquivo
				{
					Md[L][C] = Peso;
				}
				if (Peso == -1) // marca o fim da linha
				{
					L++;
					C = 0;
				}
				else
					C++;
			} // fim do arquivo
		}
		fclose(arquivo); // Fechando arquivo
		dist = Md;
		V = nv;
		return nv;
	} // Fim da fun��o Ent_Grafo

	//-------------------------------------------------------
	//      M�TODO QUE DISPONIBILIAR UM MENU PARA GRAFOS
	//-------------------------------------------------------
	int Menu_Grafo()
	{
		int opc;
		cout << "--------------------------------------" << endl;
		cout << "    [1] MOSTRAR GRAFO G(V,A)" << endl;
		cout << "    [2] MOSTRAR MATRIZ DE ADJACENCIA DE G(V,A)" << endl;
		cout << "    [3] MOSTRAR MATRIZ DE DISTANCIA DE  G(V,A)" << endl;
		cout << "    [4] INSERIR ARCO EM G(V,A)" << endl;
		cout << "    [5] REMOVER ARCO DE G(V,A)" << endl;
		cout << "    [6] REALIZAR BUSCA EM PROFUNDIDADE EM G(V,A)" << endl;
		cout << "    [7] ALGORITMO DE DIJKSTRA EM G(V,A)" << endl;
		cout << "    [8] GRAU DOS VERTICES DE G(V,A)" << endl;
		cout << "    [9] SAIR DO PROGRAMA" << endl;
		cout << "--------------------------------------" << endl;
		cout << "Escolha um Item do Menu acima: ";
		cin >> opc;
		return opc;
	}

	void GrauG(int n)
	{
		int i, j;
		for (i = 0; i < n; i++)
			grau[i] = 0;
		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				if (adj[i][j] == 1)
					grau[i]++;
	}

	void MostraGrauG(int n)
	{
		int i;
		// Exibindo o grau dos v�rtices de G(V,A)
		cout << "\t----------------------------------------" << endl;
		cout << "\t      Grau dos Vertices de G(V,A)       " << endl;
		cout << "\t----------------------------------------" << endl;
		for (i = 0; i < n; i++)
			cout << "\tV[" << i << "]=" << grau[i] << endl;
	}

	//---------------------------------------------------------------
	//       M�TODO AUXILIAR PARA BUSCA EM PROFUNDIDADE NO GRAFO
	//---------------------------------------------------------------

	void BP(int ini, int *visitado, int cont)
	{
		int i;
		visitado[ini] = cont;
		for (i = 0; i < grau[ini]; i++)
		{
			if (!visitado[adj[ini][i]])
				BP(adj[ini][i], visitado, cont + 1);
		}
	}

	//---------------------------------------------------------------
	//         M�TODO PARA BUSCA EM PROFUNDIDADE NO GRAFO
	//---------------------------------------------------------------
	void BuscaProfundidade(int ini, int *visitado)
	{
		int i, cont = 1;
		cout << "\t----------------------------------" << endl;
		cout << "\t  Busca em Profundidade em G(V,A) " << endl;
		cout << "\t----------------------------------" << endl;
		for (i = 0; i < V; i++)
			visitado[i] = 0;
		BP(ini, visitado, cont);
		for (i = 0; i < V; i++)
			cout << "\t" << i << " -> " << visitado[i] << endl;
	}

	//---------------------------------------------------------------
	//         M�TODO PARA O ALGORITMO DE DIJKSTRA EM GRAFO
	//---------------------------------------------------------------
	void Dijkstra(int n, int inicio, int **custo)
	{
		int *distancia, *pred;
		int *visitado, cont, distanciamin, proxno, i, j;
		visitado = (int *)malloc(n * sizeof(int));
		pred = (int *)malloc(n * sizeof(int));
		distancia = (int *)malloc(n * sizeof(int));
		for (i = 0; i < n; i++)
		{
			distancia[i] = custo[inicio][i];
			pred[i] = inicio;
			visitado[i] = 0;
		}
		distancia[inicio] = 0;
		visitado[inicio] = 1;
		cont = 1;
		while (cont < n - 1)
		{
			distanciamin = inf;
			for (i = 0; i < n; i++)
				if (distancia[i] < distanciamin && !visitado[i])
				{
					distanciamin = distancia[i];
					proxno = i;
				}

			// verifica se existe melhor caminho atraves do proximo node
			visitado[proxno] = 1;
			for (i = 0; i < n; i++)
			{
				if (!visitado[i])
					if (distanciamin + custo[proxno][i] < distancia[i])
					{
						distancia[i] = distanciamin + custo[proxno][i];
						pred[i] = proxno;
					}
			}
			cont++;
		}

		// imprime o caminho e a distancia de cada node para o inicio
		cout << "\t----------------------------------------" << endl;
		cout << "\t  Caminho Minimo dos Vertices de G(V,A) " << endl;
		cout << "\t----------------------------------------" << endl;
		for (i = 0; i < n; i++)
			if (i != inicio)
			{
				cout << "\n\tDistancia do No " << inicio << " ao " << i << " = " << distancia[i];
				cout << "\n\tCaminho: " << i;
				j = i;
				do
				{
					j = pred[j];
					cout << " <- " << j;
				} while (j != inicio);
			}
		cout << endl;
	}

	//---------------------------------------------------------------------------
	//         FUN��O AUXILIAR: Aloca��o de uma matriz de inteiros
	//---------------------------------------------------------------------------
	int **Aloca_MatrizInt(int l, int c, int **m)
	{
		m = (int **)malloc(l * sizeof(int *));
		for (int i = 0; i < l; i++)
			m[i] = (int *)malloc(c * sizeof(int));
		return m;
	}
};