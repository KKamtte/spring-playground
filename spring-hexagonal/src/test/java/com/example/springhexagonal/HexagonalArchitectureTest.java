package com.example.springhexagonal;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

// ImportOption.DoNotIncludeTests.class -> 테스트쪽 클래스는 불필요하게 검증하지 않도록함
@AnalyzeClasses(packages = "com.example.springhexagonal", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

    /**
     *
     */
    @ArchTest
    void hexagonalArchitecture(JavaClasses classes) {
        // 계층형 검증 규칙 시작
        Architectures.layeredArchitecture()
                // 모든 의존 관계에 대해서 검사
                .consideringAllDependencies()
                // 레이어 정의
                .layer("domain").definedBy("com.example.springhexagonal.domain..")
                .layer("application").definedBy("com.example.springhexagonal.application..")
                .layer("adapter").definedBy("com.example.springhexagonal.adapter..")
                // 조건 부여
                // domain 레이어는 application, adapter 레이어에서 접근이 가능하다
                .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
                // application 레이어는 adapter 레이어 에서만 접근이 가능하다
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
                // 어떤 레이어도 adapter 레이어에 의존해서는 안된다
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);
    }

}
