package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.db.DBConnection;
import org.example.model.Ong;
import org.example.model.Usuario;
import org.example.model.Voluntario;

public class UsuarioDAO {

    public boolean cadastrarVoluntario(Voluntario v) {
        String sqlUser = "INSERT INTO usuario(nome,email,senha,tipo) VALUES (?,?,?,?)";
        String sqlHabilidade = "INSERT INTO voluntario_habilidades(voluntario_id, habilidade) VALUES (?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

            stmtUser.setString(1, v.getNome());
            stmtUser.setString(2, v.getEmail());
            stmtUser.setString(3, v.getSenha());
            stmtUser.setString(4, "voluntario");
            stmtUser.executeUpdate();

            try (ResultSet rs = stmtUser.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    try (PreparedStatement stmtH = conn.prepareStatement(sqlHabilidade)) {
                        for (String h : v.getHabilidades()) {
                            stmtH.setInt(1, userId);
                            stmtH.setString(2, h);
                            stmtH.executeUpdate();
                        }
                    }
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Erro voluntário: " + e.getMessage());
            return false;
        }
    }

    public boolean cadastrarOng(Ong o) {
        String sqlUser = "INSERT INTO usuario(nome,email,senha,tipo) VALUES (?,?,?,?)";
        String sqlOng = "INSERT INTO ong(usuario_id) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

            stmtUser.setString(1, o.getNome());
            stmtUser.setString(2, o.getEmail());
            stmtUser.setString(3, o.getSenha());
            stmtUser.setString(4, "ong");
            stmtUser.executeUpdate();

            try (ResultSet rs = stmtUser.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    try (PreparedStatement stmtOng = conn.prepareStatement(sqlOng)) {
                        stmtOng.setInt(1, userId);
                        stmtOng.executeUpdate();
                    }
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ONG: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String email, String senha) {
        String sql = "SELECT nome, tipo FROM usuario WHERE email=? AND senha=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String nome = rs.getString("nome");
                    if (tipo.equals("voluntario")) return new Voluntario(nome, email, senha);
                    if (tipo.equals("ong")) return new Ong(nome, email, senha);
                    if (tipo.equals("admin")) return new Usuario(nome, email, senha) {};
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro login: " + e.getMessage());
        }
        return null;
    }

    public List<Ong> listarOngs() {
        List<Ong> ongs = new ArrayList<>();
        String sql = """
            SELECT o.id AS ong_id, u.nome, u.email, u.senha
            FROM ong o
            JOIN usuario u ON o.usuario_id = u.id
            WHERE u.tipo = 'ong'
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ong ong = new Ong(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                ong.setId(rs.getInt("ong_id"));
                ongs.add(ong);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar ONGs: " + e.getMessage());
        }

        return ongs;
    }

    public List<Voluntario> listarVoluntarios() {
        List<Voluntario> voluntarios = new ArrayList<>();
    
        String sql = "SELECT id, nome, email, senha FROM usuario WHERE tipo = 'voluntario'";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Voluntario v = new Voluntario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                v.setId(rs.getInt("id")); 
                voluntarios.add(v);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return voluntarios;
    }

public Usuario buscarPorEmail(String email) {
    String sql = "SELECT id, nome, email, senha, tipo FROM usuario WHERE email = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String tipo = rs.getString("tipo");
                int id = rs.getInt("id");

                if ("voluntario".equalsIgnoreCase(tipo)) {
                    Voluntario v = new Voluntario(nome, rs.getString("email"), senha);
                    v.setId(id);
                    return v;
                } else if ("ong".equalsIgnoreCase(tipo)) {
                    Ong o = new Ong(nome, rs.getString("email"), senha);
                    o.setId(id);
                    return o;
                } else {
                    Usuario u = new Usuario(nome, rs.getString("email"), senha) {};
                    return u;
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro buscarPorEmail: " + e.getMessage());
    }

    return null; 
}
    


}
