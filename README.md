# loankbpbonl

Spring Boot + Java 21 + MyBatis XML + Oracle JNDI DataSource + external Tomcat WAR.

## Project coordinates

- Group: `kh.com.copilot.app`
- Artifact: `loankbpbonl`
- Packaging: `war`
- Base package: `kh.com.copilot.app`

## Requirements

- JDK 21
- Maven 3.6.3+ (or use the Maven Wrapper if you add one)
- Apache Tomcat 10.1.x
- Oracle Database and a service name reachable from Tomcat
- Oracle JDBC `ojdbc11.jar`

## 1. Configure Tomcat

Copy the Oracle JDBC driver to the Tomcat common library directory:

```bash
cp /path/to/ojdbc11.jar "$CATALINA_HOME/lib/"
```

Copy `tomcat/lnekbpbonl-context.xml` to:

```text
$CATALINA_BASE/conf/Catalina/localhost/loankbpbonl.xml
```

The context descriptor filename must match the WAR name: `loankbpbonl.war`.

Edit the resource settings in that file:

- `url`: your Oracle JDBC URL
- `username`: application database account
- `password`: database password (use a protected external secret mechanism in shared/production environments)

The configured JNDI name is:

```text
java:comp/env/jdbc/LneDS
```

The application references that same name in `src/main/resources/application.yml`.

For Tomcat 10.1, `javax.sql.DataSource` remains correct: JDBC is part of Java SE, not the Jakarta Servlet namespace.

## 2. Build

```bash
mvn clean package
```

Output:

```text
target/loankbpbonl.war
```

## 3. Deploy

```bash
cp target/loankbpbonl.war "$CATALINA_BASE/webapps/"
```

Start Tomcat:

```bash
"$CATALINA_HOME/bin/catalina.sh" run
```

Application URLs:

- `http://localhost:8080/loankbpbonl/api/health`
- `http://localhost:8080/loankbpbonl/api/database/info`
- `http://localhost:8080/loankbpbonl/api/database/dual?value=Hello`

The database diagnostic endpoints should be removed or access-controlled before production.

## 4. IntelliJ IDEA local run

Recommended: configure IntelliJ **Tomcat Server → Local** and deploy the `loankbpbonl:war exploded` artifact.

Make sure the Tomcat instance started by IntelliJ can see:
- `ojdbc11.jar` in its common `lib` directory
- `conf/Catalina/localhost/loankbpbonl.xml` in its active `CATALINA_BASE/conf` directory

IntelliJ may use a separate `CATALINA_BASE` from the Tomcat installation directory. Check the Run console for the active base directory.

This project is configured for external Tomcat JNDI. Running `LoankbpbonlApplication.main()` directly from IntelliJ will not automatically provide the Tomcat JNDI resource. To run directly, add a separate local JDBC/Hikari profile and make the Oracle driver available at runtime, or use external Tomcat for local development.

## 5. Change database environments

Keep the JNDI name the same in all environments:

```text
java:comp/env/jdbc/LneDS
```

Change only the Tomcat resource's URL, username, password and pool settings for DEV, SIT, UAT and PROD. The WAR and Java code remain the same.

## 6. Notes

- Spring Boot 3.5 uses Jakarta Servlet APIs and is suitable for Tomcat 10.1.
- The Oracle JDBC driver is declared with Maven `provided` scope because external Tomcat supplies it.
- Do not place real database passwords in Git.
- Tune pool sizes against Oracle capacity and the number of Tomcat instances.
- Use HTTPS and appropriate access controls in production.
