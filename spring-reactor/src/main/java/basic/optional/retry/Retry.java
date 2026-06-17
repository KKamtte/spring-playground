package basic.optional.retry;

import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

public class Retry {
    public static void main(String[] args) {
        // retry: 에러가 발생하면 원본 퍼블리셔를 다시 구독

        AtomicInteger callCount = new AtomicInteger();

        Mono.defer(() -> {
                    int count = callCount.incrementAndGet();
                    System.out.println("call # " + count);

                    if (count < 3) {
                        return Mono.error(new RuntimeException("temporary error"));
                    }

                    return Mono.just("success");
                })
                .retry() // 무한으로 재시도해서 위험도가 존재
//                .retry(1) // retry 횟수가 정해져 있다면 해당 횟수까지만 수행하고 에러로 처리
                .subscribe(
                        data -> System.out.println("data: " + data),
                        throwable -> System.out.println("error: " + throwable.getMessage())
                );
    }
}
