 package com.learnjava.Completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

import java.util.concurrent.*;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

 public class ProductServiceUsingCompletableFuture {
     private ProductInfoService productInfoService;
     private ReviewService reviewService;

     public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
         this.productInfoService = productInfoService;
         this.reviewService = reviewService;
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

     public static void main(String[] args) throws InterruptedException {

         ProductInfoService productInfoService = new ProductInfoService();
         ReviewService reviewService = new ReviewService();
         ProductService productService = new ProductService(productInfoService, reviewService);
         String productId = "ABC123";
         Product product = productService.retrieveProductDetails(productId);
         log("Product is " + product);

     }

 }
