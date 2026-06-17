package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import server.domain.Product;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/first")
    public Mono<Product> getFirst() {
        return Mono.just(new Product("1", "notebook", 1500000));
    }

    @GetMapping
    public Flux<Product> getAll() {
        return Flux.just(
                new Product("1", "notebook", 1500000),
                new Product("2", "mouse", 50000),
                new Product("3", "keyboard", 120000)
        );
    }

    @GetMapping("/available")
    public Mono<ResponseEntity<Product>> getAvailable() {
        boolean inStock = false;

        Mono<Product> found = inStock
                ? Mono.just(new Product("1", "notebook", 1500000))
                : Mono.empty();

        return found
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
