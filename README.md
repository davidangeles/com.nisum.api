# Nisum Api

Proyecto ejercicio en Java. CRUD de Usuario

![Diagram](Diagram.png)

## Instrucciones

- Clonar este repositorio
- Asegúrese de estar usando Java 11 y Maven 3.x
```
git clone https://github.com/davidangeles/com.nisum.api.git
mvn clean
mvn install
```
### IntelliJ IDEA
- Abra IntelliJ IDEA y seleccione Archivo > Abrir... .
- Elija el directorio nisum.user.api y haga clic en Aceptar .
- Seleccione Archivo > Estructura del proyecto... y asegúrese de que el SDK del proyecto.
- Abra la vista de Maven con View > Tool Windows > Maven .
- En la vista de Maven, ejecute la aplicación.

### Línea de comando
- Coloquese en el directorio raíz del proyecto
- Ejecute alguno de los comandos

```
        mvn exec:java -Dexec.mainClass=com.nisum.user.api.NisumUserApiApplication        
or
        mvn spring-boot:run
or
        java -jar target/nisum.user.api-0.0.1-SNAPSHOT.jar
```
- Para saltear el token JWT, ejecute en el profile local. Ejecute 
```
        mvn exec:java -Dexec.mainClass=com.nisum.user.api.NisumUserApiApplication -Dspring.profiles.active=local        
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=local"
or
        java -jar -Dspring.profiles.active=local target/nisum.user.api-0.0.1-SNAPSHOT.jar
```

### Para ingresar a la consola h2
- http://localhost:8090/nisum-user/h2-console/

## Contenido

- JUnit en las capas web, service y persistencia.
- Javax Validation para la validación de los "request".
- Integración con Hibernate.
- H2 Database.
- Spring Actuator.
- Generación y autentificación de token JWT.
- Spring Security para securizar los endpoints usando token JWT. Si desea saltar esta parte, activar el profile local.

## Pruebas

- Puede realizar las pruebas con POSTMAN, importe el archivo NISUM-USER.postman_collection.json

### Para probar con CURL :
Para generar el token de acceso, ejecute : 
```
curl http://localhost:8090/nisum-user/token
```
SaveUser
```
curl -d @src/test/resources/json/saveUserRequest.json -H "Content-Type: application/json" -X POST http://localhost:8090/nisum-user/api/v1
```
FindUserById
```
curl http://localhost:8090/nisum-user/api/v1/5fa78ebf-01a3-4e68-b8ad-eea4630cd661
```
UpdateUser
```
curl -d @src/test/resources/json/updateUserRequest.json -H "Content-Type: application/json" -X PATCH http://localhost:8090/nisum-user/api/v1/5fa78ebf-01a3-4e68-b8ad-eea4630cd661
```
DeleteUser
```
curl -X DELETE http://localhost:8090/nisum-user/api/v1/5fa78ebf-01a3-4e68-b8ad-eea4630cd661
```

### Para probar con Swagger :
- http://localhost:8090/nisum-user/swagger-ui/index.html#/

![Swagger-UI](Swagger-UI.png)