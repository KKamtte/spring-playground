package flux.process;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ConcatMap {
    public static void main(String[] args) throws InterruptedException {
        // flatMap: slow-fast-middle 순으로 시작하지만 fast 가 먼저 끝나 순서가 뒤섞임
        Flux.just("slow", "fast", "middle").flatMap(task -> {
            long delay = switch (task) {
                case "slow" -> 300;
                case "fast" -> 100;
                case "middle" -> 200;
                default -> 0;
            };
            return Mono.just(task).delayElement(Duration.ofMillis(delay));
        }).subscribe(System.out::println);

        // concatMap: 이전 inner publisher 가 완료되어야 다음 것을 구독 -> 순서 보장, 동시성 없음
        // slow(300ms) 완료 후 fast(100ms) 시작 -> 총 600ms 소요 (flatMap 이면 300ms)
        long start = System.currentTimeMillis();
        Flux.just("slow", "fast", "middle").concatMap(task -> {
            long delay = switch (task) {
                case "slow" -> 300;
                case "fast" -> 100;
                case "middle" -> 200;
                default -> 0;
            };
            return Mono.just(task).delayElement(Duration.ofMillis(delay));
        }).subscribe(data -> {
            long elap = System.currentTimeMillis() - start;
            System.out.println(elap + "ms: " + data);
        });

        Thread.sleep(1000);
    }
}
