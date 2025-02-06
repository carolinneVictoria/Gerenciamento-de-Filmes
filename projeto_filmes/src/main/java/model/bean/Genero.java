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
public class Genero {
    private int id_genero;
    private String descricao;
    private ArrayList<Filme> filmes;

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<Filme> filmes) {
        this.filmes = filmes;
    }
     /* Os dois próximos métodos abaixo servem para poder comparar o fornecedor de 
       minhaCompra (na TelaCompra) com os objetos de Fornecedor do combobox de Fornecedor */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Genero genero = (Genero) obj;
        return this.id_genero == genero.id_genero; // visto que id é um identificador único para o genero
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_genero); // Utiliza o mesmo campo de comparação
    }    
    @Override
    public String toString() {
        return this.descricao; // Retorna a descrição do gênero
}
}
