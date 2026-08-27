# API Gateway (LibraSys - Enterprise Cloud Architecture 📚)

**Student Name:** Seenathul Ilma  
**Student Number:** 241711005  
**Slack Handle:** Seenathul Ilma Musawwir  
**GCP Project ID:** `librasys-eca`

---

## 📖 Overview

`api-gateway` is the **single public entry point** into the LibraSys backend. Built with Spring Cloud Gateway, it dynamically routes every incoming request to the correct downstream microservice based on the path prefix, resolving the target instance through Eureka rather than a fixed address.

This is a submodule of the parent repo **[`librasys-platform`](https://github.com/Seenathul-Ilma/librasys-platform)**.

---

## 🏗️ Architecture Breakdown

The frontend (and Nginx on the backend VM) only ever talks to the gateway on port `8080` — it never calls `user-service`, `book-service`, or `loan-service` directly. This keeps the business services private and lets them scale/change ports without breaking clients.

```
   Nginx (80/443) ──▶ api-gateway (8080)
                            │
             route by path prefix, resolved via Eureka
                            │
        ┌───────────────────┼───────────────────┐
        ▼                   ▼                   ▼
  /api/users/**       /api/books/**        /api/loans/**
        │                   │                   │
   user-service        book-service        loan-service
     (8081)              (8082)              (8083)
```

Routing is defined declaratively (via `application.yaml` pulled from `config-server`), mapping each `Path=` predicate to a `lb://<service-name>` URI, which Spring Cloud LoadBalancer resolves using Eureka's live registry.

---

## 🧰 Tech Stack

| Item | Value |
|---|---|
| Framework | Spring Cloud Gateway, Java 25 |
| Discovery client | Netflix Eureka client |
| Config client | Spring Cloud Config client |
| Build tool | Maven |
| Port | `8080` |
| CORS | Enabled for the Cloud Run frontend origin |
| Process manager (prod) | PM2 (`autorestart: true`) |

---

## ⚙️ Setup & Local Run

### Build
```bash
git clone https://github.com/Seenathul-Ilma/api-gateway.git
cd api-gateway
mvn clean package -DskipTests
```

### Run
> Start `config-server` and `service-registry` first.
```bash
java -jar target/api-gateway-*.jar
```

### Verify
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/api/users     # should proxy through to user-service
```

---

## ☁️ Live Deployment

Runs as one of the 6 PM2-managed processes on the `librasys-backend-group` Managed Instance Group in project `librasys-eca`. Nginx on each VM proxies port `80`/`443` traffic to `localhost:8080`, and the whole group sits behind the Global HTTP(S) Load Balancer at `https://librasys-eca.duckdns.org`, which is what the [live frontend](https://librasys-frontend-529440660281.us-central1.run.app) calls.

---

## 🔗 Related Repositories
- Parent repo: https://github.com/Seenathul-Ilma/librasys-platform
- Business services: https://github.com/Seenathul-Ilma/librasys-services
- Frontend: https://github.com/Seenathul-Ilma/librasys-frontend