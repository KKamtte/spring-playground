package board.article;

import board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        log.info("response: {}", response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest() {
        ArticleResponse create = create(new ArticleCreateRequest(
                "read test", "my content", 1L, 1L
        ));
        ArticleResponse response = read(create.getArticleId());
        log.info("response: {}", response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        ArticleResponse create = create(new ArticleCreateRequest(
                "will be update test", "my content", 1L, 1L
        ));
        ArticleResponse response = update(create.getArticleId());
        log.info("response: {}", response);
    }

    ArticleResponse update(Long articleId) {
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("update test", "my content2"))
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void deleteTest() {
        ArticleResponse create = create(new ArticleCreateRequest(
                "delete test", "my content", 1L, 1L
        ));
        delete(create.getArticleId());

        assertThatThrownBy(() -> read(create.getArticleId()))
                .isInstanceOf(HttpServerErrorException.InternalServerError.class);
    }

    ArticleResponse delete(Long articleId) {
        return restClient.delete()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}
