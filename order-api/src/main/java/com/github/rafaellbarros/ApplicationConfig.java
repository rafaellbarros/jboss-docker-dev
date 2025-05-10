package com.github.rafaellbarros;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Produtos",
                version = "1.0.0",
                description = "API para gerenciamento de produtos"
        ),
        servers = {
                @Server(url = "/order-api", description = "Servidor Local")
        }
)
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
}