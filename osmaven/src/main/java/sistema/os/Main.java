package sistema.os;

import io.javalin.Javalin;
import sistema.os.API.Routes.PessoaRoutes;
import sistema.os.API.Routes.FormaPagamentoRoutes;
import sistema.os.API.Routes.PagamentoRoutes;
import sistema.os.API.Routes.UsuarioRoutes;
import sistema.os.API.Routes.AutenticacaoRoutes;
import sistema.os.API.Routes.ProdutoRoutes;
import sistema.os.API.Routes.ServicoRoutes;
import sistema.os.API.Routes.OrdemServicoRoutes;
import sistema.os.Infraestrutura.persistence.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco (cria tabela se não existir)
        DatabaseConnection.DatabaseInitializer.inicializar();
        
        // Corrige a constraint da tabela ordem_servico
        DatabaseConnection.DatabaseInitializer.corrigirConstraintOrdemServico();
        
        // Adiciona coluna data_fechamento se não existir
        DatabaseConnection.DatabaseInitializer.adicionarColunaDataFechamento();

        // Inicia a API
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
            // Configuração de CORS
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
        }).start(8080);

        // Registra rotas
        new PessoaRoutes().register(app);
        new FormaPagamentoRoutes().register(app);
        new PagamentoRoutes().register(app);
        new UsuarioRoutes().register(app);
        new AutenticacaoRoutes().register(app);
        new ProdutoRoutes().register(app);
        new ServicoRoutes().register(app);
        new OrdemServicoRoutes().register(app);

        System.out.println("API rodando em http://localhost:8080");
        System.out.println("Rotas disponíveis:");
        System.out.println("  - /api/pessoas");
        System.out.println("  - /api/formas-pagamento");
        System.out.println("  - /api/pagamentos");
        System.out.println("  - /api/usuarios");
        System.out.println("  - /api/login");
        System.out.println("  - /api/produtos");
        System.out.println("  - /api/servicos");
        System.out.println("  - /api/ordens-servico");
    }
}