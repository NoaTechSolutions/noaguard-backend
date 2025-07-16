# NoaGuard Backend

> 🇺🇸 Backend API for NoaGuard – a secure, multi-role daycare management platform.  
> 🇪🇸 API backend de NoaGuard – una plataforma segura de gestión de guarderías con múltiples roles.

---

## 🌐 Tech Stack / Tecnologías

- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Maven

---

## 🚀 Features / Funcionalidades

- ✅ User registration & login with JWT
- ✅ Role-based access (DAYCARE_ADMIN, TEACHER, PARENT, etc.)
- ✅ Secure endpoints with Spring Security
- ✅ User CRUD with pagination, filtering & toggle activation
- ✅ Ready for multi-tenancy architecture

---

## ⚙️ Installation / Instalación

### Prerequisites / Requisitos:
- Java 17
- PostgreSQL
- Maven

### Steps / Pasos:

```bash
# Clone the repo / Clona el repositorio
git clone https://github.com/YOUR-USERNAME/noaguard-backend.git
cd noaguard-backend

# Create .env or set environment variables / Crea un archivo .env o configura variables
# Example:
# app.jwt.secret=your_super_secret_key
# app.jwt.expiration=3600000

# Run the app / Ejecuta la aplicación
./mvnw spring-boot:run
```

---

## 🧪 API Endpoints

Authentication:
- `POST /api/auth/login`
- `POST /api/auth/register`

User Management:
- `GET /api/users`
- `POST /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `PATCH /api/users/{id}/toggle-active`
- `DELETE /api/users/{id}`
- `GET /api/users/search?search=&role=&page=0&size=10`

---

## 🛡️ Security & Roles / Seguridad y Roles

- JWT Authentication
- Secure routes based on roles (e.g., only `DAYCARE_ADMIN` can manage users)
- Role injection in token for UI logic

---

## 📁 Folder Structure / Estructura de Carpetas

```
src/
├── config/             # Spring Security config
├── controller/         # REST Controllers
├── dto/                # Data Transfer Objects
├── entity/             # JPA Entities
├── enums/              # RoleType, etc.
├── repository/         # Spring Data Repositories
├── service/            # Business logic
├── security/jwt/       # JWT utils and filters
└── NoaguardApplication.java
```

---

## 👤 Author / Autor

Israel Esparza  
[noatechsolutions.com](https://noatechsolutions.com/)

---

## 📄 License

MIT License
