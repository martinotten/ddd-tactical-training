package com.bigpugloans.scoring.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AntragstellerIDTest {
    @Test
    void testAntragstellerID() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();

        assertNotNull(antragstellerID.id());
        System.out.println(antragstellerID.id());
    }

    @Test
    void testAntragstellerIDBeiGleichenWerten() {
        AntragstellerID antragstellerID_1 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();

        AntragstellerID antragstellerID_2 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();

        assertEquals(antragstellerID_1, antragstellerID_2, "Beide AntragstellerIDs sollten gleich sein.");

    }

    @Test
    void testAntragstellerIDBeiUmzugWerten() {
        AntragstellerID antragstellerID_1 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();

        AntragstellerID antragstellerID_2 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("80331")
                .stadt("München")
                .strasse("Kreuzstr. 16")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();

        assertNotEquals(antragstellerID_1, antragstellerID_2, "Beide AntragstellerIDs sollten unterschiedlich sein.");
    }


}
