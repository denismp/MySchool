#!/bin/bash

JAVA=$JAVA_HOME/bin/java
JAR=$HOME/.m2/repository/org/hsqldb/hsqldb/2.3.0/hsqldb-2.3.0.jar

cd $(dirname $0)

DB_HOME=~/hsqldb
test -d $DB_HOME || mkdir $DB_HOME

TOP=$(pwd)

$JAVA -cp $JAR org.hsqldb.server.Server -database.0 file:$DB_HOME/hsqldb &
echo $! > $DB_HOME/hsqldb.pid

exec $TOP/hsqldb_client.sh
