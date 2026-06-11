package flux.condition;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class HasElement {
    public static void main(String[] args) {
        Mono.just("hello")
                .hasElement()
                .subscribe(has -> System.out.println("[Mono.just] has element: " + has));

        Mono.empty()
                .hasElement()
                .subscribe(has -> System.out.println("[Mono.empty] has element: " + has));

        Flux.just(1, 2, 3)
                .hasElements()
                .subscribe(has -> System.out.println("[Flux.just] has element: " + has));

        Flux.empty()
                .hasElements()
                .subscribe(has -> System.out.println("[Flux.empty] has element: " + has));
    }
}
