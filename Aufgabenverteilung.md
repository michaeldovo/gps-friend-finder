Jeder ist für einen Bereich verantwortlich und erstellt (prinzipiell) für diesen
  * Komponente (mit API)
    * erst STUB **mit Testdaten**
    * und dann fertige Komponente
  * Dazugehörige Screens
  * Fehlerbehandlung


# Boris #

**GPS**
  * Datenauslese
  * Dummie, der sich von alleine aufs Zielobjekt zubewegt
  * Richtungs- und Entferungsberechnung (darauf sollte nett formatiert zugegriffen werden können, damit man das nicht noch im MIDlet machen muss)
  * Testdaten

# Bernd #

**Web-Service**
  * DB-Struktur
  * Service-Implementierung auf Server (PHP) und Client (Java RMI o.ä.)
  * Schreiben / Lesen (erst in local store, dann in DB)
    * Session
    * GPS-Position
    * Nachrichten
  * Screen-Elemente für Nachricht senden / empfangen

# Christian #

**Start des Vorgangs / SMS**
  * Anfrage starten
    * spezielle SMS generieren und an PIM-Browser oder Nummer schicken
    * SMS-Request empfangen und auslesen
    * Request beantworten (über Web-Service)
  * Screengrafik (besonders Pfeil)
  * Engineering (Dokumente ala diesem 