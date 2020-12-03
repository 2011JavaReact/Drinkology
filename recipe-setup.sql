DROP TABLE IF EXISTS liquor CASCADE;
CREATE TABLE liquor (
	id SERIAL PRIMARY KEY,
	liquor_name VARCHAR(255) NOT NULL
);

INSERT INTO liquor
(liquor_name)
VALUES
('(none)'),
('Tequila'),
('Vodka'),
('Whiskey'),
('Gin'),
('Sweet Vermouth'),
('Dry Vermouth'),
('Negroni'),
('Triple Sec'),
('Champagne'),
('Bitters'),
('Rum');

DROP TABLE IF EXISTS mixer CASCADE;
CREATE TABLE mixer (
	id SERIAL PRIMARY KEY,
	mixer_name VARCHAR(255) NOT NULL
);

INSERT INTO mixer
(mixer_name)
VALUES
('(none)'),
('Lime Juice'),
('Lemon Juice'),
('Orange Juice'),
('Peach Puree'),
('Coke'),
('Cranberry Juice'),
('Pineapple Juice');

DROP TABLE IF EXISTS sweetener CASCADE;
CREATE TABLE sweetener (
	id SERIAL PRIMARY KEY,
	sweetener_name VARCHAR(255) NOT NULL
);

INSERT INTO sweetener
(sweetener_name)
VALUES
('(none)'),
('Simple Syrup'),
('Sugar Cube'),
('Cranberry Juice'),
('Coconut Cream');

DROP TABLE IF EXISTS garnish CASCADE;
CREATE TABLE garnish (
	id SERIAL PRIMARY KEY,
	garnish_name VARCHAR(255) NOT NULL
);

INSERT INTO garnish
(garnish_name)
VALUES
('(none)'),
('Lime Wheel'),
('Orange Wheel'),
('Orange Peel'),
('Pineapple Slice'),
('Peach Slice'),
('Maraschino Cherry');

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (
	id SERIAL PRIMARY KEY,
	role_name VARCHAR(255) NOT NULL
);

INSERT INTO roles
(role_name)
VALUES
('admin'),
('user');

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	user_name VARCHAR(255) NOT NULL,
	user_email VARCHAR(255) NOT NULL,
	user_password VARCHAR(255) NOT NULL,
	user_role_id INTEGER REFERENCES roles(id)
);

INSERT INTO users
(user_name, user_email, user_password, user_role_id)
VALUES
('gera', 'gera@gera.com', '1234', 1);

DROP TABLE IF EXISTS recipe;
CREATE TABLE recipe (
	recipe_id SERIAL PRIMARY KEY,
	recipe_name VARCHAR(255) NOT NULL,
	liquor1_id INTEGER REFERENCES liquor(id),
	liquor2_id INTEGER REFERENCES liquor(id),
	mixer_id INTEGER REFERENCES mixer(id),
	sweetener_id INTEGER REFERENCES sweetener(id),
	garnish_id INTEGER REFERENCES garnish(id),
	owner_id INTEGER REFERENCES users(id)
);

INSERT INTO recipe
(recipe_name, liquor1_id, liquor2_id, mixer_id, sweetener_id, garnish_id, owner_id)
VALUES
('Margarita', 2, 9, 2, 2, 2, 1),
('Old Fashioned', 4, 11, 1, 3, 4, 1),
('Cosmopolitan', 3, 1, 2, 4, 2, 1),
('Bellini', 10, 1, 5, 2, 6, 1);