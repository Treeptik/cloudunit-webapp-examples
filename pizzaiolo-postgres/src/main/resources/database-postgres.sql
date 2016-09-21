CREATE  TABLE if not exists pizza (
  id serial PRIMARY KEY,
  name VARCHAR(45) ,
  price integer);

INSERT INTO pizza(id, name, price) 
SELECT '1', 'Reine', '7' WHERE NOT EXISTS (SELECT 1 FROM pizza WHERE id=1);
INSERT INTO pizza (id, name, price) 
SELECT '2', 'Royale', '6' WHERE NOT EXISTS (SELECT 1 FROM pizza WHERE id=2);
INSERT INTO pizza (id, name, price) 
SELECT '3', 'Pepperoni', '6' WHERE NOT EXISTS (SELECT 1 FROM pizza WHERE id=3);
