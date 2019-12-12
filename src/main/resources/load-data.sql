--
-- insert data

-- INSERT INTO coffees VALUES (1, 'Espresso');
-- INSERT INTO coffees VALUES (2, 'Espresso');
-- INSERT INTO coffees VALUES (3, 'Latte');

INSERT INTO coffees VALUES (nextval('coffee_seq'), 'Espresso');
INSERT INTO coffees VALUES (nextval('coffee_seq'), 'Espresso');
INSERT INTO coffees VALUES (nextval('coffee_seq'), 'Latte');
