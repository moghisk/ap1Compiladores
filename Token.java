package ap1_parcial;

public class Token {
    String lexema;
    TipoToken padrao;
    int linha;  // linha onde o lexema foi encontrado

    public Token(String lexema, TipoToken padrao, int linha) {
        this.lexema = lexema;
        this.padrao = padrao;
        this.linha = linha;
        //System.out.print(toString());
    }
    
    @Override
    public String toString() {
        return "<" + lexema + "," + padrao + "," + linha + ">\n";  
    }
}