package concorrencia;

public class Usuario {
    private int id;
    private String nomeArquivo;
    private byte[] conteudo;
    private char typeExecution;

    public Usuario(String nomeArquivo, byte[] conteudo, int id, char typeExecution) {
        this.nomeArquivo = nomeArquivo;
        this.conteudo = conteudo;
        this.id = id;
        this.typeExecution = typeExecution;
    }

    public int getId() {
        return id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public char getTypeExecution() {
        return typeExecution;
    }

    public String toString() {
        return String.format("IDusuario %s", this.id);
    }
}
