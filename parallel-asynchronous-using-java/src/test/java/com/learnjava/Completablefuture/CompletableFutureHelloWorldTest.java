package com.learnjava.Completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
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

    @Test
    void helloWorld_4_async_calls() {

        //give

        //when
        String helloWorld = cfhw.helloWorld_4_async_calls();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE BYE!",helloWorld);

    }

    @Test
    void helloWorld_theCompose() {
        //give
        startTimer();
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_theCompose();

        //then
        completableFuture.thenAccept(s->{
            assertEquals("HELLO WORLD!",s);
        });

        timeTaken();
    }

    @Test
    void helloworld_3_async_calls_log() {

        //when
        String helloWorld = cfhw.helloworld_3_async_calls_log();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",helloWorld);

    }

    @Test
    void helloworld_3_async_calls_custom_threadpool() {

        //when
        String helloWorld = cfhw.helloworld_3_async_calls_custom_threadpool();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",helloWorld);

    }


}