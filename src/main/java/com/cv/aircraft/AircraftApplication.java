package com.cv.aircraft;

import com.cv.aircraft.telegram.Bot;
import com.fasterxml.jackson.contrib.jsonpath.DefaultJsonUnmarshaller;
import com.fasterxml.jackson.contrib.jsonpath.JsonUnmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.TelegramBotsApi;

import lombok.SneakyThrows;

@SpringBootApplication
@PropertySource("classpath:build.properties")
public class AircraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(AircraftApplication.class, args);
    }

    @Autowired
    private ApplicationContext context;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JsonUnmarshaller jsonUnmarshaller() {
        return new DefaultJsonUnmarshaller();
    }

    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        telegramBotsApi.registerBot(context.getBean(Bot.class));
        telegramBotsApi.registerBot(context.getBean(Bot.class));
        return telegramBotsApi;
    }
}
