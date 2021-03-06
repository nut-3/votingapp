package com.github.nut3.votingapp.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Clocks {
    @Slf4j
    @TestConfiguration
    public static class TestClock10 {

        public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 8, 30, 10, 0, 0);

        @Primary
        @Bean
        protected Clock fixedClock10() {
            Clock clock = Clock.fixed(LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
            log.info("Setting time to {}", LOCAL_DATE_TIME);
            return clock;
        }
    }

    @Slf4j
    @TestConfiguration
    public static class TestClock12 {

        public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 8, 30, 12, 0, 0);

        @Primary
        @Bean
        public Clock fixedClock12() {
            Clock clock = Clock.fixed(LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
            log.info("Setting time to {}", LOCAL_DATE_TIME);
            return clock;
        }
    }

    @Slf4j
    @TestConfiguration
    public static class TestClockForMenu {

        public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(22021, 8, 27, 12, 0, 0);

        @Primary
        @Bean
        public Clock fixedClockForMenu() {
            Clock clock = Clock.fixed(LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
            log.info("Setting time to {}", LOCAL_DATE_TIME);
            return clock;
        }
    }
}
