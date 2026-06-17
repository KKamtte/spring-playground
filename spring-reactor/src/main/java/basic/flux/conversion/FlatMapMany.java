package basic.flux.conversion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FlatMapMany {
    public static void main(String[] args) {
        // flatMapMany: Mono(1개) -> Flux(N개) 로 확장할 때 사용
        // flatMap 은 Mono -> Mono 이지만, flatMapMany 는 Mono -> Flux
        Mono.just("hello")
                .flatMapMany(s -> Flux.fromArray(s.split("")))
                .subscribe(System.out::println);

        // 단건 조회(userId) 결과로 다건 스트림(orders) 을 이어받을 때
        Mono.just("user1")
                .flatMapMany(userId -> findOrdersByUserId(userId))
                .subscribe(System.out::println);
    }

    static Flux<String> findOrdersByUserId(String userId) {
        return Flux.just("order#1001", "order#1002", "order#1003");
    }
}
