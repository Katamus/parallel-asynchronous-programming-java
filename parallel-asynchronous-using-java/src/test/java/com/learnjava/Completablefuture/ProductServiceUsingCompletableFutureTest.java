package com.learnjava.Completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();

    ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(productInfoService,reviewService,inventoryService);
    @Test
    void retrieveProductDetails() {
        //given
        String productId = "ABC123";

        //When
        Product product = pscf.retrieveProductDetails(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventory() {
        //given
        String productId = "ABC123";

        //When
        Product product = pscf.retrieveProductDetailsWithInventory(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2() {
        String productId = "ABC123";
        pscf.retrieveProductDetails_approach2(productId).thenAccept(product -> {
            assertNotNull(product);
            assertTrue(product.getProductInfo().getProductOptions().size() > 0);
            assertTrue(product.getProductInfo().getProductOptions().size() > 0);
            assertNotNull(product.getReview());
        }).join();

    }
}