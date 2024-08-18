# SMS - Stock Management System

## Docker/Database
to start the database run `docker compose up` in the directory of your `docker-compose.yml`.
start the database before you run the jar
to stop the database run `docker compose stop`

## Endpoints

Mapping: `/sms/api/{endpoint}`

Container:
- `/health`: returns 'alive' when alive
- get `/container?secret={secret}&id={id}`: returns ContainerItem object with requested id
- post `/container?secret={secret}` with body of type {`data`: data, `user`: user, `id`: id }, stores container and returns container
- delete `/container?secret={secret}&id={id}`: returns "success" or error

Group:
- post `/createGroup?secret={secret}&name={name}`: returns succuess or error message
- post `/addToGroup?secret={secret}&name={name}&id={id}`: returns success or error message
- get `/getGroup?secret={secret}&name={name}`: returns list of containers
- delete `/deleteGroup?secret={secret}&name{name}`: returns sucess of error message
- get `/groups?secret={secret}`: returns list of groups
- post `/renameGroup?secret={secret}&name={name}&renameTo={renameString}`: returns success or error message
