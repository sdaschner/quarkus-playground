#!/bin/bash
set -euo pipefail
cd ${0%/*}

if [[ ! -f neo4j-migrations-cli-0.0.12.zip ]]; then
  wget https://repo.maven.apache.org/maven2/eu/michael-simons/neo4j/neo4j-migrations-cli/0.0.12/neo4j-migrations-cli-0.0.12.zip -O neo4j-migrations-cli-0.0.12.zip
fi
if [[ ! -d neo4j-migrations-0.0.12 ]]; then
  unzip neo4j-migrations-cli-0.0.12.zip
fi

docker build -t sdaschner/neo4j-coffee-shop-migration:v002 .
