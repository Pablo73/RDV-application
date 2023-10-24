package com.project.rdv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.project.rdv.models.entity")
@EnableJpaRepositories("com.project.rdv.models.repository")
@ComponentScan("com.project.rdv")
public class RdvApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdvApplication.class, args);
	}

}
