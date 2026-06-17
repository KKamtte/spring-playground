package basic.flux;

import reactor.core.publisher.Flux;

public class Generate {
    public static void main(String[] args) {
        // generate: 프로그래밍방식으로 상태를 유지하면서 데이터를 하나씩 동기식으로 생성
        Flux<Integer> generated = Flux.generate(
                () -> 0, // 초기상태 (supplier)
                (state, sink) -> { // BiFunction
                    sink.next(state); // 데이터 발행
                    if (state == 4) {
                        sink.complete(); // 완료 신호
                    }
                    return state + 1; // 다음 상태
                }
        );

        generated.subscribe(System.out::println);
    }
}
