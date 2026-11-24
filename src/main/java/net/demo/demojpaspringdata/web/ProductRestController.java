package net.demo.demojpaspringdata.web;

import net.demo.demojpaspringdata.entities.Product;
import net.demo.demojpaspringdata.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    private final ProductRepository productRepository;

    // Injection des dependances par constructeur (recommande)
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Consulter tous les produits
    @GetMapping("/products")
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    // Chercher des produits par mot-cle : /api/products/find?keyword=pr
    @GetMapping("/products/find")
    public List<Product> findProducts(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return productRepository.findByNameContains(keyword);
    }

    // Consulter un produit : /api/products/1
    @GetMapping("/products/{id}")
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // Ajouter un produit
    @PostMapping("/products")
    public Product save(@RequestBody Product product) {
        product.setId(null); // l'id est genere automatiquement
        return productRepository.save(product);
    }

    // Mettre a jour un produit
    @PutMapping("/products/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }

    // Supprimer un produit
    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
