package mono;

import reactor.core.publisher.Mono;

public class Chaining {

    public static void main(String[] args) {
        Mono<String> originMono = Mono.just("hello");

        Mono<String> upperMono = originMono.map(String::toUpperCase);
        upperMono.subscribe(s -> System.out.println("Uppercase: " + s));

        Mono<Integer> length = Mono.just("hello reactor")
                .map(s -> s.toUpperCase()) // T -> R
                .map(s -> s.length());

        length.subscribe(s -> System.out.println("Length: " + s));

        Mono<String> result = Mono.just("1")
                .flatMap(userId -> findUserName(userId)); // T -> Mono<R>
        result.subscribe(data -> System.out.println("Result: " + data));

        // 만약 map 이라면 <Mono<Mono<String>>> 이 나온다.
        Mono<Mono<String>> map = Mono.just("2")
                .map(userId -> findUserName(userId));
        map.subscribe(data -> System.out.println("Result: " + data));
    }

    static Mono<String> findUserName(String userId) {
        if (userId.equals("1")) {
            return Mono.just("jackson");
        } else if (userId.equals("2")) {
            return Mono.just("samuel");
        } else {
            return Mono.empty();
        }
    }
}
