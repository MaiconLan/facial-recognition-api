package br.com.zapelini.lanzendorf.facialrecognitionapi;

import br.com.zapelini.lanzendorf.facialrecognitionapi.config.property.FacialRecognitionApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(FacialRecognitionApiProperty.class)
public class FacialRecognitionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacialRecognitionApiApplication.class, args);
	}


}
