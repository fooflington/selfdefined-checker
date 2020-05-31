Self-defined checker
====================

Proof-of-concept language checker using the [Self-defined project](https://www.selfdefined.app/).

Requirements
------------

* J2EE compatible web server (eg. Tomcat)

Preparation
-----------

1. Clone the repository
2. Get the dependencies and ensure they're either in `WEB-INF/lib` or the webserver's shared `lib`:
    * SQLite JDBC driver (eg. https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.30.1.jar)
    * JSON library (eg. http://stleary.github.io/JSON-java/index.html)
3. Compile the code (YMMV)
    ```shell
    cd WEB-INF
    export LIBPATH=/path/to/shared/java/libraries
    javac -d classes -classpath src:classes:lib/json-20200518.jar:$LIBPATH/el-api.jar:$LIBPATH/servlet-api.jar:$LIBPATH/sqlite-jdbc.jar src/uk/org/mafoo/selfdefined/Checker.java
    ```
4. Build a war file
    ```shell
    jar -cvf self-defined.war .
    ```
5. Deploy! (move the war file to your webservers `/webapps` folder and wait for it to deploy)

Notes
-----

The included database is build using the data from https://github.com/tatianamac/selfdefined.
