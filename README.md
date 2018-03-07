Aplicación en Android que permita gestionar las tareas de un usuario.
    Lo datos de la tarea son:
- Nombre
- Descripción
- Importancia (es un valor de 1 a 10)
- Fecha de finalización
- Enlace
- Imagen

Consta de 4 Activities:
-Add:Permite añadir una nueva tarea. Se accede pulsando en el FloatingActionButton de añadir.
-Update:Permite editar una tarea del listado. Se accede pulsando en la opcion editar del menu del listado.
-Email: Permite enviar(en formato texto) el listado de tareas a un correo. Se accede pulsando en el FloatingActionButton de correo.
-Main: Permite acceder a las otras activities y borrar elementos del listado.

Para conectarse a los datos en Json hace uso de un ApiService y de las librerias OkHttpClient, Gson y Retrofit.
Otra libreria usada en ButterKnife, para la asignacion de valor de los elementos de la interfaz en el codigo.

La aplicación se comunica con un API REST situada en un servidor LEMP en un alojamiento propio en DigitalOcean.
Se utiliza el framework Laravel, la información se guarda en una base de datos MySQL.
 
La ruta a la api es www.c-raterstudio.com/api/tareas{/tarea}. 

Mejoras:
-Acceso por Https.
-En vez de añadir la ruta a la imagen, subir la imagen como tal. 
-Acceso de usuario para evitar la obtencion de tareas no propias.