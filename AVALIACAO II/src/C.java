import java.io.*;
import java.util.*;

/**
 * Classe Grafo que representa um grafo ponderado não direcionado usando matriz de distâncias.
 */
class Grafo {
    protected int[][] matrizDistancias;
    protected int totalVertices;
    protected int totalArestas;
    protected int grau;
    protected static final int SEM_CONEXAO = 999999999;

    /**
     * Construtor: inicia o grafo vazio.
     */
    public Grafo() {
        this.matrizDistancias = new int[0][0];
        this.totalVertices = 0;
        this.totalArestas = 0;
        this.grau = 0;
        System.out.println("Grafo inicializado!");
    }

    // ==================================================
    // 1. Carregar grafo de arquivo .txt
    // ==================================================
    public void carregarDoArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();
            if (linha == null) return;

            totalVertices = Integer.parseInt(linha.trim());
            matrizDistancias = new int[totalVertices][totalVertices];
            totalArestas = 0;

            // Inicializa toda a matriz com SEM_CONEXAO
            for (int i = 0; i < totalVertices; i++) {
                for (int j = 0; j < totalVertices; j++) {
                    matrizDistancias[i][j] = SEM_CONEXAO;
                }
            }

            // Lê os valores do arquivo
            for (int i = 0; i < totalVertices; i++) {
                linha = br.readLine();
                if (linha == null) break;

                String[] valores = linha.trim().split("\\s+");
                for (int j = 0; j < totalVertices; j++) {
                    if (j >= valores.length) continue;
                    int peso = Integer.parseInt(valores[j]);

                    if (peso > 0 && peso != SEM_CONEXAO) {
                        matrizDistancias[i][j] = peso;
                        // Conta arestas apenas na metade superior da matriz (grafo não direcionado)
                        if (i < j) totalArestas++;
                    } else {
                        matrizDistancias[i][j] = SEM_CONEXAO;
                    }
                }
            }

            if ((linha = br.readLine()) != null && linha.trim().equals("-1")) {
                System.out.println("Grafo carregado com sucesso!");
                System.out.println("Total de vértices: " + totalVertices);
                System.out.println("Total de arestas: " + totalArestas);
            }

            atualizarGrauGrafo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
        esperar();
    }

    // ==================================================
    // 2. Exportar grafo para arquivo .txt
    // ==================================================
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
            System.out.println("Grafo exportado com sucesso para: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        esperar();
    }

    // ==================================================
    // 3. Mostrar matriz de distâncias
    // ==================================================
    public void imprimirMatrizDistancias() {
        System.out.println("\nMatriz de Distâncias/Pesos:");
        System.out.print("   ");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] == SEM_CONEXAO) {
                    System.out.print("Inf\t");
                } else {
                    System.out.print(matrizDistancias[i][j] + "\t");
                }
            }
            System.out.println();
        }
        esperar();
    }

    // ==================================================
    // 4. Matriz de adjacência
    // ==================================================
    public int[][] gerarMatrizAdjacencia() {
        int[][] matrizAdjacencia = new int[totalVertices][totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                matrizAdjacencia[i][j] =
                        (matrizDistancias[i][j] > 0 && matrizDistancias[i][j] != SEM_CONEXAO) ? 1 : 0;
            }
        }
        return matrizAdjacencia;
    }

    public void imprimirMatrizAdjacencia() {
        int[][] matrizAdj = gerarMatrizAdjacencia();
        System.out.println("\nMatriz de Adjacência:");
        System.out.print("   ");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < totalVertices; j++) {
                System.out.print(matrizAdj[i][j] + " ");
            }
            System.out.println();
        }
        esperar();
    }

    // ==================================================
    // 5. Adicionar aresta
    // ==================================================
    public void adicionarAresta(int origem, int destino, int peso) {
        if (origem < 0 || origem >= totalVertices || destino < 0 || destino >= totalVertices) {
            System.out.println("Vértices inválidos! Devem estar entre 0 e " + (totalVertices - 1));
            return;
        }

        if (origem == destino) {
            System.out.println("Não é permitido adicionar laço (origem = destino).");
            return;
        }

        if (peso <= 0) {
            System.out.println("Peso inválido! Deve ser positivo.");
            return;
        }

        // Se não havia aresta antes, incrementa o contador
        if (matrizDistancias[origem][destino] == SEM_CONEXAO) {
            totalArestas++;
        }

        matrizDistancias[origem][destino] = peso;
        matrizDistancias[destino][origem] = peso;
        atualizarGrauGrafo();
        System.out.println("Aresta adicionada com sucesso entre " + origem + " e " + destino + " com peso " + peso);
        esperar();
    }

    // ==================================================
    // 6. Remover aresta
    // ==================================================
    public void removerAresta(int origem, int destino) {
        if (origem < 0 || origem >= totalVertices || destino < 0 || destino >= totalVertices) {
            System.out.println("Vértices inválidos!");
            return;
        }

        if (matrizDistancias[origem][destino] != SEM_CONEXAO) {
            totalArestas--;
            matrizDistancias[origem][destino] = SEM_CONEXAO;
            matrizDistancias[destino][origem] = SEM_CONEXAO;
            atualizarGrauGrafo();
            System.out.println("Aresta removida com sucesso entre " + origem + " e " + destino);
        } else {
            System.out.println("Não existe aresta entre " + origem + " e " + destino);
        }
        esperar();
    }

    // ==================================================
    // 7. Adicionar vértice
    // ==================================================
    public void adicionarVertice() {
        int[][] novaMatriz = new int[totalVertices + 1][totalVertices + 1];

        // Copia a matriz antiga
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                novaMatriz[i][j] = matrizDistancias[i][j];
            }
        }

        // Inicializa as novas posições com SEM_CONEXAO
        for (int i = 0; i <= totalVertices; i++) {
            novaMatriz[totalVertices][i] = SEM_CONEXAO;
            novaMatriz[i][totalVertices] = SEM_CONEXAO;
        }

        matrizDistancias = novaMatriz;
        totalVertices++;
        atualizarGrauGrafo();
        System.out.println("Vértice " + (totalVertices - 1) + " adicionado. Total agora: " + totalVertices);
        esperar();
    }

    // ==================================================
    // 8. Remover vértice
    // ==================================================
    public void removerVertice(int indice) {
        if (indice < 0 || indice >= totalVertices) {
            System.out.println("Índice inválido! Deve estar entre 0 e " + (totalVertices - 1));
            return;
        }

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
        atualizarGrauGrafo();
        System.out.println("Vértice " + indice + " removido. Total agora: " + totalVertices);
        esperar();
    }

    // ==================================================
    // 9. Recalcular total de arestas
    // ==================================================
    private void recalcularTotalArestas() {
        totalArestas = 0;
        for (int i = 0; i < totalVertices; i++) {
            for (int j = i + 1; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO && matrizDistancias[i][j] > 0) {
                    totalArestas++;
                }
            }
        }
    }

    // ==================================================
    // 10. Grau dos vértices e do grafo
    // ==================================================
    public int calcularGrauGrafo() {
        int[] graus = calcularGrauVertices();
        int maior = 0;
        for (int g : graus) {
            if (g > maior) maior = g;
        }
        return maior;
    }

    public int[] calcularGrauVertices() {
        int[] grauVertices = new int[totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            int grau = 0;
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO && matrizDistancias[i][j] > 0) {
                    grau++;
                }
            }
            grauVertices[i] = grau;
        }
        return grauVertices;
    }

    public void imprimirGrauVertices() {
        int[] graus = calcularGrauVertices();
        System.out.println("\nGrau de cada vértice:");
        for (int i = 0; i < graus.length; i++) {
            System.out.println("Vértice " + i + ": Grau = " + graus[i]);
        }
        System.out.println("Grau máximo do grafo: " + getGrauGrafo());
        esperar();
    }

    public void atualizarGrauGrafo() {
        this.grau = calcularGrauGrafo();
    }

    // ==================================================
    // 11. Algoritmo Floyd-Warshall CORRIGIDO
    // ==================================================
    public int[][] floydWarshall() {
        int[][] menorDistancia = new int[totalVertices][totalVertices];

        // Inicializa a matriz de distâncias
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] == SEM_CONEXAO && i != j) {
                    menorDistancia[i][j] = SEM_CONEXAO;
                } else if (i == j) {
                    menorDistancia[i][j] = 0;
                } else {
                    menorDistancia[i][j] = matrizDistancias[i][j];
                }
            }
        }

        // Floyd-Warshall: atualiza distâncias mínimas
        for (int k = 0; k < totalVertices; k++) {
            for (int i = 0; i < totalVertices; i++) {
                for (int j = 0; j < totalVertices; j++) {
                    if (menorDistancia[i][k] != SEM_CONEXAO && menorDistancia[k][j] != SEM_CONEXAO) {
                        long novaDistancia = (long) menorDistancia[i][k] + menorDistancia[k][j];
                        if (novaDistancia < menorDistancia[i][j]) {
                            menorDistancia[i][j] = (int) novaDistancia;
                        }
                    }
                }
            }
        }

        // Impressão formatada
        System.out.println("\nMatriz de menores distâncias (Floyd-Warshall):");
        System.out.print("   ");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        for (int i = 0; i < totalVertices; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < totalVertices; j++) {
                if (menorDistancia[i][j] == SEM_CONEXAO) {
                    System.out.print("Inf\t");
                } else {
                    System.out.print(menorDistancia[i][j] + "\t");
                }
            }
            System.out.println();
        }
        esperar();

        return menorDistancia;
    }

    // ==================================================
    // 12. BFS - Busca em Largura
    // ==================================================
    public void bfs(int inicio) {
        if (inicio < 0 || inicio >= totalVertices) {
            System.out.println("Vértice inicial inválido! Deve estar entre 0 e " + (totalVertices - 1));
            return;
        }

        boolean[] visitado = new boolean[totalVertices];
        Queue<Integer> fila = new LinkedList<>();
        fila.add(inicio);
        visitado[inicio] = true;

        System.out.println("\nBusca em Largura (BFS) a partir do vértice " + inicio + ":");
        System.out.print("Ordem de visitação: ");

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
        esperar();
    }

    // ==================================================
    // 13. DFS - Busca em Profundidade
    // ==================================================
    public void dfs(int inicio) {
        if (inicio < 0 || inicio >= totalVertices) {
            System.out.println("Vértice inicial inválido! Deve estar entre 0 e " + (totalVertices - 1));
            return;
        }

        boolean[] visitado = new boolean[totalVertices];
        System.out.println("\nBusca em Profundidade (DFS) a partir do vértice " + inicio + ":");
        System.out.print("Ordem de visitação: ");
        dfsRecursivo(inicio, visitado);
        System.out.println();
        esperar();
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

    // ==================================================
    // 14. Lista de adjacência
    // ==================================================
    public void imprimirListaAdjacencia() {
        System.out.println("\nLista de Adjacência:");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print("Vértice " + i + ": ");
            boolean primeiro = true;
            for (int j = 0; j < totalVertices; j++) {
                if (matrizDistancias[i][j] != SEM_CONEXAO && matrizDistancias[i][j] > 0) {
                    if (!primeiro) System.out.print(", ");
                    System.out.print(j + " (peso=" + matrizDistancias[i][j] + ")");
                    primeiro = false;
                }
            }
            if (primeiro) {
                System.out.print("(sem conexões)");
            }
            System.out.println();
        }
        esperar();
    }

    // ==================================================
    // 15. Verificar se o grafo é conexo
    // ==================================================
    public boolean ehConexo() {
        if (totalVertices == 0) return true;

        boolean[] visitado = new boolean[totalVertices];
        Queue<Integer> fila = new LinkedList<>();
        fila.add(0);
        visitado[0] = true;
        int contadorVisitados = 1;

        while (!fila.isEmpty()) {
            int v = fila.poll();
            for (int i = 0; i < totalVertices; i++) {
                if (!visitado[i] && matrizDistancias[v][i] != SEM_CONEXAO && matrizDistancias[v][i] > 0) {
                    fila.add(i);
                    visitado[i] = true;
                    contadorVisitados++;
                }
            }
        }

        return contadorVisitados == totalVertices;
    }

    public void verificarConexidade() {
        if (ehConexo()) {
            System.out.println("\nO grafo é CONEXO (todos os vértices estão conectados)");
        } else {
            System.out.println("\nO grafo NÃO é conexo (existem vértices isolados ou componentes separadas)");
        }
        esperar();
    }

    // ==================================================
    // 16. Métodos utilitários e getters
    // ==================================================
    private void esperar() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {}
    }

    public int getTotalVertices() { return totalVertices; }
    public int getTotalArestas() { return totalArestas; }
    public int getGrauGrafo() { return grau; }

    // ==================================================
    // MÉTODO MAIN PARA TESTES
    // ==================================================
    public static void main(String[] args) {
        Grafo g = new Grafo();

        // Teste 1: Carregar arquivo
        g.carregarDoArquivo("Dist5.txt");

        // Teste 2: Imprimir estruturas
        g.imprimirListaAdjacencia();
        g.imprimirMatrizDistancias();
        g.imprimirMatrizAdjacencia();

        // Teste 3: Graus
        g.imprimirGrauVertices();

        // Teste 4: Buscas
        g.bfs(0);
        g.dfs(0);

        // Teste 5: Floyd-Warshall (CORRIGIDO)
        g.floydWarshall();

        // Teste 6: Verificar conexidade
        g.verificarConexidade();

        // Teste 7: Adicionar vértice
        g.adicionarVertice();
        g.imprimirListaAdjacencia();
        g.imprimirMatrizDistancias();
        g.floydWarshall();

        // Teste 8: Adicionar aresta
        g.adicionarAresta(0, g.getTotalVertices() - 1, 10);
        g.imprimirMatrizDistancias();
        g.floydWarshall();

        // Teste 9: Exportar
        g.exportarParaArquivo("grafo_exportado.txt");
    }
}