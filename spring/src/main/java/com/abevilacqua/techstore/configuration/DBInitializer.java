package com.abevilacqua.techstore.configuration;

import com.abevilacqua.techstore.model.Product;
import com.abevilacqua.techstore.repository.ProductRepo;
import org.springframework.boot.ApplicationRunner;

import java.util.stream.Stream;

public final class DBInitializer {

    public static ApplicationRunner initializeDB(ProductRepo productRepo) {
        return args -> Stream.of(
                new Product(1, "Mouse", "Really good mouse", 20),
                new Product(2, "Notebook", "Great notebook", 1000),
                new Product(3, "Keyboard", "Great keyboard", 300),
                new Product(4, "Headphones", "Great headphones", 60),
                new Product(5, "Touchscreen", "Great screen", 500))
                .forEach(productRepo::save);
    }
}
