package board.article.data;

import board.article.entity.Article;
import board.common.snowflake.Snowflake;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
public class DataInitializer {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TransactionTemplate transactionTemplate;
    Snowflake snowflake = new Snowflake();
    CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);

    static final int BULK_INSERT_SIZE = 2000;
    static final int EXECUTE_COUNT = 6000;
    static final int FLUSH_SIZE = 1000;

    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                long count = latch.getCount() - 1;
                latch.countDown();
                if (count % 100 == 0) {
                    log.info("remain = {}", count);
                }
            });
        }
        latch.await();
        executorService.shutdown();
    }

    void insert() {
        transactionTemplate.executeWithoutResult(status -> {
            for (int i = 1; i <= BULK_INSERT_SIZE; i++) {
                Article article = Article.create(
                        snowflake.nextId(),
                        "title " + i,
                        "content " + i,
                        1L,
                        1L
                );
                entityManager.persist(article);

                if (i % FLUSH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        });
    };
}
