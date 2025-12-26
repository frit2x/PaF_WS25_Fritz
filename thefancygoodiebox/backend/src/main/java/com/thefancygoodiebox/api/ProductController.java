
package com.thefancygoodiebox.api;

import com.thefancygoodiebox.infrastructure.ProductEntity;
import com.thefancygoodiebox.infrastructure.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ProductEntity> all() {
        return repo.findAll();
    }
}
