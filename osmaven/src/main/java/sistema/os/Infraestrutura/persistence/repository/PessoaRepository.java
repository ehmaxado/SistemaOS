package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;



public class PessoaRepository implements IPessoaRepository {
    
    // Persiste pessoa no banco de dados
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
}