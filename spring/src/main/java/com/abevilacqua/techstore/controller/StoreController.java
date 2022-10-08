package com.abevilacqua.techstore.controller;

import com.abevilacqua.techstore.model.Product;
import com.abevilacqua.techstore.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/products")
@Slf4j
public class StoreController {

    private final ProductRepo productRepo;

    @Autowired
    public StoreController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getProducts() {
        return new ResponseEntity<>(productRepo.findAll(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") final String id) {
        return productRepo.findById(Long.valueOf(id))
                .map(product -> new ResponseEntity<>(product, OK))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(productRepo.save(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(productRepo.save(product), OK);
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<Product> updateProductDescription(@PathVariable("id") final String id,
                                                            @RequestBody final String description) {
        var optProduct = productRepo.findById(Long.valueOf(id));
        if(optProduct.isPresent()) {
            log.info("Changing product {}",optProduct.get());
            optProduct.get().setDescription(description);
            productRepo.save(optProduct.get());
            return new ResponseEntity<>(optProduct.get(), OK);
        } else {
            log.info("Product with id {} not found", id);
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") final long id) {
        if(productRepo.findById(id).isPresent()){
            productRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(NOT_FOUND);
    }
}
