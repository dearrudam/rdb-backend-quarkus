package com.temaula.rdb.validation;

import com.temaula.rdb.validation.constraints.Unique;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UniqueConstraintValidatorTest {

    static final Integer IDADE_DO_MAX = Period.between(LocalDate.of(1982, 10, 4), LocalDate.now()).getYears();
    static final String NOME_DO_MAX = "Max";

    @Inject
    Validator validator;

    @BeforeAll
    @Transactional
    public static void beforeAll() {
        new PojoA(NOME_DO_MAX, IDADE_DO_MAX).persistAndFlush();
    }


    @AfterAll
    @Transactional
    public static void afterAll() {
        PojoA.deleteAll();
    }


    @DisplayName("testing -> @Unique")
    @ParameterizedTest(name = "\"case {index} - {0}\"")
    @MethodSource("testsArgs")
    void tests(String reason, Object pojo, int constraintViolationsSize) {

        var constraintViolations =
                validator.validate(pojo);
        assertEquals(constraintViolationsSize, constraintViolations.size(), "múmero de violações de restrição " +
                "esperado está inválido");
    }

    public static Stream<Arguments> testsArgs() {
        return Stream.of(
                Arguments.arguments("" +
                                "deve ocorrer 2 violações de restrição para a " +
                                "constraint @Unique(ignoreCase=false)",
                        new PojoAWithCaseSensitive(NOME_DO_MAX, IDADE_DO_MAX),
                        2
                ),

                Arguments.arguments("" +
                                "deve ocorrer 1 violação de restrição para a " +
                                "constraint @Unique(ignoreCase=false)",
                        new PojoAWithCaseSensitive(NOME_DO_MAX.toLowerCase(), IDADE_DO_MAX),
                        1
                ),

                Arguments.arguments("" +
                                "não deve ocorrer nenhuma violação de restrição para a " +
                                "constraint @Unique(ignoreCase=false)",
                        new PojoAWithCaseSensitive(NOME_DO_MAX + "dcmsdkcmsdkc", IDADE_DO_MAX + 1),
                        0
                ),

                Arguments.arguments("" +
                                "deve ocorrer 2 violações de restrição para a " +
                                "constraint @Unique(ignoreCase=true)",
                        new PojoAWithCaseInsensitive(NOME_DO_MAX, IDADE_DO_MAX),
                        2
                ),

                Arguments.arguments("" +
                                "deve ocorrer 2 violações de restrição para a " +
                                "constraint @Unique(ignoreCase=true)",
                        new PojoAWithCaseInsensitive(NOME_DO_MAX.toLowerCase(), IDADE_DO_MAX),
                        2
                ),

                Arguments.arguments("" +
                                "deve ocorrer 1 violação de restrição para a " +
                                "constraint @Unique(ignoreCase=true)",
                        new PojoAWithCaseInsensitive(NOME_DO_MAX + "dkjfvnfdkjn", IDADE_DO_MAX),
                        1
                ),

                Arguments.arguments("" +
                                "não deve ocorrer nenhuma violação de restrição para a " +
                                "constraint @Unique(ignoreCase=true)",
                        new PojoAWithCaseInsensitive(NOME_DO_MAX + "dkjfvnfdkjn", IDADE_DO_MAX + 1),
                        0
                )
        );
    }


    @Entity
    public static class PojoA extends PanacheEntity {

        private String nome;
        private Integer idade;

        public PojoA() {
        }

        public PojoA(String nome, Integer idade) {
            this.nome = nome;
            this.idade = idade;
        }
    }

    static class PojoAWithCaseInsensitive {

        @Unique(entityType = PojoA.class, fieldName = "nome")
        private final String nome;
        @Unique(entityType = PojoA.class, fieldName = "idade")
        private final Integer idade;

        PojoAWithCaseInsensitive(String nome, Integer idade) {
            this.nome = nome;
            this.idade = idade;
        }

        @Override
        public String toString() {
            return "PojoAWithCaseInsensitive{" +
                    "nome='" + nome + '\'' +
                    ", idade=" + idade +
                    '}';
        }
    }

    static class PojoAWithCaseSensitive {

        @Unique(entityType = PojoA.class, fieldName = "nome", ignoreCase = false)
        private final String nome;
        @Unique(entityType = PojoA.class, fieldName = "idade")
        private final Integer idade;

        PojoAWithCaseSensitive(String nome, Integer idade) {
            this.nome = nome;
            this.idade = idade;
        }

        @Override
        public String toString() {
            return "PojoAWithCaseSensitive{" +
                    "nome='" + nome + '\'' +
                    ", idade=" + idade +
                    '}';
        }
    }

}