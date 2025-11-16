package API.Controller;

import API.view.MenuView;
import Application.UseCase.CriarPessoaUseCase;
import Infraestrutura.persistence.DatabaseConnection.DatabaseInitializer;
import Infraestrutura.persistence.repository.PessoaRepositoryImpl;
import domain.Entidades.Pessoa;
import domain.Interfaces.IPessoaRepository;

public class PessoaController {
    private final CriarPessoaUseCase useCase;
    private final MenuView view;

    public PessoaController() {
        IPessoaRepository repo = new PessoaRepositoryImpl();
        this.useCase = new CriarPessoaUseCase(repo);
        this.view = new MenuView();
    }

    public void iniciar() {
        while (true) {
            view.mostrarMenu();
            String opcao = view.scanner.nextLine();

            if ("1".equals(opcao)) {
                cadastrarPessoa();
            } else if ("2".equals(opcao)) {
                view.mostrarEncerramento();
                break;
            } else {
                view.mostrarErro("Opção inválida!");
            }
        }
    }

    private void cadastrarPessoa() {
        try {
            String[] dados = view.capturarDadosPessoa();
            Pessoa pessoa = useCase.executar(dados[0], dados[1], dados[2], dados[3]);
            view.mostrarSucesso(pessoa.getId().toString());
        } catch (Exception e) {
            view.mostrarErro(e.getMessage());
        }
    }

    // MAIN LIMPO: só inicializa e roda
    public static void main(String[] args) {
        DatabaseInitializer.inicializar();  // ← Banco aqui!
        new PessoaController().iniciar();
    }
}