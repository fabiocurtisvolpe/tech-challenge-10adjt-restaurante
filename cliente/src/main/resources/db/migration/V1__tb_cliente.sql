--Tabela de Controle de Revisão (Obrigatória para o Hibernate Envers)
create table if not exists revinfo (
                                       rev INTEGER primary key,
                                       revtstmp BIGINT
);

-- Sequência para a tabela revinfo
create sequence if not exists revinfo_seq
    start with
        1 increment by 1;

-- ===================================================================
-- TABELA CLIENTE
-- ===================================================================
create table if not exists tb_cliente (
                                          id BIGSERIAL primary key,
                                          nome VARCHAR(50) not null,
                                          cpf VARCHAR(20) not null,
                                          email VARCHAR(50) not null,
                                          senha VARCHAR(255) not null,
                                          dt_cadastro TIMESTAMP not null default CURRENT_TIMESTAMP,
                                          id_perfil BIGINT not null,
                                          constraint uk_cliente_cpf_email unique (cpf, email)
);

-- Tabela de Auditoria Cliente
create table if not exists tb_cliente_aud (
                                              id BIGINT not null,
                                              rev INTEGER not null,
                                              revtype smallint,
                                              nome VARCHAR(50),
                                              cpf VARCHAR(20),
                                              email VARCHAR(50),
                                              senha VARCHAR(255),
                                              dt_cadastro TIMESTAMP,
                                              primary key (id,
                                                           rev),
                                              constraint fk_cliente_aud_revinfo foreign key (rev) references revinfo (rev)
);
-- ===================================================================
-- TABELA ENDERECO
-- ===================================================================
create table if not exists tb_endereco (
                                           id BIGSERIAL primary key,
                                           id_cliente BIGINT not null,
                                           rua VARCHAR(100) not null,
                                           bairro VARCHAR(50) not null,
                                           cep VARCHAR(10) not null,
                                           complemento VARCHAR(50),
                                           numero VARCHAR(10),
                                           cidade VARCHAR(50) not null,
                                           uf CHAR(2) not null,
                                           principal BOOLEAN default false,
                                           observacao TEXT,
                                           constraint fk_endereco_cliente foreign key (id_cliente) references tb_cliente (id) on
                                               delete
                                               cascade
);
-- Tabela de Auditoria Endereço
create table if not exists tb_endereco_aud (
                                               id BIGINT not null,
                                               rev INTEGER not null,
                                               revtype smallint,
                                               id_cliente BIGINT,
                                               rua VARCHAR(100),
                                               bairro VARCHAR(50),
                                               cep VARCHAR(10),
                                               complemento VARCHAR(50),
                                               numero VARCHAR(10),
                                               cidade VARCHAR(50),
                                               uf CHAR(2),
                                               principal BOOLEAN,
                                               observacao TEXT,
                                               primary key (id,
                                                            rev),
                                               constraint fk_endereco_aud_revinfo foreign key (rev) references revinfo (rev)
);

-- ===================================================================
-- TABELA PERFIL
-- ===================================================================
create table if not exists tb_perfil (
                                         id SERIAL primary key,
                                         nome VARCHAR(20) not null unique
);

create table if not exists tb_perfil_aud (
                                             id INTEGER not null,
                                             rev INTEGER not null,
                                             revtype smallint not null,
                                             nome VARCHAR(20),
                                             primary key (id, rev),
                                             constraint fk_perfil_aud_revinfo foreign key (rev) references revinfo(rev)
);

insert into tb_perfil (nome) values ('ROLE_CLIENTE') on conflict (nome) do nothing;
