package ap1_parcial;

import java.util.ArrayList;
import java.util.List;

public class TransformaLista {
    private final LeitorArquivo leitor;

    public TransformaLista(String nome) {
        this.leitor = new LeitorArquivo(nome);
    }

    public List<String> transformarEmLinhas() {
        List<String> linhas = new ArrayList<>();
        String linha;
        while ((linha = leitor.lerProxLinha()) != null) {
            linhas.add(linha.replace("\t", " ") + " ");
        }
        leitor.fechar();
        return linhas;
    }
}