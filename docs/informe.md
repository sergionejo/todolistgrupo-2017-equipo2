# **EQUIPO 2**

- [EQUIPO 2](#equipo-2)
    - [Integrantes](#integrantes)
    - [Historias de usuario escogidas para el sprint](#historias-de-usuario-escogidas-para-el-sprint)
    - [Funcionalidades implementadas](#funcionalidades-implementadas)
    - [Informe sobre la metodología seguida](#informe-sobre-la-metodolog%C3%ADa-seguida)
        - [Responsables](#responsables)
        - [Pasos del 4 al 9 por cada issue asignada](#pasos-del-4-al-9-por-cada-issue-asignada)
        - [**Daily semana 1**](#daily-semana-1)
            - [Planificación primera semana](#planificaci%C3%B3n-primera-semana)
        - [**Daily semana 2**](#daily-semana-2)
            - [Planificación segunda semana](#planificaci%C3%B3n-segunda-semana)
            - [Revisión primera semana](#revisi%C3%B3n-primera-semana)
        - [**Daily semana 3**](#daily-semana-3)
            - [Planificación tercera semana](#planificaci%C3%B3n-tercera-semana)
            - [Revisión segunda semana](#revisi%C3%B3n-segunda-semana)
        - [**Daily semana 4**](#daily-semana-4)
            - [Planificación cuarta semana](#planificaci%C3%B3n-cuarta-semana)
            - [Revisión tercera semana](#revisi%C3%B3n-tercera-semana)
    - [Resultado de la retrospectiva](#resultado-de-la-retrospectiva)

## **Integrantes**

- Sergio Conesa
- Diego Maroto
- Traian Mirci

## **Historias de usuario escogidas para el sprint**

- Tableros contienen tareas (M)
  - Como usuario quiero poder asignar las tareas a tableros, para poder agruparlas.

- Crear grupos (M)
  - Como usuario registrado debo poder crear grupos (de usuarios).

- Confirmación borrar tarea (S)
  - Como usuario quiero ser advertido al borrar una tarea para no borrarla accidentalmente.

- Papelera de tareas (M)
  - Como usuario poder recuperar tareas que hayan sido eliminadas.

- Cerrar tableros (S)
  - Como usuario quiero cerrar tableros.

- Enviar mensajes a grupo (M)
  - Como usuario en un equipo quiero poder enviar mensajes a usuarios de mi equipo para poder comunicarme.

- Papelera de Tableros (S)
  - Como usuario poder recuperar tableros que hayan sido eliminados.

- Tableros con label (M)
  - Como usuario quiero añadir labels a los tableros para que sea más fácil buscarlos

- Priorizar tareas (S)
  - Como usuario quiero poder establecer una prioridad a cada tarea para organizarlas por orden de importancia

- Filtrar tareas (M)
  - Como usuario quiero poder filtrar tareas para encontrarlas más fácilmente

- Buscar tareas normal y avanzada (M)
  - Como usuario quiero poder buscar las tareas, por nombre y de forma más avanzada, cuando hay muchas el ruido de UX hace la información inaccesible.

## **Funcionalidades implementadas**

- Tableros contienen tareas (M)
  - Relacionamos las tareas con los tableros, ahora las tareas están obligadas a decir a qué tablero pertenecen cuando se crean desde la parte visual. Esta relación era esencial para darle sentido a la aplicación, sobretodo cuando teníamos pensado crear grupos.

- Crear grupos (M)
  - Añadimos la entidad Grupo a la aplicación, con la capacidad de agrupar usuarios de forma muy simple, permitiendo así en un futuro la posibilidad de invitar a usuarios a trabajar en un tablero directamente desde grupos, en lugar de añadirlos uno a uno.

- Confirmación borrar tarea (S)
  - Cambiamos la forma en la que se hacen los delete de tareas y tableros, en lugar de hacer un redirect y render, nos centramos en que por ajax devuelva el ok o no, para permitir al usuario un aviso y la confirmación consiguiente a haber eliminado definitivamente un tablero o una tarea.

- Papelera de tareas (M)
  - Creación de la entidad papelera, relacionada 1-1 con Usuario y 1-N con Tarea de manera que se pueda ver en la barra de navegacion un enlace a la papelera y también las tareas sean borradas en dos fases (primero a la papelera y luego borrado definitivo) de manera que se puedan enmendar errores y desde la papelera se pueda devolver la tarea a su estado anterior o bien eliminarla definitivamente.

- Cerrar tableros (S)
  - Adicion de un campo a la tabla Tablero de manera que cuendo este sea cerrado esté en una vista apartada para no tener que deshacernos de un tablero cuando se acaben las tareas de éste, la idea de añadir un campo de este tipo a la entidad es que en un futuro se puedan realizar facilmente ampliaciones sin necesidad de tocar la entidad, como por ejemplo poder marcar los tableros como en marcha o distintos estados.
  - Para cerrar un tablero simplemente el administrador de este tendrá que clickar en el botón que aparecerá en el listado de tableros, en la fila del que se desee cerrar.

- Enviar mensajes a grupo (M)
  - AAAAA

- Papelera de Tableros (S)
  - Ampliación de la tarea de Papelera de Tareas de forma que además de poder tener en ésta las tareas tambien pueda contener tableros, para lo cual se añade una relacion 1-N entre Papelera y Tablero y permita el borrado en dos fases como se ha explicado en la funcionalidad de Papelera de Tareas.

## **Informe sobre la metodología seguida**

Hemos utilizado la metodología scrum para la realización de esta práctica, de manera que hemos realizado reuniones una vez a la semana y también hemos mantenido constantemente el contacto para poder ser ágiles en el desarrollo.

### Responsables

- Jefe de grupo: Sergio Conesa

- Responsable de integración: Diego Maroto

### Pasos del 4 al 9 por cada issue asignada

1. Añadir Backlog a Trello

1. Elegir las issues que se van a realizar en el sprint

1. Añadir issues a Github

1. Abrir rama de desarrollo y mover issue “En proceso”

1. Realizar diversos commits hasta finalizar la issue

1. Abrir pull request

1. Esperar a que un compañero revise los cambios y Travis pase los tests

1. Cerrar pull request y mergear con master

1. Mover la tarea a “Terminado”

1. Realizar retrospectiva en la próxima reunión de Scrum

### **Daily semana 1**

#### Planificación primera semana

- Se deciden las tareas que se van a realizar durante las cuatro semanas las cuales estan detalladas al principio del informe

- Traian: Despues de hablar acerca de cual tarea realizar, decidimos darle valor a que se pudieran tener Tableros con label por lo tanto comienza por esta medium
- Diego: Decide realizar primero una tarea small como toma de contacto y que creemos necesaria para un uso más correcto de la plataforma como es Confirmación de borrar tarea

- Sergio: Prefiere por comenzar por una tarea algo más compleja como es una medium que es Papelera de tareas ya que creemos necesario que se pueda realizar un borrado en dos fases además de con confirmacion para evitar errores

### **Daily semana 2**

#### Planificación segunda semana

- Traian: Continúa con la tarea que estaba realizando

- Diego: Sacamos de la pila de backlog la funcionalidad Crear grupos que se trata de una medium que creemos que aporta mucho valor a la aplicación final para una TODO List

- Sergio: Como esta semana tiene complicaciones para trabajar comienza con una tarea como es Cerrar tableros ya que además es muy útil para gestionar tableros o proyectos como podría hacerse por ejemplo en otros gestores de tableros.

#### Revisión primera semana

- Diego: Finalizó sin problemas la tarea de la primera semana

- Sergio: Realizó muchos avances durante la primera semana ya que contaba con tiempo necesario y terminó la medium en una semana

- Traian: Continua en principio sin complicaciones con su tarea

### **Daily semana 3**

#### Planificación tercera semana

- Traian: Continúa con la tarea que estaba realizando

- Diego: Continúa con la tarea que estaba realizando

- Sergio: Se propone para esta semana realizar una tarea medium que se valora como muy interesante y con mucho valor para los usuarios de la aplicacion como es Enviar mensajes a grupo ya que así hay más comunicacion entre los grupos lo que ayuda auna mejor gestion de los tableros y las tareas

#### Revisión segunda semana

- Traian: Problemas con la tarea de Tableros con label los cueles está tratando de solucionar pero van a retrasar la tarea

- Diego: Añade más subtareas a Crear grupos de manera que sea un CRUD completo lo que conlleva aumento de tiempo de realización de la tarea.

- Nos damos cuenta de que el mapeo de columnas en tablas de hibernate da problemas de claves ajenas cuando los nombres son los mismos, nos retrasa el cierre de varias issues varias horas por trabajo de debug, track e investigación.

### **Daily semana 4**

#### Planificación cuarta semana

- Traian: Continúa con la tarea que estaba realizando

- Diego: Para finalizar y aunque no estaba como prevista, creemos oportuno añadir la tarea medium de Tablero contienen tareas ya que si no veiamos un poco innecesaria la existencia de tableros sin esta relación, por lo tanto pese a la carga de trabajo, por eso mismo como esta última semana no tiene demasiado trabajo, Diego decide asignarsela

- Sergio: Esta ultima semana tiene muchas tareas a realizar de otras asignaturas y por lo tanto decidimos que una small que aportaria mucho al proyecto sería Papelera de tableros ya que es una ampliacion necesaria para terminar de darle sentido a la papelera

#### Revisión tercera semana

- Diego: Debido al aumento de subtareas sobre crear grupos tuvo que aumentar el tiempo requerido para acabarla, pero finalmente consigue finalizar la tarea del CRUD completo de grupos.

- Sergio: Como la tarea que le fue asignada le pareció muy útil aumentando un poco el ritmo de trabajo durante la semana, consiguio finalizar la tarea medium en una semana ya que no ocurrieron imprevistos.

## **Resultado de la retrospectiva**

- La planificación de tareas y el seguimiento semanal nos ha resultado muy útil, saber lo que tienen que hacer los compañeros y en qué punto están, además de la facilidad de comunicación y ayuda unos a otros cuando alguien tiene algún problema que no sabe solventar.

- Nos hemos encontrado con ciertos problemas de logística, pues tenemos horarios muy dispares, y a menudo para poder seguir trabajando necesitábamos que nos revisasen y confirmasen el pull request, por eso tenemos algunas ramas que hemos mergeado con master a mitad de su vida, para recuperar todo el trabajo de otros compañeros que pueda sernos útil para seguir o acabar con nuestro desarrollo.

- En cuanto a la planificación, Diego sobreestimó su capacidad de trabajo y se puso en el backlog una tarea de más, pensando que la podría realizar y quedando sin empezar. Se decidió dejar esa en cola porque el resto parecían más útiles/importantes de cara a presentar la demo y que la integración tuviera sentido. No haber implementado que los tableros tuvieran tareas, por ejemplo, habría dejado la aplicación bastante coja de cara a una presentación con el cliente.
