package za.tomvuma.lostandfoundhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lost and Found Hub API")
                        .version("1.0")
                        .description("API documentation for Lost and Found Hub")
                        .contact(new Contact().name("Tom Vuma").email("tom@example.com"))
                        .license(new License().name("MIT").url("https://choosealicense.com/licenses/mit/")));
    }
}