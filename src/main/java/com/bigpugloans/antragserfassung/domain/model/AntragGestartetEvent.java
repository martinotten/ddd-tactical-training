package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class AntragGestartetEvent {
    @TargetAggregateIdentifier
    private UUID antragsnummer;

    public AntragGestartetEvent(UUID antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public UUID getAntragsnummer() {
        return antragsnummer;
    }
}
