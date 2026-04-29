-- 1. (Opcional) Geralmente login também deve ser único
ALTER TABLE usuario ADD CONSTRAINT uk_nome_usuario UNIQUE (nome_usuario);