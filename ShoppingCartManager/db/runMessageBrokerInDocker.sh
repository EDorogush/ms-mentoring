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

DOCKER_NAME='rabbitmq'
HOST='docker.for.mac.localhost'
EXT_PORT=5672
PASSWORD='abc123'
HOST_INTERNAL='docker.for.mac.localhost'

echo 'Starting docker in background. To kill use:'
echo "docker kill ${DOCKER_NAME}"
docker run \
--rm \
--name="${DOCKER_NAME}" \
--hostname="${HOST}" \
-p 5672:5672 \
-p 15672:15672 \
-p 4369:4369 \
-e erlang-cookie='secret cookie here' \
-e RABBITMQ_USE_LONGNAME=true \
rabbitmq:3-management

