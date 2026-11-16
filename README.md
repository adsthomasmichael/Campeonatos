# SISTEMA DE GERENCIAMENTO DE CAMPEONATOS ESPORTIVOS

## 1. Introdução

Este trabalho apresenta o desenvolvimento de um backend RESTful para o gerenciamento de campeonatos
esportivos, elaborado como Trabalho do Grau A da disciplina de Desenvolvimento de Backend. O sistema
contempla o cadastro e a administração de três entidades principais — Campeonato, Time e Jogador —
permitindo representar, de forma simplificada, a organização de competições esportivas, as equipes que
delas participam e os atletas vinculados a cada equipe.
O problema abordado é comum a federações, ligas e organizadores de eventos esportivos: a necessidade
de manter um cadastro centralizado e consistente de campeonatos, dos times inscritos em cada edição e
dos jogadores que compõem cada elenco, evitando duplicidade de informações e permitindo consultas
rápidas sobre quem disputa o quê.
O objetivo do sistema é disponibilizar uma API REST completa, com operações de criação, consulta,
atualização e remoção (CRUD) para as três entidades, incluindo o gerenciamento do relacionamento
muitos-para-muitos entre Campeonato e Time (um time pode disputar vários campeonatos ao longo do
tempo, e um campeonato reúne vários times). A API foi construída seguindo uma arquitetura em camadas
(Controller, Service, Entity e DTO), com persistência em banco de dados relacional, validação de dados de
entrada e tratamento padronizado de erros, conforme detalhado nas seções seguintes.

## 2. Arquitetura da Aplicação

A aplicação foi construída em Java 21, utilizando o framework Spring Boot, e organizada em camadas com
responsabilidades bem definidas, conforme ilustrado na Figura 1.
<img width="775" height="171" alt="image" src="https://github.com/user-attachments/assets/14dc2ac2-e3b1-4592-882f-882c443350c1" />

Figura 1 — Fluxo de uma requisição através das camadas da aplicação

### 2.1 Camadas
• **Controller:** classes anotadas com @RestController, responsáveis por expor os endpoints HTTP, validar a
entrada (@Valid) e delegar o processamento ao Service.

• **Service:** classes anotadas com @Service, responsáveis pelas regras de negócio, pelas transações
(@Transactional) e pela conversão entre Entity e DTO.

• **Repository:** interfaces que estendem JpaRepository, responsáveis pelo acesso a dados via Spring Data
JPA.

• **Entity:** classes anotadas com @Entity, que representam as tabelas do banco de dados (mapeamento
objeto-relacional).

• **DTO (Data Transfer Object):** records usados para desacoplar o formato de entrada/saída da API do
modelo interno de persistência, evitando expor detalhes da entidade e recursão infinita em
relacionamentos bidirecionais.

• **Exception Handler:** classe anotada com @RestControllerAdvice, que centraliza o tratamento de erros da
aplicação, retornando respostas padronizadas em JSON.

### 2.2 Tecnologias utilizadas
• Java 21
• Spring Boot 4.1.1 (Web, Data JPA, Validation)
• PostgreSQL (banco de dados relacional)
• Lombok (redução de código repetitivo nas entidades)
• springdoc-openapi / Swagger UI (documentação interativa da API)
• Maven (gerenciamento de dependências e build)
• Postman (testes e demonstração das rotas API)

## 3. Modelagem de Dados
O modelo de dados é composto por três entidades principais — Campeonato, Time e Jogador —
relacionadas conforme o diagrama simplificado abaixo (Figura 2).
<img width="732" height="576" alt="image" src="https://github.com/user-attachments/assets/17fa9a4f-2f6a-45e2-a8ec-204a085de82b" />

### 3.1 Entidade Campeonato
<img width="770" height="270" alt="image" src="https://github.com/user-attachments/assets/8c5d8851-13ae-48c2-bb6f-8506126c45f4" />

### 3.2 Entidade Time
<img width="765" height="232" alt="image" src="https://github.com/user-attachments/assets/38f3747b-9809-483b-b131-4cd099da87a8" />

### 3.3 Entidade Jogador
<img width="767" height="267" alt="image" src="https://github.com/user-attachments/assets/e7d65f11-cda6-4bca-9cfb-87cf62219e74" />

## 4. Relacionamentos entre as Entidades
### 4.1 Campeonato ↔ Time (muitos-para-muitos)
Um time pode disputar diversos campeonatos ao longo do tempo, e um campeonato reúne vários times —
trata-se, portanto, de um relacionamento N:N. Essa relação é implementada por meio de uma tabela
associativa (campeonato_time), mapeada no código através das anotações @ManyToMany e @JoinTable
na entidade Campeonato, e @ManyToMany(mappedBy = "times") na entidade Time. A API expõe
endpoints próprios para associar e desassociar um time de um campeonato, sem a necessidade de recriar
nenhuma das duas entidades.

### 4.2 Time ↔ Jogador (um-para-muitos)
Cada time possui vários jogadores, mas cada jogador pertence a exatamente um time — trata-se de um
relacionamento 1:N. Essa relação é mapeada através da anotação @OneToMany(mappedBy = "time") na
entidade Time e @ManyToOne na entidade Jogador, que mantém a chave estrangeira (time_id).

## 5. Descrição das Rotas da API
### 5.1 Campeonatos — /api/campeonatos
<img width="767" height="322" alt="image" src="https://github.com/user-attachments/assets/4060d009-7b40-40b1-bd40-c8f08397caef" />

### 5.2 Times — /api/times
<img width="767" height="266" alt="image" src="https://github.com/user-attachments/assets/ea34d0c5-1967-473b-966b-25807066cc8f" />

### 5.3 Jogadores — /api/jogadores
<img width="766" height="231" alt="image" src="https://github.com/user-attachments/assets/2aefb929-c193-4103-a8eb-43a473e86a1e" />










