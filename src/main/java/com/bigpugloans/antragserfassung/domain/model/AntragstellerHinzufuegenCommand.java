package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class AntragstellerHinzufuegenCommand {
    @TargetAggregateIdentifier
    private UUID antragsnummer;
    private String vorname;
    private String nachname;
    private String strasse;
    private String familienstand;
    private String branche;
    private String berufsart;
}
