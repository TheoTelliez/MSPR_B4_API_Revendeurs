package xyz.msprpayetonkawa.apirevendeur;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;

@SpringBootApplication

public class ApiRevendeurApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRevendeurApplication.class, args);
	}

	@Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REVENDEURS")
                        .description("API for Revendeurs")
                        .version("0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("https:/wiki...."));
    }

}
