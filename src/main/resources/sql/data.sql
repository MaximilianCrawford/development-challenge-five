INSERT INTO usuario(nome, senha, enabled)
VALUES (
    'user',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
    1
);

INSERT INTO usuario(nome, senha, enabled)
VALUES (
    'cliente',
    '$2a$10$66Lc0B8JihaYFReGn.K79.lFPQYWLfTFFBU1l3H1wnouGKKrHLSby',
    1
);

INSERT INTO authority(authority) VALUES ('ROLE_USER');

INSERT INTO authority_usuario(id_authority, id_usuario) VALUES (1, 1);