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
import javax.swing.JOptionPane;
import model.bean.Filme;
import model.bean.FilmeGenero;
import model.bean.Genero;

//@author carol

public class FilmeGeneroDAO {
    public void create(FilmeGenero fg){
        //inserir um novo registro na tabela FilmeGenero
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "INSERT INTO filmegenero(id_filme, id_genero) VALUES (?,?)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, fg.getId_filme().getId_filme());
            stmt.setInt(2, fg.getId_genero().getId_genero());
            
            stmt.executeUpdate();
        
            JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar!!.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar!! Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
}
    
     public ArrayList<FilmeGenero> read(){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<FilmeGenero> filmesGeneros = new ArrayList();
        
        try {
            String query = """
                          SELECT g.descricao, fg.id_genero, fg.id_filme, f.titulo
                           FROM filmegenero fg
                           INNER JOIN filme f ON fg.id_filme = f.id_filme
                           INNER JOIN genero g ON fg.id_genero = g.id_genero
                           ORDER BY fg.id_filme
                           """;
            stmt = con.prepareStatement(query);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                FilmeGenero fg = new FilmeGenero();
                Genero g = new Genero();
                Filme f = new Filme();
                g.setId_genero(rs.getInt("id_genero"));
                f.setId_filme(rs.getInt("id_filme"));
                g.setDescricao(rs.getString("descricao"));
                f.setTitulo(rs.getString("titulo"));
                fg.setId_filme(f); // associar filme e usuario á filmeGenero
                fg.setId_genero(g);
                fg.getId_filme().getTitulo();
                fg.getId_genero().getDescricao();
                
                filmesGeneros.add(fg);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Associações. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
        return filmesGeneros;
    }
     
       public FilmeGenero read(int idFilme) {
    //Consulta a tabela FilmeGenero no bd para buscar a associacao de filme e genero, com base no ID fornecido
    FilmeGenero fg = null;
    String sql = "SELECT * FROM FilmeGenero WHERE id_filme = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idFilme);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            fg = new FilmeGenero();
            Genero g = new Genero();
             Filme f = new Filme();
                g.setId_genero(rs.getInt("id_genero"));
                f.setId_filme(rs.getInt("id_filme"));
                fg.setId_filme(f); 
                fg.setId_genero(g);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Falha ao consultar Associações. Erro: " + ex.getMessage());
    }
    
    return fg;
}
       
    public FilmeGenero readByFilmeId(int idFilme, int idGenero) {
    //Consulta a tabela FilmeGenero no bd para buscar a associacao de filme e genero, com base no ID fornecido
    FilmeGenero fg = null;
    String sql = "SELECT * FROM FilmeGenero WHERE id_filme = ? AND id_genero = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idFilme);
        stmt.setInt(2, idGenero);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            fg = new FilmeGenero();
            Genero g = new Genero();
             Filme f = new Filme();
                g.setId_genero(rs.getInt("id_genero"));
                f.setId_filme(rs.getInt("id_filme"));
                fg.setId_filme(f); 
                fg.setId_genero(g);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Falha ao consultar Associações. Erro: " + ex.getMessage());
    }
    
    return fg;
}
    
    public void destroy(FilmeGenero fg){
        //apagar um novo registro na tabela filmegenero
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM FilmeGenero WHERE id_genero = ? AND id_filme = ?";
            stmt = con.prepareStatement(query);
            
            stmt.setInt(1, fg.getId_genero().getId_genero()); // mt importante para apagar
            stmt.setInt(2, fg.getId_filme().getId_filme()); // mt importante para apagar
             
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Associacao excluida com sucesso.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao excluir Associacao. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public void update(FilmeGenero fg){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE FilmeGenero "
                    + "SET id_genero = ? "
                    + "WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, fg.getId_genero().getId_genero());
            stmt.setInt(2, fg.getId_filme().getId_filme()); // mt importante para atualizar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar Associacao.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao atualizar Associacao. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

}

