🍴 Restaurante Microservices Ecosystem - Tech Challenge Fase 3
Este projeto consiste em uma arquitetura distribuída de microserviços para a gestão de um ecossistema de restaurante, abrangendo desde o cadastro de clientes e restaurantes até o fluxo complexo de pedidos e processamento assíncrono de pagamentos.
O sistema foi desenvolvido utilizando Quarkus (Supersonic Subatomic Java) e foca em alta performance, escalabilidade e resiliência.
🏗️ Arquitetura do Sistema
O projeto segue os princípios da Clean Architecture (Arquitetura Limpa) e Hexagonal Architecture, separando regras de negócio (Core/UseCases) de detalhes de infraestrutura (Adapters/HTTP/gRPC).
Microserviços:
Cliente Service: Gerencia o cadastro de usuários e perfis. Sincroniza identidades com o Keycloak.
Restaurante Service: Gerencia estabelecimentos, tipos de cozinha e cardápios.
Pedido Service: Orquestrador principal. Expõe uma API GraphQL, valida dados via gRPC com outros serviços e inicia o fluxo de cobrança.
Pagamento Service: Consome eventos de novos pedidos, integra-se com gateway externo (Procpag) e gerencia a resiliência de pagamentos.
Base Library: Módulo compartilhado contendo utilitários, interceptores de segurança e exceções comuns.
🛠️ Tecnologias Utilizadas
Tecnologia	Finalidade
Java 21/25	Linguagem principal do ecossistema.
Quarkus	Framework Java nativo para nuvem.
PostgreSQL	Banco de dados relacional (um banco independente por serviço).
Keycloak	Gestão de Identidade e Acesso (IAM) via OIDC e JWT.
RabbitMQ	Mensageria assíncrona para comunicação entre Pedido e Pagamento.
gRPC	Comunicação síncrona de alta performance entre microserviços.
GraphQL	Interface de consulta flexível para o usuário final no serviço de Pedidos.
Flyway	Versionamento e migração de esquemas de banco de dados.
Hibernate Envers	Auditoria completa de todas as tabelas do sistema.
SmallRye Fault Tolerance	Resiliência com Circuit Breaker, Retry e Fallback.
🚀 Como Rodar o Projeto
Pré-requisitos:
Docker e Docker Compose instalados.
Maven 3.8+ instalado.
Passo 1: Preparar o módulo compartilhado
Como os microserviços dependem do módulo base, você deve instalá-lo no seu repositório local:
code
Bash
mvn clean install -DskipTests
Passo 2: Subir a infraestrutura (Databases, RabbitMQ, Keycloak, Procpag)
Na raiz do projeto, execute:
code
Bash
docker-compose up -d
O Keycloak subirá automaticamente com o Realm restaurante-realm, Roles e Clientes configurados através do arquivo de importação.
Passo 3: Executar os microserviços
Você pode rodar cada um em seu terminal respectivo usando o modo dev do Quarkus:
code
Bash
# Exemplo para o serviço de pedidos
cd pedido
mvn quarkus:dev
🔐 Fluxo de Segurança e Mensageria
Autenticação: O usuário obtém um token JWT através do Keycloak.
Propagação de Token: O serviço de Pedido captura o token do usuário e o propaga via interceptores gRPC para os serviços de Cliente e Restaurante para validar a posse do recurso.
Pedido Criado: Ao finalizar um pedido, o serviço publica um evento pedido.criado no RabbitMQ.
Pagamento: O serviço de Pagamento consome o evento, tenta processar via API externa.
Sucesso: Publica pagamento.resultado (Aprovado).
Falha/Timeout: O Circuit Breaker entra em ação e o Fallback move o pedido para a fila pagamento.pendente para reprocessamento futuro.
🔍 Endpoints Principais (Portas Padrão)
Cliente REST: http://localhost:9091/cliente
Restaurante REST: http://localhost:9092/restaurante-private
Pedido GraphQL UI: http://localhost:9093/q/graphql-ui
Keycloak Admin: http://localhost:8180 (admin/admin)
RabbitMQ Management: http://localhost:15672 (guest/guest)
🛡️ Resiliência (Item 5.4 do Desafio)
A integração com o gateway de pagamento conta com:
Retry: 5 tentativas automáticas em caso de erro 502 ou 408.
Circuit Breaker: Interrompe chamadas se o gateway externo estiver instável.
Fallback: Em caso de falha total, o pedido é marcado como PENDENTE_PAGAMENTO e enviado para uma fila de recuperação automática, garantindo que nenhum pedido seja perdido.
Este projeto faz parte do Tech Challenge - Fase 3 - Engenharia de Software.
