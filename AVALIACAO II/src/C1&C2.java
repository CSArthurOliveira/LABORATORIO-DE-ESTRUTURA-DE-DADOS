import java.io.*;
import java.util.*;

/**
 * Classe Grafo que representa um grafo ponderado não direcionado usando matriz de distâncias.
 */

class Grafo {
    protected int[][] matrizDistancias; // Matriz de distâncias/pesos
    protected int totalVertices;
    protected int totalArestas;
    protected int grau; // Grau máximo do grafo
    protected static final int SEM_CONEXAO = 999999999; // Representa ausência de aresta

    /**
     * Construtor: inicia o grafo vazio.
     */
    public Grafo() {
        System.out.println("Grafo inicializado!");
        totalArestas = 0;
        grau = 0;
    }

    // ================================
    // 1. Carregar grafo de arquivo .txt
    // ================================
    public void carregarDoArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();
            if (linha == null) return;

            totalVertices = Integer.parseInt(linha.trim());
            matrizDistancias = new int[totalVertices][totalVertices];
            totalArestas = 0;

            for (int i = 0; i < totalVertices; i++) {
                linha = br.readLine();
                if (linha == null) break;

                String[] valores = linha.trim().split("\\s+");
                for (int j = 0; j < totalVertices; j++) {
                    int peso = Integer.parseInt(valores[j]);
                    if (peso > 0) {
                        matrizDistancias[i][j] = peso;
                    } else {
                        matrizDistancias[i][j] = SEM_CONEXAO;
                    }
                    if (i < j && peso != SEM_CONEXAO) totalArestas++; // Conta arestas únicas
                }
            }

            linha = br.readLine(); // Última linha -1
            if (linha != null && linha.trim().equals("-1")) {
                System.out.println("Grafo carregado com sucesso!");
                System.out.println("Total de vértices: " + totalVertices);
                System.out.println("Total de arestas: " + totalArestas);
            }

            atualizarGrauGrafo(); // Atualiza grau após carregar
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    // ================================
    // 2. Exportar grafo para arquivo .txt
    // ================================
    public void exportarParaArquivo(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println(totalVertices);
            for (int i = 0; i < totalVertices; i++) {
                for (int j = 0; j < totalVertices; j++) {
                    writer.print(matrizDistancias[i][j] + "\t");
                }
                writer.println();
            }
            writer.println("-1");
            System.out.println("Grafo exportado em: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    // ================================
    // 3. Mostrar matriz de distâncias
    // ================================
    public void imprimirMatrizDistancias() {
        System.out.println("Matriz de Distâncias/Pesos:");
        for (int[] linha : matrizDistancias) {
            for (int valor : linha) {
                System.out.print(valor + "\t");
            }
            System.out.println();
        }
    }

    // ================================
    // 4. Gerar matriz de adjacência (0/1)
    // ================================
    public int[][] gerarMatrizAdjacencia() {
        int[][] matrizAdjacencia = new int[totalVertices][totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                matrizAdjacencia[i][j] = (matrizDistancias[i][j] > 0 && matrizDistancias[i][j] != SEM_CONEXAO) ? 1 : 0;
            }
        }
        return matrizAdjacencia;
    }

    public void imprimirMatrizAdjacencia() {
        int[][] matrizAdj = gerarMatrizAdjacencia();
        System.out.println("Matriz de Adjacência:");
        for (int[] linha : matrizAdj) {
            for (int valor : linha) {
                System.out.print(valor + "\t");
            }
            System.out.println();
        }
    }

    // ================================
    // 5. Adicionar uma aresta
    // ================================
    public void adicionarAresta(int origem, int destino, int peso) {
        if (origem >= 0 && origem < totalVertices && destino >= 0 && destino < totalVertices) {
            if (matrizDistancias[origem][destino] == SEM_CONEXAO) totalArestas++; // Nova aresta
            matrizDistancias[origem][destino] = peso;
            matrizDistancias[destino][origem] = peso; // Grafo não direcionado
            atualizarGrauGrafo(); // Atualiza o grau
        }
    }

    // ================================
    // 6. Remover uma aresta
    // ================================
    public void removerAresta(int origem, int destino) {
        if (origem >= 0 && origem < totalVertices && destino >= 0 && destino < totalVertices) {
            if (matrizDistancias[origem][destino] != SEM_CONEXAO) totalArestas--; // Aresta existente
            matrizDistancias[origem][destino] = SEM_CONEXAO;
            matrizDistancias[destino][origem] = SEM_CONEXAO;
            atualizarGrauGrafo(); // Atualiza o grau
        }
    }

    // ================================
    // 7. Adicionar um vértice
    // ================================
    public void adicionarVertice() {
        int[][] novaMatriz = new int[totalVertices + 1][totalVertices + 1];
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                novaMatriz[i][j] = matrizDistancias[i][j];
            }
        }
        for (int i = 0; i <= totalVertices; i++) {
            novaMatriz[totalVertices][i] = SEM_CONEXAO;
            novaMatriz[i][totalVertices] = SEM_CONEXAO;
        }
        matrizDistancias = novaMatriz;
        totalVertices++;
        atualizarGrauGrafo(); // Atualiza o grau
        System.out.println("Vértice adicionado. Total agora: " + totalVertices);
    }

    // ================================
    // 8. Remover um vértice
    // ================================
    public void removerVertice(int indice) {
        if (indice < 0 || indice >= totalVertices) return;

        int[][] novaMatriz = new int[totalVertices - 1][totalVertices - 1];
        int linhaNova = 0;
        for (int i = 0; i < totalVertices; i++) {
            if (i == indice) continue;
            int colunaNova = 0;
            for (int j = 0; j < totalVertices; j++) {
                if (j == indice) continue;
                novaMatriz[linhaNova][colunaNova] = matrizDistancias[i][j];
                colunaNova++;
            }
            linhaNova++;
        }
        matrizDistancias = novaMatriz;
        totalVertices--;
        recalcularTotalArestas();
        atualizarGrauGrafo(); // Atualiza o grau
        System.out.println("Vértice " + indice + " removido. Total agora: " + totalVertices);
    }

    // ================================
    // 9. Recalcular o número total de arestas
    // ================================
    private void recalcularTotalArestas() {
        totalArestas = 0;
        for (int i = 0; i < totalVertices; i++) {
            for (int j = i + 1; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO) totalArestas++;
            }
        }
    }

    // ================================
    // 10. Calcular grau do grafo e dos vértices
    // ================================
    public int calcularGrauGrafo() {
        int[] grausVertices = calcularGrauVertices();
        int maiorGrau = 0;
        for (int grauVertice : grausVertices) {
            if (grauVertice > maiorGrau) maiorGrau = grauVertice;
        }
        return maiorGrau;
    }

    public int[] calcularGrauVertices() {
        int[] grauVertices = new int[totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            int grau = 0;
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO && matrizDistancias[i][j] > 0) grau++;
            }
            grauVertices[i] = grau;
        }
        return grauVertices;
    }

    public void imprimirGrauVertices() {
        int[] graus = calcularGrauVertices();
        System.out.println("Grau de cada vértice:");
        for (int i = 0; i < graus.length; i++) {
            System.out.println("Vértice " + i + ": Grau = " + graus[i]);
        }
    }

    public void atualizarGrauGrafo() {
        this.grau = calcularGrauGrafo();
    }

    // ================================
    // 11. Retornar informações
    // ================================
    public int getTotalArestas() {
        return totalArestas;
    }

    public int getGrauGrafo() {
        return grau;
    }

    public int[][] floydWarshall() {
        int[][] menorDistancia = new int[totalVertices][totalVertices];

        // Inicializa a matriz de distâncias
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                if (i == j) menorDistancia[i][j] = 0;
                else if (matrizDistancias[i][j] == SEM_CONEXAO) menorDistancia[i][j] = Integer.MAX_VALUE;
                else menorDistancia[i][j] = matrizDistancias[i][j];
            }
        }

        // Atualiza distâncias mínimas
        for (int k = 0; k < totalVertices; k++) {
            for (int i = 0; i < totalVertices; i++) {
                for (int j = 0; j < totalVertices; j++) {
                    if (menorDistancia[i][k] != Integer.MAX_VALUE && menorDistancia[k][j] != Integer.MAX_VALUE) {
                        menorDistancia[i][j] = Math.min(menorDistancia[i][j], menorDistancia[i][k] + menorDistancia[k][j]);
                    }
                }
            }
        }

        return menorDistancia;
    }

    // ================================
    // 12. BFS - Busca em Largura
    // ================================
    public void bfs(int inicio) {
        if (inicio < 0 || inicio >= totalVertices) {
            System.out.println("Vértice inicial inválido!");
            return;
        }

        boolean[] visitado = new boolean[totalVertices];
        Queue<Integer> fila = new LinkedList<>();
        fila.add(inicio);
        visitado[inicio] = true;

        System.out.println("Ordem BFS a partir do vértice " + inicio + ":");

        while (!fila.isEmpty()) {
            int v = fila.poll();
            System.out.print(v + " ");

            for (int i = 0; i < totalVertices; i++) {
                if (!visitado[i] && matrizDistancias[v][i] != SEM_CONEXAO && matrizDistancias[v][i] > 0) {
                    fila.add(i);
                    visitado[i] = true;
                }
            }
        }
        System.out.println();
    }

    // ================================
    // 13. DFS - Busca em Profundidade
    // ================================
    public void dfs(int inicio) {
        if (inicio < 0 || inicio >= totalVertices) {
            System.out.println("Vértice inicial inválido!");
            return;
        }

        boolean[] visitado = new boolean[totalVertices];
        System.out.println("Ordem DFS a partir do vértice " + inicio + ":");
        dfsRecursivo(inicio, visitado);
        System.out.println();
    }

    private void dfsRecursivo(int v, boolean[] visitado) {
        visitado[v] = true;
        System.out.print(v + " ");

        for (int i = 0; i < totalVertices; i++) {
            if (!visitado[i] && matrizDistancias[v][i] != SEM_CONEXAO && matrizDistancias[v][i] > 0) {
                dfsRecursivo(i, visitado);
            }
        }
    }

    // ================================
    // 14. Mostrar lista de adjacência
    // ================================
    public void imprimirListaAdjacencia() {
        System.out.println("Lista de Adjacência:");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print("Vértice " + i + ": ");
            boolean primeiro = true;
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO && matrizDistancias[i][j] > 0) {
                    if (!primeiro) System.out.print(", ");
                    System.out.print(j + "(peso=" + matrizDistancias[i][j] + ")");
                    primeiro = false;
                }
            }
            System.out.println();
        }
    }

    static void main() {
        Grafo g = new Grafo();
        g.carregarDoArquivo("Dist5.txt");
        g.imprimirMatrizAdjacencia();
        g.imprimirMatrizDistancias();
        g.imprimirGrauVertices();
        g.dfs(0);
        g.bfs(0);

        /*
        System.out.println();
        int[][] matrizFloydWarshall = g.floydWarshall();
        for(int i = 0; i < matrizFloydWarshall.length; i++){
            for(int j = 0; j < matrizFloydWarshall[i].length; j++){
                System.out.print(matrizFloydWarshall[i][j] + " ");
            }
            System.out.println();
        }
        */
    }
}