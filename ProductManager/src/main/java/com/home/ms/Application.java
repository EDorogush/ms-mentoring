package com.home.ms;

import com.fasterxml.jackson.databind.ObjectMapper;
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
  public ObjectMapper getObjectMapper(){
    return new ObjectMapper();
  }

  @Bean
  public HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }
}


