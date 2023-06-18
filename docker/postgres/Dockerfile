FROM postgres:15.3-alpine
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB viswals
COPY ./docker/postgres/sql/ /docker-entrypoint-initdb.d/
