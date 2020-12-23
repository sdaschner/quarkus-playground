CREATE CONSTRAINT ON (node:CoffeeBean) ASSERT (node.name) IS UNIQUE;
CREATE CONSTRAINT ON (node:Origin) ASSERT (node.country) IS UNIQUE;
CREATE CONSTRAINT ON (node:Flavor) ASSERT (node.name) IS UNIQUE;
