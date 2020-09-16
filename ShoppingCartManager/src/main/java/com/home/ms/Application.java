package com.home.ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.shoppingcart.service.IdGenerator;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  IdGenerator idGenerator() {
    return new IdGenerator();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ConnectionFactory connectionFactory(
      @Value("${rabbitmq.host}") String hostname, @Value("${rabbitmq.port}") int port) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostname);
    factory.setPort(port);
    return factory;
  }

  @Bean
  public HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }
}
