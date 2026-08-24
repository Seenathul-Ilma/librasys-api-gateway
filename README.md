# API Gateway — Spring Cloud Gateway

**Student Name:** Seenathul Ilma  
**Student Number:** 241711005  
**Slack Handle:** Seenathul Ilma Musawwir  
**GCP Project ID:** librasys-eca 

---

## 📖 Description
The `api-gateway` serves as the single entry point for all frontend client traffic, routing incoming requests to registered microservice instances (`user-service`, `book-service`, `loan-service`) via Eureka client-side load balancing.

## 🛠️ Technology Stack
- **Java 25**
- **Spring Boot 4.1.1**
- **Spring Cloud Gateway (WebFlux)**
- **Eureka Discovery Client**

## 🚀 Getting Started
Runs on port `8080`.

```bash
./mvnw clean package -DskipTests
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```
