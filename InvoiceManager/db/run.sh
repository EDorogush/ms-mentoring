#!/bin/env bash
set -eu
#DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
#cd "$DIR"

DOCKER_NAME='invoice_postgres'

HOST='localhost'
PORT=5432
PASSWORD='abc123'

echo 'Starting docker in background. To kill use:'
echo "docker kill ${DOCKER_NAME}"
docker run \
--rm \
--name="${DOCKER_NAME}" \
-e POSTGRES_PASSWORD="${PASSWORD}" \
-d \
-p ${PORT_ALLDB}:5432 \
postgres


function mysqlFile {
  echo "Running file ${1}"
  docker run \
  -i \
  --rm \
  mysql:${MYSQL_VERSION} \
  mysql \
  --host="${HOST_ALLDB}" \
  --user="${USER_ALLDB}" \
  --password="${PASSWORD_ALLDB}" \
  --port=${PORT_ALLDB} \
  --verbose < "${1}"
}

function mysqlCmd {
  echo "Running cmd"
  docker run \
  --rm \
  mysql:${MYSQL_VERSION} \
  mysql \
  --host="${HOST_ALLDB}" \
  --user="${USER_ALLDB}" \
  --password="${PASSWORD_ALLDB}" \
  --port=${PORT_ALLDB} \
  --verbose \
  -e "${1}"
}

echo 'Waiting for mysql server to start.'
until
  mysqlCmd ';' ; do
  echo 'Unable to connect, waiting.'
  sleep 5
done
echo 'Connected.'

mysqlFile 'schema/ops_db.sql'
mysqlFile 'schema/authdb.sql'
mysqlFile 'schema/core_portal_service.sql'
mysqlFile 'schema/coredb.sql'
mysqlFile 'schema/rulesdb.sql'
mysqlFile 'schema/archivedb.sql'
mysqlFile 'schema/payloaddb.sql'

mysqlFile 'data/ops_db_appconfig.sql'
mysqlFile 'data/ops_db_common_service_registry.sql'
mysqlFile 'data/authdb_resource.sql'
mysqlFile 'data/authdb_role.sql'
mysqlFile 'data/authdb_scope.sql'
mysqlFile 'data/authdb_role_scope.sql'
mysqlFile 'data/authdb_users_scope.sql'
mysqlFile 'data/authdb_category.sql'
mysqlFile 'data/authdb_category_policy.sql'
mysqlFile 'data/authdb_state.sql'
mysqlFile 'data/coredb_marktype.sql'
mysqlFile 'data/coredb_payload_pool.sql'

echo 'Running some extra queries'
mysqlCmd "
USE ops_db;

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'db.jdbc.hostname';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'db.jdbc.port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'db.password';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'db.user';

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_username';

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'cps.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'cps.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'cps.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'auth' AND service_category = 'db' AND config_key = 'cps.db.jdbc_username';

UPDATE appconfig SET config_value = '${AUTH_ROOT_DIR}' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'config.root.path';
UPDATE appconfig SET config_value = '${AUTH_KEYSTORE_LOCATION}' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'keystore.location';
UPDATE appconfig SET config_value = '${AUTH_KEYSTORE_PASSWORD}' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'keystore.password';

UPDATE appconfig SET config_value = '${URL_AUTH}/auth/v2' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'auth.v2.endpoint';

UPDATE appconfig SET config_value = '${URL_CORE}/api/v1/triggers/quota' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'trigger.quota.end.point';
UPDATE appconfig SET config_value = '${URL_CORE}/api/v2/triggers/quota' WHERE service_name = 'auth' AND service_category = 'config' AND config_key = 'trigger.v2.quota.end.point';


UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'coreservices.db.jdbc_username';

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'rulesdb.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'rulesdb.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'rulesdb.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'rulesdb.db.jdbc_username';

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'archivedb.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'archivedb.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'archivedb.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'archivedb.db.jdbc_username';

UPDATE appconfig SET config_value = '${HOST_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'payloadHandler.db.jdbc_default_master_host';
UPDATE appconfig SET config_value = '${PORT_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'payloadHandler.db.jdbc_default_master_port';
UPDATE appconfig SET config_value = '${PASSWORD_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'payloadHandler.db.jdbc_passwd';
UPDATE appconfig SET config_value = '${USER_ALLDB}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'payloadHandler.db.jdbc_username';

UPDATE appconfig SET config_value = '${HOST_DYNAMO}' WHERE service_name = 'coreservices' AND service_category = 'db' AND config_key = 'reveal.trigger.dynamodb.endpoint';

UPDATE appconfig SET config_value = '${URL_SHORT}' WHERE service_name = 'coreservices' AND service_category = 'endpoints' AND config_key = 'lpp.service.shorturl.endpoint';
INSERT INTO appconfig VALUES ('coreservices','endpoints','lpp.service.shorturl.endpoint.fallback','${URL_SHORT_FALLBACK}','','# shorturl fallback end point.',1,'2019-02-11 07:32:54');
UPDATE appconfig SET config_value = '${URL_CORE}' WHERE service_name = 'coreservices' AND service_category = 'endpoints' AND config_key = 'lpp.service.self.endpoint';

UPDATE appconfig SET config_value = '${URL_AUTH}/auth/validate' WHERE service_name = 'authclient' AND service_category = 'config' AND config_key = 'authservice.endpoint';
UPDATE appconfig SET config_value = '${URL_AUTH}/auth/v1/validate' WHERE service_name = 'authclient' AND service_category = 'config' AND config_key = 'authservice.v1.endpoint';
UPDATE appconfig SET config_value = '${URL_AUTH}/auth/v2/token' WHERE service_name = 'authclient' AND service_category = 'config' AND config_key = 'authservice.v2.createtoken.endpoints';
UPDATE appconfig SET config_value = '${URL_AUTH}/auth/v2/validate' WHERE service_name = 'authclient' AND service_category = 'config' AND config_key = 'authservice.v2.endpoint';

UPDATE appconfig SET config_value = '${USER_MASTER_ID}' WHERE service_name = 'resolver-service' AND service_category = 'config' AND config_key = 'resolver.service.client.id';
UPDATE appconfig SET config_value = '${USER_MASTER_PASSWORD}' WHERE service_name = 'resolver-service' AND service_category = 'config' AND config_key = 'resolver.service.client.secret';
UPDATE appconfig SET config_value = '${URL_VALIDATION}/api/v1/validations' WHERE service_name = 'resolver-service' AND service_category = 'endpoints' AND config_key = 'validation.service.endpoint';

UPDATE appconfig SET config_value = 'local' WHERE service_name = 'common-config' AND service_category = 'config' AND config_key = 'environment';
UPDATE appconfig SET config_value = 'false' WHERE service_name = 'common' AND service_category = 'config' AND config_key = 'get.account.from.s3';

USE authdb;

INSERT INTO authdb.auth_client_details (client_id, first_name, last_name, email, access_token_expiration_value, client_name, client_secret, grant_types, scopes) VALUES ('${USER_MASTER_ID}', 'master_first_name', 'master_last_name', 'master@email.com', 360000000, 'master_name', '${USER_MASTER_PASSWORD}', 'ClientCredentials', 'default,user_service,bizdev,system_service,scan_service');
INSERT INTO authdb.account (id, name, category_id, state_id, owner_id, description, customerkey, company_prefix) VALUES ('${ACCOUNT_MASTER_ID}', 'account_name', (SELECT id from authdb.category WHERE name = 'exp.program'), (SELECT id from authdb.state WHERE name = 'Evaluating'), '${ACCOUNT_MASTER_ID}', 'account description', 'oHAI1Ul', 'NA');
INSERT INTO authdb.project (id, name, account_id, createdby, payoff_id, experience_rule_id) VALUES ('${PROJECT_MASTER_ID}', 'project name', '${ACCOUNT_MASTER_ID}', '${USER_MASTER_ID}', NULL, NULL);
INSERT INTO authdb.user_project (id, project_id, client_id, invitedby, is_default, role_id) VALUES ('user_project_id', '${PROJECT_MASTER_ID}', '${USER_MASTER_ID}', '${USER_MASTER_ID}', 1, (SELECT id from authdb.role WHERE name = 'admin'));
INSERT INTO authdb.user_account (account_id, client_id) VALUES ('${ACCOUNT_MASTER_ID}', '${USER_MASTER_ID}');

"

echo "Successfully done preparing ${DOCKER_NAME}. To kill use:"
echo "docker kill ${DOCKER_NAME}"
