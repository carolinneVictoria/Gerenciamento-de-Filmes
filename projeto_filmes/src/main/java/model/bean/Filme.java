/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.bean;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author carol
 */
public class Filme {
    private int id_filme;
    private String titulo;
    private int ano;
    private ArrayList<Genero> generos;
    private double classificacao;
    private int duracao;
    private String sinopse;

     private ArrayList<Avaliacao> avaliacoes;

    public Filme() {
        this.avaliacoes = new ArrayList<>();
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
    
    public int getId_filme() {
        return id_filme;
    }

    public void setId_filme(int id_filme) {
        this.id_filme = id_filme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<Genero> generos) {
        this.generos = generos;
    }


    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
     
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Filme filme = (Filme) obj;
        return this.id_filme == filme.id_filme; // visto que id é um identificador único para o fornecedor
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_filme); // Utiliza o mesmo campo de comparação
    }    
    @Override
        public String toString() {
        return this.titulo; 
    }
    
}
