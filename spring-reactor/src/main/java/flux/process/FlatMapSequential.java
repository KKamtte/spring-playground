package flux.process;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class FlatMapSequential {
    public static void main(String[] args) throws InterruptedException {
        // flatMapSequential: 내부 작업은 flatMap 처럼 동시에 실행되지만, 결과는 입력 순서대로 방출
        // slow(300ms), fast(100ms), middle(200ms) 모두 동시 시작 -> fast 가 먼저 끝나도 slow 결과를 먼저 방출
        long start = System.currentTimeMillis();

        Flux.just("slow", "fast", "middle").flatMapSequential(task -> {
            long delay = switch (task) {
                case "slow" -> 300;
                case "fast" -> 100;
                case "middle" -> 200;
                default -> 0;
            };
            return Mono.just(task).delayElement(Duration.ofMillis(delay));
        }).subscribe(data -> {
            long elap = System.currentTimeMillis() - start;
            System.out.println(elap + "ms " + data);
        });

        Thread.sleep(500);
    }
}
