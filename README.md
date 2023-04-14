# PrestacionesSpring
### Aplicación web realizada en Eclipse EE, empleando Spring Boot, servidor Tomcat, PostgreSQL como gestor de bases de datos y Java (jdkcomo lenguaje de programación.

Pasos a seguir para instalar la base de datos "banco" a través del símbolo del sistema (cmd):

1º Accedemos con nuestro usuario de postgres:
<b>psql -U <usuario> -h localhost</b>

2º Una vez dentro, creamos la base de datos llamada "banco":
<b>CREATE DATABASE banco;</b>

3º Salimos de la base de datos escribiendo \q.

4º Nos situamos en la carpeta donde se encuentra el fichero "banco.sql" para importar las tablas mediante dump:
Ejemplo: cd C:\Users\user\Downloads
C:\Users\user\Downloads> <b>psql -U <usuario> -h localhost banco < banco.sql</b>

5º Comprobamos que se ha creado la base de datos y las tablas correspondientes. Accedemos a postgres como en el primer paso y una vez dentro:
Para listar las bases de datos: \l 

Si queremos ver el la configuración de cada elemento de la tabla: 
<b>\d datos; 
\d historial;</b>

Para ver el contenido de cada una de las tablas: 
<b>SELECT * FROM public.datos;</b>

//////////////////////////////////////////////////////////////////////////////////

Seguir estos pasos para abrir el proyecto en Eclipse y poder ejecutarlo:

1º Abrir "Eclipse IDE for Enterprise Java and Web Developers" e instalar Spring Boot 4: Help > Eclipse Marketplace > <b>"Spring Boot 4 (aka Spring Tool Suite 4)"</b>

2º Para importar el proyecto: File > Import > Existing Maven Projects > Buscar el proyecto en nuestro equipo > Finish

3º Antes de ejecutar el proyecto, ir al paquete "com.example.springboot.config" y modificar el usuario y contraseña del postgresql al suyo propio,
en los archivos: <b>WebAppInizializer.java</b>, <b>SpringMvcConfig.java</b> y <b>MiConexion.java</b>. Estos archivos se localizan en la siguiente ruta:
<b>PrestacionesSpring > Java Resources > src/main/java > com.example.springboot.config</b>

4º Ejecutar nuestro proyecto: <b>botón derecho sobre el proyecto > Run As > Spring Boot App</b>

5º Abrir nuestro navegador web (Google Chrome es el que recomiendo), y buscar: <b>localhost:8080</b>.


En caso de querer extraer el archivo .WAR de nuestro proyecto para hacer "deploy" en nuestro servidor.
<b>Botón derecho sobre el proyecto > Export > WAR file > Destination (seleccionar la ubicación donde quieres que se te genere el .WAR)</b>
