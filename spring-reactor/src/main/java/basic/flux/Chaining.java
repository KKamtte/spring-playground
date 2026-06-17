package basic.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Chaining {

    public static void main(String[] args) {
        Flux<String> upperCase = Flux.just("apple", "orange", "banana")
                .map(String::toUpperCase);
        upperCase.subscribe(System.out::println);

        Flux<Integer> length = Flux.just("hello", "world", "reactor")
                .map(String::toUpperCase)
                .map(String::length);
        length.subscribe(System.out::println);

        Flux<String> result = Flux.just("1", "2", "3")
                .flatMap(userId -> findUserName(userId));
        result.subscribe(System.out::println);

        Flux<String> expanded = Flux.just("apple", "orange", "banana")
                .flatMap(word -> Flux.fromArray(word.split("")));
        expanded.subscribe(System.out::println);

        Flux<Integer> even = Flux.range(1, 10)
                .filter(i -> i % 2 == 0);
        even.subscribe(System.out::println);

        Flux<String> startA = Flux.just("apple", "avocado", "banana", "apricot", "orange")
                .filter(s -> s.startsWith("a"))
                .map(String::toUpperCase);
        startA.subscribe(System.out::println);
    }

    static Mono<String> findUserName(String userId) {
        return Mono.just("User-" + userId);
    }
}
