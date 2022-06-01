package com.LuanGabriel.polinomium;

import com.LuanGabriel.polinomium.domain.Usuario;
import com.LuanGabriel.polinomium.repository.UsuarioRepository;
import com.LuanGabriel.polinomium.service.UsuarioService;

import org.springframework.boot.SpringApplication;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class PolinomiumApplication {

	public static void main(String[] args) {

		SpringApplication.run(PolinomiumApplication.class, args);
		
		UsuarioRepository usuarioRepository;
	
		Usuario user = new Usuario();


		System.out.println("teste");
	
	}

	@Bean
  	public Docket docket() {
    return new Docket(DocumentationType.OAS_30)
        .apiInfo(new ApiInfoBuilder()
            .title("DemoDs3 2021 API")
            .description("Uma API REST para o Trabalho PP ")
            .version("0.0.1-SNAPSHOT")
            .license("MIT")
            .licenseUrl("https://opensource.org/licenses/MIT")
            .build())
        .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .build();
  }

}
 