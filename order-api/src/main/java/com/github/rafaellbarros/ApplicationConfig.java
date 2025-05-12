package com.github.rafaellbarros;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@OpenAPIDefinition(
        info = @Info(
                title = "Order API",
                version = "1.0",
                description = "API para gerenciamento de pedidos",
                contact = @Contact(
                        name = "Sua Equipe",
                        url = "http://seusite.com",
                        email = "contato@seusite.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "Localhost",
                        url = "http://localhost:8080/order-api"
                )
        }
)
@ApplicationPath("/api")
public class ApplicationConfig extends Application {


}