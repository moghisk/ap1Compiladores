package ap1_parcial;

import java.io.FileNotFoundException;
import java.util.List;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
    	TesteImprime lex = new TesteImprime("arq.gyh");
		List t = lex.proximoToken();
		AnalisadorLexico analisa = new AnalisadorLexico(t);
    }
}
