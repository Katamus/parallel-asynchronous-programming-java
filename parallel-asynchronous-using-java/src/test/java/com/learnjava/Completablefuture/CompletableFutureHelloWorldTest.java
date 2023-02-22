package com.learnjava.Completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();
    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

    @Test
    void helloWorld_approach1() {
        String helloWorld = cfhw.helloWorld_approach1();
        assertEquals("HELLO WORLD!",helloWorld.toUpperCase());
    }

    @Test
    void helloworld_multiple_async_calls() {
        //give

        //when
        String helloWorld = cfhw.helloworld_multiple_async_calls();

        //then
        assertEquals("HELLO WORLD!",helloWorld);


    }

    @Test
    void helloworld_3_async_calls() {
        //give

        //when
        String helloWorld = cfhw.helloworld_3_async_calls();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",helloWorld);


    }
}