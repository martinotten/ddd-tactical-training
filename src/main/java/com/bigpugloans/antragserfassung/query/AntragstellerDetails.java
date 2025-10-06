package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.Anschrift;
import com.bigpugloans.antragserfassung.domain.model.Familienstand;
import com.bigpugloans.antragserfassung.domain.model.Branche;
import com.bigpugloans.antragserfassung.domain.model.Berufsart;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.time.LocalDate;

@InfrastructureRing
public record AntragstellerDetails(
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