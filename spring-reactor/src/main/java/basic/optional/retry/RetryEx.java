package basic.optional.retry;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryEx {
    public static void main(String[] args) throws InterruptedException {
        Mono.defer(() -> {
            System.out.println("External API Call...");
            return Mono.<String>error(new RuntimeException("Service Error"));
        })
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(200)))
                .onErrorResume(error -> { // retryExhaustedException 이 발생하고 onErrorResume 에서 처리
                    System.out.println("Max retry, return cache data");
                    return Mono.just("cached data");
                })
                .subscribe(data -> System.out.println("result: " + data));

        Thread.sleep(1000);
    }
}
