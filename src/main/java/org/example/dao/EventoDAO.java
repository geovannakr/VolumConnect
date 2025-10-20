package org.example.dao;

import org.example.db.DBConnection;
import org.example.model.Evento;
import org.example.model.Ong;
import org.example.model.Voluntario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public boolean cadastrarEvento(Evento e) {
        String sqlEvento = "INSERT INTO evento(nome,cidade,publico,ong_id) VALUES (?,?,?,?)";
        String sqlCap = "INSERT INTO evento_capacidades(evento_id,capacidade) VALUES (?,?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {

            int ongId = getOngId(e.getOng(), conn);
            if (ongId == -1) {
                System.out.println("ONG não encontrada!");
                return false;
            }

            stmtEvento.setString(1, e.getNome());
            stmtEvento.setString(2, e.getCidade());
            stmtEvento.setString(3, e.getPublico());
            stmtEvento.setInt(4, ongId);
            stmtEvento.executeUpdate();

            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    int eventoId = rs.getInt(1);
                    try (PreparedStatement stmtCap = conn.prepareStatement(sqlCap)) {
                        for (String c : e.getCapacidades()) {
                            stmtCap.setInt(1, eventoId);
                            stmtCap.setString(2, c);
                            stmtCap.executeUpdate();
                        }
                    }
                }
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Erro evento: " + ex.getMessage());
            return false;
        }
    }

    public List<Evento> listarEventos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.id, e.nome, e.cidade, e.publico, u.nome as ong_nome, u.email as ong_email, u.senha as ong_senha " +
                "FROM evento e " +
                "JOIN ong o ON e.ong_id = o.id " +
                "JOIN usuario u ON o.usuario_id = u.id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ong ong = new Ong(rs.getString("ong_nome"), rs.getString("ong_email"), rs.getString("ong_senha"));
                Evento e = new Evento(
                        rs.getString("nome"),
                        rs.getString("cidade"),
                        rs.getString("publico"),
                        new ArrayList<>(),
                        ong
                );
                eventos.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Erro listar eventos: " + ex.getMessage());
        }

        return eventos;
    }

    private int getOngId(Ong ong, Connection conn) throws SQLException {
        String sql = "SELECT id FROM ong o JOIN usuario u ON o.usuario_id=u.id WHERE u.email=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ong.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        return -1;
    }

    public void inscreverVoluntario(Evento e, Voluntario v) {
        String sql = "INSERT INTO evento_voluntario(evento_id, voluntario_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection()) {

            int eventoId = getEventoId(e, conn);
            int voluntarioId = getVoluntarioId(v, conn);

            if (eventoId == -1 || voluntarioId == -1) {
                System.out.println("Evento ou voluntário não encontrado!");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, eventoId);
                stmt.setInt(2, voluntarioId);
                stmt.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println("Erro inscrição: " + ex.getMessage());
        }
    }

    private int getEventoId(Evento e, Connection conn) throws SQLException {
        String sql = "SELECT id FROM evento WHERE nome=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            stmt.setString(1, e.getNome());
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    private int getVoluntarioId(Voluntario v, Connection conn) throws SQLException {
        String sql = "SELECT id FROM usuario WHERE email=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, v.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        return -1;
    }
}