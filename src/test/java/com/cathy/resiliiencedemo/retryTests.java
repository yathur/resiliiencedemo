package com.cathy.resiliiencedemo;

import com.cathy.resiliiencedemo.services.HelloWorldService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;
import java.util.function.Supplier;


@RunWith(SpringRunner.class)
@SpringBootTest
public class retryTests {

    @Autowired
    HelloWorldService helloWorldService;

    @Test
    public void retry_hello() {
        // Create a Retry with at most 3 retries and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("helloWorld");

        // Decorate your call with automatic retry
        Supplier<String> decoratedSupplier = Retry
                .decorateSupplier(retry, helloWorldService::sayHello);

        // Execute the decorated supplier and recover from any exception
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();
        System.out.println(result);
    }

    @Test
    public void retry_helloname() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("helloWorld");
        // Create a Retry with at most 3 retries and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("helloWorld");

        // Decorate your call with automatic retry
        Function<String,String> decoratedSupplier = Retry
                .decorateFunction(retry, helloWorldService::sayHelloWithName);

        // Execute the decorated supplier and recover from any exception
        Try<String> result = Try.of(() -> decoratedSupplier.apply("Tom"));
        System.out.println(result);
    }

}
