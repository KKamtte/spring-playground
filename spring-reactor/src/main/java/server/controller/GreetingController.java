package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello WebFlux");
    }

    @GetMapping("/items")
    public Flux<String> items() {
        return Flux.just("apple", "banana", "cherry");
    }

    @GetMapping("/list")
    public Mono<List<String>> itemsList() {
        return Flux.just("apple", "banaba", "cherry").collectList();
    }
}
