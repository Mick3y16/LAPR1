package lapr1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Formatter;

/**
 * Esta classe tem como função dar uso a todos os métodos do programa, chamando
 * os mesmos de forma sequencial.
 * 
 * @author DAB03
 */
public class Principal {

    /**
     * String que irá conter todos os resultados dos cálculos a serem registados
     * num ficheiro HTML.
     */
    private static String HTML = CodificadorHTML.criarCabecalho()
                                +CodificadorHTML.inicializarCorpo()
                                +CodificadorHTML.imagemISEP();

    /**
     * Este método tem como função permitir a execução do programa.
     * 
     * @param args Vetor de Strings com os argumentos recebidos por parametro, quando o programa é corrido na consola.
     */
    public static void main(String[] args) {
        String nomeFicheiroLeitura, nomeFicheiroEscrita;
        
        if (args.length != 0) { 
            if (!Validar.numParametros(args)) {
                System.out.println("Deve de introduzir exactamente 2 parametros, o ficheiro de leitura e o ficheiro de escrita.");
                System.exit(0);
            } else if (!Validar.nomeParametros(args)) {
                System.out.println("O nome dos ficheiros de leitura e escrita não podem ser iguais.");
                System.exit(0);
            } else if (!Validar.tipoFicheiro(args[0])) {
                System.out.println("O ficheiro de leitura não está em formato HTML.");
                System.exit(0);
            } else if (!Validar.tipoFicheiro(args[1])) {
                System.out.println("O ficheiro de escrita não está em formato HTML.");
                System.exit(0);
            }

            nomeFicheiroLeitura = args[0];
            nomeFicheiroEscrita = args[1];
        } else {
            nomeFicheiroLeitura = "dados_exemplo2.html";
            nomeFicheiroEscrita = "a.html";
        }

        String conteudo = DescodificadorHTML.lerHTML(nomeFicheiroLeitura);

        if (!Validar.conteudoFicheiro(conteudo)) {
            System.out.println("O ficheiro de leitura não é um ficheiro HTML válido.");
            System.exit(0);
        }

        String titulo = DescodificadorHTML.extrairdEntreTags(conteudo, "</?title>");
        String tabela = DescodificadorHTML.extrairdEntreTags(conteudo, "</?table>");

        if (!Validar.tabela(tabela)) {
            System.out.println("A tabela do ficheiro HTML não é válida.");
            System.exit(0);
        }

        int[][] adjacencias = DescodificadorHTML.criarAdjacencias(tabela);
        String[] designacoesNos = DescodificadorHTML.criarDesignacoesNos(tabela);
        int dimensao = adjacencias.length, maxLigacoes = adjacencias.length-1;
        int[][] matrizDistMcurtas = new int[dimensao][dimensao], matrizNCaminhosMCurtos = new int[dimensao][dimensao]; 

        if (!Validar.simetriaMatriz(adjacencias, dimensao)) {
            System.out.println("A matriz extraida da tabela não é simétrica.");
            System.exit(0);
        } else if (Validar.nulidadeMatriz(adjacencias, dimensao)) {
            System.out.println("A matriz extraida da tabela não pode ser nula.");
            System.exit(0);
        }

        Janela.criar(titulo);
        gerarJanelaDados(nomeFicheiroLeitura);
        gerarJanelaGrafo(adjacencias, designacoesNos);
        gerarJanelaCentralidadeGraueResultados(adjacencias, designacoesNos, dimensao, maxLigacoes);
        gerarResultadosMatrizElevada(adjacencias, matrizDistMcurtas, matrizNCaminhosMCurtos, dimensao);
        gerarResultadosMatriz(matrizDistMcurtas, "Matriz das Distâncias mais curtas");
        gerarJanelaCentralidadeProximidadeeResultados(adjacencias, matrizDistMcurtas, designacoesNos, dimensao, maxLigacoes);
        gerarResultadosMatriz(matrizNCaminhosMCurtos, "Matriz do Nº de Caminhos mais curtos");
        gerarJanelaCentralidadeIntermediacaoeResultados(adjacencias, matrizDistMcurtas, matrizNCaminhosMCurtos, designacoesNos, dimensao);
        gerarJanelaCentralidadeValoresProprios(adjacencias, designacoesNos, dimensao);
        criarResultadosHTML(nomeFicheiroEscrita);
    }

    /**
     * Este método tem como função chamar o método que calcula a matriz das
     * distâncias mais curtas e registo de resultados.
     * 
     * @param adjacencias Matriz de adjacências.
     * @param matrizDistMcurtas Matriz das distâncias mais curtas.
     * @param matrizNCaminhosMCurtos Matriz do número de caminhos mais curtos.
     * @param dimensao Tamanho das matrize.
     */
    private static void gerarResultadosMatrizElevada(int[][] adjacencias, int[][] matrizDistMcurtas, int[][] matrizNCaminhosMCurtos, int dimensao) {
        HTML += CodificadorHTML.titulo("Cálculos de Potênciação da matriz adjacências");
        HTML += CodificadorHTML.subtitulo("Nota: Estes cálculos permitem determinar a matriz das distâncias mais curtas e a matriz do nº de caminhos mais curtos.");
        HTML += OperacoesMatriciais.calcMatrizDistMcurtasEmatrizNCaminhosMCurtos(adjacencias, matrizDistMcurtas, matrizNCaminhosMCurtos, dimensao);
    }

    /**
     * Este método tem como função adicionar uma matriz aos resultados.
     * 
     * @param matriz Matriz de inteiros.
     * @param titulo Nome da matriz.
     */
    private static void gerarResultadosMatriz(int[][] matriz, String titulo) {
        HTML += CodificadorHTML.titulo(titulo);
        HTML += CodificadorHTML.mostrarMatriz(matriz);
    }

    /**
     * Este método tem como função chamar o método de criação da janela de dados,
     * relativo ao ficheiro HTML carregado.
     * 
     * @param nomeFicheiroLer Nome do ficheiro de leitura.
     */
    private static void gerarJanelaDados(String nomeFicheiroLer) {
        Janela.adicionarDados(nomeFicheiroLer);
    }

    /**
     * Este método tem como função chamar os métodos de criação da janela do grafo
     * e registo de resultados, relativos ao conteudo extraído.
     * 
     * @param adjacencias Matriz de adjacências.
     * @param designacoesNos Vetor com a designação de cada nó.
     */
    private static void gerarJanelaGrafo(int[][] adjacencias, String[] designacoesNos) {
        gerarResultadosMatriz(adjacencias, "Matriz de Adjências");
        Janela.adicionarGrafo(adjacencias, designacoesNos);
    }

    /**
     * Este método tem como função chamar os métodos de cálculo, criação de janela
     * da centralidade de grau e registo de resultados, relativos aos cálculos.
     * 
     * @param adjacencias Matriz das adjacências.
     * @param designacoesNos Vetor com a desiginação de cada nó.
     * @param dimensao Tamanho da matriz.
     * @param maxLigacoes Número máximo de ligações possíveis na matriz.
     */
    private static void gerarJanelaCentralidadeGraueResultados(int[][] adjacencias, String[] designacoesNos, int dimensao, int maxLigacoes) {
        int[] conectividadeNos = OperacoesMatriciais.calcSomaLinhas(adjacencias, dimensao);
        double[] medidasDegree = OperacoesMatriciais.calcCentralidadeGrau(conectividadeNos, dimensao, maxLigacoes);
        double maiorMedidaDegree = OperacoesMatriciais.calcMaiorValor(medidasDegree, dimensao);
        String[] designacoesNosMaiorDegree = OperacoesMatriciais.criarDesignacaoNosMaior(designacoesNos, medidasDegree, maiorMedidaDegree, dimensao);

        Janela.adicionarGrafoDegree(adjacencias, designacoesNos, designacoesNosMaiorDegree, maiorMedidaDegree);

        HTML += CodificadorHTML.titulo("Cálculos da Centralidade de Grau");
        HTML += CodificadorHTML.subtitulo("Cálculo da soma de cada uma das linhas da matriz de adjacências");
        HTML += CodificadorHTML.mostrarSomaLinhas(adjacencias, designacoesNos, conectividadeNos);
        HTML += CodificadorHTML.apresentarMaxLigacoes(maxLigacoes);
        HTML += CodificadorHTML.subtitulo("Cálculo da divisão da soma de cada uma das linhas da matriz pelo número máximo de ligações");
        HTML += CodificadorHTML.mostrarMedidas(designacoesNos, medidasDegree);
        HTML += CodificadorHTML.apresentarValorDecimal("Maior Centralidade de Grau", maiorMedidaDegree);
    }

    /**
     * Este método tem como função chamar os métodos de cálculo, criação de janela
     * da centralidade de proximidade e registo de resultados, relativos ao cálculos.
     * 
     * @param adjacencias Matriz das adjacências.
     * @param matrizDistMcurtas Matriz das distâncias mais curtas.
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param dimensao Tamanho das matrizes.
     * @param maxLigacoes Número máximo de ligações possíveis na matriz.
     */
    private static void gerarJanelaCentralidadeProximidadeeResultados(int[][] adjacencias, int[][] matrizDistMcurtas, String[] designacoesNos, int dimensao, int maxLigacoes) {
        HTML += CodificadorHTML.titulo("Cálculos da Centralidade de Proximidade");
        if (Validar.conectividadeMatriz(matrizDistMcurtas, dimensao)) {
            int[] distanciaNos = OperacoesMatriciais.calcSomaLinhas(matrizDistMcurtas, dimensao);
            double[] medidasCloseness = OperacoesMatriciais.calcCentralidadeProximidade(distanciaNos, dimensao, maxLigacoes);
            double maiorMedidaCloseness = OperacoesMatriciais.calcMaiorValor(medidasCloseness, dimensao);
            String[] designacoesNosMaiorCloseness = OperacoesMatriciais.criarDesignacaoNosMaior(designacoesNos, medidasCloseness, maiorMedidaCloseness, dimensao);

            Janela.adicionarGrafoCloseness(adjacencias, designacoesNos, designacoesNosMaiorCloseness, maiorMedidaCloseness);

            HTML += CodificadorHTML.subtitulo("Calculo da soma de cada uma das linhas da matriz das distâncias mais curtas");
            HTML += CodificadorHTML.mostrarSomaLinhas(matrizDistMcurtas, designacoesNos, distanciaNos);
            HTML += CodificadorHTML.subtitulo("Cálculo da divisão do número máximo de ligaçõesda pela soma de cada uma das linhas da matriz");
            HTML += CodificadorHTML.mostrarMedidas(designacoesNos, medidasCloseness);
            HTML += CodificadorHTML.apresentarValorDecimal("Maior Centralidade de Proximidade", maiorMedidaCloseness);
        } else {
            HTML += CodificadorHTML.subtitulo("A Centralidade de grau não foi calculada, pois o grafo não é conexo, contém ilhéus.");
        }
    }

    /**
     * Este método tem como função chamar os métodos de cálculo, criação de janela
     * da centralidade de intermediação e registo de resultados, relativos aos cálculos.
     * 
     * @param adjacencias Matriz das adjacências.
     * @param matrizDistMCurtas Matriz das distâncias mais curtas.
     * @param matrizNCaminhosMCurtos Matriz do número de caminhos mais curtos.
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param dimensao Tamanho das matrizes.
     */
    private static void gerarJanelaCentralidadeIntermediacaoeResultados(int[][] adjacencias, int[][] matrizDistMCurtas, int[][] matrizNCaminhosMCurtos, String[] designacoesNos, int dimensao) {
        double[] medidasBetweeness = OperacoesMatriciais.calcCentralidadeIntermediacao(matrizDistMCurtas, matrizNCaminhosMCurtos, dimensao);
        double maiorMedidaBetweeness = OperacoesMatriciais.calcMaiorValor(medidasBetweeness, dimensao);
        String[] designacoesNosMaiorBetweeness = OperacoesMatriciais.criarDesignacaoNosMaior(designacoesNos, medidasBetweeness, maiorMedidaBetweeness, dimensao);

        Janela.adicionarGrafoBetweeness(adjacencias, designacoesNos, designacoesNosMaiorBetweeness, maiorMedidaBetweeness);

        HTML += CodificadorHTML.titulo("Cálculos da Centralidade de Intermediação");
        HTML += CodificadorHTML.subtitulo("Cálculo do número de vezes que um Nó passa noutro para chegar a um terceiro");
        HTML += CodificadorHTML.mostrarMedidas(designacoesNos, medidasBetweeness);
        HTML += CodificadorHTML.apresentarValorDecimal("Maior Centralidade de Intermediação", maiorMedidaBetweeness);
    }

    /**
     * Este método tem como função chamar os métodos de cálculo, criação de janela
     * da centralidade de valores próprios e registo de resultados, relativos aos 
     * cálculos.
     * 
     * @param adjacencias Matriz das adjacências.
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param dimensao Tamanho da matriz.
     */
    private static void gerarJanelaCentralidadeValoresProprios(int[][] adjacencias, String[] designacoesNos, int dimensao) {
        double[][] adjacenciasDouble = new double[dimensao][dimensao];
        HTML += OperacoesMatriciais.calcCentralidadeVetorProprio(adjacenciasDouble, adjacencias, designacoesNos, dimensao);

        double[] medidasDegreeEigenValue = OperacoesMatriciais.extrairVetorProprio(adjacenciasDouble, dimensao);
        double maiorMedidaDegreeEigenValue = OperacoesMatriciais.calcMaiorValor(medidasDegreeEigenValue, dimensao);
        String[] designacoesNosMaiorDegreeEigenValue = OperacoesMatriciais.criarDesignacaoNosMaior(designacoesNos, medidasDegreeEigenValue, maiorMedidaDegreeEigenValue, dimensao);

        Janela.adicionarGrafoDegreeEigenValue(adjacencias, designacoesNos, designacoesNosMaiorDegreeEigenValue, maiorMedidaDegreeEigenValue);

        HTML += CodificadorHTML.subtitulo("Matriz A-λI Condensada");
        HTML += CodificadorHTML.mostrarMatrizDouble(adjacenciasDouble);
        HTML += CodificadorHTML.subtitulo("Vetor Próprio");
        HTML += CodificadorHTML.mostrarMedidas(designacoesNos, medidasDegreeEigenValue);
        HTML += CodificadorHTML.apresentarValorDecimal("Maior Centralidade de Vetor Próprio", maiorMedidaDegreeEigenValue);
    }

    /**
     * Este método tem como função escrever os resultados registados num ficheiro HTML.
     * 
     * @param nomeFicheiroCriado Nome do ficheiro dos resultados.
     */
    private static void criarResultadosHTML(String nomeFicheiroCriado) {
        HTML += CodificadorHTML.terminarCorpo();

        try (Formatter escrever = new Formatter(new File(nomeFicheiroCriado), "UTF-8")) {
                escrever.format(HTML);
                escrever.close();
                Janela.adicionarResultados(nomeFicheiroCriado);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Não foi possível guardar o ficheiro, a janela de resultados não irá correr.");
        }
    }

}
