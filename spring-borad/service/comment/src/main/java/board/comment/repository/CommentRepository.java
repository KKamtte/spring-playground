package board.comment.repository;

import board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 article에서 특정 comment의 parentCommentId가 동일한 것들의 댓글 개수 가져옴
     * 자식 개수를 구할 수 있음
     */
    @Query(
            value = "SELECT COUNT(1) " +
                    "FROM (" +
                    "   SELECT comment_id " +
                    "   FROM comment " +
                    "   WHERE article_id = :articleId " +
                    "     AND parent_comment_id = :parentCommentId " +
                    "   LIMIT :limit" +
                    ") t",
            nativeQuery = true
    )
    Long countBy(
            @Param("articleId") Long articleId,
            @Param("parentCommentId") Long parentCommentId,
            @Param("limit") Long limit);
}
