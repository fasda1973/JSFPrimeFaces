-- 1. Desativa a checagem de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Ajusta os IDs das tabelas principais (Primary Keys)
ALTER TABLE empresa MODIFY COLUMN id BIGINT AUTO_INCREMENT;
ALTER TABLE ramo_atividade MODIFY COLUMN id BIGINT AUTO_INCREMENT;
ALTER TABLE usuario MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- 3. Ajusta as colunas que são Chaves Estrangeiras (Foreign Keys)
-- Na tabela Empresa, a coluna que aponta para RamoAtividade precisa ser BIGINT também
ALTER TABLE empresa MODIFY COLUMN ramo_atividade_id BIGINT;

-- Se houver outras FKs, ajuste-as aqui (Exemplo: se cliente tem usuario_id)
-- ALTER TABLE cliente MODIFY COLUMN usuario_id BIGINT;

-- 4. Reativa a checagem de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 1;