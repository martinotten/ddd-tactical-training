package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;
import java.util.UUID;

@DomainModelRing
@ValueObject
public record AntragstellerErfassenCommand(
    @TargetAggregateIdentifier UUID antragsnummer,
    String vorname,
    String nachname,
    LocalDate geburtsdatum,
    String telefonnummer,
    String emailAdresse,
    Anschrift anschrift,
    Familienstand familienstand,
    Integer anzahlKinder,
    String kundennummer,
    Branche branche,
    Berufsart berufsart,
    String arbeitgeber,
    LocalDate beschaeftigtSeit
) {}