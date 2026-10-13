# Sustentabilidade API

## 📖 Propósito da API

A Sustentabilidade API foi desenvolvida com foco em ESG (Environmental, Social and Governance), especificamente no eixo ambiental, permitindo que empresas registrem emissões de carbono e gerenciem ações de redução de impacto ambiental.

O objetivo da aplicação é fornecer uma solução segura para:

- cadastro de empresas;
- autenticação com JWT;
- monitoramento de emissões de CO2;
- gerenciamento de ações de redução de carbono;
- controle de acesso entre empresas.

A API foi construída com regras de segurança para garantir que uma empresa não alterar dados de outra empresa.

Mesmo autenticado, o usuário só pode:

- cadastrar emissões para sua própria empresa;
- criar agendamentos vinculados às suas emissões;
- atualizar seus próprios agendamentos;
- concluir seus próprios agendamentos;
- cancelar seus próprios agendamentos.

Caso tente acessar ou alterar dados de outra empresa, a API retorna:

403 Fordidden

Isso garante isolamento e segurança entre os clientes da plataforma.


API REST desenvolvida em Java com Spring Boot para monitoramento de emissões de carbono e gerenciamento de agendamentos de redução de carbono.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- Bean Validation
- Oracle Database
- Flyway
- Maven

---

# Tema ESG

O projeto está relacionado ao eixo ambiental de ESG, com foco em:

- cadastro de empresas;
- registro de emissões de carbono;
- criação de ações para redução de carbono;
- controle de status dos agendamentos;
- proteção dos dados por autenticação JWT.

---

# Regras de negócio

## Empresa

- A empresa deve se cadastrar com nome, cidade, estado, e-mail e senha.
- A senha é criptografada antes de ser salva no banco.
- O login é feito com e-mail e senha.
- Após o login, a empresa recebe um token JWT.
- Endpoints protegidos exigem o envio do token.

## Emissão de carbono

- Uma emissão pertence a uma empresa.
- Para cadastrar uma emissão, é necessário informar uma empresa existente.
- A fonte de emissão deve ser um dos valores válidos do sistema.

### Fontes aceitas

```text
PECUARIA
INDUSTRIA
TRANSPORTE
QUEIMA_COMBUSTIVEL
GERACAO_ENERGIA
AGROPECUARIA
```

## Agendamento de redução de carbono

- Um agendamento está vinculado a uma emissão de carbono.
- Ao ser criado, todo agendamento recebe automaticamente o status `PENDENTE`.
- O cliente não pode cadastrar um agendamento já como `CONCLUIDO` ou `CANCELADO`.
- Somente a empresa dona da emissão pode criar agendamento para ela.
- Somente a empresa dona do agendamento pode atualizar, cancelar ou concluir.
- Agendamento `CANCELADO` não pode ser atualizado nem concluído.
- Agendamento `CONCLUIDO` não pode ser atualizado nem cancelado.
- Agendamento já `CANCELADO` não pode ser cancelado novamente.
- Agendamento já `CONCLUIDO` não pode ser concluído novamente.

### Status possíveis

```text
PENDENTE
CONCLUIDO
CANCELADO
```

---

# Autenticação

A autenticação é feita com JWT.

Após o login, use o token recebido no header:

```http
Authorization: Bearer SEU_TOKEN_AQUI
```

---

# Endpoints

## 1. Registrar empresa

```http
POST /auth/register
```

Endpoint público.

### Request

```json
{
  "nomeEmpresa": "Eco Agro Industrial",
  "nomeCidade": "Ribeirao Preto",
  "siglaEstado": "SP",
  "email": "ecoagro@email.com",
  "senha": "123456"
}
```

### Response

```json
{
  "id": 1,
  "nomeEmpresa": "Eco Agro Industrial",
  "nomeCidade": "Ribeirao Preto",
  "siglaEstado": "SP",
  "email": "ecoagro@email.com"
}
```

---

## 2. Login

```http
POST /auth/login
```

Endpoint público.

### Request

```json
{
  "email": "ecoagro@email.com",
  "senha": "123456"
}
```

### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 3. Listar empresas

```http
GET /empresas
```

Endpoint protegido.

### Header

```http
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 4. Cadastrar emissão de carbono

```http
POST /emissoes-carbono
```

Endpoint protegido.

### Request

```json
{
  "idEmpresa": 1,
  "fonteEmissao": "TRANSPORTE",
  "dataRegistro": "2026-05-23",
  "quantidadeCo2": 850.5
}
```

### Response

```json
{
  "id": 1,
  "empresa": {
    "id": 1,
    "nomeEmpresa": "Eco Agro Industrial"
  },
  "fonteEmissao": "TRANSPORTE",
  "dataRegistro": "2026-05-23",
  "quantidadeCo2": 850.5
}
```

---

## 5. Cadastrar agendamento de redução de carbono

```http
POST /agendamentos-reducao-carbono
```

Endpoint protegido.

### Request

```json
{
  "idEmissao": 1,
  "descricaoAcao": "Troca da frota por veiculos eletricos",
  "nomeResponsavel": "Fernanda Costa",
  "dataInicio": "2026-06-10",
  "dataPrevisao": "2026-11-30",
  "quantidadeReducaoEstimada": 500
}
```

### Response

```json
{
  "id": 1,
  "emissaoCarbono": {
    "id": 1,
    "fonteEmissao": "TRANSPORTE"
  },
  "descricaoAcao": "Troca da frota por veiculos eletricos",
  "nomeResponsavel": "Fernanda Costa",
  "dataInicio": "2026-06-10",
  "dataPrevisao": "2026-11-30",
  "quantidadeReducaoEstimada": 500,
  "statusAgendamento": "PENDENTE"
}
```

---

## 6. Atualizar agendamento

```http
PUT /agendamentos-reducao-carbono/{id}
```

Endpoint protegido.

### Request

```json
{
  "descricaoAcao": "Troca da frota por veiculos eletricos e hibridos",
  "nomeResponsavel": "Mariana Costa",
  "quantidadeReducaoEstimada": 650
}
```

### Não permitido

- Atualizar agendamento cancelado.
- Atualizar agendamento concluído.
- Atualizar agendamento de outra empresa.

---

## 7. Cancelar agendamento

```http
PATCH /agendamentos-reducao-carbono/{id}/cancelar
```

Endpoint protegido.

### Não permitido

- Cancelar agendamento já cancelado.
- Cancelar agendamento concluído.
- Cancelar agendamento de outra empresa.

---

## 8. Concluir agendamento

```http
PATCH /agendamentos-reducao-carbono/{id}/concluir
```

Endpoint protegido.

### Não permitido

- Concluir agendamento já concluído.
- Concluir agendamento cancelado.
- Concluir agendamento de outra empresa.

### Observação

- Após a conclusão do agendamento, o valor da quantidade de redução estimada é subtraído da quantidade total de CO2 da emissão e atualizado automaticamente no banco de dados.
---

# Exemplos de erros

## Campo obrigatório ausente

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação nos campos."
}
```

---

## Empresa não encontrada

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Empresa com ID 999 não encontrada."
}
```

---

## Emissão não encontrada

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Emissão de carbono com ID 999 não encontrada."
}
```

---

## Agendamento não encontrado

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Agendamento com ID 999 não encontrado."
}
```

---

## Ação não permitida

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Não é possível cancelar um agendamento concluído."
}
```

---

# Fluxo de uso

1. Registrar empresa em `/auth/register`.
2. Fazer login em `/auth/login`.
3. Copiar o token JWT retornado.
4. Enviar o token no header Authorization.
5. Cadastrar emissão de carbono.
6. Criar agendamento de redução.
7. Atualizar, cancelar ou concluir conforme as regras.

---

# Observações

- A senha da empresa é armazenada criptografada.
- O token JWT expira após o tempo configurado.
- O sistema impede alteração de dados entre empresas diferentes.
- Todo agendamento inicia como `PENDENTE`.
- Alterações de status ocorrem apenas pelos endpoints específicos.
