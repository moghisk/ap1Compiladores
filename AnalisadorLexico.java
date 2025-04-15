package ap1_parcial;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

	public AnalisadorLexico(List<String> lista) { // construtor
		analisar(lista);
	}

	public List<Token> analisar(List<String> lista) {
		List<Token> tokens = new ArrayList<>(); // lista que armazena os tokens encontrados
		String car = "";
		char asp = '"'; // caractere aspas
		String aspas = "" + asp;

		for (int n = 0; n < lista.size(); n++) { // lê cada linha do arquivo transformado em lista
			int p = lista.get(n).length(); // tamanho da linha
			int x = p - 1;
			String palavra = "";

			for (int i = 0; i <= x; i++) { // monta palavras
				car = lista.get(n).substring(lista.get(n).length() - p, lista.get(n).length() - p + 1);
				palavra += car;

				if (palavra.matches("[0-9]*")) { // verifica se está lidando com um número
					int bool = 0;
					while (p > 1 &&
							(lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).matches("[0-9]*") ||
									lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("."))) {
						// enquanto o número tiver ponto
						car = lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2);
						if (car.equals(".")) {
							bool = 1;
						}
						palavra += car;
						p--;
						i++;
					}

					if (bool == 1) {
						tokens.add(new Token(palavra, TipoToken.NumReal)); // adiciona número real
						palavra = "";
						break;
					} else {
						tokens.add(new Token(palavra, TipoToken.NumInt)); // adiciona número inteiro
						palavra = "";
						break;
					}
				}

				boolean maisc = palavra.equals(palavra.toUpperCase()); // palavra toda em maiúscula
				if (maisc) { // se todas as letras forem maiúsculas
					switch (palavra) {
					
					case "#": // ignora comentário
						i = x;
						palavra = "";
						break;
					
						case " ":
							palavra = "";
							break; // palavra vazia

						case "INI":
							tokens.add(new Token(palavra, TipoToken.PCIni));
							palavra = "";
							break;

						case "FIM":
							tokens.add(new Token(palavra, TipoToken.PCFim));
							palavra = "";
							break;

						case "DEC":
							tokens.add(new Token(palavra, TipoToken.PCDec));
							palavra = "";
							break;

						case "PROG":
							tokens.add(new Token(palavra, TipoToken.PCProg));
							palavra = "";
							break;

						case "INT":
							tokens.add(new Token(palavra, TipoToken.PCInt));
							palavra = "";
							break;

						case "REAL":
							tokens.add(new Token(palavra, TipoToken.PCReal));
							palavra = "";
							break;

						case "LER":
							tokens.add(new Token(palavra, TipoToken.PCLer));
							palavra = "";
							break;

						case "IMPRIMIR":
							tokens.add(new Token(palavra, TipoToken.PCImprimir));
							palavra = "";
							break;

						case "SE":
							tokens.add(new Token(palavra, TipoToken.PCSe));
							palavra = "";
							break;

						case "SENAO":
							tokens.add(new Token(palavra, TipoToken.PCSenao));
							palavra = "";
							break;

						case "ENTAO":
							tokens.add(new Token(palavra, TipoToken.PCEntao));
							palavra = "";
							break;

						case "E":
							if (lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("NQTO")) {
								palavra += "NQTO";
								p--;
								i++;
								tokens.add(new Token(palavra, TipoToken.PCEnqto));
								palavra = "";
								break;
							} else {
								tokens.add(new Token(palavra, TipoToken.OpBoolE));
								palavra = "";
								break;
							}//para nao confundir o E com ENQTO

						case "OU":
							tokens.add(new Token(palavra, TipoToken.OpBoolOu));
							palavra = "";
							break;
						
					}
					
				} else { //simbolos que nao sao letra
					switch(palavra) {
					
					case "*":
						tokens.add(new Token(palavra, TipoToken.OpAritMult));
						palavra = "";
						break;

					case "/":
						tokens.add(new Token(palavra, TipoToken.OpAritDiv));
						palavra = "";
						break;

					case "+":
						tokens.add(new Token(palavra, TipoToken.OpAritSoma));
						palavra = "";
						break;

					case "-":
						tokens.add(new Token(palavra, TipoToken.OpAritSub));
						palavra = "";
						break;

					
					case ":":
						tokens.add(new Token(palavra, TipoToken.Delim));
						palavra = "";
						break;

					case "(":
						tokens.add(new Token(palavra, TipoToken.AbrePar));
						palavra = "";
						break;

					case ")":
						tokens.add(new Token(palavra, TipoToken.FechaPar));
						palavra = "";
						break;

					case "!":
						if (lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("=")) {
							// se o próximo caractere for "="
							palavra += "=";
							p--;
							i++;
							tokens.add(new Token(palavra, TipoToken.OpRelDif));
							palavra = "";
							break;
						}
						break;

					case "<":
						if (lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("=")) {
							palavra += "=";
							p--;
							i++;
							tokens.add(new Token(palavra, TipoToken.OpRelMenorIgual));
							palavra = "";
							break;
						} else {
							tokens.add(new Token(palavra, TipoToken.OpRelMenor));
							palavra = "";
							break;
						}

					case ">":
						if (lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("=")) {
							palavra += "=";
							p--;
							i++;
							tokens.add(new Token(palavra, TipoToken.OpRelMaiorIgual));
							palavra = "";
							break;
						} else {
							tokens.add(new Token(palavra, TipoToken.OpRelMaior));
							palavra = "";
							break;
						}

					case "=":
						if (lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2).equals("=")) {
							palavra += "=";
							p--;
							i++;
							tokens.add(new Token(palavra, TipoToken.OpRelIgual));
							palavra = "";
							break;
						}
						break;

					
						
					default:
						if (palavra.equals(aspas)) { // palavra entre aspas
							while (!lista.get(n)
									.substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2)
									.equals(aspas)) {
								palavra += lista.get(n).substring(lista.get(n).length() - p + 1,
										lista.get(n).length() - p + 2);
								p--;
								i++;
							}
							tokens.add(new Token(palavra, TipoToken.Cadeia));
							palavra = "";
							break;
						}
						break;
					}
				
					// começa com letra minúscula ou não é palavra reservada → variável
					while (p > 1 && lista.get(n)
							.substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2)
							.matches("[a-zA-Z0-9_]")) {
						car = lista.get(n).substring(lista.get(n).length() - p + 1, lista.get(n).length() - p + 2);
						palavra += car;
						p--;
						i++;
					}
					tokens.add(new Token(palavra, TipoToken.Var)); // adiciona token de variável
					palavra = "";
					break;
				}

				p--; // decrementa a posição da palavra
			}
		}

		return tokens; // retorna todos os tokens encontrados
	}
}


