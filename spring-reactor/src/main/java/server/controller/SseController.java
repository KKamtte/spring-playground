package server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class SseController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> "이벤트 #" + tick);
    }
    /*
        1. data: 보내는 데이터
        2. id: 이벤트에 대한 고유 ID (클라이언트 재연결 -> Last Event ID 헤더로 전달)
        3. event: 이벤트 타입에 대한 이름
        4. retry: 재연결에 대한 대기 시간
     */
}
