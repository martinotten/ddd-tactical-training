package com.bigpugloans.scoring.application.model;

import java.util.Date;

public record ScoringDatenAusAntrag(
        String antragsnummer,
        String kundennummer,
        int monatlicheAusgaben,
        int monatlicheEinnahmen,
        int monatlicheDarlehensbelastungen,
        String wohnort,
        int kaufnebenkosten,
        int marktwert,
        int summeDarlehen,
        int summeEigenmittel,
        String vorname,
        String nachname,
        String strasse,
        String stadt,
        String postleitzahl,
        Date geburtsdatum
)

{
}
