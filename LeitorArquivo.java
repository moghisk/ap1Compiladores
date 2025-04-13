package ap1_parcial;
import java.io.BufferedReader; //biblioteca para ler linhas do arquivo
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeitorArquivo {
	FileReader is;
	BufferedReader leitor; 
	public String linha;
	int cont = 0;
 
	public LeitorArquivo(String nome) {
		try {
			is=new FileReader(nome);
			leitor = new BufferedReader(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String lerProxLinha() { //semelhante a "ler proximo carater"
		try {
			linha=leitor.readLine();
			cont++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linha;
	}
	

}