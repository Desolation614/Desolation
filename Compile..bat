@echo off
title Compiling
javac -d bin -cp lib/cache.jar;lib/jython.jar;lib/slf4j-api-1.5.8.jar;lib/mina-core-2.0.0-M6.jar;lib/slf4j-jdk14-1.5.8.jar;lib/commons-compress-1.0.jar;lib/netty-3.1.5.GA.jar;lib/junit-4.6.jar -sourcepath src src\dragonkk\rs2rsps\*.java
pause