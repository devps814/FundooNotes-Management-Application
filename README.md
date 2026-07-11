# 📝 FundooNotes — Management Application

A full-stack **Google Keep–inspired** note-taking application built as a production-style project — with secure authentication, OTP verification, Google OAuth2 login, note collaboration, reminders, and background job processing.

Unlike a plain notes app, FundooNotes adds real-world engineering pieces on top of the core CRUD: **OTP + email-based auth flows, JWT security, Redis caching, RabbitMQ-driven reminder notifications, note sharing/collaboration, and OpenAPI documentation** — built to mirror how notes/productivity apps are architected in industry.

👤 **Author:** Dev Pratap Singh  
🔗 **GitHub:** [@devps814](https://github.com/devps814)  
📦 **Repo:** FundooNotes-Management-Application

---

## 📌 Table of Contents

- [About This Project](#-about-this-project)
- [Key Features](#-key-features)
- [Project Structure](#-project-structure)
- [Tech Stack](#-tech-stack)
- [System Architecture](#-system-architecture)
- [API Overview](#-api-overview)
- [Getting Started](#-getting-started)
- [Environment Variables](#-environment-variables)
- [Running the Application](#-running-the-application)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Roadmap](#-roadmap)
- [Tech References](#-tech-references)
- [Contributing](#-contributing)
- [Acknowledgements](#-acknowledgements)

---

## 📖 About This Project

FundooNotes is a note-management platform where users can create, organize, label, pin, archive, and set reminders on notes — similar to Google Keep — with the added ability to **collaborate on notes with other registered users** in real time (sharing access, not just viewing).

The backend is a **Spring Boot REST API** built with a layered architecture (Controller → Service → Repository), custom exception handling, and secured with **JWT-based authentication** alongside **Google OAuth2 login**. Password recovery and account verification are handled through **OTP-based email flows**, and note reminders are processed asynchronously using **RabbitMQ** with a scheduled job that checks due reminders.

The frontend (in progress) will be built with **React.js (TypeScript)**, consuming this REST API to deliver a Keep-like UI with masonry note layout, labels sidebar, and collaborator management.

> 🔔 **Status:** Backend is complete and fully functional. Frontend is under active development.

---

## ✨ Key Features

### 🔐 Authentication & Security
- Registration with **OTP email verification** before account activation
- Login with email/password (JWT-based session)
- **Google OAuth2 Sign-In** as an alternative login method
- Forgot password via **OTP** or **secure email link (token-based)**
- Resend OTP with expiry validation
- Logout with token invalidation
- Centralized exception handling (`ResourceNotFoundException`, `ConflictException`, `UnauthorizedException`, `BadRequestException`) mapped to correct HTTP status codes

### 🗒️ Notes Management
- Create, update, delete notes
- Pin / unpin notes
- Archive / unarchive notes
- Move to trash / restore
- Color-coded notes with color-based filtering
- Full-text search across notes
- Set / remove **reminders** on notes

### 🏷️ Labels
- Create, update, delete custom labels
- Attach/remove labels to/from notes
- Filter notes by label

### 🤝 Collaboration
- Add/remove collaborators (by email) on a note
- View all collaborators on a note
- View notes shared with you by others

### ⏰ Smart Reminders (Async)
- Reminder scheduler runs periodically to detect due reminders
- Due reminders are **published to RabbitMQ** and consumed asynchronously to trigger notifications — decoupling reminder detection from delivery

### 📄 Developer Experience
- **Swagger / OpenAPI** auto-generated interactive API docs
- Environment-based configuration via `.env` (never hardcoded secrets)
- Clean DTO ↔ Entity mapping via ModelMapper

---

## 🗂 Project Structure

This repository is organized as a **monorepo** — one repo, two top-level modules:

```
FundooNotes-Management-Application/
│
├── FundooNotes_Backend/        # Spring Boot REST API (complete)
│   ├── src/main/java/com/fundoonotes/fundoo_notes/
│   │   ├── config/             # Security, Redis, RabbitMQ, Swagger config
│   │   ├── controller/         # REST controllers (User, Note, Label, Collaborator)
│   │   ├── dto/                # Request/response DTOs
│   │   ├── exception/          # Custom exceptions + GlobalExceptionHandler
│   │   ├── jms/                # RabbitMQ producer/consumer + reminder scheduler
│   │   ├── mapper/              # Entity <-> DTO mappers
│   │   ├── model/               # JPA entities
│   │   ├── repository/          # Spring Data JPA repositories
│   │   ├── security/            # JWT filter, JWT util, OAuth2 handlers
│   │   └── service/              # Business logic (interfaces + impl)
│   ├── pom.xml
│   └── .env.example
│
└── FundooNotes_Frontend/        # React + TypeScript client (coming soon)
```

---

## 🛠️ Tech Stack

### Backend

| Technology | Purpose |
|---|---|
| **Java 21** | Core language |
| **Spring Boot 3.5** | Application framework |
| **Spring Web (MVC)** | REST API layer |
| **Spring Data JPA / Hibernate** | ORM & database access |
| **PostgreSQL** | Primary relational database |
| **Spring Security + JWT (JJWT)** | Authentication & authorization |
| **Spring OAuth2 Client** | Google Sign-In integration |
| **Redis (Spring Data Redis / Lettuce)** | Caching layer |
| **RabbitMQ (Spring AMQP)** | Async messaging for reminder notifications |
| **Spring Mail** | OTP & verification emails |
| **ModelMapper** | Entity ↔ DTO conversion |
| **Lombok** | Boilerplate reduction |
| **Hibernate Validator (Jakarta Validation)** | Request payload validation |
| **springdoc-openapi (Swagger UI)** | Auto-generated API documentation |
| **spring-dotenv** | `.env` based configuration management |
| **Maven** | Build & dependency management |

### Frontend *(in progress)*

| Technology | Purpose |
|---|---|
| **React.js** | UI library |
| **TypeScript** | Type-safe frontend development |
| **React Router** | Client-side routing |
| **Axios** | API communication with backend |
| **Context API / Redux Toolkit** | State management (auth state, notes state) |
| **Tailwind CSS / CSS Modules** | Styling |
| *(Alternative: Angular with TypeScript, evaluated depending on final architecture decision)* | |

### Tooling
| Tool | Purpose |
|---|---|
| IntelliJ IDEA | Backend development |
| VS Code | Frontend development |
| Postman | API testing |
| Git & GitHub | Version control |
| pgAdmin | PostgreSQL database management |

---

## 🏗 System Architecture

```
                        ┌────────────────────┐
                        │   React Frontend    │  (planned)
                        │   (TypeScript)       │
                        └──────────┬──────────┘
                                   │  REST (JSON) + JWT
                                   ▼
                        ┌────────────────────┐
                        │   Spring Boot API   │
                        │  Controller Layer   │
                        └──────────┬──────────┘
                                   │
                 ┌─────────────────┼─────────────────┐
                 ▼                 ▼                  ▼
        ┌────────────────┐ ┌──────────────┐  ┌────────────────┐
        │  Service Layer  │ │ Security(JWT/│  │ Global Exception│
        │  (Business Logic)│ │  OAuth2)     │  │    Handler      │
        └────────┬────────┘ └──────────────┘  └────────────────┘
                 │
     ┌───────────┼─────────────┬─────────────────┐
     ▼           ▼              ▼                  ▼
┌─────────┐ ┌─────────┐  ┌──────────────┐  ┌────────────────┐
│PostgreSQL│ │  Redis  │  │  RabbitMQ    │  │  Email Service  │
│ (JPA)    │ │(Caching)│  │ (Reminders)  │  │  (OTP / Verify) │
└─────────┘ └─────────┘  └──────────────┘  └────────────────┘
```

**Reminder flow:** `ReminderScheduler` (cron-based) checks notes with due reminders → publishes a message via `ReminderProducer` to RabbitMQ → `ReminderConsumer` picks it up asynchronously and triggers the notification — keeping the API responsive and decoupled from notification delivery.

---

## 🔌 API Overview

Base URL: `http://localhost:8080`

### 👤 Auth & User — `/api/users`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/register` | Register a new user, sends OTP for verification |
| POST | `/verify-otp` | Verify account using OTP |
| POST | `/resend-otp` | Resend OTP if expired/not received |
| GET | `/verify` | Verify account via email link token |
| POST | `/login` | Login with email & password → returns JWT |
| POST | `/logout` | Invalidate current session token |
| POST | `/forgot-password-otp` | Request OTP for password reset |
| POST | `/reset-password-otp` | Reset password using OTP |
| POST | `/forgot-password` | Request password reset link (email) |
| POST | `/reset-password` | Reset password using token from email link |

### 🗒️ Notes — `/api/notes`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/` | Create a new note |
| GET | `/` | Get all notes for logged-in user |
| PUT | `/{id}` | Update a note |
| DELETE | `/{id}` | Delete a note |
| PATCH | `/{id}/pin` | Toggle pin status |
| PATCH | `/{id}/archive` | Toggle archive status |
| PATCH | `/{id}/trash` | Move to / restore from trash |
| GET | `/pinned` | Get all pinned notes |
| GET | `/archived` | Get all archived notes |
| GET | `/trash` | Get all trashed notes |
| GET | `/search?keyword=` | Search notes by keyword |
| GET | `/color?color=` | Filter notes by color |
| PATCH | `/{id}/reminder` | Set a reminder on a note |
| DELETE | `/{id}/reminder` | Remove a reminder |

### 🏷️ Labels — `/api/labels`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/` | Create a label |
| GET | `/` | Get all labels |
| PUT | `/{id}` | Update a label |
| DELETE | `/{id}` | Delete a label |
| POST | `/{labelId}/notes/{noteId}` | Attach label to a note |
| DELETE | `/{labelId}/notes/{noteId}` | Remove label from a note |
| GET | `/{labelId}/notes` | Get all notes under a label |

### 🤝 Collaborators — `/api/notes/{noteId}/collaborators`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/notes/{noteId}/collaborators` | Add a collaborator to a note |
| DELETE | `/api/notes/{noteId}/collaborators/{email}` | Remove a collaborator |
| GET | `/api/notes/{noteId}/collaborators` | List collaborators on a note |
| GET | `/api/notes/shared` | Get notes shared with the logged-in user |

> All endpoints except `/register`, `/login`, `/verify`, and OAuth2 routes require a valid `Authorization: Bearer <token>` header.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- **Java 21+** (JDK)
- **Maven** (or use the included `mvnw` wrapper)
- **PostgreSQL** (running instance + a database created)
- **Redis** (running instance)
- **RabbitMQ** (running instance, default port `5672`)
- An **SMTP-enabled email account** (for sending OTPs — e.g. Gmail App Password)
- (Optional) **Google OAuth2 credentials** for Google Sign-In

### Clone the Repository

```bash
git clone https://github.com/devps814/FundooNotes-Management-Application.git
cd FundooNotes-Management-Application/FundooNotes_Backend
```

---

## 🔑 Environment Variables

Copy `.env.example` to `.env` inside `FundooNotes_Backend/` and fill in real values:

```bash
cp .env.example .env
```

```env
# ---------- SERVER ----------
SERVER_PORT=8080

# ---------- POSTGRESQL ----------
DB_URL=jdbc:postgresql://localhost:5432/fundoonotes
DB_USERNAME=
DB_PASSWORD=

# ---------- REDIS ----------
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# ---------- RABBITMQ ----------
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest

# ---------- MAIL (for OTP emails) ----------
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=
MAIL_PASSWORD=

# ---------- GOOGLE OAUTH2 ----------
GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

# ---------- JWT ----------
JWT_SECRET=
JWT_EXPIRATION=

# ---------- APP-SPECIFIC ----------
OAUTH2_REDIRECT_URI=http://localhost:8080/login/oauth2/code/google
```

⚠️ **`.env` is git-ignored and must never be committed.** Only `.env.example` (with placeholder values) is tracked in version control.

---

## ▶️ Running the Application

**Using Maven Wrapper:**

```bash
./mvnw spring-boot:run
```

**Using IntelliJ IDEA:**

1. Open the `FundooNotes_Backend` folder as a project.
2. Ensure the run configuration's **working directory** is set to `FundooNotes_Backend` (so `.env` is picked up correctly).
3. Run `FundooNotesApplication.java`.

The server will start at:

```
http://localhost:8080
```

---

## 📄 API Documentation (Swagger)

Once the app is running, interactive API docs are available at:

```
http://localhost:8080/swagger-ui/index.html
```

This lets you explore and test every endpoint directly from the browser.

---

## 🗺 Roadmap

| Feature | Status |
|---|---|
| Backend — Auth, Notes, Labels, Collaborators, Reminders | ✅ Complete |
| Swagger API Documentation + Postman Collection | ✅ Complete |
| Frontend — React + TypeScript UI | 🔄 In Progress |
| Real-time note sync (WebSockets) | 📋 Planned |
| Rich text / checklist notes | 📋 Planned |
| Docker Compose setup (Postgres + Redis + RabbitMQ + App) | 📋 Planned |
| CI/CD pipeline | 📋 Planned |

---

## 🌐 Tech References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring AMQP / RabbitMQ](https://docs.spring.io/spring-amqp/reference/index.html)
- [Spring Data Redis](https://docs.spring.io/spring-data/redis/reference/index.html)
- [JJWT (Java JWT)](https://github.com/jwtk/jjwt)
- [springdoc-openapi](https://springdoc.org/)
- [Baeldung — Spring Boot Guides](https://www.baeldung.com/spring-boot)
- [React Documentation](https://react.dev/)
- [TypeScript Documentation](https://www.typescriptlang.org/docs/)

### 🎥 Video Resources

- [**Coder Army**](https://www.youtube.com/@CoderArmy9) — Spring Boot series that helped shape the backend architecture and concepts used in this project

---

## 🤝 Contributing

1. Fork this repository 🍴
2. Create a new branch: `git checkout -b feature/your-feature`
3. Commit your changes with clear messages 📝
4. Push to your fork and submit a Pull Request 🚀

---

## 🙌 Acknowledgements

- Inspired by **Google Keep**'s note-taking UX
- Built as part of hands-on backend engineering practice — REST API design, security, async messaging, and caching
- References from official Spring documentation and the open-source community

---

⭐ If you find this project useful, consider starring the repository!

Made with ❤️ by **Dev Pratap Singh**