package br.com.alura.screenmatch.modelos;

import br.com.alura.screenmatch.calculos.Classificavel;

public class Filme extends Titulo implements Classificavel {
    private String diretor;

    //Construtor
    public Filme(String nome, int anoDeLancamento) {
        super(nome, anoDeLancamento);
    }

    //Get and Set
    public String getDiretor() {
        return diretor;
    }
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    //Novas Assinaturas
    @Override
    public int getClassificacao() {
        return (int) pegaMedia() / 2;
    }

    @Override
    public String toString() {return "Filme: " + this.getNome() + " (" + this.getAnoDeLancamento() + ")";}
}
