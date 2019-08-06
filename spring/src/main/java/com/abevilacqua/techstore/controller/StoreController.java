package com.abevilacqua.techstore.controller;

import com.abevilacqua.techstore.model.Product;
import com.abevilacqua.techstore.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class StoreController {

    private ProductRepo productRepo;

    @Autowired
    public StoreController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getProducts() {
        return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") final String id) {
        return productRepo.findById(Long.valueOf(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(productRepo.save(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(productRepo.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") final long id) {
        if(productRepo.findById(id).isPresent()){
            productRepo.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
