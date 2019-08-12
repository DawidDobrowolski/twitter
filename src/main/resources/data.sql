INSERT INTO role(role) VALUE ('ROLE_USER'),('ROLE_ADMIN');
INSERT INTO user(email, first_name, last_name, password) VALUE ('dawidek66@gmail.com','Dawid','Dobrowolski','$2a$10$bTx70Bjnij/GRy3eY5xsGefYPrDjpCUMYBMuCFfWK0J.foqRBlI6W');
INSERT INTO user_role(user_id, role_id) VALUE (1,1),(1,2);