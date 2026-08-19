package board.article.repository;

import board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(
            value = "SELECT a.article_id, a.title, a.content, a.board_id, a.writer_id, a.created_at, a.modified_at " +
                    "FROM (  " +
                    "   SELECT article_id" +
                    "   FROM article" +
                    "   WHERE board_id = :boardId" +
                    "   ORDER BY article_id desc" +
                    "   LIMIT :limit OFFSET :offset" +
                    ") t left join article a ON t.article_id = a.article_id",
            nativeQuery = true
    )
    List<Article> findAll(
            @Param("boardId") Long boardId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    @Query(
            value = "SELECT COUNT(1) " +
                    "FROM (" +
                    "   SELECT article_id" +
                    "   FROM article" +
                    "   WHERE board_id = :boardId" +
                    "   LIMIT :limit) t",
            nativeQuery = true
    )
    Long count(@Param("boardId") Long boardId, @Param("limit") Long limit);
}
