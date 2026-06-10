package flux;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class DisposableEx {
    public static void main(String[] args) throws InterruptedException {
        // Disposable: subscribe 는 Disposable을 반환하며 이를 통해 구독을 외부에서 취소할 수 있다.
        Flux<Integer> flux = Flux.range(1, 5);

        // 동기 Flux에서는 사실상 의미 없다.
        Disposable disposable = flux.subscribe(System.out::println);
        System.out.println("isDisposed = " + disposable.isDisposed());

        // 비동기 무한 스트림에서 의미가 있다.
        Flux<Long> interval = Flux.interval(Duration.ofMillis(500)); // 500ms 무한 Flux

        Disposable intervalDisposable = interval.subscribe(System.out::println); // 구독 시작

        System.out.println("intervalDisposable.isDisposed() = " + intervalDisposable.isDisposed());
        
        Thread.sleep(3000);
        
        intervalDisposable.dispose(); // 구독 취소
        System.out.println("intervalDisposable.isDisposed() = " + intervalDisposable.isDisposed());
    }
}
