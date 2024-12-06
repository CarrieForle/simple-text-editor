@echo off

cd src
javac -d ../out Main.java

if %ERRORLEVEL% equ 0 (
    java -cp ../out Main.java
)

cd ..