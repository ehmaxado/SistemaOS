package sistema.os;

import io.javalin.Javalin;
import sistema.os.API.Routes.PessoaRoutes;
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

        System.out.println("API rodando em http://localhost:8080/api/pessoas");
    }
}