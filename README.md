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

- `http://localhost:8092/loankbpbonl/api/health`
- `http://localhost:8092/loankbpbonl/api/database/info`
- `http://localhost:8092/loankbpbonl/api/database/dual?value=Hello`

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

## 7. Create CSM table

```sql
CREATE TABLE LNE_MGR.TSCSM0001 (
    OPER_GROUP_CD        CHAR(3)         NOT NULL,
    MENU_ID              VARCHAR2(10)    NOT NULL,
    PARENT_MENU_ID       VARCHAR2(10)    NOT NULL,
    MENU_ENG_NAME        VARCHAR2(200)   NOT NULL,
    MENU_URL             VARCHAR2(200),
    ORDER_SEQ            NUMBER(38,0)    NOT NULL,
    USE_YN               CHAR(1)         NOT NULL,
    APPROVAL_YN          CHAR(1)         NOT NULL,
    BRANCH_AUTH          VARCHAR2(1000),
    SYS_CREATE_YMS       TIMESTAMP       NOT NULL,
    SYS_UPDATED_YMS      TIMESTAMP       NOT NULL,
    SYS_CREATE_UNO       VARCHAR2(20)    NOT NULL,
    SYS_UPDATED_UNO      VARCHAR2(20)    NOT NULL
);

ALTER TABLE LNE_MGR.TSCSM0001
    ADD CONSTRAINT PK_TSCSM_MENU
    PRIMARY KEY (OPER_GROUP_CD, MENU_ID);
    
GRANT SELECT, INSERT, UPDATE, DELETE ON LNE_MGR.TSCSM0001 TO LNE_APP;

CREATE TABLE LNE_MGR.TSCSM0002 (
    OPER_GROUP_CD        CHAR(3)         NOT NULL,
    UNO                  VARCHAR2(20)    NOT NULL,
    USER_NAME            VARCHAR2(200)   NOT NULL,
    PWD                  VARCHAR2(150)   NOT NULL,
    BRN_CODE             VARCHAR2(3),
    POS_CODE             VARCHAR2(5),
    DEP_CODE             VARCHAR2(5),
    REG_CODE             VARCHAR2(5),
    USE_YN               CHAR(1)         NOT NULL,
    SYS_CREATE_YMS       TIMESTAMP       NOT NULL,
    SYS_UPDATED_YMS      TIMESTAMP       NOT NULL,
    SYS_CREATE_UNO       VARCHAR2(20)    NOT NULL,
    SYS_UPDATED_UNO      VARCHAR2(20)    NOT NULL
);

ALTER TABLE LNE_MGR.TSCSM0002
    ADD CONSTRAINT XSCSM0001P
    PRIMARY KEY (OPER_GROUP_CD, UNO);

GRANT SELECT, INSERT, UPDATE, DELETE ON LNE_MGR.TSCSM0002 TO LNE_APP;

CREATE TABLE LNE_MGR.TSCSM0003 (
    OPER_GROUP_CD        CHAR(3)          NOT NULL,
    LANG_DSTCD           VARCHAR2(3)      NOT NULL,
    ERR_MSGCD            VARCHAR2(9)      NOT NULL,
    CHNL_DSTCD           VARCHAR2(2)      NOT NULL,
    OUTPT_CTNT1          VARCHAR2(1000)   NOT NULL,
    OUTPT_CTNT2          VARCHAR2(1000),
    MSG_KND_CD_NO        VARCHAR2(2)      NOT NULL,
    SYS_CREATE_YMS       TIMESTAMP       NOT NULL,
    SYS_UPDATED_YMS      TIMESTAMP       NOT NULL,
    SYS_CREATE_UNO       VARCHAR2(20)    NOT NULL,
    SYS_UPDATED_UNO      VARCHAR2(20)    NOT NULL
);

ALTER TABLE LNE_MGR.TSCSM0003
    ADD CONSTRAINT XSCSM0003P
    PRIMARY KEY (OPER_GROUP_CD, ERR_MSGCD, LANG_DSTCD, CHNL_DSTCD);

GRANT SELECT, INSERT, UPDATE, DELETE ON LNE_MGR.TSCSM0003 TO LNE_APP;
```
