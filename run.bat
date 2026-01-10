@echo off
echo Compilando...
if not exist bin mkdir bin
javac -d bin src\Main.java src\model\*.java src\service\*.java src\utils\*.java src\view\*.java
if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)
echo Executando...
java -cp bin Main
