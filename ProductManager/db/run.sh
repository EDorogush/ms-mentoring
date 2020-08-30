#!/bin/bash
# -e  errexit Exit immediately if a command exits with a non-zero status.
# -u  nounset Treat unset variables as an error when substituting.
# -pipefail the return value of a pipeline is the status of
#                             the last command to exit with a non-zero status,
#                             or zero if no command exited with a non-zero status
set -o errexit
set -o nounset
set -o pipefail
clear
echo 'start'
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "work in directory: ${DIR}"
cd "$DIR"

DOCKER_NAME='product_manager_postgres'
HOST='localhost'
EXT_PORT=5433
PASSWORD='abc123'
HOST_INTERNAL='docker.for.mac.localhost'

echo 'Starting docker in background. To kill use:'
echo "docker kill ${DOCKER_NAME}"
# docker kill product_manager_postgres
docker run \
--rm \
--name="${DOCKER_NAME}" \
-e POSTGRES_PASSWORD="${PASSWORD}" \
-d \
-p ${EXT_PORT}:5432 \
postgres

function postgresRunScriptFile() {
  local DATABASE_NAME=${1}
  local USER_NAME=${2}
  local FILE_NAME=${3}
  echo "running script in database ${DATABASE_NAME} by user ${USER_NAME} : ${FILE_NAME}"
  docker run \
  -i \
  --rm \
  -e PGPASSWORD="${PASSWORD}" \
  postgres psql \
    -d "${DATABASE_NAME}" \
    -h "${HOST_INTERNAL}" \
    -U "${USER_NAME}" \
    -p ${EXT_PORT} \
    < "${FILE_NAME}"
}

function postgresRunSqlCommand {
  local DATABASE_NAME=${1}
  local USER_NAME=${2}
  local COMMAND=${3}
  echo "running command in database ${DATABASE_NAME} by user ${USER_NAME} : ${COMMAND}"
  docker run \
  --rm \
  -e PGPASSWORD="${PASSWORD}" \
  postgres psql \
    -d "${DATABASE_NAME}" \
    -h "${HOST_INTERNAL}" \
    -U "${USER_NAME}" \
    -p ${EXT_PORT}\
    -c "${COMMAND}"
}

echo 'Waiting for postgres server to start.'
until
  postgresRunSqlCommand postgres postgres '\dt' ; do
  echo 'Unable to connect, waiting.'
  sleep 5
done
echo 'Connected.'


postgresRunSqlCommand postgres postgres 'CREATE DATABASE product_manager_db'
postgresRunScriptFile product_manager_db postgres 'dbscripts/schema.sql'

postgresRunSqlCommand postgres postgres "CREATE USER product_manager_user WITH ENCRYPTED PASSWORD '${PASSWORD}'"
postgresRunScriptFile product_manager_db postgres 'dbscripts/grandRoles.sql'

echo 'start filling  tables...'
postgresRunScriptFile product_manager_db product_manager_user 'dbscripts/dbInitData.sql'
echo 'done'
postgresRunSqlCommand product_manager_db product_manager_user 'select * from purchase_history'
postgresRunSqlCommand product_manager_db product_manager_user 'select * from user_games'
postgresRunSqlCommand product_manager_db product_manager_user 'select * from games'


echo "Successfully done preparing ${DOCKER_NAME}. To kill use:"
echo "docker kill ${DOCKER_NAME}"
