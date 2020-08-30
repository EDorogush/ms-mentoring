
# Getting Started

## How to start PostgreSQL

Database is installed entirely inside the application folder.
Please, follow the instructions:

### Init database file structure in current application directory with the command ([See details](https://www.postgresql.org/docs/11/app-initdb.html)):
```
initdb --locale=C -E UTF-8 postgres
```

### Start the server in foreground with the command ([See details](https://www.postgresql.org/docs/11/app-postgres.html))
```
postgres -D postgres
```

### Create database with name gamedb:
```
psql -d postgres -c 'create database gamedb'
```
 ### Create user with name gameuser:
```
psql -d postgres  -c 'create user gamedbuser'
```
### Grant privileges to gamedbuser:
```
psql -d postgres -c 'grant all privileges on database gamedb to gamedbuser'
```
## IMPORTANT: When work from Windows OS use " instead of ' .

### Create application schemas on gamedb
```
psql -d gamedb -U gamedbuser -f src/main/resources/dbscripts/schema.sql
```
### Insert dbInitData
```
psql -d gamedb -U gamedbuser -f src/main/resources/dbscripts/dbInitData.sql
```
#### You may also need following commands:
```
psql -d gamedb -U gamedbuser -c 'select * from games'
```
##### Start db in background:
```
pg_ctl -D postgres -l postgres.log start
```

##### Find out which program is running on port 5432
```
netstat -vanp tcp | grep 5432
```

##### Find out details of program by it's pid
```
ps aux | grep <pid>
```


