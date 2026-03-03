package com.example.springhexagonal;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SpringHexagonalApplicationTest {

    @Test
    void run() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            SpringHexagonalApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(SpringHexagonalApplication.class, new String[]{}));
        }
    }

}