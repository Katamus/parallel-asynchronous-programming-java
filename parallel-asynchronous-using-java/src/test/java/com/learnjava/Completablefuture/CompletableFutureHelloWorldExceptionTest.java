package com.learnjava.Completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException hwcfe;

    @Test
    void helloworld_3_async_calls_handle() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException(" Exception Runtime"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.helloworld_3_async_calls_handle();

        assertEquals(" WORLD! HI COMPLETABLEFUTURE",result);

    }

    @Test
    void helloworld_3_async_calls_handle_2() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException(" Exception Runtime"));
        when(helloWorldService.world()).thenThrow(new RuntimeException(" Exception Runtime"));

        String result = hwcfe.helloworld_3_async_calls_handle();

        assertEquals(" HI COMPLETABLEFUTURE",result);

    }
}