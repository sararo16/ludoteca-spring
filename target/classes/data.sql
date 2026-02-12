
/*Este archivo inserta datos iniciales en la  base de datos */

INSERT INTO category (name) VALUES ('Eurogames');
INSERT INTO category (name) VALUES ('Ameritrash');
INSERT INTO category (name) VALUES ('Familiar');


INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');



INSERT INTO game(title, age, author_id, category_id) VALUES ('Aventureros', '8', 1, 3);
INSERT INTO game( title, age, author_id, category_id) VALUES ('Catan', '10', 1, 1);
INSERT INTO game( title, age, author_id, category_id) VALUES ('Carcassonne', '8', 2, 1);
INSERT INTO game( title, age, author_id, category_id) VALUES ('Pandemic', '12', 2, 3);
INSERT INTO game(title, age, author_id, category_id) VALUES ('Agricola', '14', 3, 2);
INSERT INTO game(title, age, author_id, category_id) VALUES ('Terraforming Mars', '12', 3, 2);

INSERT INTO client (name) VALUES ('Luis Rodríguez');
INSERT INTO client (name) VALUES ('Marcos Vega');
INSERT INTO client (name) VALUES ('Sara Rodríguez');

-- Prestamos de ejemplo --

INSERT INTO prestamo (game_id, client_id, start_date, end_date)
VALUES (1, 1, DATE '2020-01-01', DATE '2020-01-05');

INSERT INTO prestamo (game_id, client_id, start_date, end_date)
VALUES (2, 1, DATE '2020-01-03', DATE '2020-01-10');

INSERT INTO prestamo (game_id, client_id, start_date, end_date)
VALUES (3, 2, DATE '2020-01-02', DATE '2020-01-08');

INSERT INTO prestamo (game_id, client_id, start_date, end_date)
VALUES (4, 3, DATE '2020-01-01', DATE '2020-01-06');


