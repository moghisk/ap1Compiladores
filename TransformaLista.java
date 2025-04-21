package ap1_parcial;

import java.util.ArrayList;
import java.util.List;

public class TransformaLista {
    private LeitorArquivo ldat;

    public TransformaLista(String nome) {
        ldat = new LeitorArquivo(nome);
    }

    public List<String> proximoToken() { 
        List<String> lista = new ArrayList<>();

        String linha;
        while ((linha = ldat.lerProxLinha()) != null) { // Lê até o fim do arquivo
            linha = linha.replace("\t", " "); // Substitui tabulação por espaço
            linha += " "; // Garante que termina com espaço
            lista.add(linha); // Adiciona à lista
        }

        // Imprime os itens da lista (para depuração)
        for (String item : lista) {
            System.out.println(item);
        }

        return lista;
    }
}


