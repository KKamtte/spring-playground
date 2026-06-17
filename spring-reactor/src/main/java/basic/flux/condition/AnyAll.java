package basic.flux.condition;

import reactor.core.publisher.Flux;

public class AnyAll {
    public static void main(String[] args) {
        Flux.just("apple", "banana", "orange")
                .any(fruit -> fruit.equals("banana"))
                .subscribe(has -> System.out.println("[banana] has element: " + has));

        Flux.just("apple", "banana", "orange")
                .any(fruit -> fruit.equals("cherry"))
                .subscribe(has -> System.out.println("[cherry] has element: " + has));

        Flux<Integer> numbers = Flux.just(2, 4, 6, 8, 10);

        // any: 하나라도 조건을 만족하면 true
        numbers.any(n -> n > 7)
                .subscribe(has -> System.out.println("[n > 7] has element: " + has));

        numbers.any(n -> n > 100)
                .subscribe(has -> System.out.println("[n > 100] has element: " + has));

        // all: 모두 조건을 만족해야 true
        numbers.all(n -> n % 2 == 0)
                .subscribe(has -> System.out.println("[n % 2] has element: " + has));

        numbers.all(n -> n > 3)
                .subscribe(has -> System.out.println("[n > 3] has element: " + has));
    }
}
