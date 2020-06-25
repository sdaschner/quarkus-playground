MATCH (f:Flavor)
SET f.name = f.description
REMOVE f.description
RETURN count(f);
