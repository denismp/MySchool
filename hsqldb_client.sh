#!/bin/bash

JAVA=$JAVA_HOME/bin/java
JAR=$HOME/.m2/repository/org/hsqldb/hsqldb/2.3.0/hsqldb-2.3.0.jar

$JAVA -cp $JAR org.hsqldb.util.DatabaseManagerSwing -url jdbc:hsqldb:hsql://localhost/ &
