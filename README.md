# 🐙 ChamaÊ API — Ecossistema Multiagente de IA para Negócios de Serviço

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em_Desenvolvimento_Ativo-4F46E5?style=for-the-badge)

> **Service-as-a-Software (Serviço como Software):** Back-end **Multi-tenant** em Java e Spring Boot projetado para operar uma equipe digital de agentes de IA no WhatsApp — automatizando atendimento 24/7, agendamentos sem conflito, combate a faltas (*No-Show*) e resumos financeiros para clínicas, barbearias, salões e prestadores de serviço.

---

## 🎯 O Problema e a Proposta de Valor

Pequenos negócios baseados em atendimento perdem receita todos os dias por dois gargalos operacionais: **demora para responder leads no WhatsApp** enquanto a equipe está ocupada e **cadeiras vazias por faltas (*No-Show*)**.

O **ChamaÊ** substitui formulários complexos e robôs engessados (*"Digite 1, Digite 2"*) por uma filosofia de **Interface Invisível (*Invisible UI*)**:

1. O dono configura o **DNA da Empresa** uma única vez (serviços, preços, ticket médio, regras da casa e tom de voz).
2. Os clientes conversam naturalmente no **WhatsApp** — sem baixar aplicativos nem criar senhas.
3. O ecossistema de agentes responde dúvidas, consulta a agenda real, confirma presença e mantém o dono atualizado direto no celular.

---

## 🏗️ Arquitetura Alvo ("Ecossistema Polvo" + Arquitetura Hexagonal)

Em vez de sobrecarregar um único prompt de IA (*Context Pollution*), o ChamaÊ utiliza o padrão **Orchestrator / Router** *(Roteador)* aliado a uma **camada de Cache em 3 níveis** para garantir baixa latência e viabilidade econômica (*Unit Economics*), mantendo todas as regras críticas dentro de um **Núcleo Determinístico em Java**.

```text
[ Mensagem no WhatsApp Cloud API (Oficial) ]
                     │
                     ▼
┌────────────────────────────────────────────────────────────────────┐
│ 1. PORTA ÚNICA (WebhookController — Adapter Hexagonal)             │
│    Identifica o Tenant (slug) e o papel do remetente (Cliente/Dono)│
└────────────────────────────────────────────────────────────────────┘
                     │
                     ▼
┌────────────────────────────────────────────────────────────────────┐
│ 2. ESCUDO DE CACHE (Baixa Latência & Economia de Tokens)           │
│    • Redis: DNA da empresa em RAM (< 1ms) + Rate Limiting          │
│    • Prompt Caching: Redução de 75%–90% no contexto fixo da loja   │
│    • Semantic Cache: Respostas de FAQ repetidas com custo 0 token  │
└────────────────────────────────────────────────────────────────────┘
                     │ (Quando exige raciocínio)
                     ▼
┌────────────────────────────────────────────────────────────────────┐
│ 3. ORQUESTRADOR (Router — Classificação rápida de intenção)        │
└──────┬──────────────────────────┬──────────────────────────┬───────┘
       │                          │                          │
       ▼                          ▼                          ▼
┌──────────────┐          ┌──────────────┐          ┌────────────────┐
│  AGENTE 1    │          │  AGENTE 2    │          │   AGENTE 3     │
│  Recepção &  │          │  Agenda &    │          │  Braço Direito │
│  Vendas (FAQ)│          │  Anti-Falta  │          │  do Dono       │
└──────┬───────┘          └──────┬───────┘          └───────┬────────┘
       │ (Somente leitura)       │ (Tool Calling)           │ (Owner Scope)
       └─────────────────────────┼──────────────────────────┘
                                 ▼
┌────────────────────────────────────────────────────────────────────┐
│ 4. NÚCLEO DETERMINÍSTICO (Java + Spring Boot + PostgreSQL)         │
│    TenantService | CatalogService | AppointmentService | Billing   │
│    (A IA nunca grava direto no banco — obedece aos Services Java)  │
└────────────────────────────────────────────────────────────────────┘
```

---

## 🧠 Decisões de Engenharia e *Trade-offs*

* **Núcleo Determinístico vs. IA Probabilística:** A IA cuida da comunicação natural, mas nunca "chuta" disponibilidade. Toda marcação e cálculo financeiro ocorre via **Tool Calling** invocando regras validadas nos `Services` Java e persistidas no PostgreSQL.
* **Desacoplamento com DTOs:** Uso de `Record` / DTOs dedicados (`TenantCreateDTO`) na borda da API para evitar exposição direta de entidades JPA e prevenir vulnerabilidades como *Mass Assignment*.
* **Validação *Fail-Fast* & Tratamento Global de Erros:** Regras de unicidade (como `slug` duplicado) são barradas antecipadamente com *Guard Clauses* no `Service` e traduzidas pelo `GlobalExceptionHandler` (`@RestControllerAdvice`) para respostas RESTful padronizadas (`409 Conflict`), eliminando vazamento de erros genéricos `500`.
* **Arquitetura Hexagonal (*Ports & Adapters*) para Integrações:** A comunicação com a API Oficial da Meta (*WhatsApp Cloud API*) será isolada atrás de interfaces (*Ports*), evitando *Vendor Lock-in* e mantendo o domínio 100% testável.
* **Modelagem Orientada a Negócio (*Business-Oriented Domain*):** Atributos como `averageTicket` e regra automática de *Trial* de 7 dias nascem no núcleo do domínio para calcular o **ROI** *(dinheiro recuperado em faltas evitadas)* de cada cliente.

---

## 🛠️ Stack Tecnológica

| Camada | Tecnologias |
| :--- | :--- |
| **Em uso atual** | Java, Spring Boot, Spring Web, Spring Data JPA, PostgreSQL, Docker & Docker Compose, Maven |
| **No Roadmap da V1** | Bean Validation, Spring Security (JWT), Redis (Cache & Rate Limiting), LLMs com Prompt Caching & Tool Calling, WhatsApp Cloud API (Webhooks), Angular (Painel Web) |

---

## ✅ Status Atual & Roadmap

Este projeto está sendo desenvolvido em entregas incrementais (*GitHub Flow* com *Issues* e *Conventional Commits*).

### Concluído até o momento
- [x] Ambiente containerizado com **PostgreSQL via Docker Compose** (`docker-compose.yml`).
- [x] Estrutura base **Multi-tenant** em camadas (`Controller` ➔ `Service` ➔ `Repository` ➔ `Entity`).
- [x] Endpoint de cadastro de estabelecimentos (`POST /api/tenants`) com cálculo automático de **Trial de 7 dias** e modelagem de **`averageTicket`**.
- [x] Validação **Fail-Fast** contra duplicidade de `slug` usando `Optional` e *Guard Clause*.
- [x] **`GlobalExceptionHandler`** (`@RestControllerAdvice`) traduzindo conflitos de regra de negócio para **`HTTP 409 Conflict`** com payload JSON padronizado.

### Próximas Etapas (Fases da V1)
- [ ] **Fase 1 — Núcleo Determinístico:** DNA da Empresa + Bean Validation (`Issue #1`), Catálogo de Serviços isolado por Tenant (`Issue #2`), Regras de Agendamento e Conflito de Horários (`Issue #3`) e Autenticação JWT.
- [ ] **Fase 2 — Cérebro Multiagente & Cache:** Redis, Prompt Caching, Roteador (*Orchestrator*) e Agentes 1 e 2 com *Tool Calling*.
- [ ] **Fase 3 — WhatsApp Oficial & Dono:** *Adapter* da WhatsApp Cloud API via Webhook, Agente 3 (Gestor do Dono), lembretes anti-falta e *Human Handoff*.
- [ ] **Fase 4 — Painel Enxuto & Produção:** Interface Web em Angular e deploy em VPS com Docker.

---

## 📂 Estrutura Atual do Projeto

```text
src/main/java/com/chamae/api
├── ApiApplication.java
├── exception
│   └── GlobalExceptionHandler.java   # Tratamento global de exceções REST (409 Conflict)
└── tenant
    ├── Tenant.java                   # Entidade multi-tenant do estabelecimento
    ├── TenantController.java         # Endpoints REST (/api/tenants)
    ├── TenantCreateDTO.java          # Contrato de entrada desacoplado da entidade
    ├── TenantRepository.java         # Persistência JPA + consultas por slug
    └── TenantService.java            # Regras de negócio (Trial 7 dias, Fail-Fast)
```

---

## 🚀 Como Executar Localmente

### Pré-requisitos
* **Java 21+**
* **Docker** e **Docker Compose**

### Passo a passo

1. **Clone o repositório:**
```bash
git clone https://github.com/vitormrock/chamae-api.git
cd chamae-api
```

2. **Suba o banco de dados PostgreSQL via Docker:**
```bash
docker compose up -d
```

3. **Execute a aplicação Spring Boot:**
```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

---

## 🧪 Testando a API (`curl`)

### 1. Cadastrar um novo estabelecimento (`POST /api/tenants`)
```bash
curl -i -X POST http://localhost:8080/api/tenants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Barbearia do Zé",
    "slug": "barbearia-do-ze",
    "averageTicket": 80.00
  }'
```

### 2. Testar proteção *Fail-Fast* de `slug` duplicado (`HTTP 409 Conflict`)
Ao enviar a requisição novamente com o mesmo `slug`, o `GlobalExceptionHandler` intercepta a exceção e retorna:

```http
HTTP/1.1 409 Conflict
Content-Type: application/json

{
  "message": "Já existe uma loja cadastrada com este link (slug)."
}
```

---

## 👤 Autor

Desenvolvido por **Vitor Moreira** — em construção pública aplicando boas práticas de arquitetura Java/Spring Boot e sistemas multiagente de IA.

[![GitHub](https://img.shields.io/badge/GitHub-vitormrock-181717?style=flat-square&logo=github)](https://github.com/vitormrock)