/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.bean;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author carol
 */
public class Usuario {
    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime data_criacao;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
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
        Usuario usuario = (Usuario) obj;
        return this.id_usuario == usuario.id_usuario; // visto que id é um identificador único para o usuario
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario); // Utiliza o mesmo campo de comparação
    }    
    @Override
        public String toString() {
        return this.nome;
    }
}
