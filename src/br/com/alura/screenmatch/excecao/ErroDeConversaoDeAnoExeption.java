package br.com.alura.screenmatch.excecao;

public class ErroDeConversaoDeAnoExeption extends RuntimeException {
    private String mensagem;

    //Construindo minha excecao com referencia  a mensagem
    public ErroDeConversaoDeAnoExeption(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return this.mensagem;
    }
}