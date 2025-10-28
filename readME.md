# Plataforma de Voluntariado e Impacto Social

## Descrição
Sistema que conecta voluntários a ONGs e projetos sociais, permitindo gerenciamento de eventos, inscrições e feedbacks.  
O projeto busca facilitar a interação entre voluntários e organizações, promovendo engajamento e impacto social positivo.

### Diferenciais
Sistema de matching baseado em interesses e habilidades do voluntário.
Gamificação com conquistas por número de horas voluntárias.

---

## Etapa 1 - Análise do Problema e Requisitos

### Problema
Voluntários têm dificuldade em encontrar projetos compatíveis com seus interesses e habilidades.
ONGs têm dificuldade em gerenciar voluntários e medir impacto.

### Funcionalidades Principais
Cadastro de voluntários e ONGs.
Sistema de matching por interesses e habilidades.
Gestão de eventos e inscrições.

### Atores Envolvidos
Voluntário
ONG/Projeto Social
Administrador da plataforma

### Requisitos Não Funcionais
**Desempenho:** respostas rápidas para buscas de eventos e matching.
**Segurança:** autenticação, criptografia de dados pessoais.
**Escalabilidade:** suportar aumento de voluntários e ONGs sem perda de performance.
**Disponibilidade:** sistema online 24/7.

### Fluxos Iniciais
1. Voluntário se cadastra → Preenche interesses/habilidades → Recebe sugestões de projetos.
2. ONG cria evento → Recebe inscrições.

---

## Etapa 2 - Escolha e Justificativa da Arquitetura

**Estilo Arquitetural:** MVC (Model-View-Controller) com arquitetura em camadas.
**Justificativa:**
  - Separação clara entre dados, interface e lógica de negócio.
  - Facilita manutenção, testes e futuras expansões (ex.: microserviços).

**Rationale Arquitetural:**
  - Ponto de atenção: MVC adiciona complexidade inicial, mas melhora escalabilidade e modularidade.
  - Benefício: cada módulo pode evoluir independentemente (ex.: dashboard atualizado sem impactar o sistema de matching).

---

## Etapa 3 - Aplicação de Padrões de Projeto

**Criacional – Singleton:** garantir uma única conexão com o banco de dados.
**Estrutural – Adapter:** integrar serviços externos (Google Maps para localização, APIs de e-mail/SMS para notificações).
**Comportamental – Observer:** notificar voluntários sobre novos eventos ou proximidade de datas.

---

## Etapa 4 - Modelagem Arquitetural

Visualize a modelagem arquitetural completa [aqui](https://lucid.app/lucidchart/e2084ce6-c0ce-49f1-b591-0b0e9fe9de3a/edit?viewport_loc=-4970%2C-8023%2C12405%2C6814%2C0_0&invitationId=inv_2de1fd42-3f62-4476-98ef-6e1e5509f8b1).

---

## Etapa 5 - Estratégias de Segurança e Qualidade

### 1. Estratégias de Segurança
**Autenticação e Autorização:**
  - Login com credenciais criptografadas (hash com bcrypt)
  - Controle de acesso por perfil
  - Sessões expiram automaticamente após inatividade

**Proteção de Dados:**
  - Criptografia de dados sensíveis
  - Comunicação via HTTPS
  - Conformidade com LGPD

**Prevenção de Ataques:**
  - Validação de entradas (SQL Injection, XSS)
  - Limite de tentativas de login
  - Logs de atividades suspeitas

**Integração Segura com APIs Externas:**
  - Chaves de API protegidas e variáveis de ambiente
  - Requisições autenticadas com tokens JWT

### 2. Estratégias de Qualidade
**Princípios SOLID:** para garantir modularidade, manutenibilidade e escalabilidade
**Garantia de Qualidade:** validação de dados, testes unitários e boas práticas de versionamento (Git)
**Monitoramento e Desempenho:** logs automáticos, monitoramento de tempo de resposta e caching de dados

---

## Etapa 6 - Organização do Trabalho e Metodologia

A organização das tarefas e metodologia ágil utilizada estão disponíveis [no Asana](https://app.asana.com/1/1200185110596270/project/1211763682781652/board/1211763886843346).

---

## Etapa 7 - Apresentação Final

O projeto será apresentado com demonstração do sistema, destacando funcionalidades principais, interface, segurança e impacto social.

---

## Tecnologias Utilizadas
Backend: Java
Banco de Dados: MySQL
---

## Contato
Desenvolvido por **Geovanna Krüger** e **Isabeli Ferreira**.# Plataforma de Voluntariado e Impacto Social

## Descrição
Sistema que conecta voluntários a ONGs e projetos sociais, permitindo gerenciamento de eventos, inscrições e feedbacks.  
O projeto busca facilitar a interação entre voluntários e organizações, promovendo engajamento e impacto social positivo.

### Diferenciais
Sistema de matching baseado em interesses e habilidades do voluntário.
Gamificação com conquistas por número de horas voluntárias.

---

## Etapa 1 - Análise do Problema e Requisitos

### Problema
Voluntários têm dificuldade em encontrar projetos compatíveis com seus interesses e habilidades.
ONGs têm dificuldade em gerenciar voluntários e medir impacto.

### Funcionalidades Principais
Cadastro de voluntários e ONGs.
Sistema de matching por interesses e habilidades.
Gestão de eventos e inscrições.

### Atores Envolvidos
Voluntário
ONG/Projeto Social
Administrador da plataforma

### Requisitos Não Funcionais
**Desempenho:** respostas rápidas para buscas de eventos e matching.
**Segurança:** autenticação, criptografia de dados pessoais.
**Escalabilidade:** suportar aumento de voluntários e ONGs sem perda de performance.
**Disponibilidade:** sistema online 24/7.

### Fluxos Iniciais
1. Voluntário se cadastra → Preenche interesses/habilidades → Recebe sugestões de projetos.
2. ONG cria evento → Recebe inscrições.

---

## Etapa 2 - Escolha e Justificativa da Arquitetura

**Estilo Arquitetural:** MVC (Model-View-Controller) com arquitetura em camadas.
**Justificativa:**
  - Separação clara entre dados, interface e lógica de negócio.
  - Facilita manutenção, testes e futuras expansões (ex.: microserviços).

**Rationale Arquitetural:**
  - Ponto de atenção: MVC adiciona complexidade inicial, mas melhora escalabilidade e modularidade.
  - Benefício: cada módulo pode evoluir independentemente (ex.: dashboard atualizado sem impactar o sistema de matching).

---

## Etapa 3 - Aplicação de Padrões de Projeto

**Criacional – Singleton:** garantir uma única conexão com o banco de dados.
**Estrutural – Adapter:** integrar serviços externos (Google Maps para localização, APIs de e-mail/SMS para notificações).
**Comportamental – Observer:** notificar voluntários sobre novos eventos ou proximidade de datas.

---

## Etapa 4 - Modelagem Arquitetural

Visualize a modelagem arquitetural completa [aqui](https://lucid.app/lucidchart/e2084ce6-c0ce-49f1-b591-0b0e9fe9de3a/edit?viewport_loc=-4970%2C-8023%2C12405%2C6814%2C0_0&invitationId=inv_2de1fd42-3f62-4476-98ef-6e1e5509f8b1).

---

## Etapa 5 - Estratégias de Segurança e Qualidade

### 1. Estratégias de Segurança
**Autenticação e Autorização:**
  - Login com credenciais criptografadas (hash com bcrypt)
  - Controle de acesso por perfil
  - Sessões expiram automaticamente após inatividade

**Proteção de Dados:**
  - Criptografia de dados sensíveis
  - Comunicação via HTTPS
  - Conformidade com LGPD

**Prevenção de Ataques:**
  - Validação de entradas (SQL Injection, XSS)
  - Limite de tentativas de login
  - Logs de atividades suspeitas

**Integração Segura com APIs Externas:**
  - Chaves de API protegidas e variáveis de ambiente
  - Requisições autenticadas com tokens JWT

### 2. Estratégias de Qualidade
**Princípios SOLID:** para garantir modularidade, manutenibilidade e escalabilidade
**Garantia de Qualidade:** validação de dados, testes unitários e boas práticas de versionamento (Git)
**Monitoramento e Desempenho:** logs automáticos, monitoramento de tempo de resposta e caching de dados

---

## Etapa 6 - Organização do Trabalho e Metodologia

A organização das tarefas e metodologia ágil utilizada estão disponíveis [no Asana](https://app.asana.com/1/1200185110596270/project/1211763682781652/board/1211763886843346).

---

## Etapa 7 - Apresentação Final

O projeto será apresentado com demonstração do sistema, destacando funcionalidades principais, interface, segurança e impacto social.

---

## Tecnologias Utilizadas
Backend: Java
Banco de Dados: MySQL
---

## Contato
Desenvolvido por **Geovanna Krüger** e **Isabeli Ferreira**.