#!/bin/bash
set -euo pipefail

cd ${0%/*}

docker stop tmp &> /dev/null || true

docker build -f Dockerfile.builder -t tmp-builder .

docker run --rm -ti \
  --name tmp \
  --network dkrnet \
  -p 8080:8080 \
  -v /home/sebastian/.m2/:/root/.m2/ \
  -w /workspace \
  tmp-builder

sleep 5

mvn compile quarkus:remote-dev -Dquarkus.live-reload.url=http://localhost:8080 -Dquarkus.live-reload.password=123
