# Refactoring task T1 e miglioramenti

Di seguito una descrizione riassuntiva del nostro lavoro. 
Tutte le informazioni riguardanti il refactoring del task T1 e i miglioramenti apportati al sistema complessivo si possono trovare nella 𝒅𝒐𝒄𝒖𝒎𝒆𝒏𝒕𝒂𝒛𝒊𝒐𝒏𝒆, presente nella ripository, con anche una descrizione generale del progetto e degli altri task, dei diagrammi con le parti interessate.

---

Il nostro team (composto da Lorenzo Poli matr. M63001560 - Francesco Della Valle matr. M63001500 - Michele Morgillo matr. M63001467) ha intrapreso un lavoro importante di refactoring del task T1, con l’obiettivo si  migliorare la manutenibilità, la testabilità e la scalabilità dell’applicazione, andando a lavorare sul Controller il quale, gestendo insieme sia il routing sia la logica di business, comprometteva la chiarezza del codice e la modularità dell’applicazione stessa. 
Abbiamo quindi ristrutturato il task T1 con la seguente strategia:
- **Separazione delle funzionalità**:  sono state separate dal controller le funzionalità di routing da quella della logica, che invece è stata delegata a servizi specifici.
- **Implementazione di Servizi:**: la logica di business è stata estratta in servizi dedicati, facilitando la testabilità e il riutilizzo. Così facendo le operazioni vengono affidate ai servizi, mantenendo il codice separato e focalizzato.


Oltre alla ristrutturazione del codice, sono stati introdotti diversi miglioramenti, tra cui:
- ripristino della funzionalità **reCAPTCHA** (Vedi ISSUE #27) in fase di registrazione dei Players.
- quando l'admin carica una classe (sia da sola che con i test pre-generati) è stata aggiunto un messaggio di corretto Upload Classe, seguito da un reindirizzamento automatico alla pagina dell'home admin.
