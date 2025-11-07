# 📦 Guia de Instalação Simples - Sistema de Vendas de Camisas

## Para Usuários Sem Conhecimento Técnico

### 🎯 Passo 1: Instalar o Java (Se Necessário)

1. Acesse: https://www.java.com/pt-BR/download/
2. Clique em "Download Gratuito do Java"
3. Execute o instalador baixado
4. Siga as instruções na tela
5. Reinicie o computador após a instalação

### 🎯 Passo 2: Gerar o Executável (Apenas Uma Vez)

**Para Desenvolvedores/Técnicos:**

1. Abra o terminal onde Maven funciona
2. Execute:
   ```
   .\gerar-executavel.bat
   ```
3. Aguarde a compilação (pode levar alguns minutos)
4. O arquivo `SistemaVendasCamisas.jar` será criado

### 🎯 Passo 3: Usar o Sistema

Após gerar o executável, existem 2 formas de usar:

#### Opção 1: Duplo Clique (Mais Fácil)
1. Localize o arquivo `SistemaVendasCamisas.jar`
2. Dê duplo clique nele
3. A aplicação abrirá automaticamente

#### Opção 2: Usar o Atalho
1. Dê duplo clique em `Iniciar Sistema.bat`
2. A aplicação abrirá automaticamente

---

## 📤 Distribuir para Outros Usuários

Para compartilhar o sistema com outras pessoas:

### O que enviar:
1. ✅ `SistemaVendasCamisas.jar` (arquivo executável)
2. ✅ `Iniciar Sistema.bat` (atalho para iniciar)
3. ✅ `MANUAL-USUARIO.md` (instruções de uso)

### O que NÃO precisa enviar:
- ❌ Pasta `src/` (código fonte)
- ❌ Pasta `target/` (arquivos de compilação)
- ❌ Arquivos `.bat` de desenvolvimento
- ❌ Pasta `.kiro/` (especificações)

### Instruções para o usuário final:

```
1. Certifique-se de ter Java instalado
   (baixe em: https://www.java.com/pt-BR/download/)

2. Copie os arquivos para uma pasta no seu computador

3. Dê duplo clique em "Iniciar Sistema.bat"
   OU
   Dê duplo clique em "SistemaVendasCamisas.jar"

4. Pronto! O sistema abrirá automaticamente
```

---

## 🖥️ Criar Atalho na Área de Trabalho

### Windows:

1. Clique com botão direito em `Iniciar Sistema.bat`
2. Selecione "Enviar para" → "Área de trabalho (criar atalho)"
3. Renomeie o atalho para "Sistema de Vendas"
4. (Opcional) Clique com botão direito no atalho → Propriedades
5. Clique em "Alterar ícone" para personalizar

---

## 📁 Estrutura de Arquivos para Usuário Final

```
📁 Sistema de Vendas/
├── 📄 SistemaVendasCamisas.jar      ← Executável principal
├── 📄 Iniciar Sistema.bat           ← Atalho para iniciar
├── 📄 MANUAL-USUARIO.md             ← Manual de uso
└── 📄 vendas_camisas.db             ← Banco de dados (criado automaticamente)
```

---

## ⚠️ Requisitos do Sistema

- **Sistema Operacional:** Windows 7 ou superior
- **Java:** JRE 8 ou superior
- **Memória RAM:** Mínimo 512 MB
- **Espaço em Disco:** Mínimo 50 MB
- **Resolução de Tela:** Mínimo 1024x768

---

## 🆘 Problemas Comuns

### "Java não encontrado"
**Solução:** Instale o Java em https://www.java.com/pt-BR/download/

### "Arquivo não abre ao dar duplo clique"
**Solução:** 
1. Use o arquivo "Iniciar Sistema.bat"
2. Ou abra o terminal e execute: `java -jar SistemaVendasCamisas.jar`

### "Erro ao salvar dados"
**Solução:** 
1. Certifique-se de ter permissão de escrita na pasta
2. Execute como administrador (botão direito → "Executar como administrador")

### "Aplicação lenta"
**Solução:**
1. Feche outros programas
2. Verifique se tem espaço em disco disponível
3. Reinicie o computador

---

## 📞 Suporte

Para mais informações, consulte:
- `COMO-USAR-APLICACAO.md` - Guia completo de uso
- `README.md` - Informações técnicas do projeto

---

## ✅ Checklist de Distribuição

Antes de enviar para usuários finais:

- [ ] Java instalado no computador do usuário
- [ ] Arquivo `SistemaVendasCamisas.jar` gerado
- [ ] Arquivo `Iniciar Sistema.bat` incluído
- [ ] Manual de usuário incluído
- [ ] Testado em um computador limpo
- [ ] Atalho na área de trabalho criado (opcional)

---

**Pronto para usar? Dê duplo clique em:** `Iniciar Sistema.bat` 🚀
