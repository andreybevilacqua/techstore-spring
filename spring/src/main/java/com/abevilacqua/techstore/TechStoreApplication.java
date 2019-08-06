package com.abevilacqua.techstore;

import com.abevilacqua.techstore.repository.ProductRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.abevilacqua.techstore.configuration.DBInitializer.initializeDB;

@SpringBootApplication
public class TechStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechStoreApplication.class, args);
	}

	@Bean
	public ApplicationRunner run(ProductRepo productRepo) {
		return initializeDB(productRepo);
	}

}
