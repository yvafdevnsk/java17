#!/bin/bash

JAVA_HOME="/usr/local/jdk-17.0.13+11"
CLASS_PATH_JAR="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/lib/*"
CLASS_PATH_RESOURCE="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/resource"
CLASS_PATH=$CLASS_PATH_JAR:$CLASS_PATH_RESOURCE
MAIN_CLASS=amnesia.Main

$JAVA_HOME/bin/java -cp $CLASS_PATH $MAIN_CLASS