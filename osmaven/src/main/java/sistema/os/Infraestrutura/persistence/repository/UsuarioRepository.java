package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

public class UsuarioRepository implements IUsuarioRepository {

    @Override
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, nome, email, senha, perfil, ativo, data_criacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getId().toString());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());
            ps.setString(5, usuario.getPerfil());
            ps.setBoolean(6, usuario.isAtivo());
            ps.setTimestamp(7, Timestamp.valueOf(usuario.getDataCriacao()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, email, senha, perfil, ativo, data_criacao FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("perfil"),
                    rs.getBoolean("ativo"),
                    rs.getTimestamp("data_criacao").toLocalDateTime()
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários", e);
        }

        return usuarios;
    }

    @Override
    public Usuario buscarPorId(UUID id) {
        String sql = "SELECT id, nome, email, senha, perfil, ativo, data_criacao FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("perfil"),
                        rs.getBoolean("ativo"),
                        rs.getTimestamp("data_criacao").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return null;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT id, nome, email, senha, perfil, ativo, data_criacao FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email.toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("perfil"),
                        rs.getBoolean("ativo"),
                        rs.getTimestamp("data_criacao").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }

        return null;
    }

    @Override
    public boolean deletar(UUID id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, perfil = ?, ativo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getPerfil());
            ps.setBoolean(5, usuario.isAtivo());
            ps.setString(6, usuario.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }
}
