# Kite manager con microservicios

## Links al resto de repositorios

* discovery-server -> https://github.com/OrganizacionIronHack/discovery-server.git

* kite-service ->

* person-service ->

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
- userName: String
- kites: Kite[]
}


Kite "*" o-- "1" Person

@enduml
```

![img.png](img.png)

NOTA: Pese a que en el diagrama UML se aprecia que en la clase Kite el owner es una Person, y en la clase person, la lista de cometas
es un array de kites. A la hora de desarrollar el proyecto, al estar separado cada microservicio con su base de datos independiente.
En realidad, va a ser una referencia. En el caso de owner, una string que haga referencia al nombre de la persona.
Por otro lado, en el caso de la clase Person, la lista de cometas, en realidad va a ser un array de enteros para que haga referencia a
los ids.
