package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Servico;
import sistema.os.domain.Interfaces.IServicoRepository;

public class ServicoRepository implements IServicoRepository {

    @Override
    public void salvar(Servico servico) {
        String sql = "INSERT INTO servicos (id, nome, descricao, preco, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getId().toString());
            ps.setString(2, servico.getNome());
            ps.setString(3, servico.getDescricao());
            ps.setDouble(4, servico.getPreco());
            ps.setTimestamp(5, Timestamp.valueOf(servico.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço", e);
        }
    }

    @Override
    public Servico buscarPorId(UUID id) {
        String sql = "SELECT id, nome, descricao, preco, data_cadastro FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Servico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getTimestamp("data_cadastro").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar serviço por ID", e);
        }
        return null;
    }

    @Override
    public List<Servico> listarTodos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco, data_cadastro FROM servicos ORDER BY data_cadastro DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                servicos.add(new Servico(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getTimestamp("data_cadastro").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar serviços", e);
        }
        return servicos;
    }

    @Override
    public void editar(Servico servico) {
        String sql = "UPDATE servicos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getNome());
            ps.setString(2, servico.getDescricao());
            ps.setDouble(3, servico.getPreco());
            ps.setString(4, servico.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar serviço", e);
        }
    }

    @Override
    public void deletar(UUID id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar serviço", e);
        }
    }
}
