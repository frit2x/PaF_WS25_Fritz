# Mögliche Patterns

### Repository Pattern:
Schnittstelle, um Kunden, Abos, Produkte, Anfragen und Bestellungen aus der Datenbank zu holen oder zu speichern, ohne dass die Domain-Logik diese Details kennt.

### Factory Pattern:
Zum Erzeugen komplexer Objekte wie Anfrage oder Bestellung mit validierten Initialdaten.

### Service Layer:
Enthält komplexe Domänenlogik und orchestriert Interaktionen zwischen Aggregaten, z.B. Prüfung von Kontingent und Lagerbestand, anschließende Bestellung und Benachrichtigung.

### Value Objects:
Für kleinere unveränderliche Datenobjekte, z.B. Adresse oder Mengenangaben, falls passend.

### Observer / Event-Pattern (optional):
Für Benachrichtigungen bei Statusänderungen (z.B. Bestellung erfolgreich oder abgelehnt).
