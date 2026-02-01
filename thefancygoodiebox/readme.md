
Wichtig:

rm -rf node_modules package-lock.json
npm install --legacy-peer-deps
npx ng serve

Endpoint:
GET http://localhost:8081/api/products

// --------
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

npm install
npm start
ng serve

Angular / Frontend läuft auf:
➡ http://localhost:4200

http://localhost:8081/api/subscriptions

http://localhost:8081/h2-console/login.jsp?jsessionid=951dc879030aaf70f2167cf9d88a1577


    'student': 'password123',  // Test-User 1
    'admin': 'admin',          // Admin-Zugang
    'testuser': 'abc'          // Dritter Test-User

http://localhost:8081/cart/items?username=student

--------

Paket	Version
@angular/core	17.3.12
@angular/common	17.3.12
@angular/compiler	17.3.12
@angular/platform-browser	17.3.12
@angular/platform-browser-dynamic	17.3.12
@angular/forms	17.3.12
@angular/compiler-cli	17.3.12
@angular/cli	17.3.12
@angular-devkit/build-angular	17.3.17
typescript	5.4.x
tslib	2.6.x
zone.js	0.14.x

Paket	Version
@angular/core	17.3.12
@angular/common	17.3.12
@angular/compiler	17.3.12
@angular/platform-browser	17.3.12
@angular/platform-browser-dynamic	17.3.12
@angular/forms	17.3.12
@angular/compiler-cli	17.3.12
@angular/cli	17.3.12
@angular-devkit/build-angular	17.3.17
typescript	5.4.x
tslib	2.6.x
zone.js	0.14.x

Node und NPM
Node: 20.19.5
NPM: 10.8.2

Polyfills & Zone.js
Datei src/polyfills.ts muss existieren, auch wenn leer.
zone.js muss installiert sein (npm install zone.js@0.14 --save)

