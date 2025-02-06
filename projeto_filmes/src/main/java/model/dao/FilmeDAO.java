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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import model.bean.Filme;
import model.bean.Genero;

// @author carol
 
public class FilmeDAO {
    public int create(Filme f){
        //inserir um novo registro na tabela filmes
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "INSERT INTO filme(titulo, ano, classificacao, duracao, sinopse) VALUES (?,?,?,?,?)";
            stmt = con.prepareStatement(query,  PreparedStatement.RETURN_GENERATED_KEYS); //Comando para retornar o id gerado no banco de dados logo apos a inserção
            stmt.setString(1, f.getTitulo());
            stmt.setDouble(2, f.getAno());
            stmt.setDouble(3, f.getClassificacao());
            stmt.setDouble(4, f.getDuracao());
            stmt.setString(5, f.getSinopse());
            stmt.executeUpdate();

            // Obter o ID gerado
            rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                return rs.getInt(1); // Retorna o ID gerado
                 }
                
            JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar Filme.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar Filme. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        return -1; // Retorna -1 se não conseguiu obter o ID
    }
    
    public ArrayList<Filme> read(){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Filme> filmes = new ArrayList();
          // Left Join para retornar tds os registros da tabela Filme, mesmo q nao exista correspondencia com as tabelas da direita
          
        try {
            String query = """
                           SELECT f.id_filme, f.titulo, f.ano, f.classificacao, f.duracao, f.sinopse, g.id_genero, g.descricao
                           FROM filme f
                           LEFT JOIN filmegenero fg ON f.id_filme = fg.id_filme 
                           LEFT JOIN genero g ON fg.id_genero = g.id_genero
                           """;
          
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            
            Map<Integer, Filme> mapaFilmes = new HashMap<>(); //usado para procurar os filmes nas associacoes de filmegenero, e evitar duplicações
            
            while(rs.next()){
               int idFilme = rs.getInt("id_filme");
               Filme filme = mapaFilmes.get(idFilme);
               
               if(filme == null){
                    filme = new Filme();
                    filme.setId_filme(rs.getInt("id_filme"));
                    filme.setTitulo(rs.getString("titulo"));
                    filme.setAno(rs.getInt("ano"));
                    filme.setClassificacao(rs.getDouble("classificacao"));
                    filme.setDuracao(rs.getInt("duracao"));
                    filme.setSinopse(rs.getString("sinopse"));
                    filme.setGeneros(new ArrayList<>()); // Inicializa a lista de gêneros
                    
                    mapaFilmes.put(idFilme, filme); //garante q cada filme seja criado so uma vez durante a consulta
                    
                    filmes.add(filme);
                    
               }
                    int idGenero = rs.getInt("id_genero"); 
                    if (idGenero > 0) { //verifica se o genero é valido
                     Genero genero = new Genero();
                     genero.setId_genero(idGenero);
                     genero.setDescricao(rs.getString("descricao"));
                     filme.getGeneros().add(genero); //associa o genero ao filme no Array da classe
                    }
            }
            // }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Filmes. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
        return filmes;
    }
    
    public void update(Filme f){
        //atualizar um novo registro na tabela filmes
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE filme "
                    + "SET titulo = ?, ano = ?, classificacao = ?, duracao = ?, sinopse = ? "
                    + "WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, f.getTitulo());
            stmt.setInt(2, f.getAno());
            stmt.setDouble(3, f.getClassificacao());
            stmt.setInt(4, f.getDuracao());
            stmt.setString(5, f.getSinopse());
            stmt.setInt(6, f.getId_filme()); // mt importante para atualizar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar Filme.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao atualizar Filme. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
     public void destroy(Filme f){
        //apagar um novo registro na tabela filme
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM filme WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, f.getId_filme()); // mt importante para apagar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Filme excluido com sucesso.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao excluir Filme. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
     
     public Filme read(int id){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM filme WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            Filme f = new Filme();
            if(rs.next()){
                f.setId_filme(rs.getInt("id_filme"));
                f.setTitulo(rs.getString("titulo"));
                f.setAno(rs.getInt("ano"));
                f.setClassificacao(rs.getDouble("classificacao"));
                f.setDuracao(rs.getInt("duracao"));
                f.setSinopse(rs.getString("sinopse"));
                
            }
            return f;
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Filmes. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        return null;
    }
}
