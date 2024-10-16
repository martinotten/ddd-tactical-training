CREATE TABLE antragsteller_cluster (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    antragsnummer VARCHAR(255) NOT NULL,
    wohnort VARCHAR(255),
    guthaben DECIMAL(19, 2),
    version BIGINT
);
