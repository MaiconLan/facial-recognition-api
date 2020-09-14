package br.com.zapelini.lanzendorf.facialrecognitionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class FacialRecognitionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacialRecognitionApiApplication.class, args);
	}


}
