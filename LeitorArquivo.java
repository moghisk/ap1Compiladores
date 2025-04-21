package ap1_parcial;

import java.io.*;

public class LeitorArquivo {
    private final BufferedReader leitor;

    public LeitorArquivo(String nome) {
        try {
            FileReader fileReader = new FileReader(nome);
            this.leitor = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + nome);
        }
    }

    public String lerProxLinha() {
        try {
            return leitor.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }
    }

    public void fechar() {
        try {
            leitor.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fechar arquivo");
        }
    }
}