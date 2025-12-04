package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.FormaPagamento;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class FormaPagamentoRepository implements IFormaPagamentoRepository {

    @Override
    public void salvar(FormaPagamento formaPagamento) {
        String sql = "INSERT INTO formas_pagamento (id, nome, descricao, ativo, data_criacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, formaPagamento.getId().toString());
            ps.setString(2, formaPagamento.getNome());
            ps.setString(3, formaPagamento.getDescricao());
            ps.setBoolean(4, formaPagamento.isAtivo());
            ps.setTimestamp(5, Timestamp.valueOf(formaPagamento.getDataCriacao()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar forma de pagamento", e);
        }
    }

    @Override
    public List<FormaPagamento> buscarTodas() {
        List<FormaPagamento> formas = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, ativo, data_criacao FROM formas_pagamento";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FormaPagamento forma = new FormaPagamento(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getBoolean("ativo"),
                    rs.getTimestamp("data_criacao").toLocalDateTime()
                );
                formas.add(forma);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar formas de pagamento", e);
        }

        return formas;
    }

    @Override
    public FormaPagamento buscarPorId(UUID id) {
        String sql = "SELECT id, nome, descricao, ativo, data_criacao FROM formas_pagamento WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FormaPagamento(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getBoolean("ativo"),
                        rs.getTimestamp("data_criacao").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar forma de pagamento", e);
        }

        return null;
    }

    @Override
    public boolean deletar(UUID id) {
        String sql = "DELETE FROM formas_pagamento WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar forma de pagamento", e);
        }
    }

    @Override
    public void atualizar(FormaPagamento formaPagamento) {
        String sql = "UPDATE formas_pagamento SET nome = ?, descricao = ?, ativo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, formaPagamento.getNome());
            ps.setString(2, formaPagamento.getDescricao());
            ps.setBoolean(3, formaPagamento.isAtivo());
            ps.setString(4, formaPagamento.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar forma de pagamento", e);
        }
    }
}
