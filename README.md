# ms-card

## Run docker-compose file
infra/default/default
 - docker-compose -f default-compose.yaml up
### docker-compose file contains postgres for db
### In case of modifying db schema add new change-set or delete all images and volumes and recreate using commands:
 - docker container prune
 - docker volume ls
 - docker volume rm --force <volume_name>

### In case of postgres port conflicting with other service port use port-forwarding

## Must use
#### - VAULT config for encrypting and storing sensitive data like jwt secret
#### - CONSUL config for storing pre and prod yml files
#### - API-GATEWAY and LOAD-BALANCER like Zuul Api Gateway
#### - Internal image repository for storing application images