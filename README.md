-------- DietiEstates25 --------


Stack Tecnologico

Java: 21 (Eclipse Adoptium)
Build Tool Maven 4+
GUI Framework: Java Swing
Database: PostgreSQL su AWS RDS 
Autenticazione: AWS Cognito
Containerizzazione: Docker
Testing: JUnit 5





-------- Build e Deployment --------

1. Build Locale con Maven

Dalla cartella del progetto, compilare:
mvn clean compile

Crea il JAR eseguibile
mvn clean package

Il JAR sarà in: target/DietiEstates25-jar-with-dependencies.jar

 Eseguire l'applicazione:
java -jar target/DietiEstates25-jar-with-dependencies.jar



 2. Deplyment con Docker Compose

Avvia l'applicazione (si connette a RDS)
docker-compose up -d

Verifica lo stato
docker-compose ps

Per fermare l'applicazione
docker-compose down



-------- Configurazione --------

Il file `docker-compose.yml` è già configurato con le credenziali RDS corrette:

# Database AWS RDS (già configurato)
Endpoint=database-1.cshockuiujqi.us-east-1.rds.amazonaws.com
Database=DietiEstates25DB
Porta:5432
Database: DietiEstates25DB
Username:postgres
Password:PostgresDB
Region AWS: us-east-1



-------- Testing --------

Esegui tutti i test
mvn test








-------- Licenza --------
Progetto ideato e commissionato dall'università Federico II di Napoli
In particolare nel corso di Ingegneria del Software dai professori:
Prof. Sergio Di Martino
Prof. Luigi Libero Lucio Starace


 Autori:
Manuele Borgia
Gaetano Visconti

