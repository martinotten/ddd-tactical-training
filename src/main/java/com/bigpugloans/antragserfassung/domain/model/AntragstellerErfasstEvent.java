package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@DomainModelRing
public record AntragstellerErfasstEvent(
    UUID antragsnummer,
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
    LocalDate beschaeftigtSeit,
    Instant zeitpunkt
) {}