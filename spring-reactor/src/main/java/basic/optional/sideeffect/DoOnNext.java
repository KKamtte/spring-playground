package basic.optional.sideeffect;

import reactor.core.publisher.Flux;

public class DoOnNext {
    public static void main(String[] args) {
        // doOnNext: 각 데이터가 발행될 때 실행이 된다.
        Flux.just("apple", "banana", "cherry")
                .doOnNext(data -> System.out.println("processing1: " + data))
                .map(String::toUpperCase)
                .doOnNext(data -> System.out.println("processing2: " + data))
                .subscribe(data -> System.out.println("result: " + data));
    }
}
