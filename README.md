# PaF_WS25_Fritz

# Verwaltung von produktbezogenen Kundenanfragen

Ein Kunde fragt bei einem Unternehmen eine bestimmte Menge eines Produktes an, weil er diese bestellen will. Der Kunde muss im Rahmen eines Abos, ein Kontingent erworben haben, welches die maximal zu bestellende Menge definiert. Das Unternehmen bestätigt und führt die Bestellung über die angefragte Menge aus, wenn das Produkt vorrätig ist und das Kontingent noch nicht erschöpft ist.




Für jeden Kunden wird ein festgelegtes Produktkontingent verwaltet, das kostenlos oder im Rahmen eines Abonnements bezogen werden kann.  
Das System prüft bei Eingang einer Anfrage, ob der Kunde noch ausreichendes Kontingent hat.  
Wenn ja, wird die Anfrage akzeptiert, das Kontingent reduziert, andernfalls abgelehnt und ein Warnhinweis generiert.

Alle XML-Dokumente, Prüfungsergebnisse und Statusinformationen werden in der Datenbank gespeichert,  
sodass aktuelle und historische Anfragen transparent archiviert sind.  
Somit wird der Geschäftsprozess der zentralen Produktkontingent-Steuerung und Validierung von Kundenanfragen sauber abgebildet.

## Zentrale Entitäten des Projekts

- **Kunde** – mit Attributen wie Kundennummer, Name, Kontaktdaten  
- **Produkt** – mit Produkt-ID, Name, Beschreibung  
- **Kontingent** – zugeordnet zu Kunde und Produkt, Menge  
- **Anfrage** – repräsentiert das XML-Dokument, Bestellmenge, Zeitpunkt  
- **Prüfstatus** – Status der Anfrage: akzeptiert, abgelehnt, inkl. Warnhinweisen  
- **Kommentar/Log** – Archivierung von Fehlerhinweisen oder sonstigen Benachrichtigungen


