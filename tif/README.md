
# TP Integrador Final – Programación Segura

## 🧠 Objetivo

El objetivo de este proyecto es demostrar conocimientos sobre desarrollo seguro con Java, Spring Boot y Spring Security. Incluye autenticación, autorización, JWT, encriptación, OAuth2 y despliegue en la nube.

---

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Security + JWT
- OAuth2 con GitHub
- MySQL
- HTML/CSS + JavaScript
- TailwindCSS
- Vercel (Frontend)
- Render (Backend + Base de Datos)

---

## ⚙️ Instalación y ejecución local

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/tif-programacion-segura.git
cd tif-programacion-segura/tif
```

### 2. Configurar `application.properties`

Ubicación: `src/main/resources/application.properties`

```properties
# Puerto
server.port=8080

# Configuración DB
spring.datasource.url=jdbc:mysql://localhost:3306/tif_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=una-clave-secreta-segura
jwt.expiration=3600000

# OAuth2 (GitHub)
spring.security.oauth2.client.registration.github.client-id=TU_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=TU_CLIENT_SECRET
spring.security.oauth2.client.registration.github.redirect-uri=http://localhost:8080/login/oauth2/code/github
```

## 🧱 Inicialización de la Base de Datos

Antes de iniciar la aplicación, seguí estos pasos:

### 1. Crear la base de datos

Conectate a tu servidor MySQL y ejecutá:

```sql
CREATE DATABASE IF NOT EXISTS banco_tif;

se ejecuta el backend y luego el siguiente script:

-- creamos el usuario que sera ADMIN
INSERT INTO banco_tif.users (username, password, enabled, account_not_expired, account_not_locked, credential_not_expired)
VALUES ('admin', '$2a$10$jcCYMuiVRqfUF8Sic73bo.NWHI11bJchvJfkI6kKzbw3GNDn2qOEO', 1, 1, 1, 1);


-- Si el rol ADMIN no existe:
INSERT INTO roles (role) VALUES ('ADMIN');

-- Asocia el usuario al rol (ajusta los nombres de las columnas/tablas según tu modelo)
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE username = 'admin'),
    (SELECT id FROM roles WHERE role = 'ADMIN')
);

-- se crean los permisos desde postman 
-- Asociaciones de permisos READ y CREATE al rol ADMIN
SELECT id FROM roles WHERE role = 'ADMIN';
SELECT id FROM permissions WHERE permission_name = 'CREATE';
SELECT id FROM permissions WHERE permission_name = 'READ';
SELECT id FROM permissions WHERE permission_name = 'UPDATE';
SELECT id FROM permissions WHERE permission_name = 'DELETE';

-- Insertamos las asociaciones en la tabla de enlace
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1); -- ADMIN - READ
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 2); -- ADMIN - CREATE
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 3); -- ADMIN - UPDATE
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 4); -- ADMIN - DELETE

-- Verifica la asociación
SELECT * FROM roles_permissions WHERE role_id = 1;

```


### 3. Levantar backend
```bash
./mvnw spring-boot:run
```

### 4. Levantar frontend (si es local con Vite/React)
```bash
cd frontend
npm install
npm run dev
```

---

## 🔐 Autenticación

### JWT
Los usuarios se autentican en `/auth/login` mediante:
```json
POST /auth/login
{
  "username": "usuario",
  "password": "contraseña"
}
```

### OAuth2 con GitHub

1. Crear una OAuth App en GitHub:
   - Homepage: `http://localhost:8080`
   - Callback: `http://localhost:8080/login/oauth2/code/github`

2. Colocar el `client-id` y `client-secret` en `application.properties`.

3. Acceder a: `http://localhost:8080/oauth2/authorization/github`

---

## 🚢 Despliegue

### Backend en Render

1. Crear un nuevo servicio Web en Render.
2. Elegir repo de GitHub.
3. Setear `Build Command`: `./mvnw clean install`
4. Setear `Start Command`: `java -jar target/*.jar`
5. Variables de entorno:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `JWT_SECRET`
   - `GITHUB_CLIENT_ID`
   - `GITHUB_CLIENT_SECRET`

### Base de datos en Render

1. Crear una instancia de MySQL.
2. Copiar los valores al `application.properties` o a las variables de entorno.

### Frontend en Vercel

1. Ir a [vercel.com](https://vercel.com) y conectar el repo frontend.
2. Si usás `React`, Vercel detectará y desplegará automáticamente.
3. Configurar `.env` si necesitás URL de backend:
   ```env
   VITE_API_URL=https://tu-backend.onrender.com
   ```

---

## 🧪 Testing

Incluye colección para Postman o Thunder Client ubicada en la raíz del repositorio.

📩 [Descargar colección de Postman](./postman_collection.json) para testear los endpoints.

---

## 📁 Subir a GitHub

1. Crear un repositorio en GitHub.
2. En consola:

```bash
git init
git remote add origin https://github.com/tu-usuario/tif-programacion-segura.git
git add .
git commit -m "Primera versión"
git push -u origin master
```

---

## 📎 Documentación

- Diagrama entidad-relación
- Colección de pruebas (Postman o Thunder Client)
- README.md (este archivo)
