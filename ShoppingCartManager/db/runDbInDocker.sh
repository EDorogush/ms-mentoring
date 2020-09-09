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

DOCKER_NAME='shopping_cart_manager_postgres'
HOST='localhost'
EXT_PORT=5434
PASSWORD='abc123'
HOST_INTERNAL='docker.for.mac.localhost'

APP_DATABASE='shoppingcartdb'
APP_DATABASE_USER='shoppingcartuser'
APP_USER_PASSWORD='abc123'


echo 'Starting docker in background. To kill use:'
echo "docker kill ${DOCKER_NAME}"
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
  local USER_PASSWORD=${3}
  local FILE_NAME=${4}

  echo "running script in database ${DATABASE_NAME} by user ${USER_NAME} : ${FILE_NAME}"
  docker run \
  -i \
  --rm \
  -e PGPASSWORD="${USER_PASSWORD}" \
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
  local USER_PASSWORD=${3}
  local COMMAND=${4}
  echo "running command in database ${DATABASE_NAME} by user ${USER_NAME} : ${COMMAND}"
  docker run \
  --rm \
  -e PGPASSWORD="${USER_PASSWORD}" \
  postgres psql \
    -d "${DATABASE_NAME}" \
    -h "${HOST_INTERNAL}" \
    -U "${USER_NAME}" \
    -p ${EXT_PORT}\
    -c "${COMMAND}"
}

echo 'Waiting for postgres server to start.'
until
  postgresRunSqlCommand postgres postgres "${PASSWORD}" '\dt' ; do
  echo 'Unable to connect, waiting.'
  sleep 5
done
echo 'Connected.'


postgresRunSqlCommand postgres postgres "${PASSWORD}" "CREATE DATABASE ${APP_DATABASE}"
postgresRunScriptFile "${APP_DATABASE}" postgres "${PASSWORD}" 'dbscripts/schema.sql'

postgresRunSqlCommand postgres postgres "${PASSWORD}" "CREATE USER ${APP_DATABASE_USER} WITH ENCRYPTED PASSWORD
'${APP_USER_PASSWORD}'"
postgresRunScriptFile "${APP_DATABASE}" postgres "${PASSWORD}" 'dbscripts/grandRoles.sql'

echo 'start filling  tables...'
postgresRunScriptFile "${APP_DATABASE}" "${APP_DATABASE_USER}" "${APP_USER_PASSWORD}" 'dbscripts/dbInitData.sql'
echo 'done'
postgresRunSqlCommand "${APP_DATABASE}" "${APP_DATABASE_USER}" "${APP_USER_PASSWORD}" 'select * from shoppingcarts'



echo "Successfully done preparing ${DOCKER_NAME}. To kill use:"
echo "docker kill ${DOCKER_NAME}"
