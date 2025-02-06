/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;


import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bean.Usuario;


 // @author carol

public class UsuarioDAO {
    public void create(Usuario u){
        //inserir um novo registro na tabela usuario
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuario(nome, email, senha) VALUES (?,?,?)"; 
            stmt = con.prepareStatement(query);
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar Usuario.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar Usuario. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public ArrayList<Usuario> read(){
        //Ler um registro do BD, da tabela Usuarios

        Connection con = Conexao.getConexao(); // Faz a conexão
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Usuario> usuarios = new ArrayList();

        try {
            String query = "SELECT * FROM usuario";
            stmt = con.prepareStatement(query);

            rs = stmt.executeQuery();

            while(rs.next()){
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));

                Timestamp timestamp = rs.getTimestamp("data_criacao"); //Converte um valor TimeStamp do BD para um objeto LocalDateTime em java.
                    if (timestamp != null) {
                        u.setData_criacao(timestamp.toLocalDateTime()); 
                    } else {
                        u.setData_criacao(null);
                }

                usuarios.add(u);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Usuarios. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
        return usuarios;
    }
    
    public void update(Usuario u){
        //atualizar um novo registro na tabela usuarios
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE usuario "
                    + "SET nome = ?, email = ?, senha = ? "
                    + "WHERE id_usuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setInt(4, u.getId_usuario()); // mt importante para atualizar

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar Usuario.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao atualizar Usuario. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public void destroy(Usuario u){
        //apagar um novo registro na tabela filme
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM usuario WHERE id_usuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, u.getId_usuario()); // mt importante para apagar

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso.");

        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao excluir Usuario. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public Usuario read(int id){
        //Ler a linha
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM usuario WHERE id_usuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            Usuario u = new Usuario();
            if(rs.next()){
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));

            }
            return u;

        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Usuarios. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        return null;
    }
}
