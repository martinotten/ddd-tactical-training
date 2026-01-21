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
- Java 21, aktuelle Maven‑Version, eine Docker-Runtime (für Testcontainer).
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


# Einleitung ins taktische Design

Wir gehen davon aus, dass Domain-driven Design und im speziellen das Strategische Desgin bekannt sind. Wir haben zu diesem Punkt im Prozess bereits Sub-Domains erkannt und Bounded Contexts definiert. Nun beschäftigen wir uns mit der Umsetzung von Fachlichkeit innerhalb einer Bounded Contexts. Hier können uns die Pattern aus dem taktischen Design helfen.

## Wann wende ich taktischen Design an?

Mit der strategischen Design haben wie bereits einen wichtigen Schritt gemacht. Die Bounded Contexts die wir definiert haben sollten bereits jeweils einen klaren Zweck und klare Grenzen. Wir haben also bereits einen klaren Rahmen in dem wir uns in einem einzelnen Bounded Context bewegen.

Von hier an können wir entscheiden wie wir die Fachlichkeit innerhalb eines Bounded Contexts umsetzen wollen. Wichtig ist, dass wir weiterhin ein gemeinsames Verständnis und eine gemeinsame Sprache mit dem Fachbereich haben und hier eng zusammen arbeiten. Wir wollen gemeinsames Verständnis von Problemen und gemeinsam Lösungen entwickeln und dies auch im Code abbilden. Dies können wir auch ohne die Pattern des taktischen Designes erreichen.

Die Pattern aus dem taktischen Design können helfen komplexität zu reduzieren und beherschbar zu machen. Wir setzen diese Pattern daher vor allem da ein wo wir komplexe Fachlichkeit umsetzen müssen und schnell sein wollen. Also wahrscheinlich eher bei Bounded Contexts aus unseren Core Domains oder komplexen Supporting Domains.

Bei einfacheren Bounded Contexts werden wir eher auf Architekturen und Frameworks zurückgreifen, die uns für den Anwendungsfall die meiste Arbeit abnehmen.

## Das Domain Model ist ein fachliches Regelmodell

Kern einer solchen Anwendung ist das Domain Model, der Logik-Kern unserer Anwendung. Dessen Aufgabe ist es die fachlichen Funktionen umzusetzen und sich auf die Regeln zu konzentrieren. Daher gilt: wir halten das Domain Model so einfach wie möglich und machen es nur so komplex wie nötig. Die Motivation ist schließlich, dass wir hier trotz der Komplexität möglichst wartbaren, anpassbaren, testbaren Code haben wollen, den wir vor allem schnell ändern können. Gerade für die Core Domains ist es wichtig, dass wir die Fachlichkeit schnell und einfach umsetzen können, da wir hier unseren Wettbewerbsvorteil sicherstellen wollen und uns schnell an neue Gegenheiten anpassen müssen.

Das Domain Modell ist daher in erster Linie ein "Regel Modell". Es ist kein UI-Modell oder Persistenzmodell. Jedes Attribut, dass nicht von einer fachlichen Regel abhängt, wollen wir nicht im Domain Model wiederfinden. Also keine Felder die in der UI nur angezeigt werden oder für einen anderen Anwendungsfall in der gleichen Datenbanktabelle gespeichert werden. Hier gibt es oft Missverständnisse, da wir im taktischen Design Entity als Pattern verwenden. Dies ist ein klassischer Fall von Ubiquitous Language und einem anderen Kontext. Unsere DDD-Entity ist eine Regel-Entity und hat erstmal nichts mit der Datenbank-Entity Persistenz-Entity zu tun.

In diesem Beispiel wollen wir ein Domain Modell erstellen, dass uns aufgrund von passenden Eingaben beantwortet, ob ein Scoring Rot (negativ) oder Grün(positiv) ist.

Von allem technischen muss dieses Modell nichts wissen. Keiner Infrastruktur, kein Framework, keine UI,nichts. Somit kann es sich auf seine Aufgabe konzentrieren und ist so auch gut testbar. Hier haben wir nur Unit Tests.

## Die Architektur um eine Domain Modell

Grundsätzlich lässt sich das Domain Modell als Modul unabhängig von einer bestimmten Architektur umsetzen. Es bietet sich aber an dies mit den Konzepten aus der Hexagonalen Architektur und der Onion Architektur zu kombinieren. Beide helfen uns dabei verschiedene Apspekte der Anwendung und externen Abhängigkeiten zu entkoppeln.

### Die Onion Architektur

Die Onion Architektur teilt die Anwendung in mehrere Schichten auf. (wie eine Zwiebel ) Jede Schicht hat ihre eigene Rolle und ist von den anderen Schichten isoliert. Ziel ist es die inneren Schichten von den äußeren Schichten zu trennen. 

Eine typsiche Aufteilung von Innen nach Außen:

1. Domain Model
	- Entities, Value Objects, Domain Services (wenn wirklich fachlich)
	- Domain Events (fachlich, nicht technisch)
	- Invarianten und Regeln (Policy)
	- Domain Services (optional als Koordination von fachlichen Funktionen zwischen Aggregaten)
2.	Application Services
	- Koordinationsschicht, die als Verbindung zwischen Domain und Infrastruktur fungiert
	- Definiert Ports/Interfaces, die sie braucht (z. B. CustomerRepository, PaymentGateway)
  - Policies: Wenn -> dann Regeln.
3.	Infrastructure
	- Implementiert Interfaces aus den inneren Schichten (z. B. JpaCustomerRepository implements CustomerRepository)
  - Liefert die konkreten Schnittstellen zur Außenwelt.
  - Datenbanken, APIs, User Interfaces

Die inneren Schichten dürfen nicht direkt auf die Implementierungen der äußeren Schichten zugreifen. Direkter Zugriff erfolgt nur von außen nach innen. Also Infrastruktur -> Application Services -> Domain Model. Ein Zugriff von innen nach außen ist nur inderekt über Interfaces erlaubt, die die innere Schicht breitstellt. Die Äußere Schicht liefert die konkreten Implementierungen an die inneren Schichten über Dependency Injection. Somit kann ein Application Service ein `KontostandsAbfrageInterface` anbieten, ist aber nicht davon abhängig, ob die Implementierung von einer API oder einer Datenbank kommt. (z.B. `JDBCKontostandsAbfrage` oder `SOAPKontostandsAbfrage`).

Durch diese Isolation wird nicht nur das Testen der inneren Schichten erleichtert. Die Application Services sind ähnlich gut testbar, wie das Domain Model. Die Infrastruktur kann aber auch isoliert getestet werden indem man ihre Funktion mit den externen Abhängigkeiten testet, aber die eigentliche Anwendungslogik nicht mitgetestet wird. Durch die Nutzung von Interfaces kann leicht gemockt werden, z.B. durch eine `TestKontostandsAbfrage`.

**Repository in der Onion Architektur:** Ein Repository ist ein fachliches Zugriffskonzept, kein technisches Detail. Es wird als Interface in einer inneren Schicht definiert (meist Application oder Domain), damit das Domain Model Aggregates laden und speichern kann, ohne die Infrastruktur zu kennen. Das Repository bildet also die Grenze zwischen fachlicher Welt und Persistenz: Es liefert Aggregate als Ganzes, kapselt Abfragen in fachlicher Sprache und verhindert, dass Fachlogik SQL/ORM oder API-Details kennen muss. Die konkrete Umsetzung (JDBC/JPA/Mongo/HTTP) liegt in der Infrastructure-Schicht und wird per Dependency Injection an die innere Schicht geliefert. Wichtig: Repositories speichern Aggregate Roots, nicht beliebige Entitaeten oder Value Objects, um Konsistenzgrenzen zu respektieren. Ein reines CRUD-Interface sollte beim Repository daher vermieden werden. Die Methoden stellen fachliche motivierte Funktionen dar um Aggregate zu laden oder zu verändern.

Beispiel:
```java
public interface ScoringErgebnisRepository {
    void scoringErgebnisSpeichern(ScoringErgebnis scoringErgebnis);

    Optional<ScoringErgebnis> scoringErgebnisZu(ScoringId scoringId);

    boolean scoringBereitsAbgeschlossen(ScoringId scoringId);
}
```

# Testen der fachlichen Regeln
Ausgangspunkt: `origin/main`
Lösung: `origin/0_UnitTests_red`

## Aufgabe
- Lese dir die fachlichen Scoring-Regeln durch, diese findest du in der Fallstudie.
- Schreibe Unit-Tests für jede Regel. Nutze dabei die fachlichen Begriffe aus der Formulierung der Regeln.
- Implementiere die Regeln in der Klasse `BusinessRulesTest`.
- Gehe davon aus, dass fachliche Begriffe in den Regeln als eigene Klassen implementiert sind.
- Entscheide zu welchem Begriff die Regel gehört und ordnere ihr die Regel-Funktion zu.
- Beispiel:
  - Regel: "Personen ab dem 80ten Lebensjahr bekommen bei uns keinen Kredit."
  - Code: `Lebensjahr.aus(geburtsdatum).istKoKriteriumErfuellt()`
- Achtung: Manchmal versteckt sich der eigentliche Begriff zwischen den Zeilen.

## Lösungsweg

### Änderungen zum Ausgangspunkt (`main`)
- **Kurzfassung:** Einführung von `BusinessRulesTest` mit fachlichen Regeln.
- **Technisch:** Neue Tests in `src/test/java/.../domainmodel`
- **Fachlich:** Scoring‑Regeln (KO‑Kriterien, Punkte, Schwellen) werden explizit beschrieben.

### Warum diese Änderungen?
- Tests dienen als fachliche Spezifikation und Gesprächsgrundlage für das Domänenmodell.
- Wir halten uns direkt an die Ubiquitous Language und konzentrieren uns auf die Fachlichkeit.

### DDD‑Tactical‑Patterns im Detail
- **Value Object (Kandidaten):** Die fachlichen Begriffe und Regelzuordnungen sind erste Kandidaten für Value Objects. Durch dieses Vorgehen identifizieren wir die kleinst möglichen Kandidaten. Wir können später entscheiden, ob wir sie als Value Objects oder Entities gruppieren.
- **Versteckte Bezugspunkte**: Bei der Regel für die "Antragssteller aus Hamburg oder München" ist der eigentliche fachliche Bezugspunkt die Stadt oder der Wohnort nicht explizit genannt. Wir müssen hier erkennen, dass der Antragssteller hier nicht passen kann, da er zu grob ist und viel mehr Infomrationen zu diesem gehören werden. Wir wollen schließlich kleinere und präzise Kandidaten für Value Objects oder Entities identifizieren.

---

# Minimaler Code für grüne Unit-Tests
Ausgangspunkt: `origin/0_UnitTests_red`
Lösung: `origin/1_UnitTests_green`

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Minimaler Code, um die Tests aus `0_UnitTests_red` zu erfüllen.
- Es soll ein Überblick über die fachlichen Regeln geschaffen werden, um die nachfolgenden Gruppierungen und Bildung von Value Objects oder Entities durchführen zu können.
- Zudem sind die fachlichen Regeln nach diesem Schritt bereits vollständig implementiert.
> Fokus: Minimaler Code, der Regeln abbildet – ohne Strukturentscheidungen vorwegzunehmen.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Einführung einfacher Domainklassen in `com.bigpugloans.scoring.domainmodel`.
- **Technisch:** Klassen wie `Waehrungsbetrag`, `Prozentwert`, `Punkte`, `ScoringErgebnis`, `Finanzierung`, `Haushaltsfinanzen` etc.
- **Fachlich:** KO‑Kriterien, Punktevergabe und Scoring‑Farbe sind erstmals implementiert.
- Tests bleiben unverändert; es wird nur das Nötigste ergänzt.
- Der Code spiegelt exakt die im Test beschriebenen Regeln wider.
- Die ungruppierte Struktur bleibt bestehen, damit der Schritt zur Cluster‑Sicht bewusst im nächsten Branch erfolgt.

### 5) DDD‑Tactical‑Patterns 
  - `Waehrungsbetrag`, `Prozentwert`, `Punkte`, `ScoringErgebnis`, `Finanzierung`, `Haushaltsfinanzen` sind erste Kandidaten für Domänenobjekte.

---

# Fachliche Regel-Cluster erkennen
Ausgangspunkt: `origin/1_UnitTests_green`
Lösung: `origin/2_RuleGroups_red`
Die Regeln wurden bisher bewusst ungruppiert formuliert. Ab diesem Schritt wird die Fachsicht explizit: Regel‑Cluster werden erkannt, benannt und als Struktur eingeführt.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Fachliche Regeln werden in Cluster gruppiert (Antragsteller‑, Auskunftei‑, Immobilien‑ und Finanzsituation‑Cluster).
- DDD‑Fokus: Die Sicht des Fachbereichs wird übernommen — Punkte und KO‑Regeln gehören jeweils zu einem Cluster, nicht als globaler Schnitt.
- Schreibe Tests für die neuen Cluster und ihre Schnittstellen.
- Jeder Cluster muss Punkte und KO-Regeln überprüfen. Dabei versteckt jeder Cluster bereits weitere Implementierungsdetails. (Information Hiding)

> Fokus: Fachliche Gruppierung als Strukturentscheidung.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests wurden in fachliche Cluster aufgeteilt und präzisiert.
- **Technisch:** Neue Testklassen und Packages, z. B. `.../antragstellerCluster`, `.../auskunfteiErgebnisCluster`, `.../immobilienFinanzierungsCluster`, `.../monatlicheFinanzsituationCluster`, `.../scoringErgebnis`.
- **Fachlich:** Regeln sind nicht mehr „flach“ in einer Datei, sondern in thematischen Gruppen.
- **Zusatz:** `junit-jupiter-params` wird für parameterisierte Tests ergänzt.

### 3) Warum diese Änderungen?
- Regelgruppen machen fachliche Zusammenhänge sichtbar und unterstützen spätere Aggregat‑/Bounded‑Context‑Entscheidungen.
- Der rote Zustand zwingt zur Umstrukturierung des Domänenmodells in Cluster.
 - Das Training verdeutlicht die bewusste Zäsur: erst Regeln sammeln, dann fachlich gruppieren.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Die fachlichen Regeln bleiben gleich, aber die Tests verlangen neue Strukturen (Cluster‑Klassen und Packages).
- Das bisherige „monolithische“ Testdesign wird bewusst aufgebrochen.

### 5) DDD‑Tactical‑Patterns im Detail
- **Cluster als Vorstufe zu Aggregaten:** Tests schaffen fachliche Grenzen, ohne bereits echte Aggregat‑Regeln einzuziehen.
- **Regelzuordnung:** Die fachliche Gruppierung in Regelcluster wird übernommen. Eine Gruppierung nach KO‑Kriterien und Punkte-Regeln ist zwar auf den ersten Blick naheliegend, aber nicht sinnvoll. Die Folge wäre zunächst Kommunikationsprobleme mit dem Fachbereich, der anders denkt. Und das aus gutem Grund, denn bis auf die Art der Regel gibt es insgesamt keinen Zusammenhang zwischen Regeln des selben Typs. Die Folge ist, dass eine solche Gruppierung nach Regeltyp (Punkte/KO) nicht skaliert. In diesem Beispiel sind es nur wenige Regeln, aber in einer realen Anwendung wären es deutlich mehr. Dann hätte eine solche Gruppe vielleicht hunderte Regeln. Da ist es einfacher Cluster von weniger Regeln zu bilden die zumindest fachlich zusammen gehören und die Regeln so in mindestens 5 statt 2 Gruppen aufzuteilen.
- **Value Objects:** Weiterhin in den Tests benannt (z. B. `Waehrungsbetrag`, `Prozentwert`, `Punkte`), aber die Struktur liegt jetzt in Cluster‑Tests.

### 7) Übungen / Reflexion
- Welche Cluster könnten später Aggregat‑Wurzeln werden?
- Welche Regeln sind cluster‑intern, welche sind cross‑cluster?

### 8) Zusammenfassung
- Der Branch strukturiert Regeln fachlich und macht Modellgrenzen sichtbar.
- Der rote Zustand bereitet die Umstrukturierung des Domänenmodells vor.

---

# Regel-Cluster implementieren
Ausgangspunkt: `origin/2_RuleGroups_red`
Lösung: `origin/3_RuleGroups_green`

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Minimaler Domain‑Code wird an die neuen Regelgruppen angepasst.
- DDD‑Fokus: Cluster werden in Produktivcode überführt, inklusive ihrer jeweils eigenen Punkte‑ und KO‑Regeln.
> Fokus: Clustern eine Form geben, ohne Übermodellierung.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Einführung von Cluster‑Klassen und Bereinigung der alten „flachen“ Klassen.
- **Technisch:** Neue Klassen wie `AntragstellerCluster`, `AuskunfteiErgebnisCluster`, `ImmobilienFinanzierungsCluster`.
- **Struktur:** `ScoringErgebnis` wandert in ein eigenes Unterpaket, `MonatlicheFinanzsituationCluster` ersetzt `Haushaltsfinanzen`.
- **Aufräumen:** `BusinessRulesTest` wird entfernt; parameterisierte Tests werden wieder vereinfacht.
 - **Regeländerung:** Marktwert‑Punkte werden in Tests/Implementierung von 15 auf 10 reduziert.

### 3) Warum diese Änderungen?
- Die Teststruktur aus `2_RuleGroups_red` wird in eine erste fachliche Modellstruktur überführt.
- Schrittweise Modell‑Evolution: erst Gruppierung, dann minimale Implementierung.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Tests bleiben gruppiert; der Code wird so angepasst, dass die Cluster‑Tests grün werden.
- Alte Klassen/Strukturen werden zugunsten der neuen Cluster‑Grenzen ersetzt.

### 5) DDD‑Tactical‑Patterns im Detail
- **Cluster als Modellbaustein:** Klassen je Regelgruppe bündeln Regeln und Daten.
- **Regelzuordnung:** Punkte und KO‑Regeln bleiben im jeweiligen Cluster verankert (fachliche Sicht).
- **Value Objects:** `Punkte` erhält eine Additions‑Operation (`plus`) zur Aggregation von Teilpunkten.
- **Aggregat‑Gedanke (implizit):** Cluster bündeln Regeln, ohne explizite Aggregate zu definieren.

### 7) Übungen / Reflexion
- Wo fehlen Invarianten/Validierungen in den Cluster‑Klassen?
- Welche Regeln sollten eher in Aggregat‑Methoden oder Domain Services liegen?
- Welche Cluster benötigen Identität und Lebenszyklus (Entity) vs. reine Value Objects?

### 8) Zusammenfassung
- Die fachliche Gruppierung wird in Code übersetzt, Tests sind wieder grün.
- Damit entsteht eine stabilere Basis für spätere taktische Muster (Value Objects, Entities, Aggregate).

---

# Commands für fachliche Interaktion
Ausgangspunkt: `origin/3_RuleGroups_green`
Lösung: `origin/4_CommandsEvents_red`
> Didaktischer Übergang: Nach der fachlichen Gruppierung werden nun die Interaktionen fachlich benannt. Commands ersetzen Setters, um die Sprache des Fachbereichs abzubilden.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Commands (z. B. `...Hinzufuegen`) zur fachlichen Interaktion mit Clustern.
- DDD‑Fokus: Fachliche Aktionen statt technischer Setter; Cluster liefern ein explizites Scoring‑Ergebnis.
> Fokus: Sprache des Fachbereichs in der API.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests wurden auf Command‑Aufrufe umgestellt und erwarten Cluster‑Ergebnisobjekte.
- **Technisch:** Neue Typen `ClusterGescored` und `KoKriterien` im Domain‑Modell.
- **Fachlich:** Cluster „scoren“ und liefern Punkte + KO‑Kriterien zusammen zurück.

### 3) Warum diese Änderungen?
- Commands machen den Fachkontext explizit („Einkommen hinzufügen“ statt „setXYZ“).
- Das Ergebnis eines Clusters wird als Value Object greifbar und später kombinierbar.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Regelcluster bleiben erhalten, aber der Interaktionsstil ändert sich von Settern zu Commands.
- Der rote Zustand zwingt die Modell‑API und die internen Berechnungen neu zu denken.

### 5) DDD‑Tactical‑Patterns im Detail
- **Command‑Stil:** Methoden wie `wohnortHinzufuegen`, `warnungenHinzufuegen`, `summeDarlehenHinzufuegen`.
- **Ergebnisobjekt:** `ClusterGescored` kapselt Punkte und KO‑Kriterien pro Cluster.
- **Regelzuordnung:** Punkte/KO bleiben im Cluster; das Ergebnis ist die fachliche Schnittstelle.

### 7) Übungen / Reflexion
- Welche Commands fehlen noch, um die Fachsprache präzise abzubilden?
- Wo ist ein Command wirklich fachlich und nicht nur ein umbenannter Setter?

### 8) Zusammenfassung
- Die Fachsprache wird durch Commands sichtbar.
- Cluster liefern nun explizite Ergebnisobjekte, der Code ist bewusst wieder „rot“.

---

# Command-basierte Cluster umsetzen
Ausgangspunkt: `origin/4_CommandsEvents_red`
Lösung: `origin/5_CommandsEvents_green`

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Umsetzung der Command‑Schnittstellen im Domain‑Code, sodass die Tests grün werden.
- DDD‑Fokus: Cluster liefern `ClusterGescored` als fachliches Ergebnisobjekt.
> Fokus: Command‑Schnittstellen als Vertrag.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Cluster implementieren `scoren()` und verarbeiten Commands.
- **Technisch:** KO‑Logik und Punkteberechnung sind in den Clustern integriert; `ScoringErgebnis` aggregiert Cluster‑Ergebnisse.
- **Aufräumen:** Frühere Hilfsklassen (`Wohnort`, `Guthaben`, `Eigenkapitalanteil`) werden entfernt und Logik direkt im Cluster modelliert.
- **Wertlogik:** `Waehrungsbetrag.anteilVon` und `Prozentwert(BigDecimal)` unterstützen Berechnung des Eigenkapitalanteils.
- **Struktur:** `MonatlicheFinanzsituationCluster` liegt nun im passenden Unterpaket.

### 3) Warum diese Änderungen?
- Das fachliche „Scoren“ eines Clusters ist eine zentrale Aktion und liefert ein konsistentes Ergebnisobjekt.
- Die Modelle werden näher an die Sprache des Fachbereichs gebracht.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Die Command‑Tests bleiben bestehen; der Code implementiert nun die erwartete API.
- Die Cluster übernehmen sowohl Punkte‑ als auch KO‑Regeln intern.

### 5) DDD‑Tactical‑Patterns im Detail
- **Command‑Modellierung:** `...Hinzufuegen`‑Methoden als fachliche Befehle.
- **Value Object:** `ClusterGescored` kapselt Ergebnis; `KoKriterien` repräsentiert Zählung.
- **Cluster‑Schnittstelle:** `scoren()` bildet den fachlichen Abschluss eines Clusters ab.

### 7) Übungen / Reflexion
- Welche Commands sollten invariant‑sicher sein (z. B. verbieten negativer Werte)?
- Ist `ClusterGescored` die richtige Granularität oder braucht es reichhaltigere Ergebnisse?

### 8) Zusammenfassung
- Command‑basierte Interaktion ist implementiert, die Tests sind grün.
- Die Cluster liefern fachliche Ergebnisse als Value Objects und bereiten weitere Evolution vor.

---

# Value Objects identifizieren
Ausgangspunkt: `origin/5_CommandsEvents_green`
Lösung: `origin/6_ValueObjects_red`

## Die Bedeutung von Value Objects im Tactical Design
Value Objects dienen im Domain-driven Design dazu, fachliche Konzepte, Regeln und Invarianten explizit zu modellieren und so Entities und Aggregate zu entlasten. Ihr Wert liegt weniger in der Wiederverwendung von Typen als in der klaren Kapselung von Bedeutung und Regeln.

Value Objects sind fachliche Konzepte ohne eigene Identität und ohne eigenen Lebenszyklus. Sie werden über Wertgleichheit definiert: Zwei Value Objects sind gleich, wenn alle ihre relevanten Werte übereinstimmen. Sie kapseln Regeln und Validierungen und ermöglichen es, fachliche Logik aus Entities zu delegieren. Aus diesem Grund sind Value Objects in der Regel immutable; eine fachliche Änderung führt zu einer neuen Instanz.

Solange diese Eigenschaften erfüllt sind, können Value Objects fachlich reich und regelintensiv sein. Sie dürfen mehrere Attribute enthalten und komplexe Invarianten abbilden. Ein häufiger Fehler ist es, Value Objects mit einfachen Attributen einer Entity zu verwechseln. Ebenso sind sie keine reinen Datentransferobjekte (DTOs): Enthalten sie keine eigenen Regeln oder fachliche Bedeutung, sind sie keine Value Objects.

Im Tactical Design entsteht häufig der Wunsch, Value Objects innerhalb eines Domänenmodells wiederzuverwenden. Dabei ist Vorsicht geboten, denn Wiederverwendung erzeugt Kopplung – und Kopplung sollte bewusst und auf das notwendige Minimum beschränkt werden.

Die Entscheidung zur Wiederverwendung sollte daher nicht anhand von Namensgleichheit, sondern anhand von Bedeutungs- und Regelgleichheit getroffen werden. Werden Value Objects über mehrere Entities oder Aggregate hinweg geteilt, wirken sich Änderungen an Regeln, Validierungen oder Semantik zwangsläufig auf alle Verwendungsstellen aus. Diese Kopplung ist nur dann sinnvoll, wenn alle beteiligten Stellen dasselbe fachliche Konzept mit identischen Invarianten modellieren – heute und mit hoher Wahrscheinlichkeit auch in Zukunft.

Insbesondere über Aggregatsgrenzen hinweg ist Wiederverwendung kritisch zu prüfen. Aggregate bilden eigenständige Konsistenz- und Modellierungsgrenzen; Value Objects mit domänenspezifischen Regeln gehören in der Regel in den Verantwortungsbereich eines einzelnen Aggregats. Werden sie dennoch geteilt, ist dies eine bewusste Designentscheidung mit entsprechenden Wartungs- und Evolutionskosten.

Tritt der Wunsch nach Wiederverwendung von Value Objects häufig auf, sollte dies als Signal zur Modellüberprüfung verstanden werden. Entweder beschreibt das Value Object tatsächlich ein stabiles, aggregatübergreifendes Kernkonzept – oder die betroffenen Aggregate sind fachlich nicht klar genug geschnitten und teilen implizit Verantwortung, die explizit getrennt werden sollte.


### Häufige Fallstricke bei Value Objects
	- Namensgleichheit statt Bedeutungs­gleichheit
	- Value Objects ohne eigenes Verhalten
	- Typinflation ohne fachlichen Mehrwert
Für jedes Attribut wird ein eigenes Value Object eingeführt, ohne  Regeln abzubilden. Das erhöht die Komplexität, ohne das Modell klarer zu machen.
	- Geteilte Value Objects als versteckte Policies:
Werden Value Objects über mehrere Aggregate hinweg geteilt, obwohl sie unterschiedliche fachliche Regeln ausdrücken müssten, werden Policies implizit gekoppelt und fachliche Unterschiede unsichtbar gemacht.

### Code-Regeln für die Implementierung von Value Objects

- Da Value Objects fachliche Konzepte sind benutzen wir hier keine Getter und Setter.
- Stelle über den Konstruktor sicher, dass unsere Value Objects immutable sind.
- Ein Value Object bietet nach außen nur fachlich motivierte Methoden an.

Wir sind es gewohnt uns zu einer Klasse Getter und Setter generieren zu lassen, aber in diesem Fall wollen wir das nicht tun. Hierdurch würden wir die interne Struktur offenlegen. Unser Value Object wäre schnell nur eine Datenstruktur. Wir wollen aber die *Kapselung* von fachlichen Konzepten und Information Hiding erreichen.

Im folgenden Beispiel wird ein Value Object für den Wohnort erstellt. Der Wohnort wird über einen Konstruktor initialisiert und bietet eine Methode zum Berechnen von Punkten. Durch den konstruktor stellen wir sicher, dass der Wohnort _nicht veränderbar_ (immutable) ist. Hier sollte deutlich werden warum, denn wenn wir den Wohnort verändern, dann ändert sich auch die Punkteberechnung. Solange wir den Wohnort nicht verändern, bleibt das Ergebnis konsistent.

Beispiel:

```java
public class Wohnort {
    private final String wohnort;

    public Wohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public Punkte berechnePunkte() {
        if (wohnort.equals("Hamburg") || wohnort.equals("München"))
            return new Punkte(10);
        else
            return new Punkte(0);
    }
}
```

## Aufgabe

- Überprüfe unsere bisherigen Kandidaten, ob diese die Kriterien für ein Value Object erfüllen.
- Halte dich an die Code-Regeln für die Implementierung von Value Objects.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests werden auf eigenständige fachliche Begriffe aufgeteilt.
- **Technisch:** Neue Testklassen wie `GuthabenTest`, `WohnortTest`, `WarnungTest`, `NegativMerkmalTest`, `RueckzahlungsWahrscheinlichkeitTest`, `MarktwertVergleichTest`.
- **Fachlich:** Regeln wandern gedanklich aus den Clustern in eigenständige Begriffe.

### Warum diese Änderungen?
- Fachliche Konzepte werden explizit und separat testbar.
- Es wird klar, welche Regeln zu einem Begriff gehören (nicht zu einem „zusammengewürfelten“ Cluster).

---

# Value Objects implementieren
Ausgangspunkt: `origin/6_ValueObjects_red`
Lösung: `origin/7_ValueObjects_green`

## Aufgabe
- Führe die notwendigen Änderungen um damit die ValueObjects die in den Tests definiereten Kriterien erfüllen.

## Lösungsweg

### Änderungen 
- Value Objects wurden implementiert und bilden die Basis für den weiteren Ausbau unseres Domändenmodells.
- Regeln gehören zu den Fachbegriffen
- Es ist klar, dass ein Domänenmodell ein fachliches Regelmodell ist und diese Regeln das Design treiben.
- Das Modell wird ausdrucksstärker und resilient gegen Regeländerungen.
- Durch das Bottom-Up-Design stellen wir sicher, dass wir ein Modell entwerfen, dass sich an den Bedürfnissen der Fachlichkeit orientiert und wir nicht stattdessen Top-Down Fachobjekte definieren, die gut aussehen, aber in der Architektur keinen Mehrwert liefern.

### DDD‑Tactical‑Patterns in einer Nussschale
- **Value Object (zentral):** Kapselt Regeln und Vergleichslogik (z. B. `Warnung`, `Guthaben`).
- **Nicht‑Beispiel:** Ein reines Feld ohne Regel wäre kein Value Object.
- **Verschärfte Definition:** Ein Value Object ist ein fachlicher Begriff mit eigener Logik, Regeln und Invarianten, der über seinen Wert definiert ist (Gleichheit über Attribute) und keinen eigenen Lebenszyklus/Identität besitzt. Ein „String“ oder „int“ ohne Fachregel ist kein Value Object.

---

# Entities identifizieren
Ausgangspunkt: `origin/7_ValueObjects_green`
Lösung: `origin/8_Entities_red`

## Von Value Objects zu Entities

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Entity‑Tests und Identitäten (z. B. `Antragsnummer`, `AntragstellerID`).
- DDD‑Fokus: Entities werden über Identität definiert, nicht über ihren aktuellen Wert.
> Fokus: Lebenszyklus und Identität.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests verlangen Identitäten und Gleichheit über IDs.
- **Technisch:** Neue Tests `AntragsnummerTest` und `AntragstellerIDTest`; Cluster‑Tests erwarten Konstruktoren mit IDs.
- **Fachlich:** Auskunftei‑Ergebnisse und Scoring‑Ergebnisse sind nun an Antragsnummer/Antragsteller gebunden.

### 3) Warum diese Änderungen?
- Fachliche Konzepte wie „Antrag“ und „Antragsteller“ haben einen Lebenszyklus und müssen eindeutig identifizierbar sein.
- Value Objects allein reichen nicht aus, wenn wir historisieren oder Zustände über Zeit hinweg verfolgen müssen.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Value Objects bleiben bestehen; zusätzlich wird eine Identität eingeführt.
- Der rote Zustand zwingt die Modellierung von Entities (und IDs) in den Clustern.

### 5) DDD‑Tactical‑Patterns im Detail
- **Entity (neu):** Identität als primäres Kriterium (z. B. `Antragsnummer`, `AntragstellerID`).
- **Abgrenzung:** Sobald Lebenszyklus/Identität benötigt wird, ist es kein Value Object mehr.

### 7) Übungen / Reflexion
- Welche bisherigen Value Objects benötigen tatsächlich eine Identität?
- Welche Prozesse (z. B. Antragshistorie) erzwingen Entities?

### 8) Zusammenfassung
- Die Notwendigkeit von Identität wird explizit, Entities werden vorbereitet.
- Der Branch ist bewusst „rot“, um die Entity‑Implementierung auszulösen.

---

# Entities implementieren
Ausgangspunkt: `origin/8_Entities_red`
Lösung: `origin/9_Entities_green`
> Didaktischer Übergang: Entities werden implementiert, Identität wird zum zentralen Gleichheitskriterium.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Umsetzung der Entities und Integration in Cluster und Scoring‑Ergebnis.
- DDD‑Fokus: Identität und Lebenszyklus bestimmen die Modellierung.
> Fokus: Identität als Gleichheitskriterium.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Entity‑Klassen werden eingeführt und in Cluster/Scoring verwendet.
- **Technisch:** `Antragsnummer` und `AntragstellerID` werden implementiert; `ScoringErgebnis` und `AuskunfteiErgebnisCluster` nutzen Identitäten.
- **Gleichheit:** `equals/hashCode` basieren auf Identität, nicht auf Attributwerten.

### 3) Warum diese Änderungen?
- Fachliche Stabilität: Ein Antrag bleibt „derselbe“ trotz geänderter Attribute.
- Identität erlaubt konsistente Referenzierung und Nachvollziehbarkeit.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Tests aus `origin/8_Entities_red` werden grün.
- Value Objects bleiben, Entities strukturieren nun den Lebenszyklus.

### 5) DDD‑Tactical‑Patterns im Detail
- **Entity:** `Antragsnummer`, `AntragstellerID` als Identitätsanker.
- **Value Object vs. Entity:** Entscheidung anhand Lebenszyklus, nicht anhand Komplexität.

### 7) Übungen / Reflexion
- Wo ist Identität fachlich zwingend, wo wäre sie Overhead?
- Welche Entities könnten später Aggregate werden?

### 8) Zusammenfassung
- Entities sind implementiert und bilden den Lebenszyklus ab.
- Das Modell ist bereit für Aggregat‑Entscheidungen in den nächsten Schritten.

---

# Aggregate, Konsistenzregeln und Policies identifizieren

Bevor wir Aggregate umsetzen können müssen wir diese erst einmal identifizieren. Die richtigen Aggregate zu identifizieren ist herausfordernd. Wir müssen davon ausgehen, dass unser erster Entwurf nicht perfekt ist und wir unser Modell Schrittweise verbessern.

Wir müssen herausfinden:
- Welche Konsistenzregeln es gibt.
- Welche Konsistenzregeln zusammen ausgeführt werden müssen.
- Welche Policies es gibt.
- Welche fachlichen Objekte Grundlagefür Aggregate sein können.

Konsistenzregeln: Diese Regeln schützen uns vor Invarianten. Sie stellen also sicher, dass unser Modell konsistent ist und keine Regeln verletzt werden. Sie sind wie die Regeln bei einem Gesellschaftsspiel. Diese müssen eingehalten werden, sonst schummelt jemand. Diese Regeln gelten aber nur in einem bestimmten Rahmen, zum Beispiel in einer Spielerunde, aber nicht am Nachbartisch.

Konsistenzregeln bilden die Grundlage für die Bildung von Aggregaten.

Konsistenzregeln sind immer Teil eines Aggregats.

Hinzu kommen Policies, sogenannte Wenn-Dann-Regeln. "Wenn jemand eine 7 legt, dann muss der nächste Spieler zwei Karten ziehen.". Policies steuern den Fluss in der Anwendung und in unserer Implementierung das Zusammenspiel aus Services und Aggreagten. Policies bilden mit Commands das Interface von Aggregaten nach außen. 

#### Wo werden Policies implementiert?

Policies können sowohl in Services, als auch in Aggregaten definiert werden. In Aggregaten setzen wir Policies um, die sich nur auf Abläufe innerhalb dieses Aggregates beziehen. In Services liegen Policies, die Kommunikation nach außen betreffen. Aggregate können sich auf gegenseitig aufrufen und so Policies untereinander umsetzen. 

#### Unterscheidung von Policies und Konsistenzregeln

Bei manchen Regeln ist die Unterscheidung einfach. So ist: "Spieler müssen mindestens 18 Jahre als sein." eine Konsistenzregel.

Genauso gibt es Policies die keine weitere Validierung erfordern und nur einen simplen Ablauf beschreiben.

Die Unterscheidung zwischen Policies und Konsistenzregeln ist nicht immer einfach. Schauen wir uns dazu nochmal das vorherige Beispiel an:
"Wenn jemand eine 7 legt, dann muss der nächste Spieler zwei Karten ziehen."

Hier ist ein klarer Ablauf beschrieben. Wenn wir das Spiel als Computerspiel umsetzen wollten, müssten wir diese Regel automatisieren. Wir würden erwarten, dass der Computer automatisch zwei Karten zieht, wenn eine 7 gespielt wird. Am Spieltisch muss jeder Spieler dies selbst ausführen.

Genauso ist hier aber eine Konsistenzregel versteckt, denn wir müssen sicherstellen, dass dies auch wirklich passiert ist. Wir haben also auch Regeln für ein Aggregate, die prüfen wann ein Spieler wieviele Karten ziehen darf oder muss.

#### Commands

Commands sind Aktionen, die direkt auf einem aggregate ausgeführt werden können. Sie definieren die Schnittstelle eines Aggregates und werden entweder durch Policies oder Nutzeraktionen ausgelöst.

#### Wie identifizieren wir Konsistenzregeln, Policies und Commands?

Eine oft erfolgreiche Methode ist das Software-Design-level Event Storming. Bei Event Storming denken wir oft an das Big Picture Event Storming. Beim Big Picture Event Storming haben wir nicht viele Regeln. Wir wollen meistens effektiv gemeinsames Verständnis über einen Bereich aufbauen, ein "Big Picture".
Die Design-Level Event Storming variante ist dagegen sehr strikt. Hier ist das Ziel Prozesse im Detail und lückenlos zu verstehen. Daher gibt es feste Grammatik die definiert welche Elemente vorkommen müssen und in welcher Reihenfolge.

Wir wollen so sicherstellen, dass wir keine Lücken hinterlassen, die sich erst bei der Umsetzung oder später als Bugs zeigen.

```digraph
digraph AlmostEverything {
  rankdir=LR;
  fontsize=12;
  labelloc="t";
  label="Software design picture that explains \"Almost\" everything";

  // Layout tuning to reduce crossings
  graph [
    splines=ortho,
    nodesep=0.9,
    ranksep=1.2,
    overlap=false,
    concentrate=false
  ];

  edge [
    penwidth=2.2,
    fontname="Helvetica"
  ];

  node [
    shape=box,
    style="rounded,filled",
    fontname="Helvetica",
    fixedsize=true
  ];

  // --- Post-it sizing ---
  actor [
    label="actor",
    fillcolor="#FFF59D",
    width=1.1,
    height=1.4
  ];

  command [
    label="Command / Action",
    fillcolor="#81D4FA",
    width=2.0,
    height=1.4
  ];

  query [
    label="Query Model /\nInformation",
    fillcolor="#C5E1A5",
    width=2.0,
    height=1.4
  ];

  event [
    label="Domain Event",
    fillcolor="#FFCC80",
    width=2.0,
    height=1.4
  ];

  policy [
    label="Policy",
    fillcolor="#CE93D8",
    width=3.2,
    height=1.4
  ];

  external [
    label="External System",
    fillcolor="#F8BBD0",
    width=3.6,
    height=1.4
  ];

  constraint [
    label="Constraint",
    fillcolor="#FFF9C4",
    width=3.2,
    height=1.4
  ];

  // --- Explicit layout constraints (columns + vertical ordering) ---
  // Column 1
  { rank=same;  actor }

  // Column 2
  { rank=same; policy; command; query }

  // Column 3 (stacked: constraint above external)
  { rank=same; constraint; external }
//   constraint -> external [style=invis, weight=100];

  // Column 4
  { rank=same; event }

  policy -> query [style=invis, weight=100];

  // Keep left-to-right ordering stable
//   actor   -> command   [style=invis, weight=200];
//   command -> constraint[style=invis, weight=200];
//   external-> event     [style=invis, weight=200];
//   event   -> policy    [style=invis, weight=200];

  // --- Your corrected connections (semantics unchanged) ---
  actor   -> command     [label="decides to"];

  command -> external    [label="invoked on"];
  command -> constraint  [label="invoked on", constraint=false, minlen=2];
  

  constraint -> event    [label="produces an"];
  external   -> event    [label="produces an"];

  event  -> policy       [label="activates a"];

  // Back-edge: route it "around" (avoid forcing rank changes)
  policy -> command      [label="issues a", constraint=false, minlen=2];

  event -> query         [label="results in one or more"];

  // Long edge: keep it from influencing ranks; attach to reduce clutter
  actor -> query         [label="observes"];
}
```


---

# Aggregate-Invarianten definieren
Ausgangspunkt: `origin/9_Entities_green`
Lösung: `origin/10_Aggregates_red`
> Didaktischer Übergang: Nach Entities definieren wir Konsistenzgrenzen. Aggregate stellen sicher, dass alle Teile zu einem Antrag gehören und vollständig sind.

## Aufgabe

- Einführung von Aggregat‑Regeln (Konsistenzgrenzen, Vollständigkeit, gemeinsame Antragsnummer).
- Prüfe ob die Regel-Cluster als Aggregate funktionieren.
- Die fachlichen Regeln für jeden Cluster in der Fallstudie sind Konsistenzregeln.
- Setze für jedes Aggregat ein Interface um, das zur fachlichkeit passt und den schrittweisen Aufbau von Aggregaten ermöglicht.
- Wenn alle Informationen vorhanden sind kann ein Cluster-Aggregat unabhängig von anderen Clustern gescored werden.
- Das Interface für jedes Aggregat entspricht den Commands aus dem Softwaredesign Event Storming.
- Definiere wie die Aggregate miteinander verbunden werden.
- Wie kannst du in Java damit umgehen, dass ein Aggregate nur dann gescoret wird, wenn alle Teile vorhanden sind?
- Konzentriere dich in diesem Schritt weiterhin erstmal auf die Unit Tests.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Tests verlangen gleiche `Antragsnummer` in allen Cluster‑Ergebnissen und behandeln fehlende Teilergebnisse.
- **Technisch:** Cluster‑Tests prüfen `Optional`‑Ergebnisse bei fehlenden Daten.
- **Fachlich:** Ein Antrag kann „nicht gescored“ sein, wenn Teilinformationen fehlen.

### 3) Warum diese Änderungen?
- Aggregate definieren die fachliche Konsistenz: alle Teile müssen zum selben Antrag gehören.
- Vollständigkeit wird zu einer Invariante (kein Scoring ohne alle nötigen Daten).

### 4) Wie baut der Branch auf dem vorherigen auf?
- Entities bleiben, werden aber nun als Aggregate‑Schlüssel genutzt (`Antragsnummer`).
- Der rote Zustand erzwingt Aggregate‑Logik in Scoring und Clustern.

### 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root (implizit):** `ScoringErgebnis` bündelt Cluster‑Ergebnisse eines Antrags.
- **Invarianten:** Gleichheit der `Antragsnummer` und Vollständigkeit der Teilergebnisse.
- **Ergebniszustand:** Fehlende Cluster führen zu „nicht gescored“ statt falschem Ergebnis.

### 8) Zusammenfassung
- Aggregate‑Regeln werden explizit gefordert.
- Konsistenz und Vollständigkeit werden als fachliche Invarianten sichtbar.

---

# Aggregate umsetzen und Ergebnisse ableiten
Ausgangspunkt: `origin/10_Aggregates_red`
Lösung: `origin/11_Aggregates_green`

## Aufgabe

- Umsetzung der Aggregate‑Regeln und Ergebnis‑Events im Code.
- Stelle sicher, dass die Aggregate in sich geschlossen sind.
- Stelle sicher, dass alle Konsitenzregeln zusammen ausgeführt werden.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Aggregate‑Regeln sind implementiert, Scoring liefert Events.
- **Technisch:** `ClusterGescored` enthält jetzt `Antragsnummer`; Cluster‑`scoren()` liefert `Optional`.
- **Domain Events:** `AntragScoringEvent` mit `AntragErfolgreichGescored` und `AntragKonnteNichtGescoredWerden`.
- **Konsistenz:** `ScoringErgebnis` prüft Antragsnummern und wirft bei Abweichung.

### 3) Warum diese Änderungen?
- Aggregate Root schützt die Konsistenz der Scoring‑Daten.
- Events liefern eine klare, fachliche Schnittstelle an nachfolgende Schritte.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Tests aus `origin/10_Aggregates_red` werden grün.
- Clustern liefern nur noch Ergebnisse, wenn alle notwendigen Daten vorliegen.

### 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root:** `ScoringErgebnis` koordiniert Cluster‑Ergebnisse.
- **Domain Event:** `AntragErfolgreichGescored`, `AntragKonnteNichtGescoredWerden`.
- **Invarianten:** gleiche `Antragsnummer` und vollständige Cluster‑Daten.

### 7) Übungen / Reflexion
- Welche Aggregate fehlen noch im Scoring‑Kontext?
- Wo sollten Domain Events später publiziert werden?

### 8) Zusammenfassung
- Aggregates sind umgesetzt, Invarianten werden durchgesetzt.
- Scoring liefert klare fachliche Events für die nächsten Prozessschritte.

---

# Ports definieren
Ausgangspunkt: `origin/11_Aggregates_green`
Lösung: `origin/12_Ports`
> Hinweis: Ab hier gibt es keine expliziten TDD‑Red/Green‑Schritte mehr, sondern evolutionäre Ausbauschritte. Fahre gerne mit TDD weiter fort.

## Aufgabe

- Einführung von Ports (Driving/Driven).
- Hexagonal/Ports‑&‑Adapters‑Sicht, ohne konkrete Implementierungen.
- Überlege wo Ports (Interfaces) eingeführt werden müssen, damit die inneren Schichten mit der Außenwelt kommunizieren können. Wo ist dies nötig?
- Wie sind diese Schnittstellen gestaltet? Versuche auch diese Schnittstellen so fachlich wie möglich zu gestalten.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Interfaces für Eingänge (Driving) und Abhängigkeiten (Driven).
- **Technisch:** Ports in `application/ports/driving` und `application/ports/driven`.
- **Fachlich:** Abhängigkeiten wie Auskunftei‑Abfrage oder Persistenz werden als Schnittstellen beschrieben.

### 3) Warum diese Änderungen?
- Ports entkoppeln Domäne/Anwendung von Infrastruktur.
- Die spätere Implementierung als Adapter wird vorbereitet.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Aggregate bleiben unverändert; es wird eine Anwendungsschicht eingeführt.
- Ports kapseln die Kommunikation nach außen und innen, ohne Logik zu verändern.

### 5) DDD‑Tactical‑Patterns im Detail
- **Driving Ports:** z. B. `PreScoringStart`, `VerarbeitungImmobilienBewertung` als Eingänge.
- **Driven Ports:** z. B. Repositories (`ScoringErgebnisRepository`, `AntragstellerClusterRepository`) und externe Services (`KonditionsAbfrage`, `LeseKontoSaldo`).

### 7) Übungen / Reflexion
- Welche Ports sind fachlich notwendig, welche technisch motiviert?
- Wo gehört ein neuer Port hin (driving vs. driven)?

### 8) Zusammenfassung
- Ports definieren die Grenzen des Scoring‑Moduls.
- Die Implementierung als Adapter folgt in späteren Schritten.

---

# Application Services orchestrieren
Ausgangspunkt: `origin/12_Ports`
Lösung: `origin/13_ApplicationServices`
> Didaktischer Übergang: Die Anwendungsebene orchestriert den Ablauf. Application Services koordinieren Ports, Infrastruktur und Domäne.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Application Services als Use‑Case‑Orchestrierung.
- Fokus: Prozesssteuerung (Pre‑Scoring vs. Main‑Scoring) und Integration externer Services.
> Fokus: Orchestrierung statt Fachlogik.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Application Services implementieren Driving Ports und rufen Driven Ports/Domain‑Logik.
- **Technisch:** `EingereicherAntragVerarbeitenApplicationService`, `FreigegebenerAntragVerarbeitenApplicationService`, `VerarbeitungImmobilienBewertungApplicationService`.
- **Fachlich:** Pre‑Scoring und Main‑Scoring unterscheiden sich durch Auskunftei‑Abfrageart (Konditions‑ vs. Kreditabfrage).

### 3) Warum diese Änderungen?
- Application Services koordinieren den Ablauf über mehrere Systeme/Ports hinweg.
- Sie halten Infrastruktur‑Details von der Domäne fern und definieren Transaktionsgrenzen.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Ports sind vorhanden; nun werden sie durch Application Services „verdrahtet“.
- Domain‑Logik bleibt unangetastet, Orchestrierung kommt hinzu.

### 5) Aufgaben & Abgrenzung (Application vs. Domain Service)
- **Application Service:** Orchestriert Use Cases, ruft Ports, entscheidet Ablauf (z. B. Pre/Main‑Scoring).
- **Domain Service:** Enthält fachliche Logik über mehrere Entities/Value Objects (ohne Infrastruktur).
- **In diesem Schritt:** Application Services delegieren fachliche Operationen an Domain‑Services‑Ports.

### 7) Alternative Interpretationen
- Manche Teams verwenden „Application Services“ nur als Thin Layer und legen fast alle Regeln in Aggregate.
- Andere schneiden Use‑Cases als „Command Handlers“ und verzichten auf explizite Services.
- In CQRS‑Varianten kann die Orchestrierung stärker in der Infrastruktur liegen, während Domänenlogik in Aggregaten verbleibt.

### 8) Zusammenfassung
- Application Services koordinieren den Prozess und integrieren Ports.
- Die fachliche Logik bleibt in der Domäne (oder wird in Domain Services ausgelagert).

---

# Domain Services bündeln
Ausgangspunkt: `origin/13_ApplicationServices`
Lösung: `origin/14_DomainServices`
> Didaktischer Übergang: Fachliche Logik wird aus der Orchestrierung herausgezogen. Domain Services bündeln Regeln über mehrere Cluster hinweg.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Domain Services für fachliche Operationen über mehrere Aggregate/Cluster.
- Fokus: Wiederverwendbare Fachlogik, unabhängig von Infrastruktur.
> Fokus: Fachlogik zentralisieren.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Domain Services entstehen, Domain‑Model wird in `domain/model` konsolidiert.
- **Technisch:** `AntragHinzufuegenDomainService`, `AuskunfteiHinzufuegenDomainService`, `ScoringDomainService` u. a.
- **Fachlich:** Application Services delegieren fachliche Entscheidungen an Domain Services.

### 3) Warum diese Änderungen?
- Fachliche Logik über mehrere Aggregate/Cluster gehört nicht in Application Services.
- Domain Services reduzieren Duplikation und machen Regeln zentral testbar.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Application Services bleiben, enthalten aber weniger fachliche Logik.
- Domain Services übernehmen die konkrete Verarbeitung und arbeiten mit Repositories.

### 5) Aufgaben & Abgrenzung (Domain vs. Application Service)
- **Domain Service:** Enthält Regeln, Invarianten und fachliche Entscheidungen über mehrere Entitäten.
- **Application Service:** Steuert den Ablauf und kümmert sich um Ports/Transaktionen.
- **Warum diese Aufteilung:** Orchestrierung bleibt schlank, Fachlogik bleibt zentral.

### 7) Alternative Interpretationen
- Manche Ansätze vermeiden Domain Services und legen Regeln direkt in Aggregate (rich domain model).
- Andere nutzen Domain Services als reine „Policy Objects“ ohne Repositories (funktionaler Stil).
- In anämischen Modellen verbleibt die Logik oft in Application Services — das wird hier bewusst vermieden.

### 8) Zusammenfassung
- Domain Services bündeln fachliche Logik, Application Services orchestrieren.
- Die Trennung erhöht Klarheit und Wiederverwendbarkeit im Scoring‑Prozess.

---

# Adapter-Verträge testen
Ausgangspunkt: `origin/14_DomainServices`
Lösung: `origin/15-0_DrivenAdapters_UnitTests`
> Didaktischer Übergang: Bevor konkrete Infrastruktur gewählt wird, definieren wir die Adapter‑Verträge mit Tests.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Adapter‑Unit‑Tests für die Driven Ports (Repository‑Verträge).
- Fokus: Infrastruktur bleibt offen, aber Verhalten der Adapter wird festgelegt.
> Fokus: Infrastrukturverträge definieren.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Adapter‑Tests für Repositories.
- **Technisch:** Tests in `src/test/java/com/bigpugloans/scoring/adapter/driven/*`.
- **Fachlich:** Persistenz wird als austauschbarer Mechanismus betrachtet.

### 3) Warum diese Änderungen?
- Adapter‑Tests definieren die Erwartungen an Persistenz, unabhängig von Technologie.
- Erlaubt spätere Varianten (Memento, Mongo, JPA) ohne die Domäne zu ändern.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Ports und Services bleiben; es kommen nur Adapter‑Tests hinzu.
- In‑Memory‑Repos dienen als Test‑Double.

### 5) DDD‑Tactical‑Patterns im Detail
- **Ports & Adapters:** Ports definieren, Adapter erfüllen Verträge.
- **Test‑First Infrastruktur:** Verhalten wird getestet, bevor Technologie feststeht.

### 7) Übungen / Reflexion
- Welche Adapter‑Verträge sind kritisch für die Domäne?
- Welche Anforderungen würden sich bei Technologie‑Wechsel ändern?

### 8) Zusammenfassung
- Adapter‑Verträge sind testgetrieben fixiert.
- Die konkrete Persistenz ist bewusst noch offen.

---

# Adapter mit Memento/JDBC umsetzen
Ausgangspunkt: `origin/15-0_DrivenAdapters_UnitTests`
Lösung: `origin/15-1_DrivenAdapters_Memento-Pattern`
> Didaktischer Übergang: Erste Adapter‑Implementierung mit relationaler Persistenz und Memento‑Pattern.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Umsetzung der Repository‑Adapter mit Spring Data JDBC + Memento.
- Fokus: Persistenz getrennt halten, aber Snapshot‑Mechanismus nutzen.
> Fokus: Explizites Mapping ohne JPA.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** JDBC‑Adapter und Record‑Klassen werden eingeführt.
- **Technisch:** `*JDBCRepository`, `*Record`, `schema.sql`, `data.sql`.
- **Memento:** Domain‑Objekte liefern ein Memento zur Persistenz (`...Memento`).

### 3) Warum diese Änderungen?
- Memento erlaubt Persistenz ohne JPA‑Annotationen im Domain‑Model.
- Relationale DB bleibt möglich, Mapping bleibt explizit.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests werden mit JDBC‑Implementierungen erfüllt.
- Domain‑Objekte erhalten Memento‑Funktionen (Persistenzwissen).

### 5) Bewertung & DDD‑Nähe
- **Stärken:** Domain bleibt weitgehend frei von ORM‑Anmerkungen; Mapping ist explizit.
- **Kompromiss:** Memento‑Klassen im Domain‑Model leaken Persistenz‑Interessen in die Domäne.
- **Risiko:** Gefahr der „Daten‑Aufblähung“ im Domain‑Model (UI‑Felder, technische Daten ohne Regelbezug).
- **Wann sinnvoll:** Wenn relationale DB gefordert ist und JPA bewusst vermieden werden soll, aber mit klarer Disziplin: Domain nur um regelrelevante Daten erweitern.

### 7) Übungen / Reflexion
- Ist ein Memento ein akzeptabler „technical leak“ in der Domäne?
- Welche Aggregate profitieren von expliziten Snapshots?

### 8) Zusammenfassung
- JDBC‑Adapter funktionieren, Persistenz ist relational.
- Memento‑Pattern ist ein bewusster Kompromiss zwischen Reinheit und Praktikabilität.

---

# Adapter mit MongoDB umsetzen
Ausgangspunkt: `origin/15-1_DrivenAdapters_Memento-Pattern`
Lösung: `origin/15-2_DrivenAdapters_SpringData-MongoDB`
> Didaktischer Übergang: Alternative Persistenz mit MongoDB und separatem Dokument‑Modell.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Umsetzung der Adapter mit Spring Data MongoDB.
- Fokus: Persistenzmodelle getrennt vom Domain‑Model halten.
> Fokus: Dokument‑Mapping für Aggregate.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** JDBC‑Adapter werden durch Mongo‑Adapter ersetzt.
- **Technisch:** `*Document` + `*MongoDbRepository` + Spring Data Repositories.
- **Domain‑Model:** bleibt ohne Persistenz‑Annotationen.

### 3) Warum diese Änderungen?
- MongoDB erlaubt flexible Dokument‑Strukturen für Aggregate.
- Dokument‑Klassen kapseln Persistenzdetails im Adapter.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests werden mit Mongo‑Implementierungen erfüllt.
- Domain‑Model bleibt technologisch neutral.

### 5) Bewertung & DDD‑Nähe
- **Stärken:** Domain bleibt frei von Persistenz‑Annotations; Adapter kapseln Infrastruktur.
- **Kompromiss:** Dokumente speichern Domain‑Objekte (Kopplung Domain ↔ Dokument‑Schema).
- **Einwand (berechtigt):** Schema‑Migrationen werden schwieriger/unklar, wenn das Domain‑Model direkt in Dokumenten persistiert wird.
- **Wann sinnvoll:** Wenn Dokument‑DB genutzt wird und Aggregate als Dokumente gespeichert werden sollen.

### 7) Übungen / Reflexion
- Welche Teile der Aggregate sollten dokumentbasiert gespeichert werden?
- Wo droht enge Kopplung zwischen Document und Domain?

### 8) Zusammenfassung
- Mongo‑Variante ist DDD‑nah, da Domain sauber bleibt.
- Dokument‑Mapping ist der Hauptkompromiss dieser Variante.

---

# Adapter mit JPA-Annotations umsetzen
Ausgangspunkt: `origin/15-2_DrivenAdapters_SpringData-MongoDB`
Lösung: `origin/15-3_DrivenAdapters_JPA-Annotated`
> Didaktischer Übergang: Alternative Persistenz mit JPA‑Annotationen direkt im Domänenmodell.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Umsetzung der Adapter mit Spring Data JPA.
- Fokus: Schnell integrierbare Persistenz, aber stärkere Kopplung.
> Fokus: Pragmatismus vs. Domänenreinheit.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Mongo‑Dokumente werden durch JPA‑Repositories ersetzt.
- **Technisch:** Domain‑Model wird mit `@Entity`, `@Embedded`, `@Id` annotiert.
- **Domänenänderung:** No‑Arg‑Konstruktoren und technische IDs entstehen.

### 3) Warum diese Änderungen?
- JPA ist verbreitet und produktiv für relationale Datenbanken.
- Infrastruktur wird schneller integrierbar, Tests werden vereinheitlicht.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Adapter‑Tests laufen nun gegen JPA‑Repos.
- Domain‑Klassen tragen Persistenz‑Details.

### 5) Bewertung & DDD‑Nähe
- **Stärken:** Schnelle Umsetzung mit Standard‑Stack, gute Tooling‑Unterstützung.
- **Kompromiss:** Domain‑Model ist nicht mehr persistence‑ignorant; technische Anforderungen prägen das Modell.
- **Wann sinnvoll:** Wenn Team/Organisation stark auf JPA setzt und Time‑to‑Market wichtiger ist als maximale Domänenreinheit.

### 7) Übungen / Reflexion
- Welche Persistenz‑Details dürfen im Domain‑Model sichtbar sein?
- Wie beeinflussen JPA‑Constraints die Modellierung von Aggregaten?

### 8) Zusammenfassung
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

# Backend-Adapter integrieren
Ausgangspunkt: `origin/15-3_DrivenAdapters_JPA-Annotated`
Lösung: `origin/16_DrivenAdapters_Backends`
> Didaktischer Übergang: Neben Persistenz‑Adaptern werden nun Backend‑Adapter für externe Systeme eingeführt.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung konkreter Adapter für Auskunftei‑ und Kontosaldo‑Abfragen.
- Fokus: Externe Systeme als Driven Adapter, weiterhin hinter Ports gekapselt.
> Fokus: Externe Systeme kapseln.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Backend‑Adapter implementieren Ports für Konditionsabfrage und Kontosaldo.
- **Technisch:** `KonditionsAbfrageAdapter`, `LeseKontoSaldoAdapter` sowie Tests.
- **Fachlich:** Externe Datenquellen werden in die Anwendung integriert.

### 3) Warum diese Änderungen?
- Fachliche Prozesse benötigen externe Daten (Auskunftei, Kernbank).
- Adapter halten die Domäne frei von Infrastruktur‑Details.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Persistenz‑Adapter bleiben; es kommen Backend‑Adapter für externe Services hinzu.
- Ports (`KonditionsAbfrage`, `LeseKontoSaldo`) werden nun konkret umgesetzt.

### 5) DDD‑Tactical‑Patterns im Detail
- **Driven Adapter:** Implementieren Infrastrukturzugriff, liefern Domänen‑nahe Daten zurück.
- **Abgrenzung:** Domain/Services kennen nur Ports, nicht die Adapter.

### 7) Übungen / Reflexion
- Wie unterscheiden sich Infrastruktur‑Ports (Auskunftei) von Persistenz‑Ports?
- Welche Fehler‑/Timeout‑Strategien sollten diese Adapter besitzen?

### 8) Zusammenfassung
- Backend‑Adapter sind eingeführt und kapseln externe Systeme.
- Die Port‑Struktur bewahrt die Domäne vor Infrastruktur‑Abhängigkeiten.

---

# Messaging-Adapter mit Events
Ausgangspunkt: `origin/16_DrivenAdapters_Backends`
Lösung: `origin/17-1_MessagingAdapters_SpringApplicationEvents`
> Didaktischer Übergang: Ereignisse werden als Integrationsmechanismus eingeführt. Driving/Driven Adapter reagieren und veröffentlichen Events.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Messaging‑Adaptern auf Basis von Spring Application Events.
- Fokus: Integration über Events, ohne direkte Kopplung zwischen Systemen.
> Fokus: Lose Kopplung via Events.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Neue Event‑Typen und Listener, Publishing von Scoring‑Ergebnissen.
- **Technisch:** Listener in `adapter.driving`, Publisher in `adapter.driven.messaging`.
- **Fachlich:** Eingehende Events („Antrag eingereicht“, „Immobilie bewertet“) stoßen Use‑Cases an.

### 3) Warum diese Änderungen?
- Ereignisse ermöglichen lose Kopplung und asynchrone Integration.
- Die Domäne bleibt von Messaging‑Details entkoppelt.

### 4) Wie baut der Branch auf dem vorherigen auf?
- Ports/Services bleiben; Events binden sie an die Außenwelt an.
- Scoring‑Ergebnisse werden als Events veröffentlicht.

### 5) DDD‑Tactical‑Patterns im Detail
- **Domain Events / Integration Events:** Publishing basiert auf Scoring‑Ergebnis.
- **Adapters:** Listener (Driving) und Publisher (Driven) kapseln Infrastruktur.

### 7) Übungen / Reflexion
- Sind diese Events Domain‑ oder Integration‑Events?
- Welche Informationen dürfen in Events enthalten sein, ohne die Domäne zu leaken?

### 8) Zusammenfassung
- Messaging‑Adapter führen Ereignisfluss ein und koppeln Systeme lose.
- Use‑Cases werden über Events ausgelöst und Ergebnisse veröffentlicht.

---

# Architektur mit Tests sichern
Ausgangspunkt: `origin/17-1_MessagingAdapters_SpringApplicationEvents`
Lösung: `origin/18_Architecture_Tests`
> Didaktischer Übergang: Architektur wird nicht nur beschrieben, sondern aktiv überprüft. Tests schützen Onion/Hexagonal‑Prinzipien.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Einführung von Architekturtests mit ArchUnit und jMolecules.
- Fokus: Onion und Hexagonal Architecture sowie DDD‑Regeln automatisiert sichern.
> Fokus: Architektur als überprüfbarer Vertrag.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** ArchUnit‑Tests prüfen Paket‑, Schicht‑ und Abhängigkeitsregeln.
- **Technisch:** Neue Tests in `src/test/java/com/bigpugloans/architecture`.
- **Fachlich:** Architekturinvarianten werden als Code überprüfbar.

### 3) Warum Architekturtests (besonders bei Onion/Hexagonal)?
- Diese Architekturen leben von **Richtung der Abhängigkeiten**; Verstöße sind schwer sichtbar.
- Ohne Tests schleichen sich Framework‑Abhängigkeiten in die Domäne ein.
- Architekturtests wirken als **Regression‑Schutz** gegen schleichende Kopplung.

### 4) Wie wurden sie hier umgesetzt?
- **Hexagonal‑Regeln:** `HexagonalArchitectureTests` prüfen Schichten und Dependency‑Direction.
- **Onion‑Regeln:** `ArchUnitTests` erzwingen Domänen‑Isolation.
- **DDD‑Regeln:** `DddTacticalPatternsTests` + `JMoleculesArchUnitTests`.
- **Paket‑/Naming‑Regeln:** `PackageStructureTests` verhindern Zyklen und falsche Platzierungen.

### 5) Warum diese Regeln?
- **Domäne unabhängig:** Keine Abhängigkeiten zu Adaptern oder Frameworks.
- **Ports zentral:** Adapter hängen von Ports ab, nicht umgekehrt.
- **Clusters isoliert:** Fachliche Cluster sollen nicht quer abhängen.

### 7) Übungen / Reflexion
- Welche Architekturregel schützt euch am stärksten vor Kopplung?
- Welche Regel würdet ihr lockern oder verschärfen – und warum?

### 8) Zusammenfassung
- Architekturtests sind essenziell, weil Onion/Hexagonal nur durch konsequente Abhängigkeitsrichtung funktionieren.
- Die Tests machen Architektur überprüfbar und stabil.

---

# Event Sourcing als Ausblick
Ausgangspunkt: `origin/18_Architecture_Tests`
Lösung: `origin/19_Antragserfassung_DomainModeling_EventSourced`
> Bonus‑Ausblick: Event Sourcing als Alternative für antragsbezogene Modellierung – mit Commands, Events und Projections.

## Aufgabe
- Aufgabe: Versuche die Änderungen selbst umzusetzen, bevor du den Lösungsweg vergleichst.

- Demonstration eines event‑sourced Subsystems für die Antragserfassung.
- Fokus: Event Sourcing + CQRS (Command‑/Query‑Separation) als mögliche Evolutionsrichtung.
> Fokus: Event Sourcing als Ausblick.

## Lösungsweg

### Änderungen zum vorherigen Branch
- **Kurzfassung:** Neuer Bounded Context `antragserfassung` mit Axon‑Aggregate, Commands, Events und Projections.
- **Technisch:** Axon‑Konfiguration, Projections, Web‑UI für Antragserfassung.
- **Fachlich:** Antragserfassung wird als sequenzieller Prozess modelliert (Schritte, Status, Abschluss).

### 3) Warum diese Änderungen?
- Event Sourcing eignet sich für Prozesse mit Audit‑Bedarf und klaren Status‑Übergängen.
- Änderungen werden als Ereignisse nachvollziehbar (Nachvollziehbarkeit, Historie).

### 4) Wie baut der Branch auf dem vorherigen auf?
- Das Scoring bleibt bestehen; zusätzlich wird eine event‑sourced Antragserfassung gezeigt.
- Dient als Ausblick, nicht als Migration des bestehenden Scoring‑Modells.

### 5) DDD‑Tactical‑Patterns im Detail
- **Aggregate Root:** `Antragserfassung` als Event‑sourced Aggregate.
- **Commands/Events:** `StarteAntragCommand`, `AntragGestartetEvent`, `AntragserfassungAbgeschlossenEvent`, etc.
- **Projections:** Read‑Model wird aus Events aufgebaut (Query‑Seite).

### 7) Übungen / Reflexion
- Für welche Teile des Scorings wäre Event Sourcing sinnvoll – und wo nicht?
- Welche zusätzlichen Anforderungen (Event‑Versioning, Projections‑Rebuilds) entstehen?

### 8) Zusammenfassung
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
| Aggregate Root | Einstiegspunkt eines Aggregates; einzige Entitaet, die von außen direkt referenziert wird |
| Port | Schnittstelle der Anwendung (Driving/Driven) |
| Adapter | Technische Implementierung eines Ports |
| Repository | Fachliches Interface zum Laden/Speichern von Aggregate Roots, ohne Technikbezug |
| Application Service | Orchestriert Use‑Cases, keine Fachlogik |
| Domain Service | Fachlogik über mehrere Aggregate/Entities |
| Domain Event | Fachliches Ereignis, das im Modell passiert und nach außen kommuniziert werden kann |
| Factory | Erzeugt komplexe Aggregate/Entities konsistent (statt verteilte Konstruktor-Logik) |
| Specification | Kapselt fachliche Regeln/Filter als wiederverwendbare Praedikat-Objekte |
| Module | Fachliche Gruppierung von Konzepten im Code zur Begrenzung von Kopplung |
| Event Sourcing | Zustand aus Events rekonstruiert |

---

# Lernpfad‑Checkliste (optional)
- [ ] Branch 0 verstanden
- [ ] Branch 1 verstanden
