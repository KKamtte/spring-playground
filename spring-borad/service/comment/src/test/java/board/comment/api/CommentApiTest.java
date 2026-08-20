package board.comment.api;

import board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(99L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(99L, "my comment1", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(99L, "my comment1", response1.getCommentId(), 1L));

        assertThat(response2.getParentCommentId()).isEqualTo(response1.getCommentId());
        assertThat(response3.getParentCommentId()).isEqualTo(response1.getCommentId());
    }

    @Test
    void read() {
        CommentResponse request = createComment(new CommentCreateRequest(99L, "my comment1", null, 1L));

        CommentResponse response = readComment(request.getCommentId());

        assertThat(response.getCommentId()).isEqualTo(request.getCommentId());
    }

    @Test
    void delete() {
        CommentResponse request1 = createComment(new CommentCreateRequest(99L, "my comment1", null, 1L));
        CommentResponse request2 = createComment(new CommentCreateRequest(99L, "my comment1", request1.getCommentId(), 1L));
        CommentResponse request3 = createComment(new CommentCreateRequest(99L, "my comment1", request1.getCommentId(), 1L));

        deleteComment(request1.getCommentId());
        CommentResponse response1 = readComment(request1.getCommentId());
        assertThat(response1.getDeleted()).isTrue();

        deleteComment(request2.getCommentId());
        assertThatThrownBy(() -> readComment(request2.getCommentId()))
                .isInstanceOf(InternalServerError.class);

        deleteComment(request3.getCommentId());
        assertThatThrownBy(() -> readComment(request3.getCommentId()))
                .isInstanceOf(InternalServerError.class);
        assertThatThrownBy(() -> readComment(request1.getCommentId()))
                .isInstanceOf(InternalServerError.class);
    }


    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    CommentResponse readComment(Long commentId) {
        return restClient.get()
                .uri("/v1/comments/{commentId}", commentId)
                .retrieve()
                .body(CommentResponse.class);
    }

    void deleteComment(Long commentId) {
        restClient.delete()
                .uri("/v1/comments/{commentId}", commentId)
                .retrieve()
                .toBodilessEntity();
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
