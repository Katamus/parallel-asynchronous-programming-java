 package com.learnjava.Completablefuture;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;
import lombok.extern.java.Log;

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
         List<ProductOption> productOptionList = productInfo.getProductOptions()
                 .stream()
                 .map(productOption -> {
                     Inventory inventory = inventoryService.retrieveInventory(productOption);
                     productOption.setInventory(inventory);
                     return productOption;
                 })
                 .collect(Collectors.toList());
         return productOptionList;
     }

     private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
         List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                 .stream()
                 .map(productOption -> {
                     return CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                             .exceptionally(throwable -> {
                                 log("Error Inventory " + throwable.getMessage());
                                 return Inventory.builder().count(1).build();
                             })
                             .thenApply(inventory -> {
                                 productOption.setInventory(inventory);
                                 return productOption;
                             })
                             ;
                 })
                 .collect(Collectors.toList());

         return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
     }

     public Product retrieveProductDetailsWithInventory_approach2(String productId) {
         stopWatch.start();

         CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
                 .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                 .thenApply(productInfo -> {
                     productInfo.setProductOptions(updateInventory_approach2(productInfo));
                     return productInfo;
                 });


         CompletableFuture<Review> cfReview = CompletableFuture
                 .supplyAsync(() -> reviewService.retrieveReviews(productId))
                 .exceptionally((e) -> {
                     log("Handled the Exception in reviewService : " + e.getMessage());
                     return Review.builder()
                             .noOfReviews(0).overallRating(0.0)
                             .build();
                 });

         Product product = cfProductInfo
                 .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                 .whenComplete((product1,throwable)->{
                     log("Inside whenComplete: "+productId+"and the exception is "+ throwable);
                 })
                 .join(); //block the thread

         stopWatch.stop();
         log("Total Time Taken : " + stopWatch.getTime());
         return product;
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
