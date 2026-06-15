package flux.aggretation;

import reactor.core.publisher.Flux;

import java.util.HashMap;

public class Reduce {
    public static void main(String[] args) {
        // reduce: 첫 번째 아이템이 acc 초기값, 두 번째 아이템부터 next 로 누적. 반환 타입은 Mono
        // 1 -> 1+2=3 -> 3+3=6 -> 6+4=10 -> 10+5=15
        Flux.just(1, 2, 3, 4, 5)
                .reduce((acc, next) -> acc + next)
                .subscribe(result -> System.out.println("result: " + result));

        // Hello -> HelloReactor -> HelloReactorWorld
        Flux.just("Hello", "Reactor", "World")
                .reduce((acc, next) -> acc + next)
                .subscribe(result -> System.out.println("result: " + result));

        // 100+1=101 -> 101+2=103 -> 103+3=106 -> 106+4=110 -> 110+5=115
        Flux.just(1, 2, 3, 4, 5)
                .reduce(100, (acc, next) -> acc + next)
                .subscribe(result -> System.out.println("result: " + result));

        // 100 -> 100
        Flux.<Integer>empty()
                .reduce(100, (acc, next) -> acc + next)
                .subscribe(result -> System.out.println("result: " + result));

        // 초기값 빈 HasMap, 각 단어가 도착할때 마다 단어를 키값으로 해서 map의 빈도수를 누적
        // 데이터가 모두 처리되면 최종적인 Mono로 반환
        Flux.just("apple", "banana", "apple", "cherry", "banana", "cherry")
                .reduce(new HashMap<String, Integer>(), (map, world) -> {
                    map.merge(world, 1, Integer::sum);
                    return map;
                }).subscribe(freq -> System.out.println("freq: " + freq));
    }
}
