MATCH (f:Flavor)
SET f.name = f.description
RETURN count(f);
