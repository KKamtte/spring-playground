package basic.flux.aggretation;

import reactor.core.publisher.Flux;

public class Scan {
    public static void main(String[] args) {
        // reduce: 최종 결과만 출력
        Flux.just(1,2,3,4,5)
                .reduce((acc, next) -> acc + next)
                .subscribe(System.out::println);

        // scan: 중간 누적값을 매 단계마다 방출 → 1, 3, 6, 10, 15 출력
        Flux.just(1,2,3,4,5)
                .scan((acc, next) -> acc + next)
                .subscribe(System.out::println);
    }
}
