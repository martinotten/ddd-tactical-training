package com.bigpugloans.scoring.domainmodel;

public record AntragKonnteNichtGescoredWerden(Antragsnummer antragsnummer, String hinweis) implements AntragScoringEvent {
}
