package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Enums.StatusPagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class PagamentoRepository implements IPagamentoRepository {

    @Override
    public void salvar(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id, ordem_servico_id, valor, status, data_pagamento, data_criacao, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pagamento.getId().toString());
            ps.setString(2, pagamento.getOrdemServicoId().toString());
            ps.setDouble(3, pagamento.getValor());
            ps.setString(4, pagamento.getStatus().name());
            ps.setTimestamp(5, pagamento.getDataPagamento() != null ? Timestamp.valueOf(pagamento.getDataPagamento()) : null);
            ps.setTimestamp(6, Timestamp.valueOf(pagamento.getDataCriacao()));
            ps.setString(7, pagamento.getDescricao());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pagamento", e);
        }
    }

    @Override
    public List<Pagamento> buscarTodas() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT id, ordem_servico_id, valor, status, data_pagamento, data_criacao, descricao FROM pagamentos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pagamento pagamento = new Pagamento(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("ordem_servico_id")),
                    rs.getDouble("valor"),
                    StatusPagamento.valueOf(rs.getString("status")),
                    rs.getTimestamp("data_pagamento") != null ? rs.getTimestamp("data_pagamento").toLocalDateTime() : null,
                    rs.getTimestamp("data_criacao").toLocalDateTime(),
                    rs.getString("descricao")
                );
                pagamentos.add(pagamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos", e);
        }

        return pagamentos;
    }

    @Override
    public Pagamento buscarPorId(UUID id) {
        String sql = "SELECT id, ordem_servico_id, valor, status, data_pagamento, data_criacao, descricao FROM pagamentos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pagamento(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        rs.getDouble("valor"),
                        StatusPagamento.valueOf(rs.getString("status")),
                        rs.getTimestamp("data_pagamento") != null ? rs.getTimestamp("data_pagamento").toLocalDateTime() : null,
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getString("descricao")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamento", e);
        }

        return null;
    }

    @Override
    public boolean deletar(UUID id) {
        String sql = "DELETE FROM pagamentos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pagamento", e);
        }
    }

    @Override
    public void atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamentos SET ordem_servico_id = ?, valor = ?, status = ?, data_pagamento = ?, descricao = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pagamento.getOrdemServicoId().toString());
            ps.setDouble(2, pagamento.getValor());
            ps.setString(3, pagamento.getStatus().name());
            ps.setTimestamp(4, pagamento.getDataPagamento() != null ? Timestamp.valueOf(pagamento.getDataPagamento()) : null);
            ps.setString(5, pagamento.getDescricao());
            ps.setString(6, pagamento.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pagamento", e);
        }
    }

    @Override
    public List<Pagamento> buscarPorStatus(StatusPagamento status) {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT id, ordem_servico_id, valor, status, data_pagamento, data_criacao, descricao FROM pagamentos WHERE status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pagamento pagamento = new Pagamento(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        rs.getDouble("valor"),
                        StatusPagamento.valueOf(rs.getString("status")),
                        rs.getTimestamp("data_pagamento") != null ? rs.getTimestamp("data_pagamento").toLocalDateTime() : null,
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getString("descricao")
                    );
                    pagamentos.add(pagamento);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos por status", e);
        }

        return pagamentos;
    }

    @Override
    public List<Pagamento> buscarPorData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT id, ordem_servico_id, valor, status, data_pagamento, data_criacao, descricao FROM pagamentos WHERE data_criacao BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(dataInicio));
            ps.setTimestamp(2, Timestamp.valueOf(dataFim));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pagamento pagamento = new Pagamento(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        rs.getDouble("valor"),
                        StatusPagamento.valueOf(rs.getString("status")),
                        rs.getTimestamp("data_pagamento") != null ? rs.getTimestamp("data_pagamento").toLocalDateTime() : null,
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getString("descricao")
                    );
                    pagamentos.add(pagamento);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos por data", e);
        }

        return pagamentos;
    }
}
