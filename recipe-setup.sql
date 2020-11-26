DROP TABLE liquor CASCADE;
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

DROP TABLE mixer CASCADE;
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

DROP TABLE sweetener CASCADE;
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

DROP TABLE garnish CASCADE;
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

DROP TABLE recipe;
CREATE TABLE recipe (
	recipe_id SERIAL PRIMARY KEY,
	recipe_name VARCHAR(255) NOT NULL,
	liquor1_id INTEGER REFERENCES liquor(id),
	liquor2_id INTEGER REFERENCES liquor(id),
	mixer_id INTEGER REFERENCES mixer(id),
	sweetener_id INTEGER REFERENCES sweetener(id),
	garnish_id INTEGER REFERENCES garnish(id)
);

INSERT INTO recipe
(recipe_name, liquor1_id, liquor2_id, mixer_id, sweetener_id, garnish_id)
VALUES
('Margarita', 2, 9, 2, 2, 2),
('Old Fashioned', 4, 11, 1, 3, 4),
('Cosmopolitan', 3, 1, 2, 4, 2),
('Bellini', 10, 1, 5, 2, 6);

SELECT *
FROM recipe r
	INNER JOIN liquor l
	ON r.liquor1_id = l.id
	INNER JOIN liquor l2
	ON r.liquor2_id = l2.id
	INNER JOIN mixer m
	ON r.mixer_id = m.id
	INNER JOIN sweetener s
	ON r.sweetener_id = s.id
	INNER JOIN garnish g
	ON r.garnish_id = g.id;