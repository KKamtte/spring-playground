package basic.mono;

import reactor.core.publisher.Mono;

public class Defer {

    static boolean featureEnabled = false;

    public static void main(String[] args) throws InterruptedException {
        // defer: Mono 자체의 생성을 지연
        Mono<Long> deferedMono = Mono.defer(() -> {
            System.out.println("created...");
            return Mono.just(System.currentTimeMillis());
        });

        System.out.println("Mono created");
        deferedMono.subscribe(time -> System.out.println("first subscribe: " + time));

        Thread.sleep(1000);

        System.out.println("1 sec later");
        deferedMono.subscribe(time -> System.out.println("second subscribe: " + time));


        Mono<String> conditionMono = Mono.defer(() -> {
            if (featureEnabled) {
                return Mono.just("Feature enabled");
            } else {
                return Mono.empty();
            }
        });

        System.out.println("Feature enabled: " + featureEnabled);
        conditionMono.subscribe(
                result -> System.out.println("Result: " + result),
                error -> System.out.println("Error: " + error),
                () -> System.out.println("Completed without emitting any value")
        );

        featureEnabled = true;
        conditionMono.subscribe(
                result -> System.out.println("Result: " + result),
                error -> System.out.println("Error: " + error),
                () -> System.out.println("Completed without emitting any value")
        );
    }
}
