package lapr1;

/**
 * Esta classe tem como função analisar e validar possivéis exceções no programa,
 * de forma a que o mesmo corra sem quaisquer problemas.
 * 
 * @author DAB03
 */
public class Validar {

    /**
     * Este método tem como função validar o comprimento de um vetor de Strings.
     * 
     * @param args Vetor de Strings a ser medido.
     * @return Booleano com o resultado.
     */
    public static boolean numParametros(String[] args) {
        return args.length == 2;
    }

    /**
     * Este método tem como função verificar se o conteudo na primeira e segunda
     * posição do vetor é diferente ou não.
     * 
     * @param args Vetor de Strings com o conteudo a ser comparado.
     * @return Booleano com o resultado.
     */
    public static boolean nomeParametros(String[] args) {
        return !args[0].equals(args[1]);
    }

    /**
     * Este método tem como função validar a extensão do ficheiro carregado.
     * 
     * @param arg Nome do ficheiro carregado.
     * @return Booleano com o resultado.
     */
    public static boolean tipoFicheiro(String arg) {
        return arg.matches("(?i).+.htm?l");
    }

    /**
     * Este método tem como função validar o conteudo lido de um ficheiro HTML,
     * de forma a verificar se o mesmo possui uma estrutura lógica e contém a
     * informação pretendida.
     * 
     * @param conteudo Conteúdo lido de um ficheiro HTML.
     * @return Booleano com o resultado.
     */
    public static boolean conteudoFicheiro(String conteudo) {
        return conteudo.matches("(?i)<!Doctype html.*><html.*><head>.*<title>.*</title>.*</head><body>.*<table>.*</table>.*</body></html>");
    }

    /**
     * Este método tem como função verificar se a tabela extraida do ficheiro HTML
     * é ou não uma tabela válida a nivel de estrutura.
     * 
     * @param tabela Tabela em HTML.
     * @return Booleano com o resultado.
     */
    public static boolean tabela(String tabela) {
        int aberturaTR = contarOcorrencias(tabela, "<tr>");
        int fechoTR = contarOcorrencias(tabela, "</tr>");
        
        if (aberturaTR != fechoTR || aberturaTR == 0 || fechoTR == 0) {
            return false;
        }

        String[] linhasTabela = tabela.split("(?i)</tr>");

        for (int linha = 0; linha < linhasTabela.length; linha++) {
            int aberturaTD = contarOcorrencias(linhasTabela[linha], "<td>");
            int fechoTD = contarOcorrencias(linhasTabela[linha], "</td>");

            if (aberturaTD != fechoTD || aberturaTD == 0 || fechoTD == 0 || aberturaTD != aberturaTR) {
                return false;
            }
        }

        return true;
    }

    /**
     * Este método tem como função contar o número de vezes que uma dada tag HTML
     * surge num pedaço de texto HTML.
     * 
     * @param textoHTML Pedaço de texto HTML.
     * @param tag Tag a ser procurada.
     * @return Inteiro com o número de ocorrências.
     */
    private static int contarOcorrencias(String textoHTML, String tag) {
        int     comprimentoTag = tag.length(),
                comprimentoInicial = textoHTML.length(),
                comprimentoFinal = textoHTML.replaceAll("(?i)"+tag, "").length();
        
        return (comprimentoInicial - comprimentoFinal) / comprimentoTag;
    }

    /**
     * Este método tem como função verificar se uma matriz é simétrica em relação
     * à sua diagonal principal.
     * 
     * @param matriz Matriz de inteiros.
     * @param dimensao Tamanho da matriz.
     * @return Booleano com o resultado.
     */
    public static boolean simetriaMatriz(int[][] matriz, int dimensao) {
        for (int linha = 0; linha < dimensao-1; linha++) {
            for (int coluna = linha+1; coluna < dimensao; coluna++) {
                if (matriz[linha][coluna] != matriz[coluna][linha]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Este método tem como função verificar se a matriz é, ou não, uma matriz nula.
     * 
     * @param matriz Matriz de inteiros.
     * @param dimensao Tamanho da matriz.
     * @return Booleano com o resutado.
     */
    public static boolean nulidadeMatriz(int[][] matriz, int dimensao) {
        for (int linha = 0; linha < dimensao-1; linha++) {
            for (int coluna = linha+1; coluna < dimensao; coluna++) {
                if (matriz[linha][coluna] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Este método tem como função determinar a conectividade de uma matriz, 
     * descartando matrizes não conexas ou que formem ilhéus.
     *
     * @param matriz Matriz de inteiros.
     * @param dimensao Tamanho da matriz.
     * @return Booleano com o resultado.
     */
    public static boolean conectividadeMatriz(int[][] matriz, int dimensao) {
        for (int linha = 0; linha < dimensao-1; linha++) {
            for (int coluna = linha+1; coluna < dimensao; coluna++) {
                if (linha != coluna && matriz[linha][coluna] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

}
