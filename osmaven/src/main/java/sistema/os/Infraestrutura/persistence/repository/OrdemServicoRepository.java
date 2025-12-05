package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoProduto;
import sistema.os.domain.Enums.StatusOrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;

public class OrdemServicoRepository implements IOrdemServicoRepository {

    @Override
    public void salvar(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico (id, pessoa_cliente_id, pessoa_prestador_id, status, data_criacao, data_atualizacao, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServico.getId().toString());
            ps.setString(2, ordemServico.getPessoaClienteId().toString());
            ps.setString(3, ordemServico.getPessoaPrestadorId().toString());
            ps.setString(4, ordemServico.getStatus().name());
            ps.setTimestamp(5, Timestamp.valueOf(ordemServico.getDataCriacao()));
            ps.setTimestamp(6, Timestamp.valueOf(ordemServico.getDataAtualizacao()));
            ps.setString(7, ordemServico.getDescricao());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ordem de serviço", e);
        }
    }

    @Override
    public OrdemServico buscarPorId(UUID id) {
        String sql = "SELECT id, pessoa_cliente_id, pessoa_prestador_id, status, data_criacao, data_atualizacao, descricao FROM ordem_servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrdemServico os = new OrdemServico(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("pessoa_cliente_id")),
                        UUID.fromString(rs.getString("pessoa_prestador_id")),
                        StatusOrdemServico.valueOf(rs.getString("status")),
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getTimestamp("data_atualizacao").toLocalDateTime(),
                        rs.getString("descricao")
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
        String sql = "SELECT id, pessoa_cliente_id, pessoa_prestador_id, status, data_criacao, data_atualizacao, descricao FROM ordem_servico ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                OrdemServico os = new OrdemServico(
                    id,
                    UUID.fromString(rs.getString("pessoa_cliente_id")),
                    UUID.fromString(rs.getString("pessoa_prestador_id")),
                    StatusOrdemServico.valueOf(rs.getString("status")),
                    rs.getTimestamp("data_criacao").toLocalDateTime(),
                    rs.getTimestamp("data_atualizacao").toLocalDateTime(),
                    rs.getString("descricao")
                );
                
                List<OrdemServicoServico> servicos = listarServicos(id);
                List<OrdemServicoProduto> produtos = listarProdutos(id);
                
                servicos.forEach(os::adicionarServico);
                produtos.forEach(os::adicionarProduto);
                
                ordensServico.add(os);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço", e);
        }
        return ordensServico;
    }

    @Override
    public void editar(OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET status = ?, data_atualizacao = ?, descricao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServico.getStatus().name());
            ps.setTimestamp(2, Timestamp.valueOf(ordemServico.getDataAtualizacao()));
            ps.setString(3, ordemServico.getDescricao());
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
        String sql = "INSERT INTO ordem_servico_servicos (id, ordem_servico_id, servico_id, valor_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servico.getId().toString());
            ps.setString(2, servico.getOrdemServicoId().toString());
            ps.setString(3, servico.getServicoId().toString());
            ps.setDouble(4, servico.getValorUnitario());
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
        String sql = "SELECT id, ordem_servico_id, servico_id, valor_unitario FROM ordem_servico_servicos WHERE ordem_servico_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    servicos.add(new OrdemServicoServico(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        UUID.fromString(rs.getString("servico_id")),
                        rs.getDouble("valor_unitario")
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
        String sql = "INSERT INTO ordem_servico_produtos (id, ordem_servico_id, produto_id, valor_unitario, quantidade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getOrdemServicoId().toString());
            ps.setString(3, produto.getProdutoId().toString());
            ps.setDouble(4, produto.getValorUnitario());
            ps.setInt(5, produto.getQuantidade());
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
        String sql = "SELECT id, ordem_servico_id, produto_id, valor_unitario, quantidade FROM ordem_servico_produtos WHERE ordem_servico_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ordemServicoId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    produtos.add(new OrdemServicoProduto(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("ordem_servico_id")),
                        UUID.fromString(rs.getString("produto_id")),
                        rs.getDouble("valor_unitario"),
                        rs.getInt("quantidade")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos da ordem", e);
        }
        return produtos;
    }
}
