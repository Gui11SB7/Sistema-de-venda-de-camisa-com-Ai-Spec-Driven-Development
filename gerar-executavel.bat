@echo off
echo ====================================================
echo   GERAR EXECUTAVEL - Sistema de Vendas de Camisas
echo ====================================================
echo.

echo [1/2] Compilando e empacotando aplicacao...
echo.
call mvn clean package
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERRO: Falha ao gerar executavel
    pause
    exit /b 1
)

echo.
echo ====================================================
echo   EXECUTAVEL GERADO COM SUCESSO!
echo ====================================================
echo.
echo O arquivo executavel foi criado em:
echo   target\SistemaVendasCamisas.jar
echo.
echo Para executar, basta dar duplo clique no arquivo:
echo   SistemaVendasCamisas.jar
echo.
echo Ou execute pelo terminal:
echo   java -jar target\SistemaVendasCamisas.jar
echo.
echo ====================================================
echo.

echo Copiando executavel para a pasta raiz...
copy target\SistemaVendasCamisas.jar SistemaVendasCamisas.jar
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Executavel copiado para a raiz do projeto!
    echo.
    echo Agora voce pode:
    echo   1. Dar duplo clique em "SistemaVendasCamisas.jar"
    echo   2. Ou executar "Iniciar Sistema.bat"
    echo.
)

pause
