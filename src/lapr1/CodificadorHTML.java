package lapr1;

/**
 * Esta classe tem a funcionalidade de codificar para HTML todos os cálculos
 * intermédios realizados para a conclusão de cada ponto.
 *
 * @author DAB03
 */
public class CodificadorHTML {

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * todo o cabeçalho para aplicar a um ficheiro. Contém a versão HTML, o
     * título da página e os estilos da mesma.
     *
     * @return String com a versão HTML e o cabeçalho.
     */
    public static String criarCabecalho() {
        return String.format(""
                + "<!DOCTYPE html>%n"
                + "<html>%n"
                + "<head>%n"
                + "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />%n"
                + "\t<title>Resultados dos Cálculos</title>%n"
                + "\t<style>%n"
                + "\t\th4 { margin: 0; padding: 0; }%n"
                + "\t\tbody { text-align: center; }%n"
                + "\t\ttable { padding: 0px; margin: 0px; margin-left: auto; margin-right: auto; }%n"
                + "\t\ttable.somalinhas tr { border: 1px solid; }%n"
                + "\t\ttable.matriz td { border: 1px solid; width: 20px; height: 20px; text-align: center; }%n"
                + "\t</style>%n"
                + "</head>%n");
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, com a
     * abertura do corpo.
     *
     * @return String com a tag de abertura do corpo.
     */
    public static String inicializarCorpo() {
        return "<body>%n";
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, com o
     * fecho do corpo.
     *
     * @return String com a tag de fecho do corpo e HTML.
     */
    public static String terminarCorpo() {
        return "</body>%n</html>%n";
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, com a
     * imagem do DEI-ISEP.
     *
     * @return String com a tag de imagem que contém o imagem do DEI-ISEP.
     */
    public static String imagemISEP() {
        return "\t<img src=\"http://www.dei.isep.ipp.pt/images/topo_index.png\" alt=\"Logotipo ISEP\">%n";
    }

    /**
     * Este método tem como função retornar uma String contendo o título,
     * passado por parametro, em formato HTML. Este método adiciona também, uma
     * linha abaixo e acima do título.
     *
     * @param titulo Descrição sobre o tipo de cálculo feito.
     * @return String com o titulo.
     */
    public static String titulo(String titulo) {
        return String.format(""
                + "\t<hr>%n"
                + "\t<h2>%s</h2>%n"
                + "\t<hr>%n", titulo);
    }

    /**
     * Este método tem como função retornar uma String contendo o subtitulo,
     * passado por parametro, em formato HTML.
     *
     * @param subtitulo Descrição do passo efectuado.
     * @return String com um subtitulo.
     */
    public static String subtitulo(String subtitulo) {
        return String.format("\t<h3>%s</h3>%n", subtitulo);
    }

    /**
     * Este método tem como função retornar uma String, com o nº de ligações
     * máximas por Nó, em formato HTML.
     *
     * @param maxLigacoes Número máximo de nós, a que um nó se pode ligar.
     * @return String com o nº máximo de ligações por Nó.
     */
    public static String apresentarMaxLigacoes(int maxLigacoes) {
        return String.format("\t<h3>Máximo de Ligações por Nó: %d</h3>%n", maxLigacoes);
    }

    /**
     * Este método tem como função retornar uma String, com um valor decimal e
     * respectiva descrição, em formato HTML.
     *
     * @param texto Texto de apresentação sobre o resultado.
     * @param resultado Valor decimal obtido no cálculo.
     * @return String com a apresentação de um valor decimal.
     */
    public static String apresentarValorDecimal(String texto, double resultado) {
        return String.format("\t<h3>%s: %.2f</h3>%n", texto, resultado);
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * numa tabela a matriz que lhe é passada por parametro, correctamente
     * formatada.
     *
     * @param matriz Matriz de inteiros.
     * @return String com uma tabela contendo uma matriz.
     */
    public static String mostrarMatriz(int[][] matriz) {
        String str = "\t\t<table class=\"matriz\">%n";

        for (int linha = 0; linha < matriz.length; linha++) {
            str += "\t\t\t<tr>%n";
            for (int coluna = 0; coluna < matriz.length; coluna++) {
                str += String.format("\t\t\t\t<td>%d</td>%n", matriz[linha][coluna]);
            }
            str += "\t\t\t</tr>%n";
        }
        str += "\t\t</table>%n";

        return str;
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * numa tabela a matriz que lhe é passada por parametro, correctamente
     * formatada.
     *
     * @param matriz Matriz de doubles.
     * @return String com uma tabela contendo uma matriz.
     */
    public static String mostrarMatrizDouble(double[][] matriz) {
        String str = "\t\t<table class=\"matriz\">%n";

        for (int linha = 0; linha < matriz.length; linha++) {
            str += "\t\t\t<tr>%n";
            for (int coluna = 0; coluna < matriz.length; coluna++) {
                str += String.format("\t\t\t\t<td>%.2f</td>%n", matriz[linha][coluna]);
            }
            str += "\t\t\t</tr>%n";
        }
        str += "\t\t</table>%n";

        return str;
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * o cálculo de normalização de uma linha da matriz.
     *
     * @param linha Número da linha que é normalizada.
     * @param normalizador Valor utilizado para normalizar a linha.
     * @return String com o cálculo da normalização.
     */
    public static String normalizarLinha(int linha, double normalizador) {
        return String.format(""
                + "\t\t<h4>"
                + "L<sub>%d</sub> = "
                + "%s <sup>1</sup>"
                + "&frasl;"
                + "<sub>%.2f</sub>"
                + "L<sub>%d</sub>"
                + "</h4>%n", linha + 1, normalizador < 0.00 ? "-" : "", Math.abs(normalizador), linha + 1);
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * o cálculo da normalização de uma linha da matriz.
     *
     * @param linhaAnuladora Linha usada para anular.
     * @param linhaAnulada Linha que é anulada.
     * @param anulador Valor pelo qual a linha que anula, é multiplicada.
     * @return String com o cálculo da subtração.
     */
    public static String subtrairLinha(int linhaAnuladora, int linhaAnulada, double anulador) {
        String anul = String.format("%.2f", Math.abs(anulador));

        return String.format(""
                + "\t<h4>"
                + "L<sub>%d</sub> = "
                + "L<sub>%d</sub> "
                + "%sL<sub>%d</sub>"
                + "</h4>%n", linhaAnulada + 1, linhaAnulada + 1, anulador < 0.00 ? "+ " + anul : "- " + anul, linhaAnuladora + 1);
    }

    /**
     * Este método tem como função retornar uma String, contendo o cálculo da
     * soma dos elementos de N linhas de uma matriz e respetivos resultados.
     *
     * @param matriz Matriz de inteiros.
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param somaLinhas Vetor com o resultado da soma de cada uma das linhas da
     * matriz.
     * @return String com uma tabela contendo uma matriz e o resultado da soma
     * das suas linhas.
     */
    public static String mostrarSomaLinhas(int[][] matriz, String[] designacoesNos, int[] somaLinhas) {
        String str = "\t\t<table class=\"somalinhas\">%n";
        for (int linha = 0; linha < matriz.length; linha++) {
            str += String.format(""
                    + "\t\t\t<tr>%n"
                    + "\t\t\t\t<td>%s</td>%n", designacoesNos[linha]);
            for (int coluna = 0; coluna < matriz.length; coluna++) {
                str += String.format("\t\t\t\t<td>%s</td>%n", matriz[linha][coluna] != 0 ? "<strong>" + matriz[linha][coluna] + "</strong>" : matriz[linha][coluna]);
                str += String.format("\t\t\t\t<td>%s</td>%n", coluna != matriz.length - 1 ? "+" : "=");
            }
            str += String.format(""
                    + "\t\t\t\t<td>%d</td>%n"
                    + "\t\t\t</tr>%n", somaLinhas[linha]);
        }
        str += "\t\t</table>%n";

        return str;
    }

    /**
     * Este método tem como função retornar uma String, em formato HTML,
     * contendo uma tabela com o nome dos Nós e respectivas medidas.
     *
     * @param designacoesNos Vetor com a designação de cada nó.
     * @param medidas Vetor com os resultados obtidos no cálculo das
     * centralidades.
     * @return String com uma tabela contendo o nome dos nós e respectivas
     * medidas.
     */
    public static String mostrarMedidas(String[] designacoesNos, double[] medidas) {
        String str = "\t\t<table>%n"
                + "\t\t\t<tr>%n";

        for (int i = 0; i < medidas.length; i++) {
            str += String.format("\t\t\t\t<td>%s</td>%n", designacoesNos[i]);
        }
        str += "\t\t\t</tr>%n\t\t\t<tr>%n";
        for (int i = 0; i < medidas.length; i++) {
            str += String.format("\t\t\t\t<td>%.2f</td>%n", medidas[i]);
        }

        str += "\t\t\t</tr>%n"
                + "\t\t</table>%n";

        return str;
    }

    /**
     * Este método tem como função retornar uma String em formato HTML, contendo
     * uma matriz elevada a N, correctamente formatada, sendo que a matriz e o N
     * lhe são passados por parametro.
     *
     * @param matriz Matriz de inteiros.
     * @param potencia Valor a que a matriz foi elevada.
     * @return String com uma tabela contendo a matriz elevada a N.
     */
    public static String mostrarMultiMatriz(int[][] matriz, int potencia) {
        return String.format("\t\t<h1>A<sup>%d</sup></h1>%n%s", potencia, mostrarMatriz(matriz));
    }

}
