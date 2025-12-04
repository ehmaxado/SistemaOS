package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Enums.StatusPessoa;
import sistema.os.domain.Enums.TipoPessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;
import sistema.os.domain.ValueObjects.CpfCnpj;
import sistema.os.domain.ValueObjects.Telefone;



public class PessoaRepository implements IPessoaRepository {
    
    @Override
    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoas (id, nome, cpf_cnpj, telefone, tipo, status, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getId().toString());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getCpfCnpj().getValor());
            ps.setString(4, pessoa.getTelefone().getValor());
            ps.setString(5, pessoa.getTipo().name());
            ps.setString(6, pessoa.getStatus().name());
            ps.setTimestamp(7, Timestamp.valueOf(pessoa.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pessoa", e);
        }
    }

    @Override
    public List<Pessoa> buscarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, nome, cpf_cnpj, telefone, tipo, status, data_cadastro FROM pessoas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    new CpfCnpj(rs.getString("cpf_cnpj")),
                    new Telefone(rs.getString("telefone")),
                    TipoPessoa.valueOf(rs.getString("tipo")),
                    StatusPessoa.valueOf(rs.getString("status")),
                    rs.getTimestamp("data_cadastro").toLocalDateTime()
                );
                pessoas.add(pessoa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoas", e);
        }
        
        return pessoas;
    }

    @Override
    public Pessoa buscarPorId(UUID id) {
        String sql = "SELECT id, nome, cpf_cnpj, telefone, tipo, status, data_cadastro FROM pessoas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        new CpfCnpj(rs.getString("cpf_cnpj")),
                        new Telefone(rs.getString("telefone")),
                        TipoPessoa.valueOf(rs.getString("tipo")),
                        StatusPessoa.valueOf(rs.getString("status")),
                        rs.getTimestamp("data_cadastro").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa", e);
        }
        
        return null;
    }

    @Override
    public boolean deletar(UUID id) {
        String sql = "DELETE FROM pessoas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            int linhasAfetadas = ps.executeUpdate();
            
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pessoa", e);
        }
    }
}