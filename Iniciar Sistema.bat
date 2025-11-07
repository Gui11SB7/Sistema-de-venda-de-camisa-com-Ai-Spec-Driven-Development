@echo off
title Sistema de Vendas de Camisas

REM Verifica se o executavel existe
if not exist "SistemaVendasCamisas.jar" (
    echo ====================================================
    echo   EXECUTAVEL NAO ENCONTRADO
    echo ====================================================
    echo.
    echo O arquivo SistemaVendasCamisas.jar nao foi encontrado.
    echo.
    echo Por favor, execute primeiro: gerar-executavel.bat
    echo.
    pause
    exit /b 1
)

REM Verifica se Java esta instalado
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ====================================================
    echo   JAVA NAO ENCONTRADO
    echo ====================================================
    echo.
    echo Java nao esta instalado ou nao esta no PATH.
    echo.
    echo Por favor, instale o Java JRE 8 ou superior:
    echo https://www.java.com/pt-BR/download/
    echo.
    pause
    exit /b 1
)

REM Inicia a aplicacao
cls
echo ====================================================
echo   SISTEMA DE VENDAS DE CAMISAS
echo ====================================================
echo.
echo Iniciando aplicacao...
echo.
echo Aguarde a janela abrir...
echo.

start javaw -jar SistemaVendasCamisas.jar

echo.
echo Aplicacao iniciada!
echo.
echo Voce pode fechar esta janela.
echo.
timeout /t 3 >nul
exit
