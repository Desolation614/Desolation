@echo off
echo Compiling scripts
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/dialogues/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/interfaces/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/items/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/items/brews/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/items/food/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/npcs/*.java
"C:\Program Files\Java\jdk1.7.0_04\bin\javac.exe" -d bin -cp lib/netty-3.1.5.GA.jar -sourcepath src src/dragonkk/rs2rsps/scripts/objects/*.java
pause