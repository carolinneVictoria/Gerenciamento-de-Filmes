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
import model.bean.Avaliacao;
import model.bean.Filme;
import model.bean.Usuario;

// @author carol
 
public class AvaliacaoDAO {
        
    public void create(Avaliacao a){
        //inserir um novo registro na tabela avaliacao
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "INSERT INTO Avaliacao(id_usuario, id_filme, nota, descricao) VALUES (?,?,?,?)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, a.getUsuario().getId_usuario());
            stmt.setInt(2, a.getFilme().getId_filme());
            stmt.setDouble(3, a.getNota());
            stmt.setString(4, a.getComentario());
            
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar Avaliação.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar Avaliação. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
        
    public ArrayList<Avaliacao> read(){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Avaliacao> avaliacoes = new ArrayList();
        
        try {
            String query = """
                          SELECT a.id_avaliacao, a.descricao, a.nota, a.data, f.titulo, u.nome, f.id_filme, u.id_usuario
                           FROM avaliacao a
                           INNER JOIN usuario u ON a.id_usuario = u.id_usuario
                           INNER JOIN filme f ON a.id_filme = f.id_filme
                           """;
            stmt = con.prepareStatement(query);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Avaliacao a = new Avaliacao();
                Usuario u = new Usuario();
                Filme f = new Filme();
                a.setId_avaliacao(rs.getInt("id_avaliacao"));
                u.setNome(rs.getString("nome")); //usuario
                f.setTitulo(rs.getString("titulo")); //filme
                u.setId_usuario(rs.getInt("id_usuario")); //usuario
                f.setId_filme(rs.getInt("id_filme")); //filme
                a.setFilme(f); // associar filme e usuario á avaliação
                a.setUsuario(u);
                a.setNota(rs.getDouble("nota"));
                a.setComentario(rs.getString("descricao"));
               Timestamp timestamp = rs.getTimestamp("data");
                    if (timestamp != null) {
                        a.setData_criacao(timestamp.toLocalDateTime());
                    } else {
                        a.setData_criacao(null);
                }
                
                avaliacoes.add(a);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao consultar Avaliações. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
        
        return avaliacoes;
    }
        
    public void update(Avaliacao a){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE avaliacao "
                    + "SET nota = ? , descricao = ? "
                    + "WHERE id_avaliacao = ?";
            stmt = con.prepareStatement(query);
            stmt.setDouble(1, a.getNota());
            stmt.setString(2, a.getComentario());
            stmt.setInt(3, a.getId_avaliacao()); // mt importante para atualizar
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar Avaliacao.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao atualizar Avaliacao. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public void destroy(Avaliacao a){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, a.getId_avaliacao()); // mt importante para apagar
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Avaliacao excluida com sucesso.");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Falha ao excluir Avaliacao. Erro: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public Avaliacao read(int id) {
        Connection con = Conexao.getConexao();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                String query = """
                            SELECT a.id_avaliacao, a.descricao, a.nota, a.data, f.titulo, u.nome, f.id_filme, u.id_usuario
                            FROM avaliacao a
                            INNER JOIN usuario u ON a.id_usuario = u.id_usuario
                            INNER JOIN filme f ON a.id_filme = f.id_filme
                            WHERE id_avaliacao = ?
                                            """;
                stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                Avaliacao a = new Avaliacao();
                
                if(rs.next()){
                Usuario u = new Usuario();
                Filme f = new Filme();
                a.setId_avaliacao(rs.getInt("id_avaliacao"));
                u.setNome(rs.getString("nome"));
                f.setTitulo(rs.getString("titulo"));
                u.setId_usuario(rs.getInt("id_usuario"));
                f.setId_filme(rs.getInt("id_filme"));
                a.setFilme(f); // associar filme e usuario á avaliação
                a.setUsuario(u);
                a.setNota(rs.getDouble("nota"));
                a.setComentario(rs.getString("descricao"));
                }
               return a;
                
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Falha ao consultar Avaliações. Erro: " + e.getMessage());
            } finally {
                Conexao.fecharConexao(con, stmt);
            }
            return null;
        }

}
