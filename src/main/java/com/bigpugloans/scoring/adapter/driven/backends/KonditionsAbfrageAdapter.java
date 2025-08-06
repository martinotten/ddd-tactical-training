package com.bigpugloans.scoring.adapter.driven.backends;

import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.KonditionsAbfrage;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@InfrastructureRing
@SecondaryAdapter
public class KonditionsAbfrageAdapter implements KonditionsAbfrage {
    private final HashSet<AuskunfteiErgebnis> auskunfteiErgebnisse = new HashSet<>();

    public KonditionsAbfrageAdapter() {
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(0, 0, 99));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(0, 0, 57));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(0, 1, 85));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(2, 0, 85));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(4, 0, 58));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(4, 1, 90));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(2, 2, 83));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(0, 0, 83));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(1, 0, 87));
        auskunfteiErgebnisse.add(new AuskunfteiErgebnis(0, 0, 87));

    }

    @Override
    public AuskunfteiErgebnis konditionsAbfrage(String vorname, String nachname, String strasse, String stadt, String plz, LocalDate geburtsdatum) {
        List<AuskunfteiErgebnis> list = new ArrayList<>(auskunfteiErgebnisse);
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
