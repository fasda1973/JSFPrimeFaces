CREATE TABLE dados_cliente (
    pessoa_id BIGINT NOT NULL,
    limite_credito DECIMAL(10,2),
    PRIMARY KEY (pessoa_id),
    CONSTRAINT fk_dados_cliente_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;