package net.demo.demojpaspringdata;

import net.demo.demojpaspringdata.entities.Product;
import net.demo.demojpaspringdata.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoJpaSpringDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJpaSpringDataApplication.class, args);
    }

    // Execute au demarrage de l'application
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            // 1re facon : constructeur sans parametres + setters
            Product p1 = new Product();
            p1.setName("Computer");
            p1.setPrice(4300);
            p1.setQuantity(2);
            productRepository.save(p1);

            // 2e facon : constructeur avec tous les parametres
            Product p2 = new Product(null, "Printer", 1200, 4);
            productRepository.save(p2);

            // 3e facon : pattern Builder (genere par Lombok)
            Product p3 = Product.builder()
                    .name("Smartphone")
                    .price(3200)
                    .quantity(11)
                    .build();
            productRepository.save(p3);

            // Affichage de la liste des produits
            List<Product> products = productRepository.findAll();
            products.forEach(p -> System.out.println(p.toString()));
        };
    }
}
