package lapr1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Esta classe contém métodos para lidar com a leitura e processamento de
 * ficheiros HTML, a classe é capaz e carregar e ler ficheiros na sua
 * totalidade, extrair informações de diferentes partes do ficheiro carregado,
 * gerando depois um determinado output e limpar todo o código HTML de uma dada
 * String.
 *
 * @author DAB03
 */
public class DescodificadorHTML {

    /**
     * Este método tem como função ler e guardar o conteúdo de um ficheiro
     * HTML numa String.
     *
     * @param nomeFicheiro Ficheiro a ser lido.
     * @return String com o conteudo.
     */
    public static String lerHTML(String nomeFicheiro) {
        String conteudo = "";

        try (Scanner ler = new Scanner(new File(nomeFicheiro), "UTF-8")) {
            while (ler.hasNextLine()) {
                conteudo += ler.nextLine().trim();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Ficheiro não encontrado!");
            System.exit(0);
        }

        return conteudo;
    }

    /**
     * Este método tem como função criar a Matriz de Adjacências através do
     * conteúdo lido do ficheiro HTML.
     *
     * @param tabela String HTML com uma tabela.
     * @return Matriz de inteiros com as ligações entre cada Nó.
     */
    public static int[][] criarAdjacencias(String tabela) {
        String[] vecLinha = tabela.split("(?i)</tr>");

        for (int i = 0; i < vecLinha.length; i++) {
            vecLinha[i] = removerTagsHTML(vecLinha[i]);
        }

        int dimensao = vecLinha.length - 1;
        int[][] adjacencias = new int[dimensao][dimensao];

        for (int linhaM = 0; linhaM < dimensao; linhaM++) {
            String[] vecDados = vecLinha[linhaM + 1].split("[ ]+");

            for (int colunaM = 0; colunaM < dimensao; colunaM++) {
                if (linhaM == colunaM) {
                    adjacencias[linhaM][colunaM] = 0;
                } else {
                    adjacencias[linhaM][colunaM] = Integer.parseInt(vecDados[colunaM + 1].replaceAll("[^01]+", ""));
                }
            }
        }

        return adjacencias;
    }

    /**
     * Este método tem como função criar o vector dos Nós através do conteudo
     * lido do ficheiro HTML.
     *
     * @param tabela String HTML com uma tabela.
     * @return Vetor com a designação de cada Nó.
     */
    public static String[] criarDesignacoesNos(String tabela) {
        String nomes = extrairdEntreTags(tabela, "</?tr>");

        nomes = removerTagsHTML(nomes);
        String[] vectemp = nomes.split("[ ]+");
        String[] designacoesNos = new String[vectemp.length - 1];

        for (int coluna = 0; coluna < designacoesNos.length; coluna++) {
            designacoesNos[coluna] = vectemp[coluna + 1];
        }

        return designacoesNos;
    }

    /**
     * Este método tem como função extrair todo o conteudo de entre duas Tags
     * HTML.
     *
     * @param conteudo String com um pedaço de texto em HTML.
     * @param tag Tag HTML para a extração.
     * @return String com o conteúdo entre a tag HTML.
     */
    public static String extrairdEntreTags(String conteudo, String tag) {
        String[] vecTemp = conteudo.split("(?i)" + tag);

        return vecTemp[1];
    }

    /**
     * Este método tem como função limpar todo o codigo HTML a uma String.
     *
     * @param html String a ser limpa.
     * @return String sem código HTML.
     */
    private static String removerTagsHTML(String html) {
        return html.replaceAll("<[^>]+>", " ").trim();
    }

}
