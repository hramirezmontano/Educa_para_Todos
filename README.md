# Educa para Todos

Plataforma académica web para la gestión de cursos, usuarios e inscripciones.

**Tecnologías:** Java 17, Jakarta Servlet, JPA (Hibernate 6.4), MySQL 8, Maven y Apache Tomcat 10.1.

---

## Requisitos previos

- JDK 17 o superior
- Apache Maven
- Apache Tomcat **10.1.x** (debe ser la versión 10 o superior, porque el proyecto usa `jakarta.*`)
- MySQL 8 en ejecución en `localhost:8080`

---

## 1. Crear la base de datos

1. Inicie MySQL.
2. Ejecute el script que se encuentra en `Script/Script_Educa_Para_Todos.sql`. Este crea la base de datos `educa_para_todos` con sus tablas y datos de ejemplo.

---

## 2. Configurar la contraseña de MySQL (IMPORTANTE)

Por seguridad, **la contraseña de la base de datos NO está escrita en el código**. El archivo `src/main/resources/META-INF/persistence.xml` la lee desde una propiedad de la JVM llamada `Db_Password`:

```xml
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="${Db_Password}" />
```

Por lo tanto, **antes de iniciar Tomcat** hay que entregarle su contraseña de MySQL como el argumento de JVM:

```
-DDb_Password=SU_CLAVE_DE_MYSQL
```

Reemplace `SU_CLAVE_DE_MYSQL` por la contraseña de su servidor MySQL. Elija **una** de las siguientes formas, según cómo ejecute Tomcat.

> **Usuario de MySQL:** el proyecto usa el usuario `root`. Si su MySQL usa otro usuario, cambie el valor de `jakarta.persistence.jdbc.user` en `persistence.xml`.

### Opción A: Tomcat instalado por separado (Windows)

1. Cree el archivo `bin\setenv.bat` dentro de la carpeta de Tomcat (por ejemplo `C:\apache-tomcat-10.1.57\bin\setenv.bat`).
2. Escriba en él:

   ```bat
   set "JAVA_OPTS=%JAVA_OPTS% -DDb_Password=SU_CLAVE_DE_MYSQL"
   ```

3. Inicie Tomcat con `bin\startup.bat`.

Si su contraseña contiene el símbolo `%`, escríbalo duplicado (`%%`) dentro del `.bat`.

### Opción B: Tomcat instalado por separado (Linux / macOS)

1. Cree el archivo `bin/setenv.sh` dentro de la carpeta de Tomcat.
2. Escriba en él:

   ```sh
   export JAVA_OPTS="$JAVA_OPTS -DDb_Password=SU_CLAVE_DE_MYSQL"
   ```

3. Inicie Tomcat con `bin/startup.sh`.

### Opción C: VS Code con la extensión "Community Server Connectors"

1. En el panel **Servers**, haga clic derecho sobre el servidor Tomcat y elija **Edit Server**. Se abrirá un archivo JSON.
2. Cambie este valor a `"true"`:

   ```json
   "args.override.boolean": "true",
   ```

3. Al **final** del valor de `args.vm.override.string` (sin borrar lo que ya tiene), agregue un espacio y el argumento:

   ```
   ... -Djava.io.tmpdir=\"C:\\apache-tomcat-10.1.57/temp\" -DDb_Password=SU_CLAVE_DE_MYSQL
   ```

4. Guarde el archivo con `Ctrl + S` y reinicie el servidor (Stop y luego Start).

### Opción D: Eclipse / IntelliJ / NetBeans

Abra la configuración de ejecución del servidor Tomcat y agregue `-DDb_Password=SU_CLAVE_DE_MYSQL` en el campo **VM options** (o **VM arguments**).

> Si la contraseña tiene espacios o caracteres especiales, escríbala entre comillas: `-DDb_Password="mi clave"`.

---

## 3. Compilar y desplegar

1. Desde la carpeta del proyecto, genere el archivo WAR:

   ```bash
   mvn clean package
   ```

2. Copie `target/Educa_para_Todos.war` a la carpeta `webapps` de Tomcat.
   Si ya existía una versión anterior, borre antes la carpeta `webapps/Educa_para_Todos` para evitar código antiguo.
3. Inicie (o reinicie) Tomcat.

---

## 4. Ejecutar

Abra en el navegador:

```
http://localhost:8080/Educa_para_Todos/home
```

Debe aparecer el panel "Plataforma Académica - Educa para Todos" con el número de cursos y usuarios registrados.

---

## Solución de problemas

| Síntoma | Causa probable | Qué hacer |
|---|---|---|
| Error 500 con `Access denied for user 'root'@'localhost' (using password: NO)` | Tomcat no recibió `Db_Password` | Revise el paso 2 y reinicie Tomcat por completo. En el log de arranque debe aparecer una línea `Command line argument: -DDb_Password=...` |
| Error 500 con `Access denied ... (using password: YES)` | La contraseña es incorrecta | Verifique que sea la de su usuario de MySQL |
| `Communications link failure` | MySQL no está iniciado o usa otro puerto | Inicie MySQL y revise el puerto en la URL de `persistence.xml` |
| `Unknown database 'educa_para_todos'` | No se ejecutó el script SQL | Ejecute el paso 1 |
| Los cambios no se reflejan | Tomcat usa un WAR antiguo | Borre `webapps/Educa_para_Todos` y el `.war` viejo, copie el nuevo y reinicie |

---

## Nota de seguridad

No escriba su contraseña en `persistence.xml`, en el código ni en este README. No suba a GitHub archivos que la contengan (por ejemplo `setenv.bat`, el JSON de configuración del servidor o los logs de Tomcat, que imprimen los argumentos de la JVM).

