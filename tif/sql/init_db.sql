
-- Crear base de datos (opcional)
CREATE DATABASE IF NOT EXISTS banco_tif;
USE banco_tif;

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
