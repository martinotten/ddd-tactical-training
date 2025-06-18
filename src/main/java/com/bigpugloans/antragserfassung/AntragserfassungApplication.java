package com.bigpugloans.antragserfassung;

import org.axonframework.configuration.ApplicationConfigurer;
import org.axonframework.eventsourcing.configuration.EventSourcingConfigurer;

public class AntragserfassungApplication {
    public static ApplicationConfigurer configurer() {
        return EventSourcingConfigurer.create();
    }
}
