package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;

public class ProdutoRepository implements IProdutoRepository {

    @Override
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (id, nome, descricao, marca, unidade, estoque_atual, valor_custo, valor_venda, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setString(4, produto.getMarca());
            ps.setString(5, produto.getUnidade());
            ps.setInt(6, produto.getEstoqueAtual());
            ps.setDouble(7, produto.getValorCusto());
            ps.setDouble(8, produto.getValorVenda());
            ps.setBoolean(9, produto.isAtivo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    @Override
    public Produto buscarPorId(UUID id) {
        String sql = "SELECT id, nome, descricao, marca, unidade, estoque_atual, valor_custo, valor_venda, ativo FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("marca"),
                        rs.getString("unidade"),
                        rs.getInt("estoque_atual"),
                        rs.getDouble("valor_custo"),
                        rs.getDouble("valor_venda"),
                        rs.getBoolean("ativo")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID", e);
        }
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, marca, unidade, estoque_atual, valor_custo, valor_venda, ativo FROM produtos ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("marca"),
                    rs.getString("unidade"),
                    rs.getInt("estoque_atual"),
                    rs.getDouble("valor_custo"),
                    rs.getDouble("valor_venda"),
                    rs.getBoolean("ativo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return produtos;
    }

    @Override
    public void editar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, marca = ?, unidade = ?, estoque_atual = ?, valor_custo = ?, valor_venda = ?, ativo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getMarca());
            ps.setString(4, produto.getUnidade());
            ps.setInt(5, produto.getEstoqueAtual());
            ps.setDouble(6, produto.getValorCusto());
            ps.setDouble(7, produto.getValorVenda());
            ps.setBoolean(8, produto.isAtivo());
            ps.setString(9, produto.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar produto", e);
        }
    }

    @Override
    public void deletar(UUID id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto", e);
        }
    }
}
