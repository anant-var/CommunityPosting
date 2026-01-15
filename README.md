# CommunityPosting

CommunityPosting is a **community discussion and posting platform backend** built using **Spring Boot**.  
It allows users to create posts, comment on them, and interact through voting mechanisms.  
This project is designed as a **standalone microservice** that can be easily integrated into larger platforms such as healthcare, education, or social applications.

---

## 🚀 Features

- 📝 Create and view community posts  
- 💬 Comment on posts (supports nested comments)  
- 👍👎 Upvote & downvote posts  
- 🔍 Fetch posts with pagination  
- 🗃️ Persistent storage using MySQL  
- ⚙️ RESTful APIs for easy frontend integration  
- 🧩 Microservice-ready architecture  

---

## 🛠️ Tech Stack

| Layer        | Technology |
|-------------|------------|
| Language     | Java 17 |
| Framework    | Spring Boot |
| Database     | MySQL |
| ORM          | Spring Data JPA (Hibernate) |
| Build Tool   | Maven |
| API Style    | REST |

---

## 📂 Project Structure

```
CommunityPosting/
├── src/main/java/com/example/feedservice
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repo
│   ├── service
│   └── FeedServiceApplication.java
├── src/main/resources
│   └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ API Endpoints Overview

### Posts
- GET `/api/posts`
- POST `/api/posts`

### Comments
- GET `/api/posts/{postId}/comments`
- POST `/api/comments`

### Voting
- POST `/api/votes`
- GET `/api/posts/{postId}/vote-status`

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL

### Database Setup

```sql
CREATE DATABASE community_posting;
```

Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/community_posting
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Run the Application

```bash
git clone https://github.com/anant-var/CommunityPosting.git
cd CommunityPosting
mvn spring-boot:run
```

---

## 📜 License

MIT License

---

## 👨‍💻 Author

**Anant Vardhan Bartwal**  
Email: anantvardhanb@gmail.com  
GitHub: https://github.com/anant-var
