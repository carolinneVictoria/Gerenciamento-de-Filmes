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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import model.bean.Genero;

// @author carol

    public class GeneroDAO {
        
    public void create(Genero g){
        //inserir um novo registro na tabela Genero
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "INSERT INTO genero(descricao) VALUES (?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, g.getDescricao());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar Genero.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar Genero. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public ArrayList<Genero> read(){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Set<Genero> generos = new HashSet<>();
        
        try {
            String query = " SELECT * FROM genero g ";
                       
            stmt = con.prepareStatement(query);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            Genero genero = new Genero();
            genero.setId_genero(rs.getInt("id_genero"));
            genero.setDescricao(rs.getString("descricao"));
            genero.setFilmes(new ArrayList<>()); 

            generos.add(genero);
                }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Generos. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
        return new ArrayList<>(generos);
    }
    
    public void update(Genero g){
        //atualizar um novo registro na tabela generos
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE genero "
                    + "SET descricao = ? "
                    + "WHERE id_genero = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, g.getDescricao());
            stmt.setInt(2, g.getId_genero()); // mt importante para atualizar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar Genero.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao atualizar Genero. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public void destroy(Genero g){
        //apagar um novo registro na tabela filme
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM genero WHERE id_genero = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, g.getId_genero()); // mt importante para apagar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Genero excluido com sucesso.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao excluir Genero. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public Genero read(int id){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM genero WHERE id_genero = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            Genero g = new Genero();
            if(rs.next()){
                g.setId_genero(rs.getInt("id_genero"));
                g.setDescricao(rs.getString("descricao"));
                
            }
            return g;
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Generos. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        return null;
    }
}
