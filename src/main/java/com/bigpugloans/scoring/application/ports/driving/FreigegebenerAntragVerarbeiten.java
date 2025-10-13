package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.domain.model.Antrag;

public interface FreigegebenerAntragVerarbeiten {
    
    void freigegebenerAntragVerarbeiten(Antrag antrag);
}