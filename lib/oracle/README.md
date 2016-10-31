These jars need to be installed manually with maven

```
mvn install:install-file -Dfile=lib/oracle/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/oracle/.jar -DgroupId=com.oracle -DartifactId= -Dversion=11.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/oracle/orddicom.jar -DgroupId=com.oracle -DartifactId=orddicom -Dversion=11.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/oracle/ordim.jar -DgroupId=com.oracle -DartifactId=ordim -Dversion=11.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/oracle/xdb.jar -DgroupId=com.oracle -DartifactId=xdb -Dversion=11.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/oracle/xmlparserv2.jar -DgroupId=com.oracle -DartifactId=xmlparserv2 -Dversion=11.2.0 -Dpackaging=jar
```
