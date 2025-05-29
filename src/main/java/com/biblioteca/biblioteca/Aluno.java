package com.biblioteca.biblioteca;

import java.io.Serializable;

/**
 * Entidade Aluno.
 */
public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String curso;
    private int matricula;
    private boolean bolsista;
    private int ano;  // Ano ingresso

    public Aluno() {}

    public Aluno(String nome, String curso, int matricula, boolean bolsista, int ano) {
        this.nome = nome;
        this.curso = curso;
        this.matricula = matricula;
        this.bolsista = bolsista;
        this.ano = ano;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }

    public boolean isBolsista() { return bolsista; }
    public void setBolsista(boolean bolsista) { this.bolsista = bolsista; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    @Override
    public String toString() {
        return "Aluno [matrícula=" + matricula + ", nome=" + nome + ", curso=" + curso + ", bolsista=" + bolsista + ", ano=" + ano + "]";
    }
}
