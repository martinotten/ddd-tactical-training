# Leitfaden – DDD Tactical Design Training

> Ziel: Selbststudium pro Branch, mit nachvollziehbarer Evolution der DDD‑Tactical‑Patterns.

## Fallstudie (Rahmen)
Dieses Training basiert auf der Fallstudie **„Big Pug Loans“** der Mops Bank. Ziel ist eine Scoring‑Plattform für Baufinanzierungen. Das Scoring wurde als strategisch wichtige, aber komplexe **Core Domain** identifiziert. Die fachlichen Regeln müssen daher zuverlässig und änderbar abgebildet werden. Die Fallstudie liefert die fachlichen Regeln, die Cluster‑Sicht sowie den Prozess‑Kontext (Antragserfassung und -prüfung, Pre‑Scoring, Main‑Scoring, externe Auskunftei, Immobilienbewertung).
- Quelle: `Fallstudie Tactical DDD.pdf`

## Einstieg: So arbeitest du mit dem Leitfaden
- Dieses Code-Beispiel für die Schulung führt in einzelnen Schritten durch den taktischen DDD‑Design Prozess.
- Jeder Branch enthält einen weiteren Schritt, der auf dem vorherigen Branch aufbaut.
- Dieser Leitfaden beschreibt die Schritte zum Selbststudium.
- Der erste Teil der Branches welchselt jeweils zwischen Rot und Grün. Wir gehen hier nach TDD vor und beschreiben zuerst unsere Designänderungen als Tests bevor diese umgesetzt werden.
- Es ist ratsam jeden Schritt selbst zu implementieren und dann mit der Musterlösung im folgenden Branch zu vergleichen.
- Bei Bedarf kann immer zur Musterlösung zurückgegriffen werden.

## Voraussetzungen & Setup
- Java 21, aktuelle Maven‑Version, Docker (für Testcontainer).
- IntelliJ IDEA als IDE.

## Branch‑Übersicht

| Reihenfolge | Branch | Kurzbeschreibung | Startpunkt |
| --- | --- | --- | --- |
| 1 | `origin/0_UnitTests_red` | Nur Unit‑Tests als fachliche Spezifikation (rot) | `origin/main` |
| 2 | `origin/1_UnitTests_green` | Minimaler Domain‑Code, der die Unit‑Tests grün macht | `origin/0_UnitTests_red` |
| 3 | `origin/2_RuleGroups_red` | Tests fachlich gruppiert (Cluster), bewusst wieder „rot“ | `origin/1_UnitTests_green` |
| 4 | `origin/3_RuleGroups_green` | Minimaler Cluster‑Code, der die gruppierten Tests grün macht | `origin/2_RuleGroups_red` |
| 5 | `origin/4_CommandsEvents_red` | Tests nutzen Commands und Cluster‑Ergebnisobjekte (rot) | `origin/3_RuleGroups_green` |
| 6 | `origin/5_CommandsEvents_green` | Command‑basierter Cluster‑Code (grün) | `origin/4_CommandsEvents_red` |
| 7 | `origin/6_ValueObjects_red` | Value‑Object‑Tests eingeführt (rot) | `origin/5_CommandsEvents_green` |
| 8 | `origin/7_ValueObjects_green` | Value Objects implementiert (grün) | `origin/6_ValueObjects_red` |
| 9 | `origin/8_Entities_red` | Entity‑Tests und Identitäten eingeführt (rot) | `origin/7_ValueObjects_green` |
| 10 | `origin/9_Entities_green` | Entities implementiert (grün) | `origin/8_Entities_red` |
| 11 | `origin/10_Aggregates_red` | Aggregat‑Regeln und Konsistenzgrenzen (rot) | `origin/9_Entities_green` |
| 12 | `origin/11_Aggregates_green` | Aggregates umgesetzt (grün) | `origin/10_Aggregates_red` |
| 13 | `origin/12_Ports` | Einführung von Ports (Driving/Driven) | `origin/11_Aggregates_green` |
| 14 | `origin/13_ApplicationServices` | Application Services eingeführt | `origin/12_Ports` |
| 15 | `origin/14_DomainServices` | Domain Services eingeführt | `origin/13_ApplicationServices` |
| 16 | `origin/15-0_DrivenAdapters_UnitTests` | Adapter‑Verträge via Unit Tests | `origin/14_DomainServices` |
| 17 | `origin/15-1_DrivenAdapters_Memento-Pattern` | Adapter‑Implementierung mit Memento + JDBC | `origin/15-0_DrivenAdapters_UnitTests` |
| 18 | `origin/15-2_DrivenAdapters_SpringData-MongoDB` | Adapter‑Implementierung mit MongoDB | `origin/15-1_DrivenAdapters_Memento-Pattern` |
| 19 | `origin/15-3_DrivenAdapters_JPA-Annotated` | Adapter‑Implementierung mit JPA‑Annotations im Domänenmodell | `origin/15-2_DrivenAdapters_SpringData-MongoDB` |
| 20 | `origin/16_DrivenAdapters_Backends` | Backend‑Adapter (Auskunftei, Konto) | `origin/15-3_DrivenAdapters_JPA-Annotated` |
| 21 | `origin/17-1_MessagingAdapters_SpringApplicationEvents` | Messaging‑Adapter (Spring Events) | `origin/16_DrivenAdapters_Backends` |
| 22 | `origin/18_Architecture_Tests` | Architekturtests (Onion/Hexagonal/DDD) | `origin/17-1_MessagingAdapters_SpringApplicationEvents` |
| 23 | `origin/19_Antragserfassung_DomainModeling_EventSourced` | Bonus‑Ausblick: Event Sourcing | `origin/18_Architecture_Tests` |

---

# Branch: `0_UnitTests_red`

## 1) Ziel & Kontext
- TDD‑Start: Alle identifizierten fachlichen Regeln werden als Unit‑Tests formuliert, ohne Implementierung.
- Fokus auf DDD‑Tactical Design: Ubiquitous Language wird durch die Testbegriffe etabliert.
- Wichtig: In diesem Schritt werden Cluster noch nicht vorausgesetzt; die Regeln liegen bewusst ungruppiert vor.
> Fokus: Fachbegriffe und Regeln sichtbar machen.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Einführung von `BusinessRulesTest` mit fachlichen Regeln.
- **Technisch:** Neue Tests in `src/test/java/.../domainmodel`, aber kein Produktivcode.
- **Fachlich:** Scoring‑Regeln (KO‑Kriterien, Punkte, Schwellen) werden explizit beschrieben.
 - **Hinweis:** Die Regeln sind bewusst nicht gruppiert; die Cluster entstehen erst im nächsten Schritt aus der Fachsicht.

## 3) Warum diese Änderungen?
- Tests dienen als fachliche Spezifikation und Gesprächsgrundlage für das Domänenmodell.
- Der rote Zustand zwingt zu Modellierungsentscheidungen in den nächsten Schritten.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Baut auf der leeren Basis auf; ergänzt nur Tests, keine Architekturentscheidungen.
 - Die ungruppierten Regeln schaffen eine neutrale Ausgangslage für spätere fachliche Strukturierung.

## 5) DDD‑Tactical‑Patterns im Detail
- **Value Object (angelegt):** Konzepte wie `Waehrungsbetrag`, `Prozentwert`, `Punkte` sind in Tests benannt, aber noch nicht implementiert.
- **Entity/Aggregat/Domain Event/Repository/Factory:** Nicht vorhanden in diesem Schritt.

## 6) Code‑Navigationshilfe
- Einstiegspunkt: `src/test/java/com/bigpugloans/scoring/domainmodel/BusinessRulesTest.java`

## 7) Übungen / Reflexion
- Welche Begriffe wirken wie Value Objects, welche eher wie Entities?
- Welche Invarianten (z. B. KO‑Kriterien) müssen im Modell geschützt werden?

## 8) Zusammenfassung
- Dieser Branch setzt den fachlichen Rahmen und macht Erwartungen explizit.
- Der rote Zustand ist bewusst und leitet zur Modellierung im nächsten Schritt über.

---

# Branch: `1_UnitTests_green`

## 1) Ziel & Kontext
- Minimaler Domain‑Code, um die Tests aus `0_UnitTests_red` zu erfüllen.
- Fokus: Erste Value Objects und einfache Regelimplementierung, um das Modell schrittweise zu entwickeln.
 - Cluster werden hier noch nicht modelliert; das Ziel ist nur „grün“ mit minimalem Code.
> Fokus: Minimaler Code, der Regeln abbildet – ohne Strukturentscheidungen vorwegzunehmen.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Einführung einfacher Domainklassen in `com.bigpugloans.scoring.domainmodel`.
- **Technisch:** Klassen wie `Waehrungsbetrag`, `Prozentwert`, `Punkte`, `ScoringErgebnis`, `Finanzierung`, `Haushaltsfinanzen` etc.
- **Fachlich:** KO‑Kriterien, Punktevergabe und Scoring‑Farbe sind erstmals implementiert.

## 3) Warum diese Änderungen?
- Der rote Testzustand wird mit minimaler Logik in „grün“ überführt.
- Zweck: Diskussion über Modellqualität, fehlende Invarianten und DDD‑Design‑Schulden.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Tests bleiben unverändert; es wird nur das Nötigste ergänzt.
- Der Code spiegelt exakt die im Test beschriebenen Regeln wider.
 - Die ungruppierte Struktur bleibt bestehen, damit der Schritt zur Cluster‑Sicht bewusst im nächsten Branch erfolgt.

## 5) DDD‑Tactical‑Patterns im Detail
- **Value Object:**
  - `Waehrungsbetrag`, `Prozentwert`, `Punkte` kapseln einfache Vergleiche.
  - Noch kaum Immutability/Validierung (bewusst minimal für den Schritt).
- **Entity/Aggregat:** Noch keine expliziten Entities oder Aggregatgrenzen.
- **Domain Event/Repository/Factory:** Nicht vorhanden.

## 6) Code‑Navigationshilfe
- Implementierungen: `src/main/java/com/bigpugloans/scoring/domainmodel/*`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Waehrungsbetrag.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Prozentwert.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Punkte.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ScoringErgebnis.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Finanzierung.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Haushaltsfinanzen.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/AuskunfteiErgebnis.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Eigenkapitalanteil.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Guthaben.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Immobilie.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Wohnort.java`
- Tests: `src/test/java/com/bigpugloans/scoring/domainmodel/BusinessRulesTest.java`

## 7) Übungen / Reflexion
- Wo fehlen fachliche Invarianten (z. B. Währungsgleichheit, Prozentgrenzen)?
- Welche Klassen sollten Value Objects bleiben, welche brauchen Identität?
- Welche Regeln gehören in Services/Aggregate statt in „anämischen“ Klassen?

## 8) Zusammenfassung
- Der Branch liefert einen funktionierenden, aber bewusst simplen Ausgangspunkt.
- Damit entsteht eine Basis, um in späteren Schritten DDD‑Patterns zu schärfen.

---

# Branch: `2_RuleGroups_red` (Referenz: `origin/2_RuleGroups_red`)

> Didaktischer Übergang: Die Regeln wurden bisher bewusst ungruppiert formuliert. Ab diesem Schritt wird die Fachsicht explizit: Regel‑Cluster werden erkannt, benannt und als Struktur eingeführt.

## 1) Ziel & Kontext
- Fachliche Regeln werden in Cluster gruppiert (z. B. Antragsteller‑, Auskunftei‑, Immobilien‑ und Finanzsituation‑Cluster).
- DDD‑Fokus: Die Sicht des Fachbereichs wird übernommen — Punkte und KO‑Regeln gehören jeweils zu einem Cluster, nicht als globaler Schnitt.
- Ab diesem Branch werden Cluster explizit erkannt und benannt.
> Fokus: Fachliche Gruppierung als Strukturentscheidung.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests wurden in fachliche Cluster aufgeteilt und präzisiert.
- **Technisch:** Neue Testklassen und Packages, z. B. `.../antragstellerCluster`, `.../auskunfteiErgebnisCluster`, `.../immobilienFinanzierungsCluster`, `.../monatlicheFinanzsituationCluster`, `.../scoringErgebnis`.
- **Fachlich:** Regeln sind nicht mehr „flach“ in einer Datei, sondern in thematischen Gruppen.
- **Zusatz:** `junit-jupiter-params` wird für parameterisierte Tests ergänzt.
 - **Hinweis:** `BusinessRulesTest` existiert weiterhin und wird dadurch funktional redundant.

## 3) Warum diese Änderungen?
- Regelgruppen machen fachliche Zusammenhänge sichtbar und unterstützen spätere Aggregat‑/Bounded‑Context‑Entscheidungen.
- Der rote Zustand zwingt zur Umstrukturierung des Domänenmodells in Cluster.
 - Das Training verdeutlicht die bewusste Zäsur: erst Regeln sammeln, dann fachlich gruppieren.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Die fachlichen Regeln bleiben gleich, aber die Tests verlangen neue Strukturen (Cluster‑Klassen und Packages).
- Das bisherige „monolithische“ Testdesign wird bewusst aufgebrochen.

## 5) DDD‑Tactical‑Patterns im Detail
- **Cluster als Vorstufe zu Aggregaten:** Tests schaffen fachliche Grenzen, ohne bereits echte Aggregat‑Regeln einzuziehen.
- **Regelzuordnung:** KO‑Kriterien und Punkte sind cluster‑intern gedacht; eine übergreifende Trennung ist fachlich nicht sinnvoll.
- **Value Objects:** Weiterhin in den Tests benannt (z. B. `Waehrungsbetrag`, `Prozentwert`, `Punkte`), aber die Struktur liegt jetzt in Cluster‑Tests.

## 6) Code‑Navigationshilfe
- Neue Testgruppen:
  - `src/test/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnisTest.java`

## 7) Übungen / Reflexion
- Welche Cluster könnten später Aggregat‑Wurzeln werden?
- Welche Regeln sind cluster‑intern, welche sind cross‑cluster?

## 8) Zusammenfassung
- Der Branch strukturiert Regeln fachlich und macht Modellgrenzen sichtbar.
- Der rote Zustand bereitet die Umstrukturierung des Domänenmodells vor.

---

# Branch: `origin/3_RuleGroups_green`

## 1) Ziel & Kontext
- Minimaler Domain‑Code wird an die neuen Regelgruppen angepasst.
- DDD‑Fokus: Cluster werden in Produktivcode überführt, inklusive ihrer jeweils eigenen Punkte‑ und KO‑Regeln.
> Fokus: Clustern eine Form geben, ohne Übermodellierung.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Einführung von Cluster‑Klassen und Bereinigung der alten „flachen“ Klassen.
- **Technisch:** Neue Klassen wie `AntragstellerCluster`, `AuskunfteiErgebnisCluster`, `ImmobilienFinanzierungsCluster`.
- **Struktur:** `ScoringErgebnis` wandert in ein eigenes Unterpaket, `MonatlicheFinanzsituationCluster` ersetzt `Haushaltsfinanzen`.
- **Aufräumen:** `BusinessRulesTest` wird entfernt; parameterisierte Tests werden wieder vereinfacht.
 - **Regeländerung:** Marktwert‑Punkte werden in Tests/Implementierung von 15 auf 10 reduziert.

## 3) Warum diese Änderungen?
- Die Teststruktur aus `2_RuleGroups_red` wird in eine erste fachliche Modellstruktur überführt.
- Schrittweise Modell‑Evolution: erst Gruppierung, dann minimale Implementierung.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Tests bleiben gruppiert; der Code wird so angepasst, dass die Cluster‑Tests grün werden.
- Alte Klassen/Strukturen werden zugunsten der neuen Cluster‑Grenzen ersetzt.

## 5) DDD‑Tactical‑Patterns im Detail
- **Cluster als Modellbaustein:** Klassen je Regelgruppe bündeln Regeln und Daten.
- **Regelzuordnung:** Punkte und KO‑Regeln bleiben im jeweiligen Cluster verankert (fachliche Sicht).
- **Value Objects:** `Punkte` erhält eine Additions‑Operation (`plus`) zur Aggregation von Teilpunkten.
- **Aggregat‑Gedanke (implizit):** Cluster bündeln Regeln, ohne explizite Aggregate zu definieren.

## 6) Code‑Navigationshilfe
- Cluster‑Implementierungen:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/MonatlicheFinanzsituationCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnis.java`
- Tests (gruppiert): wie in `origin/2_RuleGroups_red`.

## 7) Übungen / Reflexion
- Wo fehlen Invarianten/Validierungen in den Cluster‑Klassen?
- Welche Regeln sollten eher in Aggregat‑Methoden oder Domain Services liegen?
- Welche Cluster benötigen Identität und Lebenszyklus (Entity) vs. reine Value Objects?

## 8) Zusammenfassung
- Die fachliche Gruppierung wird in Code übersetzt, Tests sind wieder grün.
- Damit entsteht eine stabilere Basis für spätere taktische Muster (Value Objects, Entities, Aggregate).

---

# Branch: `origin/4_CommandsEvents_red`

> Didaktischer Übergang: Nach der fachlichen Gruppierung werden nun die Interaktionen fachlich benannt. Commands ersetzen Setters, um die Sprache des Fachbereichs abzubilden.

## 1) Ziel & Kontext
- Einführung von Commands (z. B. `...Hinzufuegen`) zur fachlichen Interaktion mit Clustern.
- DDD‑Fokus: Fachliche Aktionen statt technischer Setter; Cluster liefern ein explizites Scoring‑Ergebnis.
> Fokus: Sprache des Fachbereichs in der API.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests wurden auf Command‑Aufrufe umgestellt und erwarten Cluster‑Ergebnisobjekte.
- **Technisch:** Neue Typen `ClusterGescored` und `KoKriterien` im Domain‑Modell.
- **Fachlich:** Cluster „scoren“ und liefern Punkte + KO‑Kriterien zusammen zurück.

## 3) Warum diese Änderungen?
- Commands machen den Fachkontext explizit („Einkommen hinzufügen“ statt „setXYZ“).
- Das Ergebnis eines Clusters wird als Value Object greifbar und später kombinierbar.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Regelcluster bleiben erhalten, aber der Interaktionsstil ändert sich von Settern zu Commands.
- Der rote Zustand zwingt die Modell‑API und die internen Berechnungen neu zu denken.

## 5) DDD‑Tactical‑Patterns im Detail
- **Command‑Stil:** Methoden wie `wohnortHinzufuegen`, `warnungenHinzufuegen`, `summeDarlehenHinzufuegen`.
- **Ergebnisobjekt:** `ClusterGescored` kapselt Punkte und KO‑Kriterien pro Cluster.
- **Regelzuordnung:** Punkte/KO bleiben im Cluster; das Ergebnis ist die fachliche Schnittstelle.

## 6) Code‑Navigationshilfe
- Neue Domain‑Typen:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterGescored.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/KoKriterien.java`
- Tests (Command‑basiert):
  - `src/test/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnisTest.java`

## 7) Übungen / Reflexion
- Welche Commands fehlen noch, um die Fachsprache präzise abzubilden?
- Wo ist ein Command wirklich fachlich und nicht nur ein umbenannter Setter?

## 8) Zusammenfassung
- Die Fachsprache wird durch Commands sichtbar.
- Cluster liefern nun explizite Ergebnisobjekte, der Code ist bewusst wieder „rot“.

---

# Branch: `origin/5_CommandsEvents_green`

## 1) Ziel & Kontext
- Umsetzung der Command‑Schnittstellen im Domain‑Code, sodass die Tests grün werden.
- DDD‑Fokus: Cluster liefern `ClusterGescored` als fachliches Ergebnisobjekt.
> Fokus: Command‑Schnittstellen als Vertrag.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Cluster implementieren `scoren()` und verarbeiten Commands.
- **Technisch:** KO‑Logik und Punkteberechnung sind in den Clustern integriert; `ScoringErgebnis` aggregiert Cluster‑Ergebnisse.
- **Aufräumen:** Frühere Hilfsklassen (`Wohnort`, `Guthaben`, `Eigenkapitalanteil`) werden entfernt und Logik direkt im Cluster modelliert.
- **Wertlogik:** `Waehrungsbetrag.anteilVon` und `Prozentwert(BigDecimal)` unterstützen Berechnung des Eigenkapitalanteils.
- **Struktur:** `MonatlicheFinanzsituationCluster` liegt nun im passenden Unterpaket.

## 3) Warum diese Änderungen?
- Das fachliche „Scoren“ eines Clusters ist eine zentrale Aktion und liefert ein konsistentes Ergebnisobjekt.
- Die Modelle werden näher an die Sprache des Fachbereichs gebracht.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Die Command‑Tests bleiben bestehen; der Code implementiert nun die erwartete API.
- Die Cluster übernehmen sowohl Punkte‑ als auch KO‑Regeln intern.

## 5) DDD‑Tactical‑Patterns im Detail
- **Command‑Modellierung:** `...Hinzufuegen`‑Methoden als fachliche Befehle.
- **Value Object:** `ClusterGescored` kapselt Ergebnis; `KoKriterien` repräsentiert Zählung.
- **Cluster‑Schnittstelle:** `scoren()` bildet den fachlichen Abschluss eines Clusters ab.

## 6) Code‑Navigationshilfe
- Cluster‑Implementierungen:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnis.java`
- Ergebnisobjekte/Value Objects:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterGescored.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/KoKriterien.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Waehrungsbetrag.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Prozentwert.java`

## 7) Übungen / Reflexion
- Welche Commands sollten invariant‑sicher sein (z. B. verbieten negativer Werte)?
- Ist `ClusterGescored` die richtige Granularität oder braucht es reichhaltigere Ergebnisse?

## 8) Zusammenfassung
- Command‑basierte Interaktion ist implementiert, die Tests sind grün.
- Die Cluster liefern fachliche Ergebnisse als Value Objects und bereiten weitere Evolution vor.

---

# Branch: `origin/6_ValueObjects_red`

> Didaktischer Übergang: Wir trennen „Attribute“ von echten fachlichen Konzepten. Value Objects kapseln Regeln und Bedeutung, nicht nur Daten.

## 1) Ziel & Kontext
- Einführung von Value‑Object‑Tests für fachliche Konzepte (z. B. Wohnort, Guthaben, Warnung).
- DDD‑Fokus: Ein Value Object ist kein primitives Attribut, sondern ein Business‑Objekt mit Regeln.
> Fokus: Was ist ein Value Object – und was nicht?

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests werden auf eigenständige fachliche Begriffe aufgeteilt.
- **Technisch:** Neue Testklassen wie `GuthabenTest`, `WohnortTest`, `WarnungTest`, `NegativMerkmalTest`, `RueckzahlungsWahrscheinlichkeitTest`, `MarktwertVergleichTest`.
- **Fachlich:** Regeln wandern gedanklich aus den Clustern in eigenständige Begriffe.

## 3) Warum diese Änderungen?
- Fachliche Konzepte werden explizit und separat testbar.
- Es wird klar, welche Regeln zu einem Begriff gehören (nicht zu einem „zusammengewürfelten“ Cluster).

## 4) Wie baut der Branch auf dem vorherigen auf?
- Command‑Schnittstellen bleiben; zusätzlich werden Value‑Object‑Tests eingeführt.
- Der rote Zustand zeigt, dass die Value Objects noch fehlen.

## 5) DDD‑Tactical‑Patterns im Detail
- **Value Object (neu):** `Guthaben`, `Wohnort`, `Warnung`, `NegativMerkmal`, `RueckzahlungsWahrscheinlichkeit`, `MarktwertVergleich`.
- **Abgrenzung:** Value Objects sind fachliche Konzepte mit Regeln; kein „einfaches Attribut“.

## 6) Code‑Navigationshilfe
- Neue Tests:
  - `src/test/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/GuthabenTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/WohnortTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/NegativMerkmalTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/WarnungTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/RueckzahlungsWahrscheinlichkeitTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/MarktwertVergleichTest.java`

## 7) Übungen / Reflexion
- Welche Begriffe sind echte Value Objects und welche eher Entities?
- Welche Regeln sollten direkt im Value Object validiert werden?

## 8) Zusammenfassung
- Die fachlichen Begriffe werden als eigene Value Objects sichtbar gemacht.
- Der Branch ist bewusst „rot“, um die Implementierung der Konzepte zu erzwingen.

---

# Branch: `origin/7_ValueObjects_green`

> Didaktischer Übergang: Value Objects werden implementiert und in die Cluster integriert. Regeln sitzen nun dort, wo der Fachbegriff lebt.

## 1) Ziel & Kontext
- Implementierung der Value Objects und Integration in die Cluster.
- DDD‑Fokus: Value Objects kapseln Regeln und Bedeutung, nicht nur Daten.
> Fokus: Regeln gehören zu Begriffen.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Value Objects sind jetzt Teil des Produktivcodes.
- **Technisch:** Klassen wie `Guthaben`, `Wohnort`, `Warnung`, `NegativMerkmal`, `RueckzahlungsWahrscheinlichkeit`, `MarktwertVergleich` werden eingeführt.
- **Modellfluss:** Cluster delegieren Punkte/KO‑Regeln an Value Objects.
- **Ergebniszustand:** `ClusterGescored` erhält `ClusterStatus` (gescored/nicht gescored).

## 3) Warum diese Änderungen?
- Regeln wohnen bei den passenden Begriffen, nicht verstreut in Clustern.
- Das Modell wird ausdrucksstärker und resilient gegen Regeländerungen.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Die Value‑Object‑Tests aus `origin/6_ValueObjects_red` werden grün.
- Cluster‑Logik wird vereinfacht, weil sie auf fachliche Objekte delegiert.

## 5) DDD‑Tactical‑Patterns im Detail
- **Value Object (zentral):** Kapselt Regeln und Vergleichslogik (z. B. `Warnung`, `Guthaben`).
- **Nicht‑Beispiel:** Ein reines Feld ohne Regel wäre kein Value Object.
- **Verschärfte Definition:** Ein Value Object ist ein fachlicher Begriff mit eigener Logik, Regeln und Invarianten, der über seinen Wert definiert ist (Gleichheit über Attribute) und keinen eigenen Lebenszyklus/Identität besitzt. Ein „String“ oder „int“ ohne Fachregel ist kein Value Object.
- **Status‑Objekt:** `ClusterStatus` definiert den Zustand des Cluster‑Ergebnisses.

## 6) Code‑Navigationshilfe
- Value Objects:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/Guthaben.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/Wohnort.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/NegativMerkmal.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/Warnung.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/RueckzahlungsWahrscheinlichkeit.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/MarktwertVergleich.java`
- Cluster‑Integration:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsCluster.java`
- Ergebnisstatus:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterGescored.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterStatus.java`

## 7) Übungen / Reflexion
- Welche Value Objects brauchen eigene Validierungsregeln (z. B. Grenzen, Null‑Checks)?
- Wo könnten Value Objects wiederverwendet oder kombiniert werden?

## 8) Zusammenfassung
- Value Objects sind umgesetzt und tragen die fachlichen Regeln.
- Das Modell trennt klar zwischen „Attribut“ und fachlichem Konzept.

---

# Branch: `origin/8_Entities_red`

> Didaktischer Übergang: Wir prüfen, welche bisherigen Value Objects tatsächlich eine Identität und einen Lebenszyklus benötigen. Daraus entstehen Entities.

## 1) Ziel & Kontext
- Einführung von Entity‑Tests und Identitäten (z. B. `Antragsnummer`, `AntragstellerID`).
- DDD‑Fokus: Entities werden über Identität definiert, nicht über ihren aktuellen Wert.
> Fokus: Lebenszyklus und Identität.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests verlangen Identitäten und Gleichheit über IDs.
- **Technisch:** Neue Tests `AntragsnummerTest` und `AntragstellerIDTest`; Cluster‑Tests erwarten Konstruktoren mit IDs.
- **Fachlich:** Auskunftei‑Ergebnisse und Scoring‑Ergebnisse sind nun an Antragsnummer/Antragsteller gebunden.

## 3) Warum diese Änderungen?
- Fachliche Konzepte wie „Antrag“ und „Antragsteller“ haben einen Lebenszyklus und müssen eindeutig identifizierbar sein.
- Value Objects allein reichen nicht aus, wenn wir historisieren oder Zustände über Zeit hinweg verfolgen müssen.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Value Objects bleiben bestehen; zusätzlich wird eine Identität eingeführt.
- Der rote Zustand zwingt die Modellierung von Entities (und IDs) in den Clustern.

## 5) DDD‑Tactical‑Patterns im Detail
- **Entity (neu):** Identität als primäres Kriterium (z. B. `Antragsnummer`, `AntragstellerID`).
- **Abgrenzung:** Sobald Lebenszyklus/Identität benötigt wird, ist es kein Value Object mehr.

## 6) Code‑Navigationshilfe
- Neue Tests:
  - `src/test/java/com/bigpugloans/scoring/domainmodel/AntragsnummerTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/AntragstellerIDTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnisTest.java`

## 7) Übungen / Reflexion
- Welche bisherigen Value Objects benötigen tatsächlich eine Identität?
- Welche Prozesse (z. B. Antragshistorie) erzwingen Entities?

## 8) Zusammenfassung
- Die Notwendigkeit von Identität wird explizit, Entities werden vorbereitet.
- Der Branch ist bewusst „rot“, um die Entity‑Implementierung auszulösen.

---

# Branch: `origin/9_Entities_green`

> Didaktischer Übergang: Entities werden implementiert, Identität wird zum zentralen Gleichheitskriterium.

## 1) Ziel & Kontext
- Umsetzung der Entities und Integration in Cluster und Scoring‑Ergebnis.
- DDD‑Fokus: Identität und Lebenszyklus bestimmen die Modellierung.
> Fokus: Identität als Gleichheitskriterium.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Entity‑Klassen werden eingeführt und in Cluster/Scoring verwendet.
- **Technisch:** `Antragsnummer` und `AntragstellerID` werden implementiert; `ScoringErgebnis` und `AuskunfteiErgebnisCluster` nutzen Identitäten.
- **Gleichheit:** `equals/hashCode` basieren auf Identität, nicht auf Attributwerten.

## 3) Warum diese Änderungen?
- Fachliche Stabilität: Ein Antrag bleibt „derselbe“ trotz geänderter Attribute.
- Identität erlaubt konsistente Referenzierung und Nachvollziehbarkeit.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Tests aus `origin/8_Entities_red` werden grün.
- Value Objects bleiben, Entities strukturieren nun den Lebenszyklus.

## 5) DDD‑Tactical‑Patterns im Detail
- **Entity:** `Antragsnummer`, `AntragstellerID` als Identitätsanker.
- **Value Object vs. Entity:** Entscheidung anhand Lebenszyklus, nicht anhand Komplexität.

## 6) Code‑Navigationshilfe
- Entities:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/Antragsnummer.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/AntragstellerID.java`
- Entity‑Nutzung:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnis.java`

## 7) Übungen / Reflexion
- Wo ist Identität fachlich zwingend, wo wäre sie Overhead?
- Welche Entities könnten später Aggregate werden?

## 8) Zusammenfassung
- Entities sind implementiert und bilden den Lebenszyklus ab.
- Das Modell ist bereit für Aggregat‑Entscheidungen in den nächsten Schritten.

---

# Branch: `origin/10_Aggregates_red`

> Didaktischer Übergang: Nach Entities definieren wir Konsistenzgrenzen. Aggregate stellen sicher, dass alle Teile zu einem Antrag gehören und vollständig sind.

## 1) Ziel & Kontext
- Einführung von Aggregat‑Regeln (Konsistenzgrenzen, Vollständigkeit, gemeinsame Antragsnummer).
- DDD‑Fokus: Aggregate schützen Invarianten über mehrere Cluster hinweg.
> Fokus: Invarianten über mehrere Objekte.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests verlangen gleiche `Antragsnummer` in allen Cluster‑Ergebnissen und behandeln fehlende Teilergebnisse.
- **Technisch:** Cluster‑Tests prüfen `Optional`‑Ergebnisse bei fehlenden Daten.
- **Fachlich:** Ein Antrag kann „nicht gescored“ sein, wenn Teilinformationen fehlen.

## 3) Warum diese Änderungen?
- Aggregate definieren die fachliche Konsistenz: alle Teile müssen zum selben Antrag gehören.
- Vollständigkeit wird zu einer Invariante (kein Scoring ohne alle nötigen Daten).

## 4) Wie baut der Branch auf dem vorherigen auf?
- Entities bleiben, werden aber nun als Aggregate‑Schlüssel genutzt (`Antragsnummer`).
- Der rote Zustand erzwingt Aggregate‑Logik in Scoring und Clustern.

## 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root (implizit):** `ScoringErgebnis` bündelt Cluster‑Ergebnisse eines Antrags.
- **Invarianten:** Gleichheit der `Antragsnummer` und Vollständigkeit der Teilergebnisse.
- **Ergebniszustand:** Fehlende Cluster führen zu „nicht gescored“ statt falschem Ergebnis.

## 6) Code‑Navigationshilfe
- Aggregate‑Tests:
  - `src/test/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnisTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/immobilienFinanzierungsCluster/ImmobilienFinanzierungsClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/antragstellerCluster/AntragstellerClusterTest.java`
  - `src/test/java/com/bigpugloans/scoring/domainmodel/auskunfteiErgebnisCluster/AuskunfteiErgebnisClusterTest.java`

## 7) Übungen / Reflexion
- Welche Invarianten gehören in die Aggregate Root und welche in die Cluster?
- Wann ist „nicht gescored“ fachlich sinnvoller als ein negatives Ergebnis?

## 8) Zusammenfassung
- Aggregate‑Regeln werden explizit gefordert.
- Konsistenz und Vollständigkeit werden als fachliche Invarianten sichtbar.

---

# Branch: `origin/11_Aggregates_green`

> Didaktischer Übergang: Die Aggregat‑Invarianten werden umgesetzt; Scoring liefert fachliche Events statt nur Zustände.

## 1) Ziel & Kontext
- Umsetzung der Aggregate‑Regeln und Ergebnis‑Events.
- DDD‑Fokus: Aggregate Root verwaltet Konsistenz und Ergebnis‑Events.
> Fokus: Aggregates als Konsistenzgrenzen.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Aggregate‑Regeln sind implementiert, Scoring liefert Events.
- **Technisch:** `ClusterGescored` enthält jetzt `Antragsnummer`; Cluster‑`scoren()` liefert `Optional`.
- **Domain Events:** `AntragScoringEvent` mit `AntragErfolgreichGescored` und `AntragKonnteNichtGescoredWerden`.
- **Konsistenz:** `ScoringErgebnis` prüft Antragsnummern und wirft bei Abweichung.

## 3) Warum diese Änderungen?
- Aggregate Root schützt die Konsistenz der Scoring‑Daten.
- Events liefern eine klare, fachliche Schnittstelle an nachfolgende Schritte.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Tests aus `origin/10_Aggregates_red` werden grün.
- Clustern liefern nur noch Ergebnisse, wenn alle notwendigen Daten vorliegen.

## 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root:** `ScoringErgebnis` koordiniert Cluster‑Ergebnisse.
- **Domain Event:** `AntragErfolgreichGescored`, `AntragKonnteNichtGescoredWerden`.
- **Invarianten:** gleiche `Antragsnummer` und vollständige Cluster‑Daten.

## 6) Code‑Navigationshilfe
- Aggregate Root & Events:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/scoringErgebnis/ScoringErgebnis.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/AntragScoringEvent.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/AntragErfolgreichGescored.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/AntragKonnteNichtGescoredWerden.java`
- Cluster‑Ergebnisse:
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterGescored.java`
  - `src/main/java/com/bigpugloans/scoring/domainmodel/ClusterKonnteNochNichtGescoredWerden.java`

## 7) Übungen / Reflexion
- Welche Aggregate fehlen noch im Scoring‑Kontext?
- Wo sollten Domain Events später publiziert werden?

## 8) Zusammenfassung
- Aggregates sind umgesetzt, Invarianten werden durchgesetzt.
- Scoring liefert klare fachliche Events für die nächsten Prozessschritte.

---

# Branch: `origin/12_Ports`

> Didaktischer Übergang: Nach der Domänenmodellierung wird die Anwendungsgrenze klar. Ports definieren, was die Domäne braucht und was sie anbietet.
> Hinweis: Ab hier gibt es keine expliziten TDD‑Red/Green‑Schritte mehr, sondern evolutionäre Ausbauschritte.

## 1) Ziel & Kontext
- Einführung von Ports (Driving/Driven) für das Scoring‑Modul.
- DDD‑Fokus: Hexagonal/Ports‑&‑Adapters‑Sicht, ohne konkrete Implementierungen.
> Fokus: Abhängigkeitsrichtung und Schnittstellen.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Interfaces für Eingänge (Driving) und Abhängigkeiten (Driven).
- **Technisch:** Ports in `application/ports/driving` und `application/ports/driven`.
- **Fachlich:** Abhängigkeiten wie Auskunftei‑Abfrage oder Persistenz werden als Schnittstellen beschrieben.

## 3) Warum diese Änderungen?
- Ports entkoppeln Domäne/Anwendung von Infrastruktur.
- Die spätere Implementierung als Adapter wird vorbereitet.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Aggregate bleiben unverändert; es wird eine Anwendungsschicht eingeführt.
- Ports kapseln die Kommunikation nach außen und innen, ohne Logik zu verändern.

## 5) DDD‑Tactical‑Patterns im Detail
- **Driving Ports:** z. B. `PreScoringStart`, `VerarbeitungImmobilienBewertung` als Eingänge.
- **Driven Ports:** z. B. Repositories (`ScoringErgebnisRepository`, `AntragstellerClusterRepository`) und externe Services (`KonditionsAbfrage`, `LeseKontoSaldo`).

## 6) Code‑Navigationshilfe
- Driving Ports:
  - `src/main/java/com/bigpugloans/scoring/application/ports/driving/PreScoringStart.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driving/VerarbeitungImmobilienBewertung.java`
- Driven Ports:
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/ScoringErgebnisRepository.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/AntragstellerClusterRepository.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/AuskunfteiErgebnisClusterRepository.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/ImmobilienFinanzierungClusterRepository.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/MonatlicheFinanzsituationClusterRepository.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/KonditionsAbfrage.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/LeseKontoSaldo.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/ScoringErgebnisVeroeffentlichen.java`
- Application Modelle:
  - `src/main/java/com/bigpugloans/scoring/application/model/Antrag.java`
  - `src/main/java/com/bigpugloans/scoring/application/model/AuskunfteiErgebnis.java`
  - `src/main/java/com/bigpugloans/scoring/application/model/ImmobilienBewertung.java`

## 7) Übungen / Reflexion
- Welche Ports sind fachlich notwendig, welche technisch motiviert?
- Wo gehört ein neuer Port hin (driving vs. driven)?

## 8) Zusammenfassung
- Ports definieren die Grenzen des Scoring‑Moduls.
- Die Implementierung als Adapter folgt in späteren Schritten.

---

# Branch: `origin/13_ApplicationServices`

> Didaktischer Übergang: Die Anwendungsebene orchestriert den Ablauf. Application Services koordinieren Ports, Infrastruktur und Domäne.

## 1) Ziel & Kontext
- Einführung von Application Services als Use‑Case‑Orchestrierung.
- Fokus: Prozesssteuerung (Pre‑Scoring vs. Main‑Scoring) und Integration externer Services.
> Fokus: Orchestrierung statt Fachlogik.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Application Services implementieren Driving Ports und rufen Driven Ports/Domain‑Logik.
- **Technisch:** `EingereicherAntragVerarbeitenApplicationService`, `FreigegebenerAntragVerarbeitenApplicationService`, `VerarbeitungImmobilienBewertungApplicationService`.
- **Fachlich:** Pre‑Scoring und Main‑Scoring unterscheiden sich durch Auskunftei‑Abfrageart (Konditions‑ vs. Kreditabfrage).

## 3) Warum diese Änderungen?
- Application Services koordinieren den Ablauf über mehrere Systeme/Ports hinweg.
- Sie halten Infrastruktur‑Details von der Domäne fern und definieren Transaktionsgrenzen.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Ports sind vorhanden; nun werden sie durch Application Services „verdrahtet“.
- Domain‑Logik bleibt unangetastet, Orchestrierung kommt hinzu.

## 5) Aufgaben & Abgrenzung (Application vs. Domain Service)
- **Application Service:** Orchestriert Use Cases, ruft Ports, entscheidet Ablauf (z. B. Pre/Main‑Scoring).
- **Domain Service:** Enthält fachliche Logik über mehrere Entities/Value Objects (ohne Infrastruktur).
- **In diesem Schritt:** Application Services delegieren fachliche Operationen an Domain‑Services‑Ports.

## 6) Code‑Navigationshilfe
- Application Services:
  - `src/main/java/com/bigpugloans/scoring/application/service/EingereicherAntragVerarbeitenApplicationService.java`
  - `src/main/java/com/bigpugloans/scoring/application/service/FreigegebenerAntragVerarbeitenApplicationService.java`
  - `src/main/java/com/bigpugloans/scoring/application/service/VerarbeitungImmobilienBewertungApplicationService.java`
- Driving Ports:
  - `src/main/java/com/bigpugloans/scoring/application/ports/driving/EingereicherAntragVerarbeiten.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driving/FreigegebenerAntragVerarbeiten.java`
- Unterstützende Modelle:
  - `src/main/java/com/bigpugloans/scoring/application/model/Antrag.java`
  - `src/main/java/com/bigpugloans/scoring/application/model/AuskunfteiErgebnis.java`

## 7) Alternative Interpretationen
- Manche Teams verwenden „Application Services“ nur als Thin Layer und legen fast alle Regeln in Aggregate.
- Andere schneiden Use‑Cases als „Command Handlers“ und verzichten auf explizite Services.
- In CQRS‑Varianten kann die Orchestrierung stärker in der Infrastruktur liegen, während Domänenlogik in Aggregaten verbleibt.

## 8) Zusammenfassung
- Application Services koordinieren den Prozess und integrieren Ports.
- Die fachliche Logik bleibt in der Domäne (oder wird in Domain Services ausgelagert).

---

# Branch: `origin/14_DomainServices`

> Didaktischer Übergang: Fachliche Logik wird aus der Orchestrierung herausgezogen. Domain Services bündeln Regeln über mehrere Cluster hinweg.

## 1) Ziel & Kontext
- Einführung von Domain Services für fachliche Operationen über mehrere Aggregate/Cluster.
- Fokus: Wiederverwendbare Fachlogik, unabhängig von Infrastruktur.
> Fokus: Fachlogik zentralisieren.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Domain Services entstehen, Domain‑Model wird in `domain/model` konsolidiert.
- **Technisch:** `AntragHinzufuegenDomainService`, `AuskunfteiHinzufuegenDomainService`, `ScoringDomainService` u. a.
- **Fachlich:** Application Services delegieren fachliche Entscheidungen an Domain Services.

## 3) Warum diese Änderungen?
- Fachliche Logik über mehrere Aggregate/Cluster gehört nicht in Application Services.
- Domain Services reduzieren Duplikation und machen Regeln zentral testbar.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Application Services bleiben, enthalten aber weniger fachliche Logik.
- Domain Services übernehmen die konkrete Verarbeitung und arbeiten mit Repositories.

## 5) Aufgaben & Abgrenzung (Domain vs. Application Service)
- **Domain Service:** Enthält Regeln, Invarianten und fachliche Entscheidungen über mehrere Entitäten.
- **Application Service:** Steuert den Ablauf und kümmert sich um Ports/Transaktionen.
- **Warum diese Aufteilung:** Orchestrierung bleibt schlank, Fachlogik bleibt zentral.

## 6) Code‑Navigationshilfe
- Domain Services:
  - `src/main/java/com/bigpugloans/scoring/domain/service/AntragHinzufuegenDomainService.java`
  - `src/main/java/com/bigpugloans/scoring/domain/service/AuskunfteiHinzufuegenDomainService.java`
  - `src/main/java/com/bigpugloans/scoring/domain/service/ScoringDomainService.java`
- Domain‑Model (neu strukturiert):
  - `src/main/java/com/bigpugloans/scoring/domain/model/scoringErgebnis/ScoringErgebnis.java`
  - `src/main/java/com/bigpugloans/scoring/domain/model/ClusterScoring.java`

## 7) Alternative Interpretationen
- Manche Ansätze vermeiden Domain Services und legen Regeln direkt in Aggregate (rich domain model).
- Andere nutzen Domain Services als reine „Policy Objects“ ohne Repositories (funktionaler Stil).
- In anämischen Modellen verbleibt die Logik oft in Application Services — das wird hier bewusst vermieden.

## 8) Zusammenfassung
- Domain Services bündeln fachliche Logik, Application Services orchestrieren.
- Die Trennung erhöht Klarheit und Wiederverwendbarkeit im Scoring‑Prozess.

---

# Branch: `origin/15-0_DrivenAdapters_UnitTests`

> Didaktischer Übergang: Bevor konkrete Infrastruktur gewählt wird, definieren wir die Adapter‑Verträge mit Tests.

## 1) Ziel & Kontext
- Einführung von Adapter‑Unit‑Tests für die Driven Ports (Repository‑Verträge).
- Fokus: Infrastruktur bleibt offen, aber Verhalten der Adapter wird festgelegt.
> Fokus: Infrastrukturverträge definieren.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Adapter‑Tests für Repositories.
- **Technisch:** Tests in `src/test/java/com/bigpugloans/scoring/adapter/driven/*`.
- **Fachlich:** Persistenz wird als austauschbarer Mechanismus betrachtet.

## 3) Warum diese Änderungen?
- Adapter‑Tests definieren die Erwartungen an Persistenz, unabhängig von Technologie.
- Erlaubt spätere Varianten (Memento, Mongo, JPA) ohne die Domäne zu ändern.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Ports und Services bleiben; es kommen nur Adapter‑Tests hinzu.
- In‑Memory‑Repos dienen als Test‑Double.

## 5) DDD‑Tactical‑Patterns im Detail
- **Ports & Adapters:** Ports definieren, Adapter erfüllen Verträge.
- **Test‑First Infrastruktur:** Verhalten wird getestet, bevor Technologie feststeht.

## 6) Code‑Navigationshilfe
- Adapter‑Tests:
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/antragstellerCluster/AntragstellerClusterRepositoryTest.java`
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/auskunfteiErgebnisCluster/AuskunfteiErgebnisClusterRepositoryTest.java`
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/immobilienFinanzierungsCluster/ImmobilienFinanzierungsClusterRepositoryTest.java`
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationClusterRepositoryTest.java`
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/scoringErgebnis/ScoringErgebnisRepositoryTest.java`

## 7) Übungen / Reflexion
- Welche Adapter‑Verträge sind kritisch für die Domäne?
- Welche Anforderungen würden sich bei Technologie‑Wechsel ändern?

## 8) Zusammenfassung
- Adapter‑Verträge sind testgetrieben fixiert.
- Die konkrete Persistenz ist bewusst noch offen.

---

# Branch: `origin/15-1_DrivenAdapters_Memento-Pattern`

> Didaktischer Übergang: Erste Adapter‑Implementierung mit relationaler Persistenz und Memento‑Pattern.

## 1) Ziel & Kontext
- Umsetzung der Repository‑Adapter mit Spring Data JDBC + Memento.
- Fokus: Persistenz getrennt halten, aber Snapshot‑Mechanismus nutzen.
> Fokus: Explizites Mapping ohne JPA.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** JDBC‑Adapter und Record‑Klassen werden eingeführt.
- **Technisch:** `*JDBCRepository`, `*Record`, `schema.sql`, `data.sql`.
- **Memento:** Domain‑Objekte liefern ein Memento zur Persistenz (`...Memento`).

## 3) Warum diese Änderungen?
- Memento erlaubt Persistenz ohne JPA‑Annotationen im Domain‑Model.
- Relationale DB bleibt möglich, Mapping bleibt explizit.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests werden mit JDBC‑Implementierungen erfüllt.
- Domain‑Objekte erhalten Memento‑Funktionen (Persistenzwissen).

## 5) Bewertung & DDD‑Nähe
- **Stärken:** Domain bleibt weitgehend frei von ORM‑Anmerkungen; Mapping ist explizit.
- **Kompromiss:** Memento‑Klassen im Domain‑Model leaken Persistenz‑Interessen in die Domäne.
- **Risiko:** Gefahr der „Daten‑Aufblähung“ im Domain‑Model (UI‑Felder, technische Daten ohne Regelbezug).
- **Wann sinnvoll:** Wenn relationale DB gefordert ist und JPA bewusst vermieden werden soll, aber mit klarer Disziplin: Domain nur um regelrelevante Daten erweitern.

## 6) Code‑Navigationshilfe
- JDBC‑Adapter:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*JDBCRepository.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*Record.java`
- Konkrete Beispiele:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/antragstellerCluster/AntragstellerClusterJDBCRespository.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/antragstellerCluster/AntragstellerClusterRecord.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/scoringErgebnis/ScoringErgebnisJDBCRepository.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/scoringErgebnis/ScoringErgebnisRecord.java`
- Memento‑Logik:
  - `src/main/java/com/bigpugloans/scoring/domain/model/antragstellerCluster/AntragstellerCluster.java` (Memento)

## 7) Übungen / Reflexion
- Ist ein Memento ein akzeptabler „technical leak“ in der Domäne?
- Welche Aggregate profitieren von expliziten Snapshots?

## 8) Zusammenfassung
- JDBC‑Adapter funktionieren, Persistenz ist relational.
- Memento‑Pattern ist ein bewusster Kompromiss zwischen Reinheit und Praktikabilität.

---

# Branch: `origin/15-2_DrivenAdapters_SpringData-MongoDB`

> Didaktischer Übergang: Alternative Persistenz mit MongoDB und separatem Dokument‑Modell.

## 1) Ziel & Kontext
- Umsetzung der Adapter mit Spring Data MongoDB.
- Fokus: Persistenzmodelle getrennt vom Domain‑Model halten.
> Fokus: Dokument‑Mapping für Aggregate.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** JDBC‑Adapter werden durch Mongo‑Adapter ersetzt.
- **Technisch:** `*Document` + `*MongoDbRepository` + Spring Data Repositories.
- **Domain‑Model:** bleibt ohne Persistenz‑Annotationen.

## 3) Warum diese Änderungen?
- MongoDB erlaubt flexible Dokument‑Strukturen für Aggregate.
- Dokument‑Klassen kapseln Persistenzdetails im Adapter.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests werden mit Mongo‑Implementierungen erfüllt.
- Domain‑Model bleibt technologisch neutral.

## 5) Bewertung & DDD‑Nähe
- **Stärken:** Domain bleibt frei von Persistenz‑Annotations; Adapter kapseln Infrastruktur.
- **Kompromiss:** Dokumente speichern Domain‑Objekte (Kopplung Domain ↔ Dokument‑Schema).
- **Einwand (berechtigt):** Schema‑Migrationen werden schwieriger/unklar, wenn das Domain‑Model direkt in Dokumenten persistiert wird.
- **Wann sinnvoll:** Wenn Dokument‑DB genutzt wird und Aggregate als Dokumente gespeichert werden sollen.

## 6) Code‑Navigationshilfe
- Mongo‑Adapter:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*Document.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*MongoDbRepository.java`
- Konkrete Beispiele:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/antragstellerCluster/AntragstellerClusterDocument.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/antragstellerCluster/AntragstellerClusterMongoDbRepository.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/scoringErgebnis/ScoringErgebnisDocument.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/scoringErgebnis/ScoringErgebnisMongoDbRepository.java`

## 7) Übungen / Reflexion
- Welche Teile der Aggregate sollten dokumentbasiert gespeichert werden?
- Wo droht enge Kopplung zwischen Document und Domain?

## 8) Zusammenfassung
- Mongo‑Variante ist DDD‑nah, da Domain sauber bleibt.
- Dokument‑Mapping ist der Hauptkompromiss dieser Variante.

---

# Branch: `origin/15-3_DrivenAdapters_JPA-Annotated`

> Didaktischer Übergang: Alternative Persistenz mit JPA‑Annotationen direkt im Domänenmodell.

## 1) Ziel & Kontext
- Umsetzung der Adapter mit Spring Data JPA.
- Fokus: Schnell integrierbare Persistenz, aber stärkere Kopplung.
> Fokus: Pragmatismus vs. Domänenreinheit.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Mongo‑Dokumente werden durch JPA‑Repositories ersetzt.
- **Technisch:** Domain‑Model wird mit `@Entity`, `@Embedded`, `@Id` annotiert.
- **Domänenänderung:** No‑Arg‑Konstruktoren und technische IDs entstehen.

## 3) Warum diese Änderungen?
- JPA ist verbreitet und produktiv für relationale Datenbanken.
- Infrastruktur wird schneller integrierbar, Tests werden vereinheitlicht.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests laufen nun gegen JPA‑Repos.
- Domain‑Klassen tragen Persistenz‑Details.

## 5) Bewertung & DDD‑Nähe
- **Stärken:** Schnelle Umsetzung mit Standard‑Stack, gute Tooling‑Unterstützung.
- **Kompromiss:** Domain‑Model ist nicht mehr persistence‑ignorant; technische Anforderungen prägen das Modell.
- **Wann sinnvoll:** Wenn Team/Organisation stark auf JPA setzt und Time‑to‑Market wichtiger ist als maximale Domänenreinheit.

## 6) Code‑Navigationshilfe
- JPA‑Adapter:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*JpaRepository.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/*/*SpringDataRepository.java`
- JPA‑Annotations im Domain‑Model:
  - `src/main/java/com/bigpugloans/scoring/domain/model/antragstellerCluster/AntragstellerCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domain/model/auskunfteiErgebnisCluster/AuskunfteiErgebnisCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domain/model/immobilienFinanzierungsCluster/ImmobilienFinanzierungsCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domain/model/monatlicheFinanzsituationCluster/MonatlicheFinanzsituationCluster.java`
  - `src/main/java/com/bigpugloans/scoring/domain/model/scoringErgebnis/ScoringErgebnis.java`

## 7) Übungen / Reflexion
- Welche Persistenz‑Details dürfen im Domain‑Model sichtbar sein?
- Wie beeinflussen JPA‑Constraints die Modellierung von Aggregaten?

## 8) Zusammenfassung
- JPA‑Variante ist pragmatisch, aber am weitesten von DDD‑Reinheit entfernt.
- Technische Kopplung wird zugunsten von Produktivität akzeptiert.

---

# Vergleich der Adapter‑Varianten (Kurzfazit)
- **DDD‑Nähe (hoch → niedrig):** MongoDB‑Variante (Domain bleibt frei) → Memento‑Pattern (leichtes Leak) → JPA‑annotiert (starke Kopplung).
- **Kompromisse:** Memento bringt Persistenzwissen in die Domäne; Mongo bindet Dokument‑Struktur an Domain‑Form (Migrationsfrage); JPA prägt das Domain‑Model technisch.
- **Gerechtfertigt wenn:** Relationale DB ohne JPA (Memento), dokumentzentrierte Speicherung (Mongo), Standard‑Enterprise‑Stack und Tooling‑Vorteile (JPA).

## Gegenüberstellung der Varianten

| Variante | Persistenzmodell | Kopplung zur Domäne | DDD‑Nähe | Stärken | Schwächen | Geeignet wenn |
| --- | --- | --- | --- | --- | --- | --- |
| Memento + JDBC | Record + SQL Schema | Mittel | Mittel | Explizites Mapping, kein JPA | Memento‑Leak, Risiko „Daten‑Aufblähung“ | Relationale DB ohne JPA, strenge Domänen‑Disziplin |
| Spring Data MongoDB | Document‑Model | Niedrig–Mittel | Hoch | Domäne bleibt clean, flexible Struktur | Dokument‑Schema an Domäne gekoppelt, Migrationen unklar | Dokumenten‑DB, Aggregate als Dokument |
| JPA‑annotiert | JPA Entities | Hoch | Niedrig | Schneller Stack, Tooling | Domäne technisch geprägt | JPA‑Standard, Time‑to‑Market |

## Domain‑Model‑Disziplin (Checkliste)
> Ziel: Nur fachlich relevante Daten im Domänenmodell halten.

- **Regelbezug:** Unterstützt das Feld eine fachliche Regel oder Invariante?
- **Begriff:** Ist es ein eigenständiger fachlicher Begriff (Value Object) statt UI‑Feld?
- **Lebenszyklus:** Braucht der Begriff Identität/Änderung über Zeit (Entity)?
- **Abgrenzung:** Gehört es in die Domäne oder in einen Adapter/View‑Model?
- **Schlankheit:** Entfernt dieses Feld Modell‑Komplexität oder erhöht es nur die Datenlast?

# Branch: `origin/16_DrivenAdapters_Backends`

> Didaktischer Übergang: Neben Persistenz‑Adaptern werden nun Backend‑Adapter für externe Systeme eingeführt.

## 1) Ziel & Kontext
- Einführung konkreter Adapter für Auskunftei‑ und Kontosaldo‑Abfragen.
- Fokus: Externe Systeme als Driven Adapter, weiterhin hinter Ports gekapselt.
> Fokus: Externe Systeme kapseln.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Backend‑Adapter implementieren Ports für Konditionsabfrage und Kontosaldo.
- **Technisch:** `KonditionsAbfrageAdapter`, `LeseKontoSaldoAdapter` sowie Tests.
- **Fachlich:** Externe Datenquellen werden in die Anwendung integriert.

## 3) Warum diese Änderungen?
- Fachliche Prozesse benötigen externe Daten (Auskunftei, Kernbank).
- Adapter halten die Domäne frei von Infrastruktur‑Details.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Persistenz‑Adapter bleiben; es kommen Backend‑Adapter für externe Services hinzu.
- Ports (`KonditionsAbfrage`, `LeseKontoSaldo`) werden nun konkret umgesetzt.

## 5) DDD‑Tactical‑Patterns im Detail
- **Driven Adapter:** Implementieren Infrastrukturzugriff, liefern Domänen‑nahe Daten zurück.
- **Abgrenzung:** Domain/Services kennen nur Ports, nicht die Adapter.

## 6) Code‑Navigationshilfe
- Backend‑Adapter:
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/backends/KonditionsAbfrageAdapter.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/backends/LeseKontoSaldoAdapter.java`
- Ports:
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/KonditionsAbfrage.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/KreditAbfrageService.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/KonditionsAbfrageService.java`
  - `src/main/java/com/bigpugloans/scoring/application/ports/driven/LeseKontoSaldo.java`
- Tests:
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/backends/KonditionsAbfrageAdapterTest.java`
  - `src/test/java/com/bigpugloans/scoring/adapter/driven/backends/LeseKontoSaldoAdapterTest.java`

## 7) Übungen / Reflexion
- Wie unterscheiden sich Infrastruktur‑Ports (Auskunftei) von Persistenz‑Ports?
- Welche Fehler‑/Timeout‑Strategien sollten diese Adapter besitzen?

## 8) Zusammenfassung
- Backend‑Adapter sind eingeführt und kapseln externe Systeme.
- Die Port‑Struktur bewahrt die Domäne vor Infrastruktur‑Abhängigkeiten.

---

# Branch: `origin/17-1_MessagingAdapters_SpringApplicationEvents`

> Didaktischer Übergang: Ereignisse werden als Integrationsmechanismus eingeführt. Driving/Driven Adapter reagieren und veröffentlichen Events.

## 1) Ziel & Kontext
- Einführung von Messaging‑Adaptern auf Basis von Spring Application Events.
- Fokus: Integration über Events, ohne direkte Kopplung zwischen Systemen.
> Fokus: Lose Kopplung via Events.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Event‑Typen und Listener, Publishing von Scoring‑Ergebnissen.
- **Technisch:** Listener in `adapter.driving`, Publisher in `adapter.driven.messaging`.
- **Fachlich:** Eingehende Events („Antrag eingereicht“, „Immobilie bewertet“) stoßen Use‑Cases an.

## 3) Warum diese Änderungen?
- Ereignisse ermöglichen lose Kopplung und asynchrone Integration.
- Die Domäne bleibt von Messaging‑Details entkoppelt.

## 4) Wie baut der Branch auf dem vorherigen auf?
- Ports/Services bleiben; Events binden sie an die Außenwelt an.
- Scoring‑Ergebnisse werden als Events veröffentlicht.

## 5) DDD‑Tactical‑Patterns im Detail
- **Domain Events / Integration Events:** Publishing basiert auf Scoring‑Ergebnis.
- **Adapters:** Listener (Driving) und Publisher (Driven) kapseln Infrastruktur.

## 6) Code‑Navigationshilfe
- Events:
  - `src/main/java/com/bigpugloans/events/AntragEingereicht.java`
  - `src/main/java/com/bigpugloans/events/ImmobilieBewertet.java`
  - `src/main/java/com/bigpugloans/events/PreScoringGruen.java`
  - `src/main/java/com/bigpugloans/events/PreScoringRot.java`
  - `src/main/java/com/bigpugloans/events/MainScoringGruen.java`
  - `src/main/java/com/bigpugloans/events/MainScoringRot.java`
- Driving Adapter (Listener):
  - `src/main/java/com/bigpugloans/scoring/adapter/driving/AntragEingereichtMessageListener.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driving/ImmobilieBewertetMessageListener.java`
  - `src/main/java/com/bigpugloans/scoring/adapter/driving/AntragFreigegebenMessageListener.java`
- Driven Adapter (Publisher):
  - `src/main/java/com/bigpugloans/scoring/adapter/driven/messaging/ScoringErgebnisVeroeffentlichenAdapter.java`

## 7) Übungen / Reflexion
- Sind diese Events Domain‑ oder Integration‑Events?
- Welche Informationen dürfen in Events enthalten sein, ohne die Domäne zu leaken?

## 8) Zusammenfassung
- Messaging‑Adapter führen Ereignisfluss ein und koppeln Systeme lose.
- Use‑Cases werden über Events ausgelöst und Ergebnisse veröffentlicht.

---

# Branch: `origin/18_Architecture_Tests`

> Didaktischer Übergang: Architektur wird nicht nur beschrieben, sondern aktiv überprüft. Tests schützen Onion/Hexagonal‑Prinzipien.

## 1) Ziel & Kontext
- Einführung von Architekturtests mit ArchUnit und jMolecules.
- Fokus: Onion und Hexagonal Architecture sowie DDD‑Regeln automatisiert sichern.
> Fokus: Architektur als überprüfbarer Vertrag.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** ArchUnit‑Tests prüfen Paket‑, Schicht‑ und Abhängigkeitsregeln.
- **Technisch:** Neue Tests in `src/test/java/com/bigpugloans/architecture`.
- **Fachlich:** Architekturinvarianten werden als Code überprüfbar.

## 3) Warum Architekturtests (besonders bei Onion/Hexagonal)?
- Diese Architekturen leben von **Richtung der Abhängigkeiten**; Verstöße sind schwer sichtbar.
- Ohne Tests schleichen sich Framework‑Abhängigkeiten in die Domäne ein.
- Architekturtests wirken als **Regression‑Schutz** gegen schleichende Kopplung.

## 4) Wie wurden sie hier umgesetzt?
- **Hexagonal‑Regeln:** `HexagonalArchitectureTests` prüfen Schichten und Dependency‑Direction.
- **Onion‑Regeln:** `ArchUnitTests` erzwingen Domänen‑Isolation.
- **DDD‑Regeln:** `DddTacticalPatternsTests` + `JMoleculesArchUnitTests`.
- **Paket‑/Naming‑Regeln:** `PackageStructureTests` verhindern Zyklen und falsche Platzierungen.

## 5) Warum diese Regeln?
- **Domäne unabhängig:** Keine Abhängigkeiten zu Adaptern oder Frameworks.
- **Ports zentral:** Adapter hängen von Ports ab, nicht umgekehrt.
- **Clusters isoliert:** Fachliche Cluster sollen nicht quer abhängen.

## 6) Code‑Navigationshilfe
- Architekturtests:
  - `src/test/java/com/bigpugloans/architecture/HexagonalArchitectureTests.java`
  - `src/test/java/com/bigpugloans/architecture/ArchUnitTests.java`
  - `src/test/java/com/bigpugloans/architecture/DddTacticalPatternsTests.java`
  - `src/test/java/com/bigpugloans/architecture/PackageStructureTests.java`
  - `src/test/java/com/bigpugloans/architecture/JMoleculesArchUnitTests.java`
- Bounded Context Annotation:
  - `src/main/java/com/bigpugloans/scoring/package-info.java`

## 7) Übungen / Reflexion
- Welche Architekturregel schützt euch am stärksten vor Kopplung?
- Welche Regel würdet ihr lockern oder verschärfen – und warum?

## 8) Zusammenfassung
- Architekturtests sind essenziell, weil Onion/Hexagonal nur durch konsequente Abhängigkeitsrichtung funktionieren.
- Die Tests machen Architektur überprüfbar und stabil.

---

# Branch: `origin/19_Antragserfassung_DomainModeling_EventSourced`

> Bonus‑Ausblick: Event Sourcing als Alternative für antragsbezogene Modellierung – mit Commands, Events und Projections.

## 1) Ziel & Kontext
- Demonstration eines event‑sourced Subsystems für die Antragserfassung.
- Fokus: Event Sourcing + CQRS (Command‑/Query‑Separation) als mögliche Evolutionsrichtung.
> Fokus: Event Sourcing als Ausblick.

## 2) Änderungen zum vorherigen Branch
- **Kurzfassung:** Neuer Bounded Context `antragserfassung` mit Axon‑Aggregate, Commands, Events und Projections.
- **Technisch:** Axon‑Konfiguration, Projections, Web‑UI für Antragserfassung.
- **Fachlich:** Antragserfassung wird als sequenzieller Prozess modelliert (Schritte, Status, Abschluss).

## 3) Warum diese Änderungen?
- Event Sourcing eignet sich für Prozesse mit Audit‑Bedarf und klaren Status‑Übergängen.
- Änderungen werden als Ereignisse nachvollziehbar (Nachvollziehbarkeit, Historie).

## 4) Wie baut der Branch auf dem vorherigen auf?
- Das Scoring bleibt bestehen; zusätzlich wird eine event‑sourced Antragserfassung gezeigt.
- Dient als Ausblick, nicht als Migration des bestehenden Scoring‑Modells.

## 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root:** `Antragserfassung` als Event‑sourced Aggregate.
- **Commands/Events:** `StarteAntragCommand`, `AntragGestartetEvent`, `AntragserfassungAbgeschlossenEvent`, etc.
- **Projections:** Read‑Model wird aus Events aufgebaut (Query‑Seite).

## 6) Code‑Navigationshilfe
- Aggregate & Events:
  - `src/main/java/com/bigpugloans/antragserfassung/domain/model/Antragserfassung.java`
  - `src/main/java/com/bigpugloans/antragserfassung/domain/model/StarteAntragCommand.java`
  - `src/main/java/com/bigpugloans/antragserfassung/domain/model/AntragGestartetEvent.java`
  - `src/main/java/com/bigpugloans/antragserfassung/domain/model/AntragserfassungAbgeschlossenEvent.java`
- Projections/Queries:
  - `src/main/java/com/bigpugloans/antragserfassung/query/AntragsdetailProjection.java`
  - `src/main/java/com/bigpugloans/antragserfassung/query/AntragserfassungUebersichtProjection.java`
- Konfiguration & UI:
  - `src/main/java/com/bigpugloans/antragserfassung/config/AxonConfiguration.java`
  - `src/main/java/com/bigpugloans/antragserfassung/web/AntragserfassungController.java`
  - `src/main/resources/templates/antragserfassung/neu.html`

## 7) Übungen / Reflexion
- Für welche Teile des Scorings wäre Event Sourcing sinnvoll – und wo nicht?
- Welche zusätzlichen Anforderungen (Event‑Versioning, Projections‑Rebuilds) entstehen?

## 8) Zusammenfassung
- Event Sourcing wird als Bonus‑Ausblick gezeigt, nicht als Pflichtpfad.
- Der Branch demonstriert Commands, Events und Projections als Alternative Modellierungsoption.

## Wann Event Sourcing sinnvoll ist (und wann nicht)
- **Sinnvoll bei:** Audit‑/Nachvollziehbarkeit, komplexen Status‑Übergängen, langfristiger Historie, hoher Änderungsrate der Fachregeln.
- **Weniger sinnvoll bei:** sehr einfachen CRUD‑Domänen, geringen Traceability‑Anforderungen, Teams ohne ES‑Erfahrung.
- **Kompromisse:** Event‑Versionierung, Projection‑Rebuilds, erhöhte Komplexität in Betrieb und Tests.


# Glossar (optional)

| Begriff | Bedeutung |
| --- | --- |
| KO‑Kriterium | Regel, die im jeweiligen Cluster zu „ROT“ führt |
| Value Object | Fachlicher Begriff ohne Identität, Gleichheit über Wert |
| Entity | Fachlicher Begriff mit Identität und Lebenszyklus |
| Aggregate | Konsistenzgrenze, schützt Invarianten |
| Port | Schnittstelle der Anwendung (Driving/Driven) |
| Adapter | Technische Implementierung eines Ports |
| Application Service | Orchestriert Use‑Cases, keine Fachlogik |
| Domain Service | Fachlogik über mehrere Aggregate/Entities |
| Event Sourcing | Zustand aus Events rekonstruiert |

---

# Lernpfad‑Checkliste (optional)
- [ ] Branch 0 verstanden
- [ ] Branch 1 verstanden
