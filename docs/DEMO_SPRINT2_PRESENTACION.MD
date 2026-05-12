# DEMO FUNCIONAL SPRINT 2 — GESTIÓN DE TAROTISTAS Y SESIONES

## Proyecto

**ConectaTarot**

## Integrantes

* Pablo Rebolledo (Backend)
* Oscar Clavijo (Frontend)
* Matías Tiznado (Scrum Master)
* Ricardo Gaete (Product Owner)

---

# Objetivo del Sprint

Implementar el sistema completo de:

- Gestión de tarotistas
- Perfil profesional
- Especialidades
- Disponibilidad
- Agendamiento de sesiones

Historias completadas:

## HU-TAROT-01
✅ Registro de tarotista

## HU-TAROT-02
✅ Perfil profesional

## HU-TAROT-03
✅ Especialidades

## HU-SES-01
✅ Agendar sesión

Extra implementado:

✅ Confirmar sesión  
✅ Rechazar sesión  
✅ Cancelar sesión  
✅ Agenda del tarotista  
✅ Filtros por estado  
✅ Paginación  
✅ Búsqueda por especialidad  

---

# Arquitectura implementada

## Backend

* Spring Boot
* Spring Security
* JWT
* JPA / Hibernate
* MySQL
* Lombok

## Diseño

Arquitectura en capas:

Controller → Service → Repository → Database

Con:

* DTOs
* ApiResponse estándar
* Manejo global de excepciones

---

# Evidencias funcionales (Demo Narrada)

---

# 1 Registro de tarotista

Endpoint:

POST /api/tarotistas

Request:

{
 "usuarioId": 7,
 "nombreProfesional": "Tarot Nuevo"
}

Resultado esperado:

201 Created

Validación:
- Un usuario no puede tener más de un perfil

Resultado:
✅ Historia cumplida

---

# 2 Perfil profesional

Endpoint:

PUT /api/tarotistas/{id}/perfil

Request:

{
 "descripcion": "Soy tarotista profesional con experiencia en amor y trabajo",
 "precioBase": 15000
}

Validaciones:
- Solo el dueño puede editar
- Descripción mínima 20 caracteres
- Precio mayor a 0

Resultado:
✅ Historia cumplida

---

# 3 Especialidades

Agregar:

POST /api/tarotistas/{id}/especialidades

{
 "especialidadId": 2
}

Listar:

GET /api/tarotistas/{id}/especialidades

Eliminar:

DELETE /api/tarotistas/{id}/especialidades/{especialidadId}

Validaciones:
- No duplicados
- Debe existir
- Debe quedar al menos una

Resultado:
✅ Historia cumplida

---

# 4 Búsqueda de tarotistas

Todos:

GET /api/tarotistas

Por especialidad:

GET /api/tarotistas?especialidad=amor

Resultado:

{
 "nombreProfesional": "Tarot Nuevo",
 "especialidades": ["Amor", "Trabajo"]
}

Resultado:
✅ Filtro funcionando

---

# 5 Disponibilidad

Ejemplo:

INSERT INTO disponibilidad_tarotista
VALUES (2, 'SUNDAY', '17:00', '22:00', true);

Resultado:
✅ Base para validación

---

# 6 Agendar sesión

POST /api/sesiones

{
 "usuarioId": 7,
 "tarotistaId": 2,
 "especialidadId": 1,
 "fecha": "2026-05-10T17:00:00",
 "duracionMinutos": 60
}

Validaciones:
- Tarotista aprobado
- Especialidad válida
- Dentro de disponibilidad
- Sin solapamiento
- Precio automático

Resultado:
✅ Historia cumplida

---

# 7 Agenda del tarotista

GET /api/sesiones/tarotista

Con paginación:

GET /api/sesiones/tarotista?page=0&size=10

Con filtro:

GET /api/sesiones/tarotista?estado=PENDIENTE

Estados:

PENDIENTE  
CONFIRMADA  
CANCELADA  
RECHAZADA  

Resultado:
✅ Funcionalidad completa

---

# 8 Gestión de sesiones

Confirmar:

PUT /api/sesiones/{id}/confirmar

Rechazar:

PUT /api/sesiones/{id}/rechazar

Cancelar:

PUT /api/sesiones/{id}/cancelar

Resultado:
✅ Funcionalidad extendida

---

# Arquitectura aplicada

## DTOs
Separación entre entidad y respuesta

## ApiResponse

{
 "success": true,
 "message": "Operación realizada correctamente",
 "data": {}
}

## Manejo de errores

- BadRequestException
- NotFoundException
- ConflictException
- ForbiddenException

---

# Validaciones críticas

✔ Horarios válidos  
✔ No solapamiento  
✔ Seguridad por usuario  
✔ Integridad de relaciones  
✔ Reglas de negocio  

---

# Base de datos

usuario ── tarotista (1:1)  
tarotista ── especialidades (N:N)  
tarotista ── sesiones (1:N)  
tarotista ── disponibilidad (1:N)  

---

# Endpoints desarrollados

/api/tarotistas (POST)  
/api/tarotistas (GET)  
/api/tarotistas?especialidad (GET)  
/api/tarotistas/{id}/perfil (PUT)  
/api/tarotistas/{id}/especialidades (CRUD)  
/api/sesiones (POST)  
/api/sesiones/mis-sesiones (GET)  
/api/sesiones/tarotista (GET)  
/api/sesiones/{id}/confirmar (PUT)  
/api/sesiones/{id}/rechazar (PUT)  
/api/sesiones/{id}/cancelar (PUT)  

---

# Resumen técnico

✔ Gestión de tarotistas  
✔ Perfil profesional  
✔ Especialidades  
✔ Disponibilidad  
✔ Agendamiento completo  
✔ Control de estados  
✔ Filtros y paginación  
✔ Manejo de errores  
✔ API estandarizada  

---

# Estado del Sprint

100% completado

---

# Nota para el docente

Se implementó un backend completo con lógica de negocio real, validaciones complejas y arquitectura escalable.

---

## Repositorio

MA-TPY1101-Seccion001D-Grupo6

---

Fin demo.