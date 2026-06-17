package basic.flux.combination;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Merge {

    public static void main(String[] args) throws InterruptedException {
        // 동시에 합치는 연산
        long start = System.currentTimeMillis();

        Flux<String> slow = Mono.just("slow")
                .delayElement(Duration.ofMillis(500))
                .flux();

        Flux<String> fast = Mono.just("fast")
                .delayElement(Duration.ofMillis(200))
                .flux();

        Flux.merge(slow, fast) // concat 은 slow 가 처리되고 fast 가 처리됨
                .subscribe(data -> {
                    long elap = System.currentTimeMillis() - start;
                    System.out.println(elap + "ms: " + data);
                });

        Thread.sleep(1000);

        long start2 = System.currentTimeMillis();
        Flux<String> sourceA = Flux.interval(Duration.ofMillis(300))
                .take(3)
                .map(i -> "A" + i + " " + (System.currentTimeMillis() - start2) + " ms");

        Flux<String> sourceB = Flux.interval(Duration.ofMillis(500))
                .take(2)
                .map(i -> "B" + i + " " + (System.currentTimeMillis() - start2) + " ms");

        Flux.merge(sourceA, sourceB)
                .subscribe(System.out::println);

        Thread.sleep(1500);
    }
}
