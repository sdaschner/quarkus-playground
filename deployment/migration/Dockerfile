FROM adoptopenjdk/openjdk14-openj9:x86_64-alpine-jre-14_36.1_openj9-0.19.0
RUN apk add bash

ENV \
  NEO4J_ADDRESS=bolt://localhost:7687 \
  NEO4J_PASSWORD=test

COPY neo4j-migrations-0.0.12/lib/* /lib/
COPY neo4j-migrations-0.0.12/neo4j-migrations /

COPY cyphers/* /cyphers/

CMD ["/bin/bash", "-c", "/neo4j-migrations --address $NEO4J_ADDRESS --password $NEO4J_PASSWORD --location file:///cyphers/ migrate"]
