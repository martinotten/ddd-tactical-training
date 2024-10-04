package com.bigpugloans.scoring.domainmodel;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AntragstellerIDTest {
    @Test
    void testAntragstellerID() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
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
                .geburtsdatum(new Date(1970, 1, 1))
                .build();

        AntragstellerID antragstellerID_2 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();

        assertEquals(antragstellerID_1, antragstellerID_2, "Beide AntragstellerIDs sollten gleich sein.");

    }

    @Test
    void testAntragstellerIDBeiUmzugWerten() {
        AntragstellerID antragstellerID_1 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();

        AntragstellerID antragstellerID_2 = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("80331")
                .stadt("München")
                .strasse("Kreuzstr. 16")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();

        assertNotEquals(antragstellerID_1, antragstellerID_2, "Beide AntragstellerIDs sollten unterschiedlich sein.");
    }


}
