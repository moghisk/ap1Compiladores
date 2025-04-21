package ap1_parcial;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        TransformaLista transformador = new TransformaLista("arq.gyh");
        List<String> linhas = transformador.transformarEmLinhas();
        AnalisadorLexico analisador = new AnalisadorLexico(linhas);
        List<Token> tokens = analisador.analisar();
        
        // Imprime todos os tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}