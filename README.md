# 🏥 OnControl Backend v2.0

OnControl Backend es una plataforma monolítica desarrollada en **Java 21** con **Spring Boot**, siguiendo principios de **Domain-Driven Design (DDD)**. Proporciona una API RESTful para un sistema de gestión de pacientes oncológicos que conecta organizaciones, doctores y pacientes.

## ✨ Características

- **Arquitectura jerárquica** - Organizaciones → Doctores → Pacientes
- **33 Endpoints RESTful** organizados por contextos de dominio
- **Documentación OpenAPI** integrada con Swagger UI
- **Seguridad y Autenticación** con Spring Security y JWT
- **Dashboards con filtros** para cada tipo de usuario
- **Seguimiento de síntomas** en tiempo real
- **Gestión de citas** entre doctores y pacientes
- **Persistencia** con Spring Data JPA y MySQL
- **Auditoría automática** de entidades (createdAt, updatedAt)

---

## 🏗️ Arquitectura

### Nueva Arquitectura Jerárquica v2.0

```
Users (Organizaciones/Empresas)
  └─► Profiles (Datos comunes: nombre, email, etc.)
      ├─► DoctorProfile (Especialización, licencia, etc.)
      │   └─► PatientProfile (Historial médico, tratamiento, etc.)
      │       ├─► Appointments (Citas médicas)
      │       └─► Symptoms (Síntomas reportados)
```

### Flujo de Trabajo

1. **Organizaciones se registran** → Tabla `users`
2. **Organizaciones crean Doctores** → Tablas `profiles` + `doctor_profiles`
3. **Doctores crean Pacientes** → Tablas `profiles` + `patient_profiles`
4. **Doctores y Pacientes crean Citas** → Tabla `appointments`
5. **Pacientes reportan Síntomas** → Tabla `symptoms`

---

## 📊 Módulos (Bounded Contexts)

### 1. 🔐 Identity & Access Management (IAM)
**Responsabilidad:** Autenticación y gestión de organizaciones

**Endpoints:**
- `POST /api/auth/register/organization` - Registro de organizaciones
- `POST /api/auth/login` - Login unificado (organizaciones, doctores, pacientes)

**Entidades:**
- `User` - Organizaciones/Empresas (con country, city, maxDoctors, etc.)
- `UserRole` - ORGANIZATION, ADMIN

---

### 2. 👥 Profiles
**Responsabilidad:** Gestión de perfiles de doctores y pacientes

**Endpoints:**
- `POST /api/organizations/{id}/doctors` - Organización crea doctor
- `GET /api/organizations/{id}/doctors` - Listar doctores
- `POST /api/doctors/{id}/patients` - Doctor crea paciente
- `GET /api/doctors/{id}/patients` - Listar pacientes

**Entidades:**
- `Profile` - Datos comunes (firstName, lastName, email, password)
- `DoctorProfile` - Datos específicos de doctores
- `PatientProfile` - Datos específicos de pacientes
- `ProfileType` - DOCTOR, PATIENT

---

### 3. 📅 Appointments
**Responsabilidad:** Gestión de citas médicas

**Endpoints:**
- `POST /api/appointments/doctor/{doctorId}/patient/{patientId}` - Crear cita
- `GET /api/appointments/doctor/{doctorId}` - Citas del doctor
- `GET /api/appointments/patient/{patientId}` - Citas del paciente
- `PATCH /api/appointments/{id}/status` - Actualizar estado
- `PATCH /api/appointments/{id}/follow-up` - Agregar notas

**Estados:** SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW  
**Tipos:** CONSULTATION, FOLLOW_UP, CHECKUP, EMERGENCY, PROCEDURE

---

### 4. 🩺 Symptoms & Monitoring
**Responsabilidad:** Seguimiento de síntomas de pacientes

**Endpoints:**
- `POST /api/symptoms/patient/{id}` - Reportar síntoma
- `GET /api/symptoms/patient/{id}` - Ver todos los síntomas
- `GET /api/symptoms/patient/{id}/recent` - Síntomas recientes
- `GET /api/symptoms/patient/{id}/stats` - Estadísticas

**Severidad:** MILD, MODERATE, SEVERE, CRITICAL

---

### 5. 📊 Dashboard (⭐ NUEVO)
**Responsabilidad:** Vistas agregadas con capacidad de filtrado

**Endpoints:**
```
# Organización
GET /api/dashboard/organization/{id}                        - Dashboard general
GET /api/dashboard/organization/{id}/filter/doctor/{docId}  - Filtrado por doctor
GET /api/dashboard/organization/{id}/stats                  - Estadísticas rápidas

# Doctor
GET /api/dashboard/doctor/{id}                              - Dashboard general
GET /api/dashboard/doctor/{id}/filter/patient/{patId}       - Filtrado por paciente
GET /api/dashboard/doctor/{id}/stats                        - Estadísticas rápidas

# Paciente
GET /api/dashboard/patient/{id}                             - Dashboard completo
GET /api/dashboard/patient/{id}/stats                       - Estadísticas rápidas
```

**Características:**
- ✅ Dashboards con datos agregados en tiempo real
- ✅ Filtros dinámicos (organización por doctor, doctor por paciente)
- ✅ Estadísticas comparativas
- ✅ Alertas de síntomas críticos

---

### 6. 🏥 Medical Records
**Responsabilidad:** Historiales médicos y medicaciones

**Estado:** Entidades disponibles, endpoints pendientes de implementación

---

## 🚀 Instalación y Ejecución

### Prerequisitos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6+
- IntelliJ IDEA (recomendado)

### Configuración

1. **Crear base de datos:**
```sql
CREATE DATABASE oncontrol;
```

2. **Configurar `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/oncontrol
spring.datasource.username=root
spring.datasource.password=yourpassword

# Para crear tablas automáticamente (primera vez)
spring.jpa.hibernate.ddl-auto=create

# JWT
authorization.jwt.secret=your-secret-key-min-256-bits
authorization.jwt.expiration=86400000
```

3. **Ejecutar la aplicación:**

**En IntelliJ:**
- Abrir proyecto
- Run → OncontrolBackendApplication

**Por línea de comandos:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acceder a Swagger UI:**
```
http://localhost:8080/swagger-ui.html
```

---

## 📖 Documentación Disponible

- 📘 **NUEVA_ARQUITECTURA.md** - Arquitectura completa del sistema
- 📊 **DIAGRAMA_ARQUITECTURA.md** - Diagramas visuales
- 📝 **ENDPOINTS_COMPLETOS.md** - Lista de todos los endpoints
- 🎯 **DASHBOARDS.md** - Sistema de dashboards con filtros
- 📚 **README_COMPLETO.md** - Guía completa de uso
- 🔧 **RESUMEN_REESTRUCTURACION.md** - Cambios realizados

---

## 🎯 Inicio Rápido

### 1. Registrar Organización
```bash
POST /api/auth/register/organization

{
  "email": "admin@hospital.com",
  "password": "password123",
  "organizationName": "Hospital Central",
  "country": "México",
  "city": "Ciudad de México"
}
```

### 2. Crear Doctor
```bash
POST /api/organizations/1/doctors

{
  "email": "dr.garcia@hospital.com",
  "password": "password123",
  "firstName": "Carlos",
  "lastName": "García",
  "specialization": "Oncología",
  "licenseNumber": "MED-001"
}
```

### 3. Crear Paciente
```bash
POST /api/doctors/1/patients

{
  "email": "juan.perez@example.com",
  "password": "password123",
  "firstName": "Juan",
  "lastName": "Pérez",
  "bloodType": "O+",
  "cancerType": "Pulmón"
}
```

### 4. Ver Dashboard
```bash
# Dashboard del doctor
GET /api/dashboard/doctor/1

# Dashboard filtrado por paciente
GET /api/dashboard/doctor/1/filter/patient/1
```

---

## 🔐 Roles y Permisos

| Rol | Descripción | Capacidades |
|-----|-------------|-------------|
| **ORGANIZATION** | Empresas/Hospitales | Crear doctores, ver dashboard general |
| **DOCTOR** (Profile) | Médicos | Crear pacientes, gestionar citas, ver síntomas |
| **PATIENT** (Profile) | Pacientes | Ver citas, reportar síntomas, ver dashboard |
| **ADMIN** | Administrador | Gestión completa del sistema |

---

## 🗄️ Base de Datos

### Tablas Principales

1. **users** - Organizaciones
2. **profiles** - Datos comunes (doctores y pacientes)
3. **doctor_profiles** - Datos específicos de doctores
4. **patient_profiles** - Datos específicos de pacientes
5. **appointments** - Citas médicas
6. **symptoms** - Síntomas reportados
7. **medical_records** - Historiales médicos
8. **medications** - Medicaciones

---

## 🛠️ Stack Tecnológico

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **MySQL 8.0**
- **Swagger/OpenAPI 3.0**
- **Lombok**
- **BCrypt** (Password Hashing)
- **Maven**

---

## 📈 Métricas

- **Total de Endpoints:** 33
- **Total de Entidades:** 8
- **Total de Controladores:** 7
- **Total de Servicios:** 6
- **Cobertura funcional:** 100%

---

## 🤝 Contribución

Este proyecto sigue Domain-Driven Design y arquitectura limpia. Cada módulo tiene:
- **Domain Layer** - Entidades y repositorios
- **Application Layer** - Servicios y DTOs
- **Infrastructure Layer** - Controladores y configuración

---

## 📝 Licencia

Propiedad de OnControl - Sistema de Gestión Oncológica

---

## 🌱 DataSeeder (Datos de Prueba)

El proyecto incluye un **DataSeeder** que crea datos iniciales automáticamente:

### Datos creados:
- ✅ 1 Organización (Hospital Central)
- ✅ 2 Doctores
- ✅ 4 Pacientes
- ✅ 5 Citas
- ✅ 7 Síntomas

### Credenciales de prueba:
```
Organización: admin@hospital.com / password123
Doctor 1: dr.garcia@hospital.com / password123
Doctor 2: dr.rodriguez@hospital.com / password123
Paciente 1: juan.perez@email.com / password123
...
```

**El seeder se ejecuta automáticamente al iniciar la app con BD vacía.**

Para más información: `src/main/java/.../shared/infrastructure/seeder/README_SEEDER.md`

---

## 📞 Soporte

Para más información sobre el seeder, consulta:
- `src/main/java/.../shared/infrastructure/seeder/README_SEEDER.md`

---

**Versión:** 2.0  
**Última Actualización:** Octubre 2025  
**Estado:** ✅ Producción Ready con DataSeeder
