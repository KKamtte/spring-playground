package flux.conversion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class FlatMap {
    public static void main(String[] args) throws InterruptedException {
        // flatMap: 작업 순서가 보장되지 않는다.
        long start = System.currentTimeMillis();
        Flux.just("A", "B", "C")
                .flatMap(s -> Mono.just(s).delayElement(Duration.ofSeconds(1)))
                .map(s -> s + " complete")
                .subscribe(
                        data -> {
                            long elaspesed = System.currentTimeMillis() - start;
                            System.out.println(data + " - " + elaspesed + "ms");
                        }
                );
        Thread.sleep(3000);

        // concurrency=2: 동시에 최대 2개의 inner publisher 만 구독 -> 나머지는 대기
        // 6개를 2개씩 처리하므로 총 3초 소요 (무제한이면 1초에 6개 동시 처리)
        long start2 = System.currentTimeMillis();
        Flux.range(1, 6)
                .flatMap(n -> Mono.just(n)
                        .delayElement(Duration.ofSeconds(1))
                        .doOnNext(v -> {
                            long elaspesed = System.currentTimeMillis() - start2;
                            System.out.println("complete " + v + " - " + elaspesed + "ms");
                        }), 2)
                .subscribe();

        Thread.sleep(5000);
    }
}
