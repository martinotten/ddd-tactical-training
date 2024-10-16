CREATE TABLE scoring_antragsteller_cluster (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    antragsnummer VARCHAR(255) NOT NULL,
    wohnort VARCHAR(255),
    guthaben DECIMAL(19, 2),
    version BIGINT
);
CREATE UNIQUE INDEX idx_scoring_antragsteller_antragsnummer ON scoring_antragsteller_cluster(antragsnummer);

CREATE TABLE SCORING_SCORING_ERGEBNIS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    antragsnummer VARCHAR(255) NOT NULL,
    auskunftei_punkte INTEGER,
    auskunftei_ko_kriterien INTEGER,
    antragsteller_punkte INTEGER,
    antragsteller_ko_kriterien INTEGER,
    monatliche_finanzsituation_punkte INTEGER,
    monatliche_finanzsituation_ko_kriterien INTEGER,
    immobilien_finanzierung_punkte INTEGER,
    immobilien_finanzierung_ko_kriterien INTEGER,
    gesamt_punkte INTEGER,
    gesamt_ko_kriterien INTEGER,
    version BIGINT
);
