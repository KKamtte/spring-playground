import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Switching {

    public static void main(String[] args) {
        // Flux -> Mono: collectList로 모든 데이터를 List로 수집
        Mono<List<Integer>> listMono = Flux.just(1, 2, 3, 4, 5)
                .collectList();
        listMono.subscribe(list -> System.out.println(list));

        // Flux -> Mono: next 로 첫번째 데이터만 가져옴
        Mono<String> fistMono = Flux.just("first", "second", "third")
                .next();
        fistMono.subscribe(System.out::println);

        // Mono -> Flux: flux() 로 변환
        Flux<String> fromMono = Mono.just("one")
                .flux();
        fromMono.subscribe(System.out::println);

        // Flux -> Mono: count로 데이터 개수
        Mono<Long> countMono = Flux.range(1, 100).count();
        countMono.subscribe(System.out::println);
    }
}
