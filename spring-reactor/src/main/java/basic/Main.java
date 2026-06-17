package basic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        monoExample();
        fluxExample();
        coldSequenceExample();
        hotSequenceExample();
        emptyExample();
        errorExample();
        rangeExample();
    }

    static void monoExample() {
        // Mono: 하나의 데이터
        Mono<String> mono = Mono.just("Hello Reactor");
        mono.subscribe(data -> System.out.println("mono data: " + data));
    }

    static void fluxExample() {
        // Flux: 여러개의 데이터
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        flux.subscribe(data -> System.out.println("flux data: " + data));
    }

    static void coldSequenceExample() {
        // 콜드 시퀀스: 구독하지 않으면 아무일도 일어나지 않는다. (VOD)
        // 구독할 때마다 데이터를 처음부터 독립적으로 발행
        Flux<Integer> coldFlux = Flux.just(1, 2, 3);

        coldFlux.subscribe(data -> System.out.println("cold subscriber1: " + data));
        coldFlux.subscribe(data -> System.out.println("cold subscriber2: " + data));
        // subscriber1, subscriber2 모두 1, 2, 3을 처음부터 받음
    }

    static void hotSequenceExample() {
        // 핫 시퀀스: 구독 여부와 무관하게 데이터가 발행, 구독 이후 데이터만 수신 (라이브 방송)
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<String> hotFlux = sink.asFlux();

        sink.tryEmitNext("Event 1"); // 아직 구독자 없음 -> 아무도 못 받음

        hotFlux.subscribe(data -> System.out.println("hot subscriber1: " + data));

        sink.tryEmitNext("Event 2"); // subscriber1만 받음
        sink.tryEmitNext("Event 3"); // subscriber1만 받음

        hotFlux.subscribe(data -> System.out.println("hot subscriber2: " + data));

        sink.tryEmitNext("Event 4"); // subscriber1, subscriber2 모두 받음

        sink.tryEmitComplete();
    }

    static void emptyExample() {
        // 빈 Mono 또는 Flux 는 바로 완료 처리가 된다.
        Mono<String> emptyMono = Mono.empty();
        emptyMono.subscribe(
                data -> System.out.println("data = " + data),
                error -> System.err.println("error: " + error.getMessage()),
                () -> System.out.println("Complete")
        );
    }

    static void errorExample() {
        Mono<String> errorMono = Mono.error(new RuntimeException("error occurred"));
        errorMono.subscribe(
                data -> System.out.println("data = " + data),
                error -> System.err.println("error: " + error.getMessage()),
                () -> System.out.println("Complete")
        );
    }

    static void rangeExample() {
        // range: 범위를 표현할 수 있다.
        Flux<Integer> rangeFlux = Flux.range(1, 5);
        rangeFlux.subscribe(System.out::println);

        // List: Flux 에서 List 받아서 구독할 수 있다.
        List<String> list = List.of("a", "b", "c");
        Flux<String> fromList =  Flux.fromIterable(list);
        fromList.subscribe(System.out::println);

        // Array: Flux 에서 Array 받아서 구독할 수 있다.
        String[] array = {"a", "b", "c"};
        Flux<String> fromArray = Flux.fromArray(array);
        fromArray.subscribe(System.out::println);

    }
}
