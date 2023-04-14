# PrestacionesSpring
### Aplicación web realizada en Eclipse EE, empleando Spring Boot, servidor Tomcat, PostgreSQL como gestor de bases de datos y Java.

El proyecto contiene:
  * PrestacionesSpring > aplicación web.
  * banco.sql > tablas (datos e historial) de la base de datos "banco". Hay que crear la base de datos antes de importar el fichero.
  * archivos .csv > para las pruebas.

---

## Pasos a seguir para instalar la base de datos "banco" a través del símbolo del sistema (cmd):

* Accedemos con nuestro usuario de postgres:

    > **psql -U <usuario> -h localhost**

* Una vez dentro, creamos la base de datos llamada "banco":
    > **CREATE DATABASE banco;**

* Salimos de la base de datos escribiendo 
    > **\q**

* Nos situamos en la carpeta donde se encuentra el fichero "banco.sql" para importar las tablas mediante dump:
    > **Ejemplo: cd C:\Users\user\Downloads**<br>
    > **C:\Users\user\Downloads> psql -U <usuario> -h localhost banco < banco.sql**

* Comprobamos que se ha creado la base de datos y las tablas correspondientes. Accedemos a postgres como en el primer paso y una vez dentro, mostramos una lista de las bases de datos: 
   > **\l**

  * Si queremos ver el la configuración de cada elemento de la tabla: 
   > **\d datos;**
   > **\d historial;**

  * Para ver el contenido de cada una de las tablas: 
   > **SELECT * FROM public.datos;**

 ---

## Seguir estos pasos para abrir el proyecto en Eclipse y poder ejecutarlo:

* Abrir "Eclipse IDE for Enterprise Java and Web Developers" e instalar Spring Boot 4:
  > **Help > Eclipse Marketplace > "Spring Boot 4 (aka Spring Tool Suite 4)"**

* Para importar el proyecto: 
  > **File > Import > Existing Maven Projects > Buscar el proyecto en nuestro equipo > Finish**

* Antes de ejecutar el proyecto, ir al paquete "com.example.springboot.config" y modificar el usuario y contraseña del postgresql al suyo propio,
en los archivos: 
  > **WebAppInizializer.java, SpringMvcConfig.java y MiConexion.java**
Estos archivos se localizan en la siguiente ruta:
  > **PrestacionesSpring > Java Resources > src/main/java > com.example.springboot.config**

* Ejecutar nuestro proyecto: 
  > **botón derecho sobre el proyecto > Run As > Spring Boot App**

* Abrir nuestro navegador web (Google Chrome es el que recomiendo), y buscar: 
  > **localhost:8080**


 * En caso de querer extraer el archivo **.WAR** de nuestro proyecto para hacer "deploy" en nuestro servidor.
  > **Botón derecho sobre el proyecto > Export > WAR file > Destination (seleccionar la ubicación donde quieres que se te genere el .WAR)**
