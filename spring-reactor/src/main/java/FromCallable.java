import reactor.core.publisher.Mono;

public class FromCallable {

    public static void main(String[] args) {
        // fromCallable: checked exception 을 던질 수 있음
        Mono<String> callableMono = Mono.fromCallable(() -> {
            if (Math.random() > 0.5) {
                throw new Exception("Random exception occurred");
            }

            return "success";
        });

        callableMono.subscribe(
                result -> System.out.println("결과: " + result),
                error -> System.err.println("에러: " + error.getMessage())
        );
    }
}
