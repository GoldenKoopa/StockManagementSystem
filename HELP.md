# SMS - Stock Management System

## Endpoints

Mapping: `/sms/api/{endpoint}`

- `/health`: returns 'alive' when alive
- get `/container?secret={secret}&server={server}&id={id}`: returns ContainerItem object with requested id
- post `/container?secret={secret}&server={server}` with body of type {`data`: data, `user`: user, `id`: id }, stores container and returns container

JSON with filename `{server}.json` must exist in the same directory as the jar