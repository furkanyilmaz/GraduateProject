# GraduateProject-Daily

![](https://komarev.com/ghpvc/?username=furkanyilmaz)


# 
* [GitHub](https://github.com/furkanyilmaz/GraduateProject)
* 
### Gereksinimler
Java 11
intellij idea Ultimate 

## Tecnology

## BackEnd
* javase
* jdbc crud native
* hibernate
* spring core
* spring mvc
* spring data
* spring rest
* spring security

---

## Libraries
Thymeleaf
Lombok
Validation

## Database
H2 Database

---
## dependency
Spring Web
Spring Data JPA
Spring Rest
Spring Security

---
# Spring Tools
Spring Boot DevTools
Spring Configuration Processor
Spring Boot Actuator

---

## Docker Deployment

docker container ls
docker inspect 93c06848fc0b | grep IPAddress
ipaddress: 192.168.0.3


docker container ls
docker container stop 587830e964af  9aebe436b56d
docker container rm e385d3511ae3  9aebe436b56d

docker network ls
docker network rm dailyjar_default

docker network create daily
2ed62da2dbd30d2ccad9088b386cb4917d4b3b2bfd9b6d9655720b810153aacd

docker image ls 
daily.jar  

docker network ls 
docker container run --network daily --name blogContainer -p 8090:8090    daily.jar


docker-compose up -d

docker network ls
docker network rm networkID 
docker network prune
docker inspect networkID

docker container logs containerID
docker container exec -it containerID

ping 8090

docker ps 
docker container inspect 5e2f72bedafd
"NetworkSettings"
 "Networks"
    "IPAddress": "192.168.96.2",
	
	
docker container inspect 8fcce7ff1a32	
    "IPAddress": "192.168.80.2",


