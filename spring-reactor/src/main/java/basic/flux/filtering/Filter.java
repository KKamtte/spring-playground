package basic.flux.filtering;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Filter {

    public static void main(String[] args) {
        // 조건에 따른 필터링 연산자
        // filter; 동기적 filter 처리
        Flux.just(
                new Product("Notebook", 1500000, true),
                new Product("mouse", 35000, true),
                new Product("keyboard", 890000, false),
                new Product("monitor", 45000, true))
                .filter(p -> p.inStock())
                .filter(p -> p.price() <= 50000)
                .subscribe(p -> System.out.println(p.name() + " - " + p.price() + "원"));

        // filterWhen: 비동기적 filter 처리
        Flux.just("user-1", "user-2", "user-3", "user-4")
                .filterWhen(userId -> hasPermission(userId))
                .subscribe(System.out::println);
    }

    record Product(String name, int price, boolean inStock) {
    }

    static Mono<Boolean> hasPermission(String userId) {
        return Mono.just(userId.equals("user-1") || userId.equals("user-3"));
    }
}
