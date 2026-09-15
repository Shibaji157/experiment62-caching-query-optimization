# ⚡ Experiment 6.2 — Caching & Query Optimization

A Spring Boot REST API demonstrating **Database Query Optimization, N+1 Query Handling, JOIN FETCH, Native SQL, Caching with Ehcache, and Performance Testing**.

This experiment focuses on improving backend performance by reducing unnecessary database queries and avoiding repeated database access through caching.

---

## 👨‍💻 Author

**Shibaji Biswas**

B.Tech CSE (Artificial Intelligence & Machine Learning)  
Chandigarh University

---

# 📌 Objective

The objective of this experiment is to optimize backend database operations using efficient JPA queries and caching techniques.

The application demonstrates:

- Normal JPA queries
- N+1 query behavior
- `JOIN FETCH`
- Native SQL
- Spring Cache
- Ehcache
- JCache
- `@Cacheable`
- `@CacheEvict`
- Sorting
- Performance comparison using JMeter

🔥 Cache Behavior
  Client
  │
  ▼
Cache
  │
  └── Cache Miss
          │
          ▼
       Database
          │
          ▼
       Store in Cache
          │
          ▼
        Client


🏗️ Application Architecture

                   Client
                     │
                     ▼
              REST Controller
                     │
                     ▼
                Service Layer
                     │
             ┌───────┴────────┐
             │                │
             ▼                ▼
          Cache            Repository
             │                │
             │                ▼
             │            Hibernate
             │                │
             │                ▼
             │            H2 Database
             │
             ▼
        Cached Response


📂 Project Structure

experiment62/
│
├── Frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/experiment62/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ehcache.xml
│   │
│   └── test/
│
├── pom.xml
└── README.md


⭐ If you find this repository useful, consider giving it a star!
