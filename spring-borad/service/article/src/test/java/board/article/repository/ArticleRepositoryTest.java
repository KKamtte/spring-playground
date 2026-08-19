package board.article.repository;

import board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAllTest() {
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);

        assertThat(articles.size()).isEqualTo(30);
    }
    
    @Test
    void countTest() {
        Long count = articleRepository.count(1L, 10000L);

        assertThat(count).isEqualTo(10000L);
    }

    @Test
    void findInfiniteScrollTest() {
        List<Article> articles = articleRepository.findAllInfiniteScroll(1L, 30L);

        Long lastArticleId = articles.getLast().getArticleId();
        List<Article> articles2 = articleRepository.findAllInfiniteScroll(1L, 30L, lastArticleId);

        List<Long> articleIds = articles.stream().map(Article::getArticleId).toList();
        List<Long> articleIds2 = articles2.stream().map(Article::getArticleId).toList();

        assertThat(articles).hasSize(30);
        assertThat(articles2).hasSize(30);

        // 각 페이지 내부에도 중복이 없어야 한다
        assertThat(articleIds).doesNotHaveDuplicates();
        assertThat(articleIds2).doesNotHaveDuplicates();

        // 두 페이지 사이에 겹치는 article_id 가 없어야 한다
        assertThat(articleIds2).doesNotContainAnyElementsOf(articleIds);
    }
}