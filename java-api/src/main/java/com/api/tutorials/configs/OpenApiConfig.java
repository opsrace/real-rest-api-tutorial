package com.api.tutorials.configs;

import com.api.tutorials.dtos.ExceptionResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Localhost Server URL");

        Contact contact = new Contact()
                .email("info(at)opsrace.com")
                .name("Mazhar Hassan");

        Info info = new Info()
                .contact(contact)
                .description("Spring Boot 3 + Open API 3")
                .summary("Demo of Spring Boot 3 & Open API 3 Integration")
                .title("Sample REST API")
                .version("V1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        return new OpenAPI().info(info).
                addServersItem(localServer);
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        ResolvedSchema errResSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ExceptionResponse.class));

        Content content = new Content().addMediaType("application/json", new MediaType().schema(errResSchema.schema));
        return openApi -> openApi.getPaths().values()
                        .forEach(pathItem -> pathItem.readOperations()
                                .forEach(operation -> operation.getResponses()
                                        .addApiResponse("400", new ApiResponse().description("Bad Request").content(content))
                                        .addApiResponse("404", new ApiResponse().description("Resource Not Found").content(content))));
    }
}