# GraduateProject-RegisterLogin

![](https://komarev.com/ghpvc/?username=furkanyilmaz)

# endspoint

# 
* [GitHub](https://github.com/furkanyilmaz/GraduateProject)
* 
### Gereksinimler
Java 11
intellij idea Ultimate 
Visual Studio Code
Node js
---

## Visual Studio codes
Extensisons
- Browser preview
- Auto Close tag
- tabnine
- Auto import
- Auto rename tag
- Bootstrap 5 Quick Snipperts
- Bracket Pair Colorizer 2
- Css Snippet
- Es7+ React/REdux/React-Native snippets
- Html Snippets
- JsQuery Snippets
- Live Server
- Material Icon Theme
- Open-in-Browser
- Path Intellisense
- Prettier Code formatter
- Project Manager
- Reactjs code Snippets

---

## Tecnology
##FrontEnd
- Html5
- Css3
- responsive design
- Js
- jquery
- Bootstrap
- react


---

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
Mysql
Postgresql

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

1.ADIM
npx create-react-app frontend
cd frontend
npm start
code .

2.ADIM
NOT: node_modules siliyoruz bunu sonrasında 
npm i

3.ADIM
terminal: package.json seviyesinde olacak şekilde
npm install axios
npm install bootstrap --save
npm install node-sass --save

docker container ls
docker inspect 93c06848fc0b | grep IPAddress
ipaddress: 192.168.0.3


docker container ls
docker container stop 587830e964af  9aebe436b56d
docker container rm e385d3511ae3  9aebe436b56d

docker network ls
docker network rm microjar_default

docker network create micro
2ed62da2dbd30d2ccad9088b386cb4917d4b3b2bfd9b6d9655720b810153aacd

docker image ls 
micro.jar  

docker network ls 
docker container run --network micro --name blogContainer -p 5555:5555    micro.jar


docker-compose up -d

docker network ls
docker network rm networkID 
docker network prune
docker inspect networkID

docker container logs containerID
docker container exec -it containerID

ping 5555
ping 1111
ping 8090

docker ps 
docker container inspect 5e2f72bedafd
"NetworkSettings"
 "Networks"
    "IPAddress": "192.168.96.2",
	
	
docker container inspect 8fcce7ff1a32	
    "IPAddress": "192.168.80.2",


