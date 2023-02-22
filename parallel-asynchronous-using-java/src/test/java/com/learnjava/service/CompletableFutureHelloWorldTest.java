package com.learnjava.service;

import com.learnjava.Completablefuture.CompletableFutureHelloWorld;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();
    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);
    @Test
    void helloWorld() {

        CompletableFuture<String> completableFuture = cfhw.helloWorld();

        completableFuture.thenAccept(s->{
            assertEquals("HELLO WORLD",s);
        })
        .join();
    }

    @Test
    void helloWorld_withSize() {
        CompletableFuture<Integer> completableFuture = cfhw.helloWorld_withSize();

        completableFuture.thenAccept(s->{
                    assertEquals(11,s);
                })
                .join();
    }
}