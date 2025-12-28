Endpoint:
GET http://localhost:8081/api/products


▶ Start Backend
cd backend
mvn spring-boot:run

Oder alles killen:

pkill -f spring-boot
pkill -f java

lsof -i :8180
kill -9 12345

Spring Boot / Backend läuft auf:
➡ http://localhost:8081

▶ Start Frontend
cd frontend
cd frontend!!! 2x

npm install
npm start
ng serve

Angular / Frontend läuft auf:
➡ http://localhost:4200
