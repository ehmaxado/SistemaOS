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
        String sql = "INSERT INTO servicos (id, codigo, descricao, valor_padrao, tempo_estimado_minutos, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getId().toString());
            ps.setString(2, servico.getCodigo());
            ps.setString(3, servico.getDescricao());
            ps.setDouble(4, servico.getValorPadrao());
            ps.setInt(5, servico.getTempoEstimadoMinutos());
            ps.setBoolean(6, servico.isAtivo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar servi\u00e7o", e);
        }
    }

    @Override
    public Servico buscarPorId(UUID id) {
        String sql = "SELECT id, codigo, descricao, valor_padrao, tempo_estimado_minutos, ativo FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Servico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("codigo"),
                        rs.getString("descricao"),
                        rs.getDouble("valor_padrao"),
                        rs.getInt("tempo_estimado_minutos"),
                        rs.getBoolean("ativo")
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
        String sql = "SELECT id, codigo, descricao, valor_padrao, tempo_estimado_minutos, ativo FROM servicos ORDER BY descricao";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                servicos.add(new Servico(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("codigo"),
                    rs.getString("descricao"),
                    rs.getDouble("valor_padrao"),
                    rs.getInt("tempo_estimado_minutos"),
                    rs.getBoolean("ativo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar serviços", e);
        }
        return servicos;
    }

    @Override
    public void editar(Servico servico) {
        String sql = "UPDATE servicos SET codigo = ?, descricao = ?, valor_padrao = ?, tempo_estimado_minutos = ?, ativo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getCodigo());
            ps.setString(2, servico.getDescricao());
            ps.setDouble(3, servico.getValorPadrao());
            ps.setInt(4, servico.getTempoEstimadoMinutos());
            ps.setBoolean(5, servico.isAtivo());
            ps.setString(6, servico.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar servi\u00e7o", e);
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
