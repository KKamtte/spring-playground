package flux.combination;

import reactor.core.publisher.Flux;

public class Concat {
    public static void main(String[] args) {
        // 순차적으로 합치는 연산
        Flux<String> first = Flux.just("A", "B", "C");
        Flux<String> second = Flux.just("D", "E");
        Flux<String> third = Flux.just("F");

        Flux.concat(first, second, third)
                .subscribe(System.out::println);

        // concat Flux 인스턴스
        Flux.just("A", "B", "C")
                .concatWith(Flux.just("D", "E"))
                .concatWith((Flux.just("F")))
                .subscribe(System.out::println);
    }
}
