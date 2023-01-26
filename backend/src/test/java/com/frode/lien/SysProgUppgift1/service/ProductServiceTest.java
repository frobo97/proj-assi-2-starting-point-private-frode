package com.frode.lien.SysProgUppgift1.service;

import com.frode.lien.SysProgUppgift1.TestUtility;
import com.frode.lien.SysProgUppgift1.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ProductServiceTest extends TestUtility {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        ALL_PRODUCTS.addAll(testSelectAll());
        PRODUCT_WITH_CATEGORY.addAll(testSelectAllOfCategory(CATEGORY));
    }
    
    @Test
    void getAllProducts() {
        Mockito.when(productRepository.selectAll()).thenReturn(ALL_PRODUCTS);

        assertThat(productService.getAllProducts()).isEqualTo(ALL_PRODUCTS);

        Mockito.verify(productRepository, times(1)).selectAll();
    }

    @Test
    void getProductsFromCategory() {
        Mockito.when(productRepository.selectAllOfCategory(CATEGORY)).thenReturn(PRODUCT_WITH_CATEGORY);

        assertThat(productService.getProductsFromCategory(CATEGORY)).isEqualTo(PRODUCT_WITH_CATEGORY);

        Mockito.verify(productRepository, times(1)).selectAllOfCategory(CATEGORY);
    }

    @Test
    void getSpecificProductWithId() {
        Mockito.when(productRepository.getEntireProduct(ID)).thenReturn(PRODUCT_WITH_ID);

        assertThat(productService.getSpecificProductWithId(ID)).isEqualTo(PRODUCT_WITH_ID);

        Mockito.verify(productRepository, times(1)).getEntireProduct(ID);
    }
}