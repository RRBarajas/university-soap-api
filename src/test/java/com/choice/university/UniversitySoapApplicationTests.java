package com.choice.university;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class UniversitySoapApplicationTests {

  @Test
  void contextLoads(ApplicationContext context) {
    assertThat(context).as("Application should boot successfully").isNotNull();
  }

}
