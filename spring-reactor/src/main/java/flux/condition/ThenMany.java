package flux.condition;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ThenMany {
    public static void main(String[] args) {
        // 원본데이터를 무시, 완료 후 다른 Mono 실행

        /*
         *
         * 처리 중: 저장1
         * 처리 중: 저장2
         * 처리 중: 저장3
         * 결과: 모든 저장 완료
         * ---
         * 실행: 작업1
         * 실행: 작업2
         * 모든 작업 완료
         *
         */
        Flux.just("save1", "save2", "save3")
                .doOnNext(data -> System.out.println("processing: " + data))
                .then(Mono.just("done"))
                .subscribe(result -> System.out.println("result: " + result));

        // then() 인자없이 호출하면 Mono<Void> 반환
        Flux.just("job1", "job2")
                .doOnNext(data -> System.out.println("processing: " + data))
                .then()
                .subscribe(
                        data -> {
                        },
                        error -> {
                        },
                        () -> System.out.println("done")
                );

        /*
         * 초기화: 초기화1
         * 초기화: 초기화2
         * 수신: 결과 A
         * 수신: 결과 B
         * 수신: 결과 C
         */
        // thenMany: 원본 완료 후 다른 Flux 실행
        Flux.just("init1", "init2")
                .doOnNext(data -> System.out.println("initializing: " + data))
                .thenMany(Flux.just("result A", "result B", "result C"))
                .subscribe(result -> System.out.println("result: " + result));
    }
}
