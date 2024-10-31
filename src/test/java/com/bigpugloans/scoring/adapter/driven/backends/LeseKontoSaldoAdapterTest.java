package com.bigpugloans.scoring.adapter.driven.backends;

import com.bigpugloans.scoring.application.ports.driven.LeseKontoSaldo;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LeseKontoSaldoAdapterTest {
    @Test
    public void testLeseKontoSaldoAdapter() {
        LeseKontoSaldo kontoSaldoAdapter = new LeseKontoSaldoAdapter();
        Waehrungsbetrag saldo = kontoSaldoAdapter.leseKontoSaldo("2344");
        assertNotNull(saldo);
    }
}
