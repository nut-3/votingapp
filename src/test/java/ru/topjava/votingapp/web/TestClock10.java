package ru.topjava.votingapp.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
@TestConfiguration
public class TestClock10 {

    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 8, 30, 10, 0, 0);

    @Primary
    @Bean
    protected Clock fixedClock10() {
        Clock clock = Clock.fixed(LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        log.info("Setting time to {}", LOCAL_DATE_TIME);
        return clock;
    }
}
