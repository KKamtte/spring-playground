package mono;

import reactor.core.publisher.Mono;

public class FromSupplier {

    public static void main(String[] args) {
        Mono<String> getValue = Mono.just(getValue());

        System.out.println("Mono created, but value not yet retrieved");

        // fromSupplier: 구독이 발생하는 시점에 Supplier 를 실행 (Lazy Loading 과 비슷)
        Mono<String> supplierMono = Mono.fromSupplier(() -> getValue());

        System.out.println("Mono from supplier created, but value not yet retrieved");

        // 이 시점에 getValue() 실행됨
        supplierMono.subscribe(value -> System.out.println("Received: " + value));
    }

    static String getValue(){
        System.out.println("Getting value...");
        return "Hello World";
    }
}
