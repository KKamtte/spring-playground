package basic;

import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class BaseSubscriber {

    public static void main(String[] args) {
        // 일반적인 subscribe: request(Long.MAX_VALUE) 로 모든 데이터를 요청
        Flux<Integer> flux = Flux.range(1, 20);

        flux.subscribe(new reactor.core.publisher.BaseSubscriber<>() {
            int count = 0;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("Start Subscribe");
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                count++;
                System.out.println("received data = " + value);

                try {
                    Thread.sleep(333); // 아이템 하나 처리에 333ms → 초당 3개 처리
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (count % 3 == 0) {
                    System.out.println("processed 3 items, requesting next 3");
                    request(3);
                }

            }

            @Override
            protected void hookOnComplete() {
                System.out.println("subscribe completed");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.err.println("subscribe error" + throwable.getMessage());
            }
        });
    }
}
