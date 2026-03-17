# 🍴 Restaurante Microservices Ecosystem - Tech Challenge Fase 3

Este projeto consiste em uma arquitetura distribuída de microserviços para a gestão de um ecossistema de restaurante, abrangendo desde o cadastro de clientes e restaurantes até o fluxo complexo de pedidos e processamento assíncrono de pagamentos.

O sistema foi desenvolvido utilizando **Quarkus** (Supersonic Subatomic Java) e foca em alta performance, escalabilidade, resiliência e segurança robusta.

---

## 🏗️ Arquitetura do Sistema

O projeto segue os princípios da **Clean Architecture** (Arquitetura Limpa) e **Hexagonal Architecture**, garantindo que as regras de negócio fiquem isoladas de detalhes de infraestrutura.

### Microserviços:
*   **Cliente Service:** Gerencia o cadastro de usuários e perfis. Realiza a sincronização automática de identidades com o Keycloak.
*   **Restaurante Service:** Gerencia os estabelecimentos, tipos de cozinha e itens de cardápio.
*   **Pedido Service:** Orquestrador principal. Expõe uma API **GraphQL**, valida dados via **gRPC** com os demais serviços e gerencia o ciclo de vida do pedido.
*   **Pagamento Service:** Microserviço focado em integração com gateways externos (Procpag). Consome eventos do RabbitMQ e implementa padrões de resiliência.
*   **Base Library:** Módulo compartilhado (JAR) contendo utilitários, interceptores de segurança (REST/gRPC), constantes e exceções comuns.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java 21/25** | Linguagem principal do ecossistema. |
| **Quarkus** | Framework Java nativo para nuvem (Supersonic Subatomic). |
| **PostgreSQL** | Banco de dados relacional (bancos independentes por serviço). |
| **Keycloak** | Gestão de Identidade e Acesso (IAM) via OIDC e JWT. |
| **RabbitMQ** | Mensageria assíncrona para o fluxo de pedido e pagamento. |
| **gRPC** | Comunicação síncrona de alta performance entre microserviços. |
| **GraphQL** | Interface de consulta flexível para o usuário final. |
| **Flyway** | Versionamento e migração de esquemas de banco de dados. |
| **Hibernate Envers** | Auditoria completa de todas as tabelas (histórico de alterações). |
| **SmallRye Fault Tolerance** | Implementação de Circuit Breaker, Retry e Fallback. |

---

## 🚀 Como Rodar o Projeto

### 📋 Pré-requisitos:
*   Docker e Docker Compose.
*   Java JDK 21 ou superior (Projeto compatível com JDK 25).
*   Maven 3.8+ instalado.

### 1️⃣ Preparar o módulo compartilhado (Base)
Como os microserviços dependem do módulo `base`, instale-o no seu repositório local:
```bash
# Na pasta raiz do projeto
mvn clean install -DskipTests
