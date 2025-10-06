package com.bigpugloans.antragserfassung.config;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

@Configuration
@InfrastructureRing
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
        }

        @Override
        public String print(LocalDate localDate, Locale locale) {
            return localDate == null ? "" : DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
        }
    }
}