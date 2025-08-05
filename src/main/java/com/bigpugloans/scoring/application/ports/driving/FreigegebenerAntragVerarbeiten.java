package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.events.antrag.Antrag;

public interface FreigegebenerAntragVerarbeiten {
    
    void freigegebenerAntragVerarbeiten(Antrag antrag);
}