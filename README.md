# 🏋️ Sportify

Sportify è un progetto sviluppato per i corsi di **Ingegneria del Software** e **Progettazione Web** (A.A. 2022/2023).
L’obiettivo è incoraggiare le persone a praticare sport, aiutandole a scegliere quello più adatto alle proprie esigenze e la palestra migliore dove allenarsi.

Il sistema è pensato sia per **utenti** che cercano sport e palestre, sia per **palestre** che vogliono pubblicizzare corsi e raccogliere feedback.

---

## 🚀 Funzionalità principali

### Per gli utenti

* 🔍 Cercare sport e palestre
* 📝 Compilare un quiz per scoprire lo sport più adatto
* 📍 Trovare la palestra più vicina
* ✍️ Scrivere recensioni su palestre e corsi
* 📊 Tenere traccia del proprio livello nei vari sport

### Per le palestre

* ➕ Aggiungere corsi e orari
* 👀 Leggere recensioni ricevute
* 🗑️ Eliminare recensioni non costruttive
* 📢 Sponsorizzare la propria attività

---

## 🛠️ Tecnologie utilizzate

* **Java 17** con **JavaFX** (GUI e controller grafici)
* **FXML + Scene Builder** (interfacce utente)
* **Maven** (gestione del progetto e dipendenze)
* **StarUML** (diagrammi UML)
* **Figma** (storyboard HTML/CSS)
* **JUnit + Selenium** (testing unitario e GUI/API)
* **SonarCloud** (analisi qualità del codice)
* **DAO Pattern** per la gestione del database

---

## 📂 Struttura del progetto

* `src/main/java/it/uniroma2/dicii/ispw/sportify/` → codice sorgente Java
* `src/test/java/it/uniroma2/dicii/ispw/sportify/` → test JUnit e Selenium
* `Storyboard/` → prototipi HTML/CSS delle schermate
* `DB/` → script e classi DAO per la gestione del database
* `Document/Documentation/` → documentazione UML e video dimostrativi

---

## ▶️ Avvio del progetto

1. Clona il repository:

   ```bash
   git clone https://github.com/Chiara183/Sportify.git
   ```
2. Apri la cartella `Sportify` come progetto **Maven** in IntelliJ IDEA.
   Il file `pom.xml` contiene tutte le configurazioni necessarie.
3. Avvia l’applicazione tramite:

   ```
   src/main/java/it/uniroma2/dicii/ispw/sportify/MainAppLauncher.java
   ```

---

## ✅ Testing

Il progetto include:

* Test JUnit per schermate e logica (`HomePhoneTest`, `LoginTest`, `MenuTest`, ecc.)
* Test GUI con Selenium
* Test API con Selenium

---

## 📊 Analisi del codice

* \~6.7K LOC
* Nessun bug o vulnerabilità rilevata da **SonarCloud**
  🔗 [Report SonarCloud](https://sonarcloud.io/summary/new_code?id=Chiara183_Sportify2)

---

## 🎥 Demo

Sono disponibili video dimostrativi delle interfacce grafiche nella cartella:
[`Document/Documentation/Video`](https://github.com/Chiara183/Sportify/tree/main/Document/Documentation/Video)

---

## 👩‍💻 Autori

* **Chiara D’Ambrogio** – Progettazione Web
* **Matteo La Gioia** – Ingegneria del Software
