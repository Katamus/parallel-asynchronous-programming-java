package com.learnjava.Completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {

    HelloWorldService hws = new HelloWorldService();

    public CompletableFutureHelloWorldException(HelloWorldService hws) {
        this.hws = hws;
    }



    public String helloworld_3_async_calls_handle(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });

        String hw = hello
                .handle((res,e)->{
                    if(e != null){
                        log("Exception is :"+ e.getMessage());
                        return "";
                    }
                    return res;
                })
                .thenCombine(world, (h,w)->h+w)
                .handle((res,e)->{
                    if(e != null){
                        log("Exception after world is :"+ e.getMessage());
                        return "";
                    }
                    return res;

                })
                .thenCombine(hiCompletableFuture, (previos,current)->previos + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public String helloworld_3_async_calls_exceptionally(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });

        String hw = hello
                .exceptionally((e)->{
                        log("Exception is :"+ e.getMessage());
                        return "";
                })
                .thenCombine(world, (h,w)->h+w)
                .exceptionally((e)->{
                        log("Exception after world is :"+ e.getMessage());
                        return "";

                })
                .thenCombine(hiCompletableFuture, (previos,current)->previos + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }


}
