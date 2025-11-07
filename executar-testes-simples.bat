@echo off
echo ====================================================
echo   EXECUTANDO TESTES - Sistema de Vendas
echo ====================================================
echo.

echo [1/2] Compilando...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo [2/2] Executando testes...
echo.

cd target\classes
java -cp ".;%USERPROFILE%\.m2\repository\org\xerial\sqlite-jdbc\3.42.0.0\sqlite-jdbc-3.42.0.0.jar" com.vendas.TesteSistema
cd ..\..

echo.
pause
