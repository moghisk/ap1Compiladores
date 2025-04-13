package ap1_parcial;

import java.util.ArrayList;

import java.util.List;

public class TransformaLista {
	LeitorArquivo ldat;
	
	public TransformaLista(String nome) {
		ldat=new LeitorArquivo(nome);
	}
	
	public List proximoToken() { 
        String ch= null;
        List<String> lista = new ArrayList(); //inicializa lista
		while(( ch=ldat.lerProxLinha() )!= null) { //ler ate o fim do arquiv
			ch = ch.replace("\t", " ");//troca tab por espaço
			ch += " ";//termina em espaço
			lista.add(ch);//adiciona a string na lista
			}
		for(int i = 0; i < lista.size(); i++) {
		System.out.println(lista.get(i));
		}
		return lista;
		
	}
	

}
