-- Dialect: PostgreSQL

-- CREATE DATABASE spring_api

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario serial PRIMARY KEY,
    nome varchar,
    senha varchar,
    enabled integer NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS authority (
    id_authority serial PRIMARY KEY,
    authority varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS authority_usuario (
    id_authority_usuario serial PRIMARY KEY,
    id_authority integer NOT NULL,
    id_usuario integer NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY(id_usuario)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_authority FOREIGN KEY(id_authority)
        REFERENCES authority(id_authority)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endereco (
    id_endereco serial PRIMARY KEY,
    cep varchar NOT NULL,
    rua varchar,
    bairro varchar,
    cidade varchar,
    complemento varchar,
    uf char(2),
    numero integer
);

CREATE TABLE IF NOT EXISTS paciente (
    id_paciente serial PRIMARY KEY,
    data_nascimento date NOT NULL,
    nome varchar,
    email varchar UNIQUE,
    id_endereco integer,
    CONSTRAINT fk_endereco FOREIGN KEY(id_endereco)
        REFERENCES endereco(id_endereco)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);
