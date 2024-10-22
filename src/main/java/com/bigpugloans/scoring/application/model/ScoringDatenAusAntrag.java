package com.bigpugloans.scoring.application.model;

import java.util.Date;

public record ScoringDatenAusAntrag(
        String antragsnummer,
        String kundennummer,
        int ausgabenPrivateKrankenversicherung,
        int ausgabenMonatlicheBelastungKredite,
        int ausgabenLebenshaltungsKosten,
        int ausgabenMiete,
        boolean mieteEntfaelltKuenftig,
        int gehalt,
        int mietEinnahmenFinanzierungsobjekt,
        int mietEinnahmenWeitereObjekte,
        int weitereEinkuenfte,
        int monatlicheDarlehensbelastungen1,
        int monatlicheDarlehensbelastungen2,
        int monatlicheDarlehensbelastungen3,
        int monatlicheDarlehensbelastungen4,
        int kaufnebenkosten,
        int kaufpreisOderBaukosten,
        int kostenGrundstueck,
        int summeDarlehen1,
        int summeDarlehen2,
        int summeDarlehen3,
        int summeDarlehen4,
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
