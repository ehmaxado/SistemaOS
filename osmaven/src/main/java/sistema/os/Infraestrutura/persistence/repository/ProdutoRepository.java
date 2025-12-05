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
        String sql = "INSERT INTO produtos (id, nome, descricao, preco, estoque, data_cadastro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setDouble(4, produto.getPreco());
            ps.setInt(5, produto.getEstoque());
            ps.setTimestamp(6, Timestamp.valueOf(produto.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    @Override
    public Produto buscarPorId(UUID id) {
        String sql = "SELECT id, nome, descricao, preco, estoque, data_cadastro FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque"),
                        rs.getTimestamp("data_cadastro").toLocalDateTime()
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
        String sql = "SELECT id, nome, descricao, preco, estoque, data_cadastro FROM produtos ORDER BY data_cadastro DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque"),
                    rs.getTimestamp("data_cadastro").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return produtos;
    }

    @Override
    public void editar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getEstoque());
            ps.setString(5, produto.getId().toString());
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
