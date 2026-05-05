-- Criando a tabela base
CREATE TABLE pessoa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    is_cliente BOOLEAN DEFAULT FALSE,
    is_fornecedor BOOLEAN DEFAULT FALSE,
    is_funcionario BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Criando a tabela de Pessoa Física
CREATE TABLE pessoa_fisica (
    pessoa_id BIGINT NOT NULL,
    cpf VARCHAR(14),
    rg VARCHAR(20),
    PRIMARY KEY (pessoa_id),
    CONSTRAINT fk_pessoa_fisica_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Criando a tabela de Pessoa Jurídica
CREATE TABLE pessoa_juridica (
    pessoa_id BIGINT NOT NULL,
    cnpj VARCHAR(18),
    inscricao_estadual VARCHAR(20),
    PRIMARY KEY (pessoa_id),
    CONSTRAINT fk_pessoa_juridica_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Passo A: Move os nomes para a tabela pai e marca como cliente
INSERT INTO pessoa (id, nome, is_cliente)
SELECT id, nome_cliente, TRUE FROM cliente;

-- Passo B: Vincula esses IDs na tabela de Pessoa Física (para não perder a herança)
INSERT INTO pessoa_fisica (pessoa_id)
SELECT id FROM cliente;