package com.frode.lien.SysProgUppgift1.controller;

import com.frode.lien.SysProgUppgift1.model.ColorVariant;
import com.frode.lien.SysProgUppgift1.model.Product;
import com.frode.lien.SysProgUppgift1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3010")
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("products/hats")
    public List<Product> getHats() {
        String category = "hats";
        return productService.getProductsFromCategory(category);
    }

    @GetMapping("products/jackets")
    public List<Product> getJackets() {
        String category = "jackets";
        return productService.getProductsFromCategory(category);
    }

    @GetMapping("products/tshirts")
    public List<Product> getTshirts() {
        String category = "tshirts";
        return productService.getProductsFromCategory(category);
    }

    @GetMapping("products/bags")
    public List<Product> getBags() {
        String category = "bags";
        return productService.getProductsFromCategory(category);
    }

    @GetMapping("products/{id}")
    public Product getSpecificProduct(@PathVariable int id) {
        return productService.getSpecificProductWithId(id);
    }

    //Optional
    @PostMapping("products/hats")
    public ResponseEntity<Product> postHats(@RequestBody Product product) {
        String category = "hats";
        Product newProduct = productService.postProductToDatabaseWithCategory(product, category);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PostMapping("products/jackets")
    public ResponseEntity<Product> postJackets(@RequestBody Product product) {
        String category = "jackets";
        Product newProduct = productService.postProductToDatabaseWithCategory(product, category);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PostMapping("products/tshirts")
    public ResponseEntity<Product> postTshirts(@RequestBody Product product) {
        String category = "tshirts";
        Product newProduct = productService.postProductToDatabaseWithCategory(product, category);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PostMapping("products/bags")
    public ResponseEntity<Product> postBags(@RequestBody Product product) {
        String category = "bags";
        Product newProduct = productService.postProductToDatabaseWithCategory(product, category);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<String> updateSpecificProduct(@PathVariable int id,
                                                        @RequestBody Product productMeta) {
        int result;
        result = productService.updateSpecificProductMetaData(id, productMeta);
        switch (result) {
            case 0:
                return ResponseEntity.status(406).body("Request unsuccessful");
            case 1:
                return ResponseEntity.ok().build();
            case -9:
                return ResponseEntity.badRequest().body("No product with this id exists");
            default:
                return ResponseEntity.internalServerError().body("Something went wrong ");
        }
    }

    @PostMapping("products/{id}/variants")
    public ResponseEntity createNewVariantForSpecificProduct(@PathVariable int id,
                                                             @RequestBody ColorVariant colorVariantBody) {
        return ResponseEntity.status(201).body(productService.addColorVariant(id, colorVariantBody));
    }

    @PutMapping("products/{id}/variants/stock")
    public ResponseEntity restockSpecificSizeOfVariant(@PathVariable int id,
                                                       @RequestParam(required = false) String size,
                                                       @RequestParam(required = false) String color,
                                                       @RequestParam(required = false) int quantity) {
        int result = productService.restockSpecificSizeOfVariant(id, size, color, quantity);
        return result == 1 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id) {
        int result = productService.deleteProduct(id);
        return result == 1 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("products/{productId}/variants/{variantId}")
    public void deleteVariantOfProduct(@PathVariable("productId") int productId,
                                       @PathVariable("variantId") int variantId) {
        // this is wrong in the API_SPEC do last
        // @PathVariable("variantId") int variantId must be
    }


}
