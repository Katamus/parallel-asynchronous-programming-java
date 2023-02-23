 package com.learnjava.Completablefuture;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

 public class ProductServiceUsingCompletableFuture {
     private ProductInfoService productInfoService;
     private ReviewService reviewService;
     private InventoryService inventoryService;

     public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
         this.productInfoService = productInfoService;
         this.reviewService = reviewService;
     }

     public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,InventoryService inventoryService) {
         this.productInfoService = productInfoService;
         this.reviewService = reviewService;
         this.inventoryService = inventoryService;
     }

     public Product retrieveProductDetails(String productId)  {
         stopWatch.start();

         CompletableFuture<ProductInfo> cfProductoInfo = CompletableFuture.supplyAsync(
                 () -> productInfoService.retrieveProductInfo(productId)
         );

         CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(
                 () -> this.reviewService.retrieveReviews(productId)
         );

         Product product = cfProductoInfo.thenCombine(cfReview,(productInfo,review)->new Product(productId, productInfo, review))
                 .join();



         stopWatch.stop();
         log("Total Time Taken : "+ stopWatch.getTime());
         return product;
     }

     public CompletableFuture<Product> retrieveProductDetails_approach2(String productId)  {

         CompletableFuture<ProductInfo> cfProductoInfo = CompletableFuture.supplyAsync(
                 () -> productInfoService.retrieveProductInfo(productId)
         );

         CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(
                 () -> this.reviewService.retrieveReviews(productId)
         );

         return cfProductoInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review));

     }


     public Product retrieveProductDetailsWithInventory(String productId)  {
         stopWatch.start();

         CompletableFuture<ProductInfo> cfProductoInfo = CompletableFuture.supplyAsync(
                 () -> productInfoService.retrieveProductInfo(productId))
                 .thenApply(productInfo -> {
                     productInfo.setProductOptions(updateInventory(productInfo));
                     return productInfo;
                 });

         CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(
                 () -> this.reviewService.retrieveReviews(productId));

         Product product = cfProductoInfo
                 .thenCombine(cfReview,(productInfo,review)->new Product(productId, productInfo, review))
                 .join();

         stopWatch.stop();
         log("Total Time Taken : "+ stopWatch.getTime());
         return product;
     }

     private List<ProductOption> updateInventory(ProductInfo productInfo) {
         List<ProductOption> productOptionLists = productInfo.getProductOptions()
                 .stream()
                 .map(productOption -> {
                     Inventory inventory = inventoryService.addInventory(productOption);
                     productOption.setInventory(inventory);
                     return productOption;
                 })
                 .collect(Collectors.toList());
         return productOptionLists;
     }


     public static void main(String[] args) throws InterruptedException {

         ProductInfoService productInfoService = new ProductInfoService();
         ReviewService reviewService = new ReviewService();
         ProductService productService = new ProductService(productInfoService, reviewService);
         String productId = "ABC123";
         Product product = productService.retrieveProductDetails(productId);
         log("Product is " + product);

     }

 }
