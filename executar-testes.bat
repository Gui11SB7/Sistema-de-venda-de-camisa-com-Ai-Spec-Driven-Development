@echo off
echo ====================================================
echo   SISTEMA DE VENDAS DE CAMISAS - EXECUTAR TESTES
echo ====================================================
echo.

echo Compilando o projeto...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERRO: Falha na compilacao
    pause
    exit /b 1
)

echo.
echo Executando testes automatizados...
echo.
java -cp "target/classes;%USERPROFILE%/.m2/repository/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar" com.vendas.TesteSistema

echo.
echo ====================================================
echo   TESTES CONCLUIDOS
echo ====================================================
echo.
pause
