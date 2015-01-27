package lapr1;

import java.util.Scanner;

/**
 * Esta classe contém os vários métodos criados para os cálculos algébricos
 * necessários para cada ponto do enunciado.
 *
 * @author DAB03
 */
public class OperacoesMatriciais {

    // Constante de arredondamento para zero
    private static final double constante = 1E-11;
    
    /**
     * (P2) Este método tem como função calcular o valor da Centralidade de Grau
     * de cada Nó.
     *
     * @param medidasCentralidade Vetor com as medidas de Centralidade de Grau.
     * @param dimensao Tamanho da matriz.
     * @param maxLigacoes Valor máximo de ligações por nó.
     * @return Vetor de doubles com o resultado.
     */
    public static double[] calcCentralidadeGrau(int[] medidasCentralidade, int dimensao, int maxLigacoes) {
        double[] centralidadeGrau = new double[dimensao];

        for (int i = 0; i < dimensao; i++) {
            centralidadeGrau[i] = (double) medidasCentralidade[i] / maxLigacoes;
        }

        return centralidadeGrau;
    }

    /**
     * (P5) Este método tem como função calcular o valor da Centralidade de
     * Proximidade de cada Nó.
     *
     * @param medidasCentralidade Vetor com as medidas de Centralidade de
     * Proximidade.
     * @param dimensao Tamanho da matriz.
     * @param maxLigacoes Valor máximo de ligações por nó.
     * @return Vetor de doubles com o resultado.
     */
    public static double[] calcCentralidadeProximidade(int[] medidasCentralidade, int dimensao, int maxLigacoes) {
        double[] centralidadeProximidade = new double[dimensao];

        for (int i = 0; i < dimensao; i++) {
            if (medidasCentralidade[i] != 0) {
                centralidadeProximidade[i] = (double) maxLigacoes / medidasCentralidade[i];
            }
        }

        return centralidadeProximidade;
    }

    /**
     * (P7) Este método tem como função calcular o valor da Centralidade de
     * Intermediação de cada Nó.
     *
     * @param matrizDistMcurtas Matriz das distâncias mais curtas.
     * @param matrizNCaminhosMCurtos Matriz com o número de caminhos mais
     * curtos.
     * @param dimensao Tamanho das matrizes.
     * @return Vetor de doubles com o resultado.
     */
    public static double[] calcCentralidadeIntermediacao(int[][] matrizDistMcurtas, int[][] matrizNCaminhosMCurtos, int dimensao) {
        double[] centralidadeIntermediacao = new double[dimensao];

        for (int noK = 0; noK < dimensao; noK++) {
            for (int noI = 0; noI < dimensao - 1; noI++) {
                if (noI != noK) {
                    for (int noJ = noI + 1; noJ < dimensao; noJ++) {
                        if (noJ != noK) {
                            int distDirecta = matrizDistMcurtas[noI][noJ];
                            int distRepartida = matrizDistMcurtas[noI][noK] + matrizDistMcurtas[noK][noJ];

                            if (distDirecta == distRepartida && matrizDistMcurtas[noI][noK] != 0 && matrizDistMcurtas[noK][noJ] != 0 && matrizDistMcurtas[noI][noJ] != 0) {
                                centralidadeIntermediacao[noK] += ((matrizNCaminhosMCurtos[noI][noK] * matrizNCaminhosMCurtos[noK][noJ]) / (double) matrizNCaminhosMCurtos[noI][noJ]);
                            }
                        }
                    }
                }
            }
        }

        return centralidadeIntermediacao;
    }

    /**
     * (P8) Este método tem como função calcular o valor da Centralidade de
     * Vetor Próprio de cada Nó.
     *
     * @param adjacenciasDouble Matriz de doubles.
     * @param adjacencias Matriz de inteiros.
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param dimensao Tamanho das matrizes.
     * @return String com os resultados em formato HTML.
     */
    public static String calcCentralidadeVetorProprio(double[][] adjacenciasDouble, int[][] adjacencias, String[] designacoesNos, int dimensao) {
        String HTML = "";
        converterMatriz(adjacenciasDouble, adjacencias, dimensao);
        double[] valoresProprios = EigenValues.evaluate(adjacenciasDouble);
        double valorProprioPrincipal = calcMaiorValor(valoresProprios, dimensao);
        subtrairValorProprioPrincipal(adjacenciasDouble, valorProprioPrincipal, dimensao);

        HTML += CodificadorHTML.titulo("Cálculos da Centralidade do Vetor Próprio");
        HTML += CodificadorHTML.subtitulo("Valores Próprios");
        HTML += CodificadorHTML.mostrarMedidas(designacoesNos, valoresProprios);
        HTML += CodificadorHTML.apresentarValorDecimal("Maior Valor Próprio", valorProprioPrincipal);
        HTML += CodificadorHTML.subtitulo("Matriz A-λI (λ = " + valorProprioPrincipal + ")");
        HTML += CodificadorHTML.mostrarMatrizDouble(adjacenciasDouble);
        HTML += CodificadorHTML.subtitulo("Passos da Condensação");
        HTML += condensarMatriz(adjacenciasDouble, dimensao);

        return HTML;
    }

    /**
     * (P4, P6) Este método tem como função preencher duas matrizes, a primeira
     * contém todas as distâncias de um Nó a todos os outros, e a segunda contém
     * o número de caminhos possiveis de realizar com a distâncias guardadas.
     *
     * @param adjacencias Matriz das adjacências
     * @param matrizDistMcurtas Matriz das distâncias mais curtas.
     * @param matrizNCaminhosMCurtos Matriz do número de caminhos mais curtos.
     * @param dimensao Tamanho das matrizes.
     * @return String com N tabelas contendo, cada uma delas , a matriz inicial
     * elevada a uma dada potência.
     */
    public static String calcMatrizDistMcurtasEmatrizNCaminhosMCurtos(int[][] adjacencias, int[][] matrizDistMcurtas, int[][] matrizNCaminhosMCurtos, int dimensao) {
        int[][] mMultiplicada = adjacencias;
        String HTML = "";
        int numElemPPreencher = (dimensao * dimensao) - dimensao, distancia = 0;

        while (distancia < dimensao && numElemPPreencher != 0) {
            distancia++;
            HTML += CodificadorHTML.mostrarMultiMatriz(mMultiplicada, distancia);

            numElemPPreencher -= preencherMatrizes(mMultiplicada, matrizDistMcurtas, matrizNCaminhosMCurtos, dimensao, distancia);

            mMultiplicada = multiplicarMatrizes(mMultiplicada, adjacencias, dimensao);
        }

        return HTML;
    }

    /**
     * (P3) Este método tem como função multiplicar duas matrizes de inteiros.
     *
     * @param matrizBase Matriz que é multiplicada.
     * @param adjacencias Matriz que multiplica.
     * @param dimensao Tamanho das matrizes.
     * @return Matriz de inteiros com o resultado da multiplicação de duas
     * matrizes.
     */
    private static int[][] multiplicarMatrizes(int[][] matrizBase, int[][] adjacencias, int dimensao) {
        int[][] mMultiplicada = new int[dimensao][dimensao];
        int linhaMm = 0, colunaMm = 0;

        for (int linhaBase = 0; linhaBase < dimensao; linhaBase++) {
            for (int colunaAdj = 0; colunaAdj < dimensao; colunaAdj++) {
                for (int colunaBase = 0; colunaBase < dimensao; colunaBase++) {
                    int linhaAdj = colunaBase;
                    mMultiplicada[linhaMm][colunaMm] += matrizBase[linhaBase][colunaBase] * adjacencias[linhaAdj][colunaAdj];
                }
                colunaMm++;
            }
            colunaMm = 0;
            linhaMm++;
        }

        return mMultiplicada;
    }

    /**
     * (P4) Este método tem como função transpor elementos de uma matriz para
     * outras duas.
     *
     * @param mMultiplicada Matriz que é multiplicada.
     * @param matrizDistMcurtas Matriz que guarda a distancia mais curta entre
     * caminhos.
     * @param matrizNCaminhosMCurtos Matriz que guarda o número de caminhos mais
     * curtos.
     * @param dimensao Tamanho da matriz.
     * @param distancia Valor com a distância entre os nós.
     * @return Inteiro com o número de elementos transpostos para a matriz de
     * distâncias mais curtas e para a matriz do número de caminhos mais curtos.
     */
    public static int preencherMatrizes(int[][] mMultiplicada, int[][] matrizDistMcurtas, int[][] matrizNCaminhosMCurtos, int dimensao, int distancia) {
        int numElemPreenchidos = 0;

        for (int linha = 0; linha < dimensao - 1; linha++) {
            for (int coluna = linha + 1; coluna < dimensao; coluna++) {
                if (linha != coluna) {
                    if (mMultiplicada[linha][coluna] != 0) {
                        if (matrizDistMcurtas[linha][coluna] == 0 && matrizNCaminhosMCurtos[linha][coluna] == 0) {
                            matrizDistMcurtas[linha][coluna] = distancia;
                            matrizDistMcurtas[coluna][linha] = distancia;

                            matrizNCaminhosMCurtos[linha][coluna] = mMultiplicada[linha][coluna];
                            matrizNCaminhosMCurtos[coluna][linha] = mMultiplicada[linha][coluna];

                            numElemPreenchidos += 2;
                        }
                    }
                }
            }
        }

        return numElemPreenchidos;
    }

    /**
     * (P8) Este método tem como função converter uma matriz de inteiros para
     * doubles.
     *
     * @param matrizDouble Matriz de Doubles.
     * @param matriz Matriz de Inteiros
     * @param dimensao Tamanho da matriz.
     */
    private static void converterMatriz(double[][] matrizDouble, int[][] matriz, int dimensao) {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                matrizDouble[i][j] = (double) matriz[i][j];
            }
        }
    }

    /**
     * (P8) Este método tem como função subtrair o Valor proprio principal à
     * matriz, gerando a diagonal de -λ. Ex: (A-Iλ)
     *
     * @param matriz Matriz de doubles.
     * @param valorProprioPrincipal Valor próprio principal que irá ser
     * subtraido.
     * @param dimensao Tamanho da matriz.
     */
    private static void subtrairValorProprioPrincipal(double[][] matriz, double valorProprioPrincipal, int dimensao) {
        for (int posDiagonal = 0; posDiagonal < dimensao; posDiagonal++) {
            matriz[posDiagonal][posDiagonal] -= valorProprioPrincipal;
        }
    }

    /**
     * (P8) Este método tem como função condensar uma matriz acima e abaixo da
     * diagonal principal de forma a encontrar a solução do sistema e determinar
     * o vetor próprio.
     *
     * @param matriz Matriz de doubles.
     * @param dimensao Tamanho da matriz.
     * @return String com os passos da condensação em formato HTML.
     */
    public static String condensarMatriz(double[][] matriz, int dimensao) {
        String HTML = "";
        int pivotAtual = 0, passo = 1;

        while (pivotAtual < dimensao) {
            if (Math.abs(matriz[pivotAtual][pivotAtual]) < constante) {
                matriz[pivotAtual][pivotAtual] = arredondar(matriz[pivotAtual][pivotAtual]);
                pivotAtual++;
            }

            if (pivotAtual < dimensao) {
                if (matriz[pivotAtual][pivotAtual] != 1) {
                    HTML += CodificadorHTML.subtitulo(passo + "º passo:");
                    HTML += normalizarLinha(matriz, pivotAtual, dimensao);
                    HTML += CodificadorHTML.mostrarMatrizDouble(matriz);
                    passo++;
                }

                HTML += CodificadorHTML.subtitulo(passo + "º passo:");
                for (int linha = 0; linha < dimensao; linha++) {
                    if (linha != pivotAtual && matriz[linha][pivotAtual] != 0) {
                        HTML += subtrairLinha(matriz, pivotAtual, linha, dimensao);
                    }
                }
                HTML += CodificadorHTML.mostrarMatrizDouble(matriz);
                passo++;
            }
            pivotAtual++;
        }
        return HTML;
    }

    /**
     * (P8) Este método tem como função dividir toda a linha de uma matriz, pelo
     * valor do pivot, de forma a transformar o mesmo em 1.
     *
     * @param matriz Matriz de doubles.
     * @param pivotAtual Pivot.
     * @param dimensao Tamanho da matriz.
     * @return String com o cálculo efetuado.
     */
    private static String normalizarLinha(double[][] matriz, int pivotAtual, int dimensao) {
        double normalizador = matriz[pivotAtual][pivotAtual];

        for (int coluna = 0; coluna < dimensao; coluna++) {
            if (matriz[pivotAtual][coluna] != 0) {
                matriz[pivotAtual][coluna] /= normalizador;
            }
        }

        return CodificadorHTML.normalizarLinha(pivotAtual, normalizador);
    }

    /**
     *
     * @param matriz Matriz de doubles.
     * @param pivotAtual Pivot.
     * @param linha Linha a ser subtraida.
     * @param dimensao Tamanho.
     * @return String com o cálculo efetuado.
     */
    private static String subtrairLinha(double[][] matriz, int pivotAtual, int linha, int dimensao) {
        double anulador = matriz[linha][pivotAtual];

        for (int coluna = pivotAtual; coluna < dimensao; coluna++) {
            matriz[linha][coluna] -= matriz[pivotAtual][coluna] * anulador;
        }

        return CodificadorHTML.subtrairLinha(pivotAtual, linha, anulador);
    }

    /**
     * (P8) Este método tem como função determinar a coluna onde se encontra a
     * solução do sistema.
     *
     * @param matriz Matriz de doubles.
     * @param dimensao Tamanho da matriz.
     * @return Coluna onde se encontra a solução do sistema.
     */
    private static int determinarColunaSolucao(double[][] matriz, int dimensao) {
        for (int posDiagonal = 0; posDiagonal < dimensao; posDiagonal++) {
            if (matriz[posDiagonal][posDiagonal] == 0) {
                return posDiagonal;
            }
        }

        return -1;
    }

    /**
     * (P8) Este método tem como função efectuar arredondamentos a duas casas
     * decimais.
     *
     * @param valor Valor a arredondar.
     * @return Valor arredondado a duas casa decimais.
     */
    private static double arredondar(double valor) {
        return (double) Math.round(valor * 100) / 100;
    }

    /**
     * (P8) Este método tem como função extrair o vetor próprio de dentro de uma
     * matriz condensada.
     *
     * @param matriz Matriz de doubles.
     * @param dimensao Tamanho da matriz.
     * @return Vetor com o vetor próprio.
     */
    public static double[] extrairVetorProprio(double[][] matriz, int dimensao) {
        double[] vetorProprio = new double[dimensao];
        int solucao = determinarColunaSolucao(matriz, dimensao);

        if (solucao != -1) {
            vetorProprio[solucao] = 1.00;
            for (int linha = 0; linha < dimensao; linha++) {
                if (linha != solucao) {
                    vetorProprio[linha] -= arredondar(matriz[linha][solucao]);
                }
            }
        }

        return vetorProprio;
    }

    /**
     * (P2, P4) Este método tem como função calcular a soma de cada uma das
     * linhas de uma matriz.
     *
     * @param matrizBase Matriz de inteiros.
     * @param dimensao Tamanho da matriz.
     * @return Vector de Inteiros, com a soma de cada linha.
     */
    public static int[] calcSomaLinhas(int[][] matrizBase, int dimensao) {
        int[] somaLinhas = new int[dimensao];

        for (int linha = 0; linha < dimensao; linha++) {
            for (int coluna = 0; coluna < dimensao; coluna++) {
                somaLinhas[linha] += matrizBase[linha][coluna];
            }
        }

        return somaLinhas;
    }

    /**
     * (P2, P4, P7, P8) Este método tem como função determinar a maior medida de
     * entre todas.
     *
     * @param medidas Vetor com as medidas de cada nó
     * @param dimensao Tamanho da matriz.
     * @return Inteiro com o maior valor.
     */
    public static double calcMaiorValor(double[] medidas, int dimensao) {
        double maiorValor = medidas[0];

        for (int i = 1; i < dimensao; i++) {
            if (medidas[i] > maiorValor) {
                maiorValor = medidas[i];
            }
        }

        return maiorValor;
    }

    /**
     * (P2, P4, P7, P8) Este método tem como função determinar quais são os Nós,
     * cujo valor é igual ao passado por parametro.
     *
     * @param designacoesNos Vetor com a designação de cada Nó.
     * @param medidas Vetor com as medidas de cada Nó.
     * @param maiorMedida Maior medida.
     * @param dimensao Tamanho da matriz.
     * @return Vetor com os Nós de valor igual ao passado por parametro.
     */
    public static String[] criarDesignacaoNosMaior(String[] designacoesNos, double[] medidas, double maiorMedida, int dimensao) {
        String[] designacoesNosValor = new String[contarNosComAMedida(medidas, maiorMedida, dimensao)];
        int nosGuardados = 0;
        for (int i = 0; i < dimensao; i++) {
            if (medidas[i] == maiorMedida) {
                designacoesNosValor[nosGuardados] = designacoesNos[i];
                nosGuardados++;
            }
        }

        return designacoesNosValor;
    }

    /**
     * (P2, P4, P7, P8) Este método tem como função determinar quantos são os
     * Nós, cujo valor é igual ao passado por parametro.
     *
     * @param medidas Vetor com as medidas de cada Nó.
     * @param maiorMedida Maior medida.
     * @param dimensao Tamanho da matriz.
     * @return Inteiro com o número de nós com a medida dada.
     */
    private static int contarNosComAMedida(double[] medidas, double maiorMedida, int dimensao) {
        int contNosValor = 0;

        for (int i = 0; i < dimensao; i++) {
            if (medidas[i] == maiorMedida) {
                contNosValor++;
            }
        }

        return contNosValor;
    }
}
