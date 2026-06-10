package flux;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public class FromStream {
    public static void main(String[] args) {
        // 단건 소비 -> 추가로 구독할 수 없다.
        Flux<Integer> fromStream = Flux.fromStream(Stream.of(10, 20, 30));
        fromStream.subscribe(System.out::println);
        // fromStream.subscribe(System.out::println); // Caused by: java.lang.IllegalStateException: stream has already been operated upon or closed

        // 두번 이상 구독시 람다식으로 작성되어야한다.
        Flux<Integer> fromStream2 = Flux.fromStream(() -> Stream.of(40, 50, 60));
        fromStream2.subscribe(System.out::println);
        fromStream2.subscribe(System.out::println);
    }
}
