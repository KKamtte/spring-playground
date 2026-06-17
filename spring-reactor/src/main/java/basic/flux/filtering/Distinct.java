package basic.flux.filtering;

import reactor.core.publisher.Flux;

public class Distinct {

    public static void main(String[] args) {
        // 중복을 제거하는 연산자
        // 기본 distinct: equals/hashCode 기반
        Flux.just(1, 2, 3, 2, 1, 4, 3, 5)
                .distinct()
                .subscribe(data -> System.out.println(data + " "));

        // 키 기반 distinct: 특정 필드 기준으로 중복 제거
        Flux.just(
                new User("kim", "seoul"),
                new User("lee", "busan"),
                new User("park", "seoul"),
                new User("choi", "daejeon"),
                new User("jae", "busan"))
                .distinct(user -> user.city())
                .subscribe(user -> System.out.println(user.name() + " (" + user.city() + ")" ));
    }

    record User(String name, String city) {}
}
