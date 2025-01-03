# SMS - Stock Management System

## Docker/Database
to start run `docker compose up` where your `docker-compose.yml` is located


## Endpoints

Mapping: `/sms/api/{endpoint}`

Container:
- `/health`: returns 'alive' when alive
- get `/container?secret={secret}&id={id}&server={server}`: returns Container object with requested id 
- post `/container?secret={secret}&server={server}` with body of type {`data`: data, `updatedBy`: user, `name`: containerId }, stores container and returns container
- delete `/container?secret={secret}&id={id}&server={server}`: returns "success" or error
- get `/getContainerGroups?secret={secret}&server={server}&containerId={containerId}` returns list of groups

Group:
- post `/createGroup?secret={secret}&name={name}&user={user}`: returns success or error message
- post `/addToGroup?secret={secret}&groupId={groupId}&containerId={containerId}&server={server}`: returns success or error message
- get `/getGroup?secret={secret}&groupId={groupId}&server={server}`: returns list of containers
- delete `/deleteGroup?secret={secret}&groupId{groupId}`: returns success of error message
- get `/groups?secret={secret}`: returns list of groups
- post `/renameGroup?secret={secret}&groupId={groupId}&renameTo={renameString}`: returns success or error message
- delete `/deleteFromGroup?secret={secret}&groupId={groupId}&containerId={containerId}&server={server}`: returns success or error message


Container:
- id: integer
- name: string
- createdBy: string
- createdAt: ISO string
- updatedAt: ISO string
- updatedBy: string
- data: string (NBT oder so)
- server: string

Group:
- id: integer
- createdAt: ISO string
- createdBy: string
- name: string