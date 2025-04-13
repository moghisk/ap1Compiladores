package ap1_parcial;

public class Token {
	String lexema;
	TipoToken padrao;
	//lembrem-se da linha onde aparece o lexema, hein?!
	
	public Token(String lexema, TipoToken padrao) {
		this.lexema=lexema;
		this.padrao=padrao;
		System.out.print(toString());
		
	}
	
	@Override
	public String toString() {
		return "<"+lexema+","+padrao+"> \n";
	}
	
	/*public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public TipoToken getPadrao() {
		return padrao;
	}

	public void setPadrao(TipoToken padrao) {
		this.padrao = padrao;
	}*/

}
