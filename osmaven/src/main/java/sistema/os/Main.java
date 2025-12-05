package sistema.os;

import io.javalin.Javalin;
import sistema.os.API.Routes.PessoaRoutes;
import sistema.os.API.Routes.ServicoRoutes;
import sistema.os.API.Routes.ProdutoRoutes;
import sistema.os.API.Routes.OrdemServicoRoutes;
import sistema.os.Infraestrutura.persistence.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco (cria tabelas se não existirem)
        DatabaseConnection.DatabaseInitializer.inicializar();

        // Inicia a API
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
        }).start(8080);

        // Registra rotas
        new PessoaRoutes().register(app);
        new ServicoRoutes().register(app);
        new ProdutoRoutes().register(app);
        new OrdemServicoRoutes().register(app);

        System.out.println("API rodando em http://localhost:8080");
        System.out.println("Endpoints disponíveis:");
        System.out.println("  POST   /api/pessoas");
        System.out.println("  GET    /api/servicos");
        System.out.println("  GET    /api/produtos");
        System.out.println("  GET    /api/ordens-servico");
    }
}