package com.bigpugloans.scoring.adapter.driven.backends;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.LeseKontoSaldo;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class LeseKontoSaldoAdapter implements LeseKontoSaldo {
    private final HashSet<Waehrungsbetrag> kontoSalden = new HashSet<>();

    public LeseKontoSaldoAdapter() {
        kontoSalden.add(new Waehrungsbetrag(1000));
        kontoSalden.add(new Waehrungsbetrag(-1000));
        kontoSalden.add(new Waehrungsbetrag(10000));
        kontoSalden.add(new Waehrungsbetrag(11100));
        kontoSalden.add(new Waehrungsbetrag(211000));
        kontoSalden.add(new Waehrungsbetrag(1500));
        kontoSalden.add(new Waehrungsbetrag(13000));
        kontoSalden.add(new Waehrungsbetrag(21000));
        kontoSalden.add(new Waehrungsbetrag(3000));
        kontoSalden.add(new Waehrungsbetrag(4500));
        kontoSalden.add(new Waehrungsbetrag(1000000));
        kontoSalden.add(new Waehrungsbetrag(-10000));
        kontoSalden.add(new Waehrungsbetrag(102000));
        kontoSalden.add(new Waehrungsbetrag(80000));
        kontoSalden.add(new Waehrungsbetrag(200));
    }

    @Override
    public Waehrungsbetrag leseKontoSaldo(String kundennummer) {
        List<Waehrungsbetrag> list = new ArrayList<Waehrungsbetrag>(kontoSalden);
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
