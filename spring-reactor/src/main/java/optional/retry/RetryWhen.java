package optional.retry;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryWhen {
    public static void main(String[] args) throws InterruptedException {
        // retryWhen: 재시도 전략을 수립

        AtomicInteger callCount = new AtomicInteger(0);
        long start = System.currentTimeMillis();

        Mono.defer(() -> {
                    int count = callCount.incrementAndGet();
                    long elapsed = System.currentTimeMillis() - start;

                    System.out.println("callCount: " + count + ", elapsed: " + elapsed);

                    if (count < 4) {
                        return Mono.error(new RuntimeException("temporary error"));
                    }

                    return Mono.just("success");
                })
                .retryWhen(Retry.fixedDelay(5, Duration.ofMillis(500)))
                .subscribe(
                        data -> System.out.println("data: " + data),
                        error -> System.out.println("error: " + error.getMessage())
                );

        Thread.sleep(3000);
    }
}
