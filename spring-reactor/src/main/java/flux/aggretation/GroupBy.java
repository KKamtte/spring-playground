package flux.aggretation;

import reactor.core.publisher.Flux;

public class GroupBy {
    public static void main(String[] args) {
        // groupBy 반환 타입: Flux<GroupedFlux<String, Integer>> → Flux 안에 Flux
        // GroupedFlux 는 반드시 소비해야 함: 구독하지 않으면 버퍼에 쌓여 메모리 문제 발생
        // flatMap: 각 GroupedFlux(inner Flux)를 구독하고 결과를 하나의 스트림으로 평탄화
        // collectList: GroupedFlux 의 모든 아이템을 모아 Mono<List> 로 변환 (스트림 완료 시점에 방출)
        // map: Mono 안의 List 를 "key:[items]" 문자열로 변환
        Flux.range(1, 10)
                .groupBy(n -> n % 2 == 0 ? "even" : "odd")
                .flatMap(group -> group.collectList().map(list -> group.key() + ":" + list))
                .subscribe(System.out::println);
    }
}
