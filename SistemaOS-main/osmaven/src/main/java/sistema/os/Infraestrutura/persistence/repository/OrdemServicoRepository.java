package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoProduto;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;

public class OrdemServicoRepository implements IOrdemServicoRepository {

    @Override
    public void salvar(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico (id, numero_os, id_usuario, cliente_id, data_criacao, data_atualizacao, valor_total_produtos, valor_total_servicos, valor_total, valor_total_final, forma_pagamento, observacao_geral) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServico.getId().toString());
            ps.setString(2, ordemServico.getNumeroOS());
            ps.setString(3, ordemServico.getId_usuario());
            ps.setString(4, ordemServico.getClienteId());
            ps.setTimestamp(5, ordemServico.getDataAbertura() != null ? Timestamp.valueOf(ordemServico.getDataAbertura()) : null);
            ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setDouble(7, ordemServico.getValorTotalProdutos());
            ps.setDouble(8, ordemServico.getValorTotalServicos());
            ps.setDouble(9, ordemServico.getValorTotal());
            ps.setDouble(10, ordemServico.getValorTotalFinal());
            ps.setString(11, ordemServico.getFormaPagamento());
            ps.setString(12, ordemServico.getObservacaoGeral());
            
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ordem de serviço", e);
        }
    }

    @Override
    public OrdemServico buscarPorId(UUID id) {
        String sql = "SELECT id, numero_os, id_usuario, cliente_id, data_criacao, data_fechamento, valor_total_produtos, valor_total_servicos, valor_total, valor_total_final, forma_pagamento, observacao_geral FROM ordem_servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrdemServico os = new OrdemServico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("numero_os"),
                        rs.getTimestamp("data_criacao") != null ? rs.getTimestamp("data_criacao").toLocalDateTime() : null,
                        rs.getTimestamp("data_fechamento") != null ? rs.getTimestamp("data_fechamento").toLocalDateTime() : null,
                        rs.getString("id_usuario"),
                        rs.getString("cliente_id"),
                        rs.getDouble("valor_total_produtos"),
                        rs.getDouble("valor_total_servicos"),
                        rs.getDouble("valor_total"),
                        rs.getDouble("valor_total_final"),
                        rs.getString("forma_pagamento"),
                        rs.getString("observacao_geral")
                    );
                    
                    // Carrega serviços e produtos associados
                    List<OrdemServicoServico> servicos = listarServicos(id);
                    List<OrdemServicoProduto> produtos = listarProdutos(id);
                    
                    servicos.forEach(os::adicionarServico);
                    produtos.forEach(os::adicionarProduto);
                    
                    return os;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordem de serviço por ID", e);
        }
        return null;
    }

    @Override
    public List<OrdemServico> listarTodas() {
        List<OrdemServico> ordensServico = new ArrayList<>();
        String sql = "SELECT id, numero_os, id_usuario, cliente_id, data_criacao, data_fechamento, valor_total_produtos, valor_total_servicos, valor_total, valor_total_final, forma_pagamento, observacao_geral FROM ordem_servico ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                try {
                    UUID id = UUID.fromString(rs.getString("id"));
                    OrdemServico os = new OrdemServico(
                        id,
                        rs.getString("numero_os"),
                        rs.getTimestamp("data_criacao") != null ? rs.getTimestamp("data_criacao").toLocalDateTime() : null,
                        rs.getTimestamp("data_fechamento") != null ? rs.getTimestamp("data_fechamento").toLocalDateTime() : null,
                        rs.getString("id_usuario"),
                        rs.getString("cliente_id"),
                        rs.getDouble("valor_total_produtos"),
                        rs.getDouble("valor_total_servicos"),
                        rs.getDouble("valor_total"),
                        rs.getDouble("valor_total_final"),
                        rs.getString("forma_pagamento"),
                        rs.getString("observacao_geral")
                    );
                    
                    try {
                        List<OrdemServicoServico> servicos = listarServicos(id);
                        List<OrdemServicoProduto> produtos = listarProdutos(id);
                        
                        servicos.forEach(os::adicionarServico);
                        produtos.forEach(os::adicionarProduto);
                    } catch (Exception e) {
                        // Continua mesmo com erro, a OS será retornada sem serviços/produtos
                    }
                    
                    ordensServico.add(os);
                } catch (Exception e) {
                    // Continua para a próxima OS
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço", e);
        }
        return ordensServico;
    }

    @Override
    public void editar(OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET numero_os = ?, id_usuario = ?, cliente_id = ?, data_atualizacao = ?, descricao = ?, valor_total_produtos = ?, valor_total_servicos = ?, valor_total = ?, valor_total_final = ?, forma_pagamento = ?, observacao_geral = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServico.getNumeroOS());
            ps.setString(2, ordemServico.getId_usuario());
            ps.setString(3, ordemServico.getClienteId());
            ps.setTimestamp(4, ordemServico.getDataFechamento() != null ? Timestamp.valueOf(ordemServico.getDataFechamento()) : null);
            ps.setString(5, ordemServico.getObservacaoGeral());
            ps.setDouble(6, ordemServico.getValorTotalProdutos());
            ps.setDouble(7, ordemServico.getValorTotalServicos());
            ps.setDouble(8, ordemServico.getValorTotal());
            ps.setDouble(9, ordemServico.getValorTotalFinal());
            ps.setString(10, ordemServico.getFormaPagamento());
            ps.setString(11, ordemServico.getObservacaoGeral());
            ps.setString(12, ordemServico.getId().toString());
            ps.setString(4, ordemServico.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar ordem de serviço", e);
        }
    }

    @Override
    public void deletar(UUID id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Deleta serviços associados
            String deletServicos = "DELETE FROM ordem_servico_servicos WHERE ordem_servico_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deletServicos)) {
                ps.setString(1, id.toString());
                ps.executeUpdate();
            }

            // Deleta produtos associados
            String deletProdutos = "DELETE FROM ordem_servico_produtos WHERE ordem_servico_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deletProdutos)) {
                ps.setString(1, id.toString());
                ps.executeUpdate();
            }

            // Deleta a ordem
            String deletOrdem = "DELETE FROM ordem_servico WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deletOrdem)) {
                ps.setString(1, id.toString());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ordem de serviço", e);
        }
    }

    @Override
    public void adicionarServico(OrdemServicoServico servico) {
        String sql = "INSERT INTO ordem_servico_servicos (id, ordem_servico_id, servico_id, quantidade, valor_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getId().toString());
            ps.setString(2, servico.getOrdemServicoId().toString());
            ps.setString(3, servico.getServicoId().toString());
            ps.setInt(4, servico.getQuantidade());
            ps.setDouble(5, servico.getValorTotal());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar serviço à ordem", e);
        }
    }

    @Override
    public void removerServico(UUID ordemServicoId, UUID servicoId) {
        String sql = "DELETE FROM ordem_servico_servicos WHERE ordem_servico_id = ? AND servico_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            ps.setString(2, servicoId.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover serviço da ordem", e);
        }
    }

    @Override
    public List<OrdemServicoServico> listarServicos(UUID ordemServicoId) {
        List<OrdemServicoServico> servicos = new ArrayList<>();
        String sql = "SELECT oss.id, oss.ordem_servico_id, oss.servico_id, s.descricao, oss.quantidade, oss.valor_total " +
                     "FROM ordem_servico_servicos oss " +
                     "JOIN servicos s ON oss.servico_id = s.id " +
                     "WHERE oss.ordem_servico_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int quantidade = rs.getInt("quantidade");
                    double valorTotal = rs.getDouble("valor_total");
                    double valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
                    
                    servicos.add(new OrdemServicoServico(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        UUID.fromString(rs.getString("servico_id")),
                        rs.getString("descricao"),
                        quantidade,
                        valorUnitario,
                        valorTotal
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar serviços da ordem", e);
        }
        return servicos;
    }

    @Override
    public void adicionarProduto(OrdemServicoProduto produto) {
        String sql = "INSERT INTO ordem_servico_produtos (id, ordem_servico_id, produto_id, quantidade, valor_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getOrdemServicoId().toString());
            ps.setString(3, produto.getProdutoId().toString());
            ps.setInt(4, produto.getQuantidade());
            ps.setDouble(5, produto.getValorTotal());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar produto à ordem", e);
        }
    }

    @Override
    public void removerProduto(UUID ordemServicoId, UUID produtoId) {
        String sql = "DELETE FROM ordem_servico_produtos WHERE ordem_servico_id = ? AND produto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            ps.setString(2, produtoId.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover produto da ordem", e);
        }
    }

    @Override
    public List<OrdemServicoProduto> listarProdutos(UUID ordemServicoId) {
        List<OrdemServicoProduto> produtos = new ArrayList<>();
        String sql = "SELECT osp.id, osp.ordem_servico_id, osp.produto_id, p.nome, osp.quantidade, osp.valor_total " +
                     "FROM ordem_servico_produtos osp " +
                     "JOIN produtos p ON osp.produto_id = p.id " +
                     "WHERE osp.ordem_servico_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int quantidade = rs.getInt("quantidade");
                    double valorTotal = rs.getDouble("valor_total");
                    double valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
                    
                    produtos.add(new OrdemServicoProduto(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        UUID.fromString(rs.getString("produto_id")),
                        rs.getString("nome"),
                        quantidade,
                        valorUnitario,
                        valorTotal
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos da ordem", e);
        }
        return produtos;
    }

    @Override
    public void concluirOrdemServico(UUID ordemServicoId) {
        String sql = "UPDATE ordem_servico SET data_atualizacao = ?, data_fechamento = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            java.time.LocalDateTime agora = java.time.LocalDateTime.now();
            ps.setTimestamp(1, Timestamp.valueOf(agora));
            ps.setTimestamp(2, Timestamp.valueOf(agora));
            ps.setString(3, ordemServicoId.toString());
            
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao concluir ordem de serviço", e);
        }
    }
}
