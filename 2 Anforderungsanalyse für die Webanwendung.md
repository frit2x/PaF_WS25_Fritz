# Anforderungsanalyse für die Webanwendung

## Nutzerrollen und Ziele

### Kunde (Rolle: Kunde)
- **Ziele in der UI:**
  - Artikel suchen und anzeigen
  - Bestellung aufgeben (Anfrage erstellen)
  - Bestellstatus und Anfragenhistorie einsehen
  - Abonnement (Abo) verwalten (z.B. Abo-Status prüfen)
  - Kontingent einsehen (verbleibende Menge des Abos)
  - Profilinformationen verwalten (Name, Email)
  - Rechnungen einsehen

### Mitarbeiter / Administrator (Rolle: Admin)
- **Ziele in der UI:**
  - Bestellungen freigeben und bearbeiten
  - Kundendaten einsehen und editieren
  - Rechnungsdaten einsehen und editieren
  - Abos und Kontingente verwalten
  - (Optional) Reporting und Statistiken über Bestellungen und Anfragen

### Informationsarchitektur / Navigationsstruktur (nur Kundensicht)
- **Startseite:**
  - Link zur Profilseite (Aktion: Daten bearbeiten)
  - Artikel Übersicht / Suche / Menü / Filtermöglichkeit / Pagination ---> Link zum Artikel 
  - Infobereich: aktuelle Bestellung ---> Seite mit Aktuellen und abgeschlossenen Bestellungen / Rechnungen
- **Profilseite:**
  - Name, Adresse, E-Mail, Abo (Aktion: Daten ändern)
- **Artikel:**
  - Name, Bild, Preis, Lieferzeit (Aktion: in den Warenkorb)
- **Warenkorb:**
  - Artikel die gekauft werden sollen (Aktion: Löschen / Anzahl editieren / kaufen)

### Wireframes / Mockups
Wird im Draw.io (siehe Link im PDF) erstellt

### Interaktionsdesign (offen)
Beschreibung, wie Nutzer mit der Oberfläche interagieren (z.B. Formular absenden, Liste filtern, Statusanzeigen), inklusive Feedback bei Aktionen.
