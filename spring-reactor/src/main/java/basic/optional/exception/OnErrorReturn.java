package basic.optional.exception;

import reactor.core.publisher.Flux;

public class OnErrorReturn {
    public static void main(String[] args) {
        // onErrorReturn: 에러가 발생했을 때, 지정된 대체 값을 반환

        Flux.just(1, 2, 0, 4, 5)
                .map(n -> 10 / n)
                .onErrorReturn(-1)
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Done")
                );

        Flux.just(1, 2, 0, 4, 5)
                .map(n -> 10 / n)
                // 특정 Exception 을 명시하였을때, 해당하는 경우에만 -1 을 반환
//                .onErrorReturn(ArithmeticException.class, -1)
                .onErrorReturn(NullPointerException.class, -1)
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Done")
                );
    }
}
