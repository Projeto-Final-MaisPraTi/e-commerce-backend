package com.ecommerce.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
// import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication // se for acessar apenas login sem fazer get e set

// desativa a segurança padrão caso não haja necessidade de autenticação na API
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
// @ComponentScan(basePackages = {"com.ecommerce.app"})
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
