# 🍴 Restaurante Microservices Ecosystem - Tech Challenge Fase 3

Este projeto consiste em uma arquitetura distribuída de microserviços para a gestão de um ecossistema de restaurante, abrangendo desde o cadastro de clientes e restaurantes até o fluxo complexo de pedidos e processamento assíncrono de pagamentos.

O sistema foi desenvolvido utilizando **Quarkus** (Supersonic Subatomic Java) e foca em alta performance, escalabilidade, resiliência e segurança robusta.

---

## 🏗️ Arquitetura do Sistema

O projeto segue os princípios da **Clean Architecture** (Arquitetura Limpa) e **Hexagonal Architecture**, garantindo que as regras de negócio fiquem isoladas de detalhes de infraestrutura e protocolos de comunicação.

### Microserviços:
*   **Cliente Service:** Gerencia o cadastro de usuários e perfis. Realiza a sincronização automática de identidades com o **Keycloak**.
*   **Restaurante Service:** Gerencia os estabelecimentos, tipos de cozinha e itens de cardápio.
*   **Pedido Service:** Orquestrador principal. Expõe uma API **GraphQL**, realiza validações síncronas via **gRPC** com os demais serviços e gerencia o ciclo de vida do pedido.
*   **Pagamento Service:** Microserviço focado em integração com gateways externos (Procpag). Consome eventos do **RabbitMQ** e implementa padrões de resiliência.
*   **Base Library:** Módulo compartilhado (JAR) contendo utilitários, interceptores de segurança (REST/gRPC), constantes, eventos e exceções comuns.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java 21/25** | Linguagem principal utilizando recursos modernos. |
| **Quarkus** | Framework Java nativo para nuvem de alto desempenho. |
| **PostgreSQL** | Banco de dados relacional (bancos isolados por serviço). |
| **Keycloak** | Gestão de Identidade e Acesso (IAM) via OIDC e JWT. |
| **RabbitMQ** | Mensageria assíncrona para o fluxo desacoplado de pagamentos. |
| **gRPC** | Comunicação síncrona de baixa latência entre microserviços. |
| **GraphQL** | Interface de consulta flexível e agregadora para o usuário. |
| **Flyway** | Versionamento e migração automatizada de esquemas SQL. |
| **Hibernate Envers** | Auditoria completa de todas as tabelas (histórico de revisões). |
| **SmallRye Fault Tolerance** | Resiliência com Circuit Breaker, Retry e Fallback. |

---

## 🚀 Como Rodar o Projeto

### 📋 Pré-requisitos:
*   Docker e Docker Compose instalados.
*   Java JDK 21 ou superior (Projeto compatível com JDK 25).
*   Maven 3.8+ instalado.

### 1️⃣ Preparar o módulo compartilhado (Base)
Como os microserviços dependem do módulo `base`, você deve instalá-lo no seu repositório Maven local antes de subir os serviços:
```bash
# Na pasta raiz do projeto (/tech-challenge-restaurante)
mvn clean install -N
mvn clean install -DskipTests

2️⃣ Subir a infraestrutura (Docker)
Inicie os bancos de dados, RabbitMQ, Keycloak e o simulador de pagamento:
code
Bash
docker-compose up -d
O Keycloak subirá com o Realm restaurante-realm e o usuário joao.silva@email.com pré-configurados via importação de JSON.
3️⃣ Executar os microserviços
Você pode iniciar cada serviço via terminal ou através da sua IDE:
code
Bash
# Exemplo para rodar o serviço de pedidos
cd pedido
mvn quarkus:dev
🔐 Fluxo de Segurança e Resiliência
Segurança JWT: O acesso é protegido por tokens. O serviço de Pedido captura o JWT do usuário logado e o propaga automaticamente via Client Interceptor gRPC para os serviços de Cliente e Restaurante.
Mensageria: Ao criar um pedido, o status inicial é RECEBIDO e um evento pedido.criado é enviado ao RabbitMQ. O serviço de Pagamento processa este evento de forma assíncrona.
Resiliência (Item 5.4 do Desafio):
Retry: 5 tentativas automáticas em caso de erro temporário (408/502) no gateway de pagamento.
Circuit Breaker: Interrompe chamadas ao gateway se houver falha persistente, protegendo a saúde do sistema.
Fallback: Se o pagamento falhar ou o serviço estiver offline, o pedido é marcado como PENDENTE_PAGAMENTO e enviado para a fila pagamento.pendente para reprocessamento futuro.
🔍 Endpoints e Portas do Ecossistema
Serviço	Porta	Descrição
Keycloak	8180	Autenticação e Console Admin (admin/admin).
RabbitMQ	15672	Painel de controle de filas (guest/guest).
Cliente	9091	Gerenciamento de Clientes e Perfis.
Restaurante	9092	Gestão de Estabelecimentos e Cardápios.
Pedido	9093	Orquestração via GraphQL (/q/graphql-ui).
Pagamento	9094	Worker de processamento de transações.
Procpag	8089	Simulador externo de gateway de pagamento.
Este projeto é parte integrante do Tech Challenge - Fase 3 - Engenharia de Software.
