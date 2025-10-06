package com.bigpugloans.antragserfassung.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@InfrastructureRing
public class AxonConfiguration {

    @Bean
    public EventStore eventStore() {
        return EmbeddedEventStore.builder()
            .storageEngine(new InMemoryEventStorageEngine())
            .build();
    }
}