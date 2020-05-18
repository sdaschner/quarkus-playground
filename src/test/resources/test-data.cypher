MATCH (n)
DETACH DELETE n;

// flavors
UNWIND [
  {flavor: 'NUTTY'},
  {flavor: 'SWEET'},
  {flavor: 'CARAMEL'},
  {flavor: 'CHOCOLATE'},
  {flavor: 'FRUIT'},
  {flavor: 'FLORAL'},
  {flavor: 'SOUR'},
  {flavor: 'GREEN'},
  {flavor: 'OTHER'},
  {flavor: 'ROASTED'},
  {flavor: 'SPICES'}
] AS row
CREATE (:Flavor {description: row.flavor});


// countries
UNWIND [
  {country: 'Colombia', knownForFlavors: ['FRUIT', 'FLORAL']},
  {country: 'Ethiopia', knownForFlavors: ['FRUIT', 'SWEET']},
  {country: 'Kenya', knownForFlavors: ['CHOCOLATE', 'CARAMEL']},
  {country: 'Brazil', knownForFlavors: ['CHOCOLATE', 'NUTTY']}
] AS row
CREATE (o:Origin {country: row.country})
WITH o, row
UNWIND row.knownForFlavors AS flavor
MATCH (f:Flavor {description: flavor})
MERGE (o)-[:IS_KNOWN_FOR]->(f)
;

// beans
UNWIND [
  {name: 'Buna', roast: 'LIGHT', country: 'Ethiopia', profile: [
    {percentage: 0.4, flavor: 'FRUIT'},
    {percentage: 0.3, flavor: 'SWEET'},
    {percentage: 0.3, flavor: 'CARAMEL'}
  ]},
  {name: 'El gato loco', roast: 'LIGHT', country: 'Colombia', profile: [
    {percentage: 0.7, flavor: 'FRUIT'},
    {percentage: 0.3, flavor: 'FLORAL'}
  ]},
  {name: 'Saboroso', roast: 'MEDIUM', country: 'Brazil', profile: [
    {percentage: 0.5, flavor: 'CHOCOLATE'},
    {percentage: 0.3, flavor: 'NUTTY'},
    {percentage: 0.2, flavor: 'CARAMEL'}
  ]},
  {name: 'Kahawa Nzuri', roast: 'MEDIUM', country: 'Kenya', profile: [
    {percentage: 0.5, flavor: 'CHOCOLATE'},
    {percentage: 0.5, flavor: 'CARAMEL'}
  ]}
] AS row
CREATE (b:CoffeeBean {name: row.name, roast: row.roast})
WITH b, row
MATCH (c:Origin {country: row.country})
MERGE (b)-[:IS_FROM]->(c)
WITH b, row
UNWIND row.profile AS profile
MATCH (f:Flavor {description: profile.flavor})
MERGE (b)-[:TASTES {percentage: profile.percentage}]->(f)
;
