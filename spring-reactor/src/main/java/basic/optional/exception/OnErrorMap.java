package basic.optional.exception;

import reactor.core.publisher.Flux;

public class OnErrorMap {
    public static void main(String[] args) {
        // onErrorMap: 에러를 다른 에러로 변환
        Flux.just(1, 2, 0, 4, 5)
                .map(n -> 10 / n)
                .onErrorMap(ArithmeticException.class,
                        e -> new IllegalArgumentException("cannot divide by zero", e))
                .subscribe(
                        System.out::println,
                        error -> {
                            System.out.println("error message: " + error.getMessage());
                            System.out.println("error type: " + error.getClass().getSimpleName());
                        }
                );
    }
}
