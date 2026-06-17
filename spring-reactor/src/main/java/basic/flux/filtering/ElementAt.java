package basic.flux.filtering;

import reactor.core.publisher.Flux;

public class ElementAt {

    public static void main(String[] args) {
        // 특정 위치에 데이터를 가져오는 연산자
        Flux.just("apple", "banana", "cherry", "strawberry", "lemon")
                .elementAt(2)
                .subscribe(System.out::println);

        // 해당 위치에 데이터가 없고, default 가 없다면 IndexOutOfBoundsException 발생
        Flux.just("apple", "banana", "cherry", "strawberry")
                .elementAt(5, "null")
                .subscribe(System.out::println);
    }
}
