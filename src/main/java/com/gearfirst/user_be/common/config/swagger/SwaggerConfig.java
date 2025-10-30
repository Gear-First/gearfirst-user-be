package com.gearfirst.user_be.common.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("/user");

        return new OpenAPI()
                .info(new Info()
                        .title("gearfirst")
                        .description("gear first REST API Document")
                        .version("1.0.0"))
                .addServersItem(server);
    }
}
