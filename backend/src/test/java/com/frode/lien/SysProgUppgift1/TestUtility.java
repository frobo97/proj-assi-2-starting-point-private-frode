package com.frode.lien.SysProgUppgift1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frode.lien.SysProgUppgift1.model.ColorVariant;
import com.frode.lien.SysProgUppgift1.model.Product;
import com.frode.lien.SysProgUppgift1.model.SizeContainer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestUtility extends H2TestDatabase {

    protected final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected static List<Product> ALL_PRODUCTS;
    protected static List<Product> ALL_PRODUCTS_BODY;
    protected static List<Product> PRODUCT_WITH_CATEGORY;
    protected static Product PRODUCT_WITH_ID;
    protected static String CATEGORY = null;
    protected static int ID;


    @BeforeAll
    protected static void beforeAllSetUp() {
        ALL_PRODUCTS = new ArrayList<>();
        ALL_PRODUCTS_BODY = new ArrayList<>();
        PRODUCT_WITH_CATEGORY = new ArrayList<>();
        PRODUCT_WITH_ID = new Product();
        CATEGORY = "hats";
        ID = 3;
    }

    private record VariantWImages(int id, String colorName, String imagesCsv) {
    }

    public List<Product> testSelectAll() {
        return selectAll(null);
    }

    /**
     * Reads all rows from the products table and returns them as a List of Products.
     */
    public List<Product> selectAll(String category) {
        String sql = "SELECT * FROM products";

        // NOTE: leave this line as it is!
        if (category != null) sql += " WHERE category = (:category)";

        RowMapper<Product> rm = (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("category"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("preview_image"));

        // NOTE: leave the rest as it is!
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("category", category);
        return new NamedParameterJdbcTemplate(jdbcTemplate).query(sql, paramMap, rm);
    }

    /**
     * Only calls selectAll(String category) with a specific category.
     * Useful for reading all products of a specific category.
     */
    public List<Product> testSelectAllOfCategory(String category) {
        return selectAll(category);
    }
    /**
     * Reads rows from products, variants, images, and sizes,
     * constructs ONE Product object from them, and returns it.
     */
    public Product testGetEntireProduct(int productId) {
        Product product = getProductBase(productId);
        if (product == null) {
            System.out.println("Product wasn't fetched from the db");
            return null;
        }
        List<VariantWImages> variantsWImages = getVariantsAndImages(productId);
        System.out.println("variantsWImages = " + variantsWImages);
        for (VariantWImages variantWImages : variantsWImages) {
            ColorVariant colorVariant = new ColorVariant();
            colorVariant.colorName = variantWImages.colorName;
            colorVariant.sizes = getVariantSizes(variantWImages.id);
            try {
                colorVariant.setImagesFromCSV(variantWImages.imagesCsv);
            } catch (Exception e) {
                colorVariant.images = new ArrayList<>();
                System.out.println("Exception parsing images from csv; see stack trace");
                e.printStackTrace();
            }
            product.colorVariants.add(colorVariant);
        }

        return product;
    }

    /**
     * Utility function used in other methods.
     * Only reads a product's metadata.
     */
    private Product getProductBase(int productId) {
        RowMapper<Product> rm = (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("category"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("preview_image"));
        var sql = "SELECT * FROM products WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, rm, productId);
        return products.size() > 0 ? products.get(0) : null;
    }

    /**
     * Utility method used in getEntireProduct().
     * Read rows from variants and images tables.
     */
    public List<VariantWImages> getVariantsAndImages(int productId) {
        var sql = """
                SELECT v.id AS v_id, v.color_name,
                	STRING_AGG(url, ',') images
                FROM variants AS v
                LEFT OUTER JOIN images AS i1
                ON v.id = i1.variant_id
                WHERE v.product_id = ?
                GROUP BY v_id, v.color_name
                """;
        RowMapper<VariantWImages> rm = (rs, rowNum) -> new VariantWImages(
                rs.getInt("v_id"),
                rs.getString("color_name"),
                rs.getString("images"));
        return jdbcTemplate.query(sql, rm, productId);
    }

    // NOTE: NO NEED TO MODIFY THIS METHOD!

    /**
     * Utility method used in getEntireProduct().
     * Reads a row from sizes table.
     */
    public List<SizeContainer> getVariantSizes(int variantId) {
        var sql = """
                SELECT size, stock
                FROM variants v
                INNER JOIN sizes AS s
                ON v.id = s.variant_id
                WHERE v.id = ?
                """;
        RowMapper<SizeContainer> rm = (rs, rowNum) -> new SizeContainer(
                rs.getString("size"),
                rs.getInt("stock"));
        return jdbcTemplate.query(sql, rm, variantId);
    }
}
