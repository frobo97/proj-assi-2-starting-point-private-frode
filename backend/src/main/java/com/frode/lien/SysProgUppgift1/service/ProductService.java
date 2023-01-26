package com.frode.lien.SysProgUppgift1.service;

import com.frode.lien.SysProgUppgift1.model.ColorVariant;
import com.frode.lien.SysProgUppgift1.model.Product;
import com.frode.lien.SysProgUppgift1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productDataBase;

    public List<Product> getAllProducts() {
        return productDataBase.selectAll();
    }

    public List<Product> getProductsFromCategory(String category) {
        return productDataBase.selectAllOfCategory(category);
    }

    public Product getSpecificProductWithId(int id) {
        return productDataBase.getEntireProduct(id);
    }

    public Product postProductToDatabaseWithCategory(Product product, String category) {
        return productDataBase.insertProductAndProps(product, category);
    }

    public int updateSpecificProductMetaData(int id, Product productMeta) {
        return productDataBase.updateProductMeta(id, productMeta);
    }

    public int restockSpecificSizeOfVariant(int id, String size,String color,int quantity) {
        return productDataBase.restockSize(id, size, color, quantity);
    }

    public ColorVariant  addColorVariant(int id, ColorVariant colorVariantBody) {
        return productDataBase.addVariant(id, colorVariantBody);
    }

    public int deleteProduct(int id) {
        return productDataBase.deleteProduct(id);
    }

    public void deleteVariantFromDatabase() {
        // this is wrong in the API_SPEC do last
    }

}
