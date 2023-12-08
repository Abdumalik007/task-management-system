package com.taskmanagement.system.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Abdumalik",
                        email = "abdumalik.java@gmail.com",
                        url = "https://github.com/Abdumalik007"
                ),
                description = "OpenApi Documentation for Task Management System",
                title = "OpenApi specification",
                version = "1.0",
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = @Server(
                description = "Local env",
                url = "http://localhost:8080/api"
        ),
        security = @SecurityRequirement(
                name = "bearerAuth"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "This is for JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
