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
