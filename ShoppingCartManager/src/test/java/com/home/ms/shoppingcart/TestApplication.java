package com.home.ms.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.shoppingcart.service.IdGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestApplication {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  IdGenerator idGenerator() {
    return new IdGenerator();
  }
}
