package ap1_parcial;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {
    private final List<String> linhas;  // guarda todas as linhas do arquivo
    private final List<Token> tokens;   // lista onde vou armazenar os tokens encontrados
    private int linhaAtual;             // linha que está sendo processada agora
    private int posicao;                // posição atual dentro da linha

    // construtor recebe as linhas do arquivo já tratadas
    public AnalisadorLexico(List<String> linhas) {
        this.linhas = linhas;
        this.tokens = new ArrayList<>();
        this.linhaAtual = 0;   // começa na primeira linha
        this.posicao = 0;      // começa no primeiro caractere
    }

    // método principal que varre todo o arquivo
    public List<Token> analisar() {
        // vai processando cada linha do arquivo
        while (linhaAtual < linhas.size()) {
            String linha = linhas.get(linhaAtual);
            
            // varre cada caractere da linha atual
            while (posicao < linha.length()) {
                char c = linha.charAt(posicao);
                
                // ignora espaços em branco
                if (Character.isWhitespace(c)) {
                    posicao++;
                    continue;
                }
                
                // trata comentários (tudo depois do #)
                if (c == '#') {
                    break;  // pula pro fim da linha
                }
                
                // verifica se é número
                if (Character.isDigit(c)) {
                    processarNumero(linha);
                    continue;
                }
                
                // verifica se é string (começa com ")
                if (c == '"') {
                    processarString(linha);
                    continue;
                }
                
                // verifica operadores e símbolos especiais
                if (processarOperadoresESimbolos(linha)) {
                    continue;
                }
                
                // se chegou aqui, pode ser identificador ou palavra-chave
                if (Character.isLetter(c)) {
                    processarIdentificador(linha);
                    continue;
                }
                
                posicao++;  // próximo caractere se não entrou em nenhum caso
            }
            linhaAtual++;  // passa pra próxima linha
            posicao = 0;   // reseta a posição
        }
        // marca o fim do arquivo
        tokens.add(new Token("EOF", TipoToken.EOF, linhaAtual + 1));
        return tokens;
    }
//funcoes de processamento que criei para corrigir erros e ambiguidades nos casos
    // trata números inteiros e decimais
    private void processarNumero(String linha) {
        int inicio = posicao;  // guarda onde o número começa
        boolean temPonto = false; // pra saber se é decimal
        
        // vai pegando dígitos enquanto for número ou ponto
        while (posicao < linha.length()) {
            char c = linha.charAt(posicao);
            if (Character.isDigit(c)) {
                posicao++;
            } else if (c == '.' && !temPonto) {  // só permite um ponto
                temPonto = true;
                posicao++;
            } else {
                break;  // sai quando achar algo que não é número
            }
        }
        
        // pega o valor completo e define o tipo
        String valor = linha.substring(inicio, posicao);
        TipoToken tipo = temPonto ? TipoToken.NumReal : TipoToken.NumInt;
        tokens.add(new Token(valor, tipo, linhaAtual + 1));  // +1 porque linha começa em 0
    }

    // processa strings entre aspas
    private void processarString(String linha) {
        int inicio = ++posicao;  // pula a primeira aspas
        StringBuilder conteudo = new StringBuilder();
        
        // vai até encontrar a aspas final
        while (posicao < linha.length()) {
            char c = linha.charAt(posicao++);
            if (c == '"') {  // achou o fim da string
                tokens.add(new Token(conteudo.toString(), TipoToken.Cadeia, linhaAtual + 1));
                return;
            }
            conteudo.append(c);  // acumula os caracteres
        }
        
        // se não fechou a string, dá erro
        throw new RuntimeException("String não fechada na linha " + (linhaAtual + 1));
    }

    // trata operadores e símbolos como +, -, ==, etc
    private boolean processarOperadoresESimbolos(String linha) {
        char c = linha.charAt(posicao);
        
        // verifica cada operador possível
        switch (c) {
            case '+':
                tokens.add(new Token("+", TipoToken.OpAritSoma, linhaAtual + 1));
                break;
            case '-':
                tokens.add(new Token("-", TipoToken.OpAritSub, linhaAtual + 1));
                break;
            case '*':
                tokens.add(new Token("*", TipoToken.OpAritMult, linhaAtual + 1));
                break;
            case '/':
                tokens.add(new Token("/", TipoToken.OpAritDiv, linhaAtual + 1));
                break;
            case '(':
                tokens.add(new Token("(", TipoToken.AbrePar, linhaAtual + 1));
                break;
            case ')':
                tokens.add(new Token(")", TipoToken.FechaPar, linhaAtual + 1));
                break;
            case ':':
                tokens.add(new Token(":", TipoToken.Delim, linhaAtual + 1));
                break;
            case '=':
                // verifica se é ==
                if (posicao + 1 < linha.length() && linha.charAt(posicao + 1) == '=') {
                    tokens.add(new Token("==", TipoToken.OpRelIgual, linhaAtual + 1));
                    posicao++;
                }
                break;
            case '!':
                // verifica se é !=
                if (posicao + 1 < linha.length() && linha.charAt(posicao + 1) == '=') {
                    tokens.add(new Token("!=", TipoToken.OpRelDif, linhaAtual + 1));
                    posicao++;
                }
                break;
            case '<':
                // verifica se é <=
                if (posicao + 1 < linha.length() && linha.charAt(posicao + 1) == '=') {
                    tokens.add(new Token("<=", TipoToken.OpRelMenorIgual, linhaAtual + 1));
                    posicao++;
                } else {
                    tokens.add(new Token("<", TipoToken.OpRelMenor, linhaAtual + 1));
                }
                break;
            case '>':
                // verifica se é >=
                if (posicao + 1 < linha.length() && linha.charAt(posicao + 1) == '=') {
                    tokens.add(new Token(">=", TipoToken.OpRelMaiorIgual, linhaAtual + 1));
                    posicao++;
                } else {
                    tokens.add(new Token(">", TipoToken.OpRelMaior, linhaAtual + 1));
                }
                break;
            default:
                return false;  // não era um operador
        }
        posicao++;  // avança pro próximo caractere
        return true;
    }

    // processa identificadores e palavras-chave
    private void processarIdentificador(String linha) {
        int inicio = posicao;  // onde começa o identificador
        
        // vai pegando letras, números ou _
        while (posicao < linha.length()) {
            char c = linha.charAt(posicao);
            if (!Character.isLetterOrDigit(c) && c != '_') break;
            posicao++;
        }
        
        // pega o texto completo
        String valor = linha.substring(inicio, posicao);
        // verifica se é palavra reservada
        TipoToken tipo = verificarPalavraChave(valor);
        tokens.add(new Token(valor, tipo, linhaAtual + 1));
    }

    // verifica se o texto é uma palavra-chave
    private TipoToken verificarPalavraChave(String valor) {
        // compara em maiúsculas pra ser case insensitive
        switch (valor.toUpperCase()) {
            case "INI": return TipoToken.PCIni;
            case "FIM": return TipoToken.PCFim;
            case "DEC": return TipoToken.PCDec;
            case "PROG": return TipoToken.PCProg;
            case "INT": return TipoToken.PCInt;
            case "REAL": return TipoToken.PCReal;
            case "LER": return TipoToken.PCLer;
            case "IMPRIMIR": return TipoToken.PCImprimir;
            case "SE": return TipoToken.PCSe;
            case "SENAO": return TipoToken.PCSenao;
            case "ENTAO": return TipoToken.PCEntao;
            case "ENQTO": return TipoToken.PCEnqto;
            case "E": return TipoToken.OpBoolE;
            case "OU": return TipoToken.OpBoolOu;
            default: return TipoToken.Var;  // se não for reservada, é variável
        }
    }
}