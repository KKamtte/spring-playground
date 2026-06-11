package flux.conversion;

import reactor.core.publisher.Flux;

public class Map {
    public static void main(String[] args) {
        Flux.just(
                        new User("Kim", 30),
                        new User("Lee", 30),
                        new User("Park", 30))
                .map(user -> user.name)
                .subscribe(System.out::println);

        // 중간에 실패시 종료된다.
        Flux.just("1", "2", "abc", "3")
                .map(s -> Integer.parseInt(s))
                .subscribe(
                        data -> System.out.println(data),
                        err -> System.out.println(err),
                        () -> System.out.println("done")
                );
    }

    record User(String name, int age) {
    }
}
