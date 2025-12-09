package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;



public class PessoaRepository implements IPessoaRepository {
    
    @Override
    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoas (id, tipo_pessoa, nome, cpf_cnpj, telefone, email, cep, logradouro, numero, bairro, cidade, uf, status, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getId().toString());
            ps.setString(2, pessoa.getTipoPessoa());
            ps.setString(3, pessoa.getNome());
            ps.setString(4, pessoa.getCpfCnpj());
            ps.setString(5, pessoa.getTelefone());
            ps.setString(6, pessoa.getEmail());
            ps.setString(7, pessoa.getCep());
            ps.setString(8, pessoa.getLogradouro());
            ps.setString(9, pessoa.getNumero());
            ps.setString(10, pessoa.getBairro());
            ps.setString(11, pessoa.getCidade());
            ps.setString(12, pessoa.getUf());
            ps.setString(13, pessoa.getStatus());
            ps.setTimestamp(14, Timestamp.valueOf(pessoa.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pessoa", e);
        }
    }

    @Override
    public List<Pessoa> buscarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, tipo_pessoa, nome, cpf_cnpj, telefone, email, cep, logradouro, numero, bairro, cidade, uf, status, data_cadastro FROM pessoas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("tipo_pessoa"),
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cep"),
                    rs.getString("logradouro"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("uf"),
                    rs.getString("status"),
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
        String sql = "SELECT id, tipo_pessoa, nome, cpf_cnpj, telefone, email, cep, logradouro, numero, bairro, cidade, uf, status, data_cadastro FROM pessoas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("tipo_pessoa"),
                        rs.getString("nome"),
                        rs.getString("cpf_cnpj"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cep"),
                        rs.getString("logradouro"),
                        rs.getString("numero"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("uf"),
                        rs.getString("status"),
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

    @Override
    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoas SET tipo_pessoa = ?, nome = ?, cpf_cnpj = ?, telefone = ?, email = ?, cep = ?, logradouro = ?, numero = ?, bairro = ?, cidade = ?, uf = ?, status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getTipoPessoa());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getCpfCnpj());
            ps.setString(4, pessoa.getTelefone());
            ps.setString(5, pessoa.getEmail());
            ps.setString(6, pessoa.getCep());
            ps.setString(7, pessoa.getLogradouro());
            ps.setString(8, pessoa.getNumero());
            ps.setString(9, pessoa.getBairro());
            ps.setString(10, pessoa.getCidade());
            ps.setString(11, pessoa.getUf());
            ps.setString(12, pessoa.getStatus());
            ps.setString(13, pessoa.getId().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pessoa", e);
        }
    }
}