<!-- 
  ███████╗██╗   ██╗██████╗  ██████╗ ███████╗    ██████╗  █████╗  ██████╗██╗  ██╗
  ██╔════╝██║   ██║██╔══██╗██╔════╝ ██╔════╝    ██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████╗██║   ██║██████╔╝██║  ███╗█████╗      ██████╔╝███████║██║     █████╔╝ 
  ╚════██║██║   ██║██╔══██╗██║   ██║██╔══╝      ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ███████║╚██████╔╝██████╔╝╚██████╔╝███████╗    ██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝    ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
-->

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Swing-316796?style=for-the-badge&logo=java&logoColor=white" alt="Swing"/>
  <img src="https://img.shields.io/badge/GUI-Blue?style=for-the-badge&logo=windows&logoColor=white" alt="GUI"/>
</div>

<br/>

# 🏦 Super Banco — Sistema de Gestão de Clientes

> Um sistema bancário desktop em Java com interface gráfica intuitiva, validações robustas e gerenciamento completo de clientes. Desenvolvido para demonstrar boas práticas de programação orientada a objetos, tratamento de erros e experiência do usuário.

---

## 🎯 Visão Geral

O **Super Banco** é uma aplicação desktop que permite o cadastro, consulta, edição e exclusão de clientes, com foco em:

✅ Validação de CPF único  
✅ Confirmação de exclusão com alerta visual  
✅ Edição segura de dados  
✅ Busca por CPF com retorno detalhado  
✅ Interface limpa e responsiva

Ideal para projetos acadêmicos, portfólio pessoal ou demonstração de habilidades em Java Swing e lógica de negócios.

---

## 🖥️ Funcionalidades Principais

### 1. ✅ Cadastro de Cliente
- Campos: Nome completo, CPF (11 dígitos), Telefone, Data de Nascimento, Endereço e Saldo Inicial.
- **Validação automática de CPF duplicado** — impede cadastros repetidos.
- Botão verde “Cadastrar Cliente” com feedback visual de sucesso.

### 2. 🔍 Busca por CPF
- Campo de busca para localizar cliente rapidamente.
- Ao encontrar, exibe todas as informações em uma janela modal com ícone informativo.
- Mostra nome, CPF, telefone, data de nascimento, endereço e saldo atual.

### 3. 📝 Edição de Cliente
- Selecionando um cliente na tabela, clique em “Editar Cliente”.
- Os dados são carregados nos campos para modificação.
- Botão “Salvar Alterações” atualiza os dados automaticamente na lista.

### 4. ❌ Remoção Segura de Cliente
- Clique em “Remover Cliente” para excluir o registro selecionado.
- **Confirmação com janela de alerta** — previne exclusões acidentais.
- Mensagem clara com CPF do cliente a ser removido.

### 5. 📊 Visualização em Tabela
- Lista todos os clientes cadastrados em uma tabela organizada:
  - Nome
  - CPF
  - Telefone
  - Saldo (formatado como R$)
- Linhas destacadas ao selecionar um cliente.

---

## 🎨 Screenshots

| Cadastro de Cliente | Edição de Cliente |
|---------------------|-------------------|
| ![Tela Inicial](prints/Tela%20inicial.png) | ![Edição](prints/Tela%20de%20Atualização%20de%20Cadastro.png) |

| Confirmação de Exclusão | Informações do Cliente |
|--------------------------|------------------------|
| ![Exclusão](prints/Tela%20de%20Remoção%20de%20Cliente.png) | ![Busca](prints/Busca%20de%20dados%20dos%20Clientes.png) |

> 💡 *As imagens estão na pasta `prints/` — caminho verificado e corrigido.*

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17+
- **Interface Gráfica**: Java Swing (JFrame, JTable, JOptionPane, JTextField, etc.)
- **Gerenciamento de Dados**: Listas dinâmicas (`ArrayList`) — sem banco de dados externo (ideal para demonstração)
- **Validações**: Regras de negócio implementadas com métodos customizados
- **Design**: Layout limpo, botões coloridos e mensagens de alerta visuais

---

## 📁 Estrutura do Projeto
