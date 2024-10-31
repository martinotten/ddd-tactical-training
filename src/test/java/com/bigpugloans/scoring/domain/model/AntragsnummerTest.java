package com.bigpugloans.scoring.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AntragsnummerTest {
    @Test
    void testAntragsnummer() {
        Antragsnummer antragsnummer = new Antragsnummer("123456");
        assertEquals("123456", antragsnummer.nummer());
    }

    @Test
    void testAntragsnummerNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Antragsnummer(null);
        });
    }
}
