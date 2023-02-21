package com.learnjava.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();
    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld();
    @Test
    void helloWorld() {

        CompletableFuture<String> completableFuture = cfhw.helloWorld();

        completableFuture.thenAccept(s->{
            assertEquals("HELLO WORLD",s);
        })
        .join();
    }
}