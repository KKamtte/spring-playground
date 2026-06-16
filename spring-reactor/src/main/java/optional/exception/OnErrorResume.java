package optional.exception;

import reactor.core.publisher.Flux;

public class OnErrorResume {
    public static void main(String[] args) {
        // onErrorResume: 에러가 발생했을때 다른 Publisher 로 전환
        Flux.just(1, 2, 0, 4, 5)
                .map(n -> 10 / n)
                .onErrorResume(e -> {
                    System.out.println("error occurred: " + e.getMessage());
                    return Flux.just(100, 200, 300);
                })
                .subscribe(
                        data -> System.out.println("data: " + data),
                        error -> System.out.println("error occurred: " + error.getMessage()),
                        () -> System.out.println("done")
                );
    }
}
