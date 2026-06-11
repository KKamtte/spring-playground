package flux.condition;

import reactor.core.publisher.Flux;

public class IfEmpty {
    public static void main(String[] args) {
        // defaultIfEmpty
        Flux<String> result = Flux.<String>empty()
                .defaultIfEmpty("null");
        result.subscribe(System.out::println);

        Flux<String> hasData = Flux.just("a", "b", "c")
                .defaultIfEmpty("null"); // 데이터가 있으니 무시됨
        hasData.subscribe(System.out::println);

        // switchIfEmpty
        Flux<String> fromCache = Flux.empty(); // 캐시 데이터가 없다고 가정
        fromCache
                .switchIfEmpty(Flux.defer(() -> findFromDb()))
                .subscribe(System.out::println);

        Flux<String> cached = Flux.just("cache1", "cache2", "cache3");
        cached
                .switchIfEmpty(Flux.defer(() -> findFromDb())) // 데이터가 있으니 무시됨
                .subscribe(System.out::println);
    }

    private static Flux<String> findFromDb() {
        return Flux.just("db1", "db2", "db3");
    }
}
