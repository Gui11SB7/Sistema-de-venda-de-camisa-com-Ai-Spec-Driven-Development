# 📖 Manual do Usuário - Sistema de Vendas de Camisas

## 🚀 Como Iniciar o Sistema

### Primeira Vez:
1. Certifique-se de ter Java instalado (https://www.java.com/pt-BR/download/)
2. Dê duplo clique em **"Iniciar Sistema.bat"**
3. Aguarde a janela abrir

### Próximas Vezes:
- Basta dar duplo clique em **"Iniciar Sistema.bat"**

---

## 📱 Telas do Sistema

O sistema possui 4 telas principais. Use as abas no topo para navegar entre elas.

### 1️⃣ Cadastro de Produtos

**Para que serve:** Adicionar camisas ao seu estoque

**Como usar:**
1. Preencha a **Descrição** (ex: "Camisa Polo Azul")
2. Escolha o **Tamanho** (PP, P, M, G, 2G ou 3G)
3. Clique em **"Selecionar Imagem"** para adicionar foto (opcional)
4. Digite o **Valor de Compra** (quanto você pagou)
5. Escolha a **Data de Compra**
6. Clique em **"Salvar"**

**Dica:** Use o botão "Limpar" para começar um novo cadastro

---

### 2️⃣ Registro de Vendas

**Para que serve:** Registrar quando você vende uma camisa

**Como usar:**
1. Na lista, selecione o **produto** que foi vendido
2. Escolha o **cliente** que comprou
3. Digite o **Valor de Venda** (quanto o cliente pagou)
4. Escolha a **Data da Venda**
5. Clique em **"Registrar Venda"**

**Importante:** Só aparecem produtos que ainda não foram vendidos

---

### 3️⃣ Gerenciamento de Clientes

**Para que serve:** Cadastrar clientes e controlar pagamentos

#### Cadastrar Novo Cliente:
1. Clique em **"Novo Cliente"**
2. Preencha o **Nome** (obrigatório)
3. Preencha **Telefone**, **Email** e **Endereço** (opcional)
4. Clique em **"Salvar"**

#### Ver Detalhes de um Cliente:
1. Selecione o cliente na lista
2. Você verá:
   - Histórico de compras
   - Total que o cliente comprou
   - Pagamentos realizados
   - **Saldo Devedor** (quanto ainda deve)

#### Registrar Pagamento:
1. Selecione o cliente
2. Clique em **"Registrar Pagamento"**
3. Digite o **Valor Pago**
4. Adicione uma **Observação** (opcional)
5. Clique em **"Salvar"**

**Dica:** O saldo devedor aparece em vermelho quando o cliente deve dinheiro

---

### 4️⃣ Painel de Gerenciamento

**Para que serve:** Ver resumo financeiro do seu negócio

**O que você vê:**
- **Total Gasto:** Quanto você gastou comprando camisas
- **Total Recebido:** Quanto você recebeu vendendo
- **Lucro/Prejuízo:** Diferença entre vendas e compras
- **Saldo a Receber:** Quanto os clientes ainda devem
- **Lista de Produtos em Estoque:** Camisas que ainda não foram vendidas

**Dica:** Use esta tela para acompanhar se seu negócio está dando lucro

---

## 🎯 Fluxo de Trabalho Recomendado

### Quando Comprar Camisas:
1. Vá em **"Cadastro de Produtos"**
2. Cadastre cada camisa comprada
3. Tire foto e adicione (ajuda a lembrar depois)

### Quando Vender uma Camisa:
1. Se for cliente novo, cadastre em **"Gerenciamento de Clientes"**
2. Vá em **"Registro de Vendas"**
3. Selecione a camisa e o cliente
4. Registre a venda

### Quando Receber Pagamento:
1. Vá em **"Gerenciamento de Clientes"**
2. Selecione o cliente
3. Clique em **"Registrar Pagamento"**
4. Digite o valor recebido

### Para Ver Como Está o Negócio:
1. Vá em **"Painel de Gerenciamento"**
2. Veja o lucro e o saldo a receber

---

## 💡 Dicas Importantes

### ✅ Boas Práticas:
- **Cadastre clientes antes de vender** - Facilita o controle
- **Tire fotos dos produtos** - Ajuda a identificar depois
- **Registre pagamentos assim que receber** - Mantém o controle atualizado
- **Consulte o painel regularmente** - Acompanhe seu lucro

### ⚠️ Cuidados:
- **Não delete o arquivo "vendas_camisas.db"** - É onde ficam seus dados
- **Faça backup regularmente** - Copie o arquivo "vendas_camisas.db" para outro lugar
- **Não abra várias vezes o sistema** - Use apenas uma janela por vez

---

## 💾 Seus Dados

### Onde ficam salvos?
Todos os dados ficam no arquivo **"vendas_camisas.db"** na mesma pasta do sistema.

### Como fazer backup?
1. Feche o sistema
2. Copie o arquivo "vendas_camisas.db"
3. Cole em outro lugar seguro (pen drive, nuvem, etc.)

### Como restaurar backup?
1. Feche o sistema
2. Substitua o arquivo "vendas_camisas.db" pelo backup
3. Abra o sistema novamente

### Como começar do zero?
1. Feche o sistema
2. Delete o arquivo "vendas_camisas.db"
3. Abra o sistema (um novo banco será criado)

---

## 🆘 Problemas e Soluções

### Sistema não abre
**Causa:** Java não instalado
**Solução:** Instale Java em https://www.java.com/pt-BR/download/

### Não consigo salvar dados
**Causa:** Sem permissão na pasta
**Solução:** Execute como administrador (botão direito → "Executar como administrador")

### Imagem não aparece
**Causa:** Arquivo muito grande ou formato errado
**Solução:** Use imagens JPG ou PNG menores que 5MB

### Valores aparecem errados
**Causa:** Dados desatualizados
**Solução:** Feche e abra o sistema novamente

### Sistema está lento
**Causa:** Muitos dados ou pouca memória
**Solução:** 
1. Feche outros programas
2. Reinicie o computador
3. Faça limpeza de dados antigos

---

## 📊 Entendendo os Valores

### Total Gasto
Soma de todos os valores que você pagou pelas camisas

### Total Recebido
Soma de todos os pagamentos que você recebeu dos clientes

### Lucro/Prejuízo
- **Verde (positivo):** Você está lucrando
- **Vermelho (negativo):** Você está no prejuízo

### Saldo a Receber
Quanto os clientes ainda devem para você

### Saldo Devedor (por cliente)
Quanto cada cliente específico ainda deve

---

## 📞 Precisa de Ajuda?

### Documentação Técnica:
- `COMO-USAR-APLICACAO.md` - Guia detalhado
- `README.md` - Informações do sistema

### Problemas Técnicos:
- Consulte `GUIA-INSTALACAO-SIMPLES.md`

---

## ✅ Checklist Diário

Use esta lista para organizar seu trabalho:

**Ao Comprar Camisas:**
- [ ] Cadastrar cada camisa no sistema
- [ ] Adicionar foto (se possível)
- [ ] Conferir valor de compra

**Ao Vender:**
- [ ] Cadastrar cliente (se novo)
- [ ] Registrar venda no sistema
- [ ] Anotar se foi à vista ou a prazo

**Ao Receber Pagamento:**
- [ ] Registrar pagamento no sistema
- [ ] Conferir saldo devedor atualizado

**Fim do Dia:**
- [ ] Conferir painel de gerenciamento
- [ ] Verificar lucro do dia
- [ ] Fazer backup (recomendado semanalmente)

---

**Dúvidas? Explore o sistema! Não tenha medo de clicar e testar.** 😊

**Todos os dados ficam salvos automaticamente!** 💾
