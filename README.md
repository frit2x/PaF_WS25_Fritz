# PaF_WS25_Fritz

# Verwaltung von produktbezogenen Kundenanfragen

Ein Kunde fragt bei einem Unternehmen eine bestimmte Menge eines Produktes an, weil er diese bestellen will. Der Kunde muss im Rahmen eines Abos, ein Kontingent erworben haben, welches die maximal zu bestellende Menge definiert. Das Unternehmen führt die Bestellung über die angefragte Menge aus, wenn das Produkt vorrätig ist und das Kontingent noch nicht erschöpft ist. Wird die Bestellung ausgeführt, reduziert sich das Kontingent um die entsprechende Menge. Überschreitet die angfragte Menge das Kontingent, oder ist die Menge nicht verfügbar, wird der Kunde informiert.

## Zentrale Entitäten des Projekts

- **Kunde** – Kundennummer, Name, Adresse, E-Mail, aktive Abos, Bestellhistorie 
- **Produkt** – Produkt-ID, Name, Beschreibung, Preis, Lagerbestand
- **Kontingent** –ID, Produkt, Menge, Laufzeit
- **Bestellung** – Bestellmenge, Produkt, Zeitpunkt, Status, Kunde
- **Benachrichtigung** – Typ, Kunde, Nachricht, Zeitstempel
