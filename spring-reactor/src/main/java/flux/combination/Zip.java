package flux.combination;

import reactor.core.publisher.Flux;

public class Zip {
    public static void main(String[] args) {
        // 짝을 맞춰서 합치는 기능
        Flux<String> names = Flux.just("Hong", "Kim", "Lee");
        Flux<Integer> ages = Flux.just(20, 30, 40);

        Flux.zip(names, ages, (name, age) -> name + " : " + age)
                .subscribe(System.out::println);

        // Tuple
        Flux<String> name2 = Flux.just("Hong", "Kim", "Lee");
        Flux<Integer> ages2 = Flux.just(20, 30, 40);
        Flux<String> cities = Flux.just("seoul", "busan", "daejeon");

        Flux.zip(name2, ages2, cities)
                .map(tuple -> tuple.getT1() + "(" + tuple.getT2() + "세, " + tuple.getT3() + ")")
                .subscribe(System.out::println);

        // 부족한 경우 짝이 맞는 경우까지만 매칭됨 (가장 짧은 것에 맞춰 매칭되고 Lee는 버려짐)
        Flux<String> name3 = Flux.just("Hong", "Kim", "Lee");
        Flux<Integer> ages3 = Flux.just(20, 30);

        Flux.zip(name3, ages3).subscribe(System.out::println);
    }
}
