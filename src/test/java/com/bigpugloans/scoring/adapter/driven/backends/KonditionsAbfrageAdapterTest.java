package com.bigpugloans.scoring.adapter.driven.backends;

import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KonditionsAbfrageAdapterTest {
    @Test
    public void testKonditionsAbfrageAdapter() {
        KonditionsAbfrageAdapter konditionsAbfrageAdapter = new KonditionsAbfrageAdapter();
        AuskunfteiErgebnis auskunfteiErgebnis = konditionsAbfrageAdapter.konditionsAbfrage("Michael", "Plöd", "40789", "Monheim", "Krischerstrasse 100", LocalDate.now());
        assertNotNull(auskunfteiErgebnis);
    }
}
