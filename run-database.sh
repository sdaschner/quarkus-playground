#!/bin/bash
set -euo pipefail

docker run -d --rm \
  --name coffee-shop-db \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15.0
