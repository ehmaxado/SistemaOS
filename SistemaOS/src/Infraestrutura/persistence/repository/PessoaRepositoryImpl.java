package Infraestrutura.persistence.repository;

import java.sql.*;

import Infraestrutura.persistence.DatabaseConnection;
import domain.Entidades.Pessoa;
import domain.Enums.TipoPessoa;
import domain.Interfaces.IPessoaRepository;
import domain.ValueObjects.CpfCnpj;
import domain.ValueObjects.Telefone;

public class PessoaRepositoryImpl implements IPessoaRepository {
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
    public Pessoa buscarPorCpfCnpj(String cpfCnpj) {
        String sql = "SELECT * FROM pessoas WHERE cpf_cnpj = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpfCnpj);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pessoa(
                    rs.getString("nome"),
                    new CpfCnpj(rs.getString("cpf_cnpj")),
                   
                    new Telefone(rs.getString("telefone")),
                    TipoPessoa.valueOf(rs.getString("tipo"))
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa", e);
        }
    }
}