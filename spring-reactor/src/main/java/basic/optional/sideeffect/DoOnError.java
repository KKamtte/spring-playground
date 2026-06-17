package basic.optional.sideeffect;

import reactor.core.publisher.Flux;

public class DoOnError {
    public static void main(String[] args) {
        // doOnError: 에러 신호가 발생했을 때 트리거
//        Flux.just(1, 2, 0, 4)
//                .map(n -> 10 / n)
//                .doOnError(error -> System.out.println("error: " + error.getMessage()))
//                .subscribe(
//                        data -> System.out.println("data: " + data),
//                        error -> System.out.println("subscribe error:" + error.getMessage())
//                );

        Flux.just(1, 2, 0, 4)
                .map(n -> 10 / n)
                .doOnError(error -> System.out.println("error: " + error.getMessage()))
                .doOnSubscribe(sub -> System.out.println("doOnSubscribe")) // 구독이 시작될 때 트리거
                .doOnCancel(() -> System.out.println("doOnCancel")) // 구독이 취소될 때 트리거
                .doOnEach(signal -> System.out.println("doOnEach: " + signal)) // 데이터, 에러, 완료 등 모든 종류의 신호
                .doFirst(() -> System.out.println("doFirst")) // 구독 직전
                .doFinally(signalType -> System.out.println("doFinally: " + signalType)) // 스트림이 종료될 때 (어떤 이유에서든)
                .onErrorReturn(-1)
                .subscribe(
                        data -> System.out.println("data: " + data),
                        error -> System.out.println("subscribe error:" + error.getMessage()),
                        () -> System.out.println("done")
                );
    }
}
