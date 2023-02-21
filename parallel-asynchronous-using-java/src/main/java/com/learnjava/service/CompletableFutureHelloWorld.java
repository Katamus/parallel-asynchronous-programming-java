package com.learnjava.service;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture.supplyAsync(()->helloWorldService.helloWorld())
                .thenAccept((result)->{
                    log("Result is " + result);
                })
                .join();
        log("Done!");
        //delay(2000);
    }

}
