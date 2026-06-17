package basic.flux;

import reactor.core.publisher.Flux;

public class Create {
    public static void main(String[] args) {
        // create: sink를 통해서 데이터를 생성한다.
        Flux<String> created = Flux.create(sink -> {
            sink.next("First");
            sink.next("Second");
            sink.next("Third");
            sink.next("Fourth");
            sink.complete();
        });

        created.subscribe(System.out::println);
    }
}
