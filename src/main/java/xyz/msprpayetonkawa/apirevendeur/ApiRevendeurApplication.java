package xyz.msprpayetonkawa.apirevendeur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiRevendeurApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRevendeurApplication.class, args);
	}
	
	@Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("API CRM")
                        .description("API for CRM")
                        .version("0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("https:/wiki...."));
    }

}
