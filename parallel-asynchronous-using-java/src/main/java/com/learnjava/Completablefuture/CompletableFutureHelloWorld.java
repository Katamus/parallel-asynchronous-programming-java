package com.learnjava.Completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    HelloWorldService hws = new HelloWorldService();

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<Integer> helloWorld_withSize (){
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::length);
    }

    public String helloWorld_approach1(){
        String hello = hws.hello();
        String world = hws.world();
        return hello+world;
    }

    public String helloworld_multiple_async_calls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
        String hw = hello
                .thenCombine(world, (h,w)->h+w)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public String helloworld_3_async_calls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });

        String hw = hello
                .thenCombine(world, (h,w)->h+w)
                .thenCombine(hiCompletableFuture, (previos,current)->previos + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public String helloWorld_4_async_calls(){
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });

        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String result = hello
                .thenCombine(world, (s, s2) -> s + s2)
                .thenCombine(hiCompletableFuture, (s, s3) -> s + s3)
                .thenCombine(byeCompletableFuture, (s, s4) -> s + s4)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return result;
    }

    public CompletableFuture<String> helloWorld_theCompose(){
        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose((previous)->hws.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept((result)->{
                    log("Result is " + result);
                })
                .join();
        log("Done!");
        //delay(2000);
    }

}
