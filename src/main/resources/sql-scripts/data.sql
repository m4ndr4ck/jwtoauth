INSERT INTO app_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO app_user (id, first_name, last_name, password, email) VALUES (1, 'Davi', 'Junior', '$2a$10$6YE6kHtYDWiXPTQYdrcW5.HHcneKlpS9r7CEgLT2s0cAlKpbpzeB.', 'davi');
INSERT INTO app_user (id, first_name, last_name, password, email) VALUES (2, 'admin', 'admin', '$2a$10$osECVkg635CdeYSkPQWNnOubYwny1zS5aAtavZH1VYeq0xoa9gRlO', 'admin');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
