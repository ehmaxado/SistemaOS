package sistema.os;

import io.javalin.Javalin;
import sistema.os.API.Routes.PessoaRoutes;
import sistema.os.API.Routes.FormaPagamentoRoutes;
import sistema.os.API.Routes.PagamentoRoutes;
import sistema.os.Infraestrutura.persistence.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco (cria tabela se não existir)
        DatabaseConnection.DatabaseInitializer.inicializar();

        // Inicia a API
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
        }).start(8080);

        // Registra rotas
        new PessoaRoutes().register(app);
        new FormaPagamentoRoutes().register(app);
        new PagamentoRoutes().register(app);

        System.out.println("API rodando em http://localhost:8080");
        System.out.println("Rotas disponíveis:");
        System.out.println("  - /api/pessoas");
        System.out.println("  - /api/formas-pagamento");
        System.out.println("  - /api/pagamentos");
    }
}