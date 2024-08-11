La aplicación esta hecha con Spring Boot (WebFlux) y sigue un patrón de arquitectura Modelo Vista Controlador.

Para ejecutarla debe contar con una base de datos de mongo, las configuraciones de la conexión están en el archivo application.properties. La base de datos esta ejecutando en Docker container. El archivo "Poblar_DB_Mongo" contiene instrucciones  para poblar la base de datos "db_nequi" con datos de prueba. 

Adicionalmente, el aplicativo de Backend desarrollado es desplegado en Docker, para ello ver las instrucciones en el archivo "Ejecutar el aplicativo en Docker".

Finalmente, para probar el aplicativo realizado importar en Postman las colleciones de Franquicia, sucursales y producto presentes en la carpeta Collecciones_Postman.

Nota: Si se ejecuta la aplicación en local y la base de datos en un contenedor, descomentar en el archivo application.properties la linea "#spring.data.mongodb.host=localhost" y comentar la linea "spring.data.mongodb.host=172.17.0.2".

Para ejecutar el contenedor del aplicativo ya esta generado el archivo jar y configurado en el Dockerfile.