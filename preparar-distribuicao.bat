@echo off
echo ====================================================
echo   PREPARAR PACOTE DE DISTRIBUICAO
echo   Sistema de Vendas de Camisas
echo ====================================================
echo.

REM Verifica se o executavel existe
if not exist "SistemaVendasCamisas.jar" (
    echo Executavel nao encontrado. Gerando...
    echo.
    call gerar-executavel.bat
    if %ERRORLEVEL% NEQ 0 (
        echo ERRO ao gerar executavel
        pause
        exit /b 1
    )
)

echo.
echo Criando pasta de distribuicao...
if exist "Distribuicao" rmdir /s /q "Distribuicao"
mkdir "Distribuicao"

echo.
echo Copiando arquivos necessarios...

REM Copiar executavel
copy "SistemaVendasCamisas.jar" "Distribuicao\" >nul
echo ✓ SistemaVendasCamisas.jar

REM Copiar launcher
copy "Iniciar Sistema.bat" "Distribuicao\" >nul
echo ✓ Iniciar Sistema.bat

REM Copiar manuais
copy "MANUAL-USUARIO.md" "Distribuicao\" >nul
echo ✓ MANUAL-USUARIO.md

copy "LEIA-ME.txt" "Distribuicao\" >nul
echo ✓ LEIA-ME.txt

copy "GUIA-INSTALACAO-SIMPLES.md" "Distribuicao\" >nul
echo ✓ GUIA-INSTALACAO-SIMPLES.md

echo.
echo ====================================================
echo   PACOTE CRIADO COM SUCESSO!
echo ====================================================
echo.
echo Pasta criada: Distribuicao\
echo.
echo Conteudo:
echo   - SistemaVendasCamisas.jar (executavel)
echo   - Iniciar Sistema.bat (atalho)
echo   - MANUAL-USUARIO.md (manual de uso)
echo   - LEIA-ME.txt (instrucoes rapidas)
echo   - GUIA-INSTALACAO-SIMPLES.md (guia de instalacao)
echo.
echo ====================================================
echo.
echo Proximo passo:
echo   1. Comprima a pasta "Distribuicao" em um arquivo ZIP
echo   2. Envie o ZIP para os usuarios finais
echo   3. Instrua-os a extrair e dar duplo clique em "Iniciar Sistema.bat"
echo.
echo ====================================================
echo.

pause
