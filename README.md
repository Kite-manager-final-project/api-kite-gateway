# Kite manager con microservicios

## Mi tablero de Trello

https://trello.com/b/D2C2W1lb/kite-manager-microservices

## Links al resto de repositorios

* discovery-server -> https://github.com/Kite-manager-final-project/discovery-server.git

* kite-service -> https://github.com/Kite-manager-final-project/kite-service.git

* person-service -> https://github.com/Kite-manager-final-project/person-sevice.git

## Diagrama UML

Este diagrama nos da una idea de la estructura del proyecto

```
@startuml
class Kite{
- id: int
- windRequired: int
- location: String
- owner: Person
}



class Person {
- nickName: String
- name: String
- phoneNumber: int
- email: string
}


Kite "*" o-- "1" Person

@enduml
```


![img_1.png](img_1.png)


NOTA: Pese a que en el diagrama UML se aprecia que en la clase Kite el owner es una Person, A la hora de desarrollar el proyecto y
al estar separado cada microservicio con su base de datos independiente. En realidad, va a ser una referencia. En el caso de owner, una string que
haga referencia al nombre de la persona.
Para añadir más contenido a la clase person y que no tenga solo el nickname, le he puesto como atributo su nombre completo, número de 
teléfono y dirección de correo.

## Tablas en las bases de datos

Al tratarse de un microservicio, las bases de datos están separadas, por lo tanto, no hay relaciones explicitas en las tablas. Tenemos:

* person_micro (tabla persons)

![img.png](img.png)

* kite_micro (tabla kites)

![img_2.png](img_2.png)

Como se aprecia, en la tabla de cometas hay una referencia al dueño, concretamente, la columna owner, sin embargo, no es ninguna clave foránea.
Simplemente, es de tipo ```varchar```.

## Puertos de cada API

Tenemos estás APIs con sus respectivos puertos.

* ```discovery-server``` -> Es la API de todos los microservicios que escucha las peticiones, el número de puerto por el que está escuchando es
                      el ```8761```.

* ```api-kite-gateway``` -> Es la API que hace de intermediario entre los clientes y el servidor, según la ruta que le mande el cliente, 
                            llamará a una api o a otra. Tiene como número de puerto el ```8180```.

* ```person-service``` -> Es la API que gestiona las personas guardadas en la base de datos. Tiene como número de puerto el ```8081```.

* ```kite-service``` -> Es el servidor que gestiona las cometas guardadas en la base de datos. Este, a su vez, realizará llamadas a 
                        person-service donde sea necesario, ya sea, para ver el dueño de cada cometa de forma detallada, o bien, para
                        realizar comprobaciones, como verificar que el dueño asignado a esa cometa exista en la base de datos.
                        Tiene como número de puerto el ```8080```.

En resumen, discovery-server toma el rol de servidor, api-kite-gateway toma el rol de intermediario, y tanto kite-service como person-service
toman el rol de clientes.

## DEMOs

A continuación, las siguientes capturas de pantalla es de como se comporta la aplicación en sí. Voy a mostrar algunos ejemplos.

### Request persons

En cada petición, donde es necesario realizar validaciones. En caso de que algo falle, en postman aparecerá el mensaje de error para que
el usuario sepa lo que esta succediendo.

Los posibles fallos son que la persona no existe (HTTP_STATUS.NOT_FOUND (404)) o que intento crear una persona con un nickname ya existente
(HTTP_STATUS.CONFLICT (409))

#### Busqueda de todas las personas (GET)

![img_3.png](img_3.png)

#### Búsqueda de una persona por nickName (GET)

Por ejemplo, aquí voy a buscar una persona que no existe, para mostrar un mensaje de error. En caso contrario, mostraría la persona con todos
los campos.

![img_4.png](img_4.png)

#### Crear una nueva persona (POST)

Aquí voy a crear una nueva persona, si le especifico un nickName que ya existe, devuelve un HTTP_STATUS.CONFLICT (409) junto con el mensaje de
error.

![img_5.png](img_5.png)

Para que me cree esa persona, voy a asignarle como nickName debugger

#### Modificar una persona (PUT)

Aquí hay que especificar todos los campos, salvo el nickName. Si le paso un nickName no existente, devuelve un HTTP_STATUS.NOT_FOUND (404)
El nickName es el parámetro que le paso en la ruta (Path Variable)

#### Modificar correo electrónico (PATCH)

Dado el nickName, le paso la nueva dirección de correo para modificarla, falla si le mando un nickName no existente.

![img_6.png](img_6.png)

#### Modificar el número de teléfono (PATCH)

Dado el nickName, le paso el número de teléfono para modificarlo, falla si le mando un nickName no existente.

![img_7.png](img_7.png)

#### Eliminar a una persona (DELETE)

Dado el nickname, si existe esa persona, la elimina y devuelve un mensaje de confirmación. En caso contrario, devuelve un mensaje de error.

![img_8.png](img_8.png)