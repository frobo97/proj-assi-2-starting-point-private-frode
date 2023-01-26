package com.frode.lien.SysProgUppgift1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.frode.lien.SysProgUppgift1.TestUtility;
import com.frode.lien.SysProgUppgift1.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest extends TestUtility {

    @LocalServerPort
    private final int port = 19876;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ProductRepository productRepository;

    final String baseUrl = "http://localhost:" + port + "/api/v1/";

    @BeforeEach
    void setUp() {
        ALL_PRODUCTS.addAll(testSelectAll());
        PRODUCT_WITH_CATEGORY.addAll(testSelectAllOfCategory(CATEGORY));
    }

    @Test
    void getAllProducts() throws JsonProcessingException {
        Mockito.when(productRepository.selectAll()).thenReturn(ALL_PRODUCTS);
        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity(baseUrl + "products", Object.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(OBJECT_MAPPER.writeValueAsString(responseEntity.getBody())).isEqualTo(OBJECT_MAPPER.writeValueAsString(ALL_PRODUCTS));
    }

    @Test
    void getProductsFromCategoryHats() throws JsonProcessingException {
        Mockito.when(productRepository.selectAllOfCategory(CATEGORY)).thenReturn(PRODUCT_WITH_CATEGORY);
        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity(baseUrl + "products/hats", Object.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(OBJECT_MAPPER.writeValueAsString(responseEntity.getBody())).isEqualTo(OBJECT_MAPPER.writeValueAsString(PRODUCT_WITH_CATEGORY));
    }
}