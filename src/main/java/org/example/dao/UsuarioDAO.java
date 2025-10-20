package org.example.dao;
import org.example.db.DBConnection;
import org.example.model.Voluntario;
import org.example.model.Ong;
import org.example.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO {

        public boolean cadastrarVoluntario(Voluntario v) {
            String sqlUser = "INSERT INTO usuario(nome,email,senha,tipo) VALUES (?,?,?,?)";
            String sqlHabilidade = "INSERT INTO voluntario_habilidades(voluntario_id, habilidade) VALUES (?,?)";

            try(Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

                stmtUser.setString(1, v.getNome());
                stmtUser.setString(2, v.getEmail());
                stmtUser.setString(3, v.getSenha());
                stmtUser.setString(4, "voluntario");
                stmtUser.executeUpdate();

                ResultSet rs = stmtUser.getGeneratedKeys();
                if(rs.next()){
                    int userId = rs.getInt(1);
                    try(PreparedStatement stmtH = conn.prepareStatement(sqlHabilidade)) {
                        for(String h : v.getHabilidades()) {
                            stmtH.setInt(1,userId);
                            stmtH.setString(2,h);
                            stmtH.executeUpdate();
                        }
                    }
                }
                return true;
            } catch(SQLException e) {
                System.out.println("Erro voluntário: "+e.getMessage());
                return false;
            }
        }

        public boolean cadastrarOng(Ong o) {
            String sqlUser = "INSERT INTO usuario(nome,email,senha,tipo) VALUES (?,?,?,?)";
            String sqlOng = "INSERT INTO ong(usuario_id) VALUES (?)";

            try(Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

                stmtUser.setString(1,o.getNome());
                stmtUser.setString(2,o.getEmail());
                stmtUser.setString(3,o.getSenha());
                stmtUser.setString(4,"ong");
                stmtUser.executeUpdate();

                ResultSet rs = stmtUser.getGeneratedKeys();
                if(rs.next()){
                    int userId = rs.getInt(1);
                    try(PreparedStatement stmtOng = conn.prepareStatement(sqlOng)) {
                        stmtOng.setInt(1,userId);
                        stmtOng.executeUpdate();
                    }
                }
                return true;
            } catch(SQLException e) {
                System.out.println("Erro ONG: "+e.getMessage());
                return false;
            }
        }

    public Usuario login(String email, String senha) {
        String sql = "SELECT id,nome,tipo FROM usuario WHERE email=? AND senha=?";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3355/volunnConnect","root","mysqlPW");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,email);
            stmt.setString(2,senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    String tipo = rs.getString("tipo");
                    String nome = rs.getString("nome");
                    if(tipo.equals("voluntario")) return new Voluntario(nome,email,senha);
                    if(tipo.equals("ong")) return new Ong(nome,email,senha);
                    if(tipo.equals("admin")) return new Usuario(nome,email,senha){};
                }
            }
        } catch(SQLException e){
            System.out.println("Erro login: "+e.getMessage());
        }
        return null;
    }


    public List<Ong> listarOngs(){
            List<Ong> ongs = new ArrayList<>();
            String sql = "SELECT u.nome,u.email,u.senha FROM usuario u JOIN ong o ON u.id=o.usuario_id";
            try(Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    ongs.add(new Ong(rs.getString("nome"), rs.getString("email"), rs.getString("senha")));
                }
            } catch(SQLException e){ System.out.println("Erro listar ONGs: "+e.getMessage()); }
            return ongs;
        }
    }