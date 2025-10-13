package com.bigpugloans.scoring.adapter.driven.backends;

import com.bigpugloans.scoring.domain.model.Antrag;
import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.KonditionsAbfrage;
import com.bigpugloans.scoring.application.ports.driven.KonditionsAbfrageService;
import org.springframework.stereotype.Component;

@Component
public class KonditionsAbfrageServiceAdapter implements KonditionsAbfrageService {
    
    private final KonditionsAbfrage konditionsAbfrage;
    
    public KonditionsAbfrageServiceAdapter(KonditionsAbfrage konditionsAbfrage) {
        this.konditionsAbfrage = konditionsAbfrage;
    }
    
    @Override
    public AuskunfteiErgebnis konditionsAbfrage(Antrag antrag) {
        return konditionsAbfrage.konditionsAbfrage(
                antrag.getAntragsteller().getVorname(),
                antrag.getAntragsteller().getNachname(),
                antrag.getAntragsteller().getStrasse(),
                antrag.getAntragsteller().getOrt(),
                antrag.getAntragsteller().getPostleitzahl(),
                antrag.getAntragsteller().getGeburtstdatum().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        );
    }
}