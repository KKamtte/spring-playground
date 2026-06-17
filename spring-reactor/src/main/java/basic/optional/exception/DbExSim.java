package basic.optional.exception;

import reactor.core.publisher.Mono;

import java.util.Map;

public class DbExSim {
    public static void main(String[] args) {
        /*
         * DB 에러, 캐시 조회: DB 연결 실패
         * 결과: 홍길동(캐시)
         * ---
         * DB 에러, 캐시 조회: DB 연결 실패
         * 최종 에러: 사용자 조회 실패: DB 연결 실패
         */
        Map<String, String> cahce = Map.of("user-1", "홍길통(캐시)");

        // 사용자 조회: DB 에러시 캐시에서 조회, 캐시에도 없으면 에러 메시지 변환
        findUserFromDb("user-1")
                .onErrorResume(error -> {
                    System.out.println("DB 에러, 캐시 조회: " + error.getMessage());
                    String cached = cahce.get("user-1");
                    return cached != null ? Mono.just(cached) : Mono.error(error);
                })
                .subscribe(user -> System.out.println(user));

        findUserFromDb("user-2")
                .onErrorResume(error -> {
                    System.out.println("DB 에러, 캐시 조회: " + error.getMessage());
                    String cached = cahce.get("user-2");
                    return cached != null ? Mono.just(cached) : Mono.error(error);
                })
                .onErrorMap(error -> new RuntimeException("사용자 조회 실패: " + error.getMessage()))
                .subscribe(
                        user -> System.out.println("result: " + user),
                        error -> System.out.println("error: " + error.getMessage())
                );
    }

    static Mono<String> findUserFromDb(String userId) {
        // DB 조회 실패 시뮬레이션
        return Mono.error(new RuntimeException("DB conn error"));
    }
}
