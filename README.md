Sagrada

Software Engineering final project

Getting Started


Authors

    Fabrizio Siciliano - 843770 - 10522031
    Marco Premi - 848863 - 10526255
    Abu Hussnain Saeed - 844093 - 10522509

Testing

    La copertura dei test può essere trovata su:
    https://drive.google.com/open?id=1aWdIkRQuT_eEnhSxZKFaiHrmTw6Stmh0

Diagramma UML

    Link Drive: https://drive.google.com/open?id=1q7Fd1MZNHHJOLgrjHlmQM5CylEJaQHt8

Funzionalità implementate

    Regole Semplici
    Regole Complete
    CLI
    GUI
    RMI
    Socket
    Carte dinamiche
    Singolo giocatore

Limitazioni

    1. CLI su Windows non viene mostrata correttamente in quanto non vengono elaborati correttamente i valori unicode di dadi e colori (la nostra piattaforma di riferimento è stato Ubuntu 16.04). In questo modo la modalità di gioco con CLI su Windows è praticamente impossibile. Tuttavia Linux supporta entrambi GUI e CLI e Windows supporta la GUI.
    2. Durante l'esecuzione da file jar il server è lento nella chiusura della partita se i client si disconnettono.
    
Scelte di design

    1. La disconnessione durante una partita in singolo giocatore comporta la chiusura della partita e quindi la riconnessione non è possibile.
    2. La fine di qualsiasi partita (multi o singolo giocatore) comporta la chiusura del client, assieme a qualsiasi tipo di errore di comunicazione con il server.
    3. L'uso di carte dinamiche di dimensione diversa da quella "standard", non vengono tenute in conto durante il calcolo dei punti di ogni giocatore (valore assegnato di default: -99).
    4. Non è possibile creare carte dinamiche in cui uno degli schemi ha nome uguale ad uno schema precedentemente creato.
    5. Le carte dinamiche create vengono salvate su una cartella del sistema operativo nascosta "/home/user/.serversagrada" sul file "/home/user/.serversagrada/UserCard.xml"
    6. La partita in singolo giocatore viene effettuata sul server, necessita quindi di connessione al server.
