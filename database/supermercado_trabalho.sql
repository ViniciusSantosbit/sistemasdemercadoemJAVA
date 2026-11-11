-- =============================================================
--  SISTEMA DE SUPERMERCADO - TRABALHO PRATICO POO
--  Java + Swing (JFrame) + MySQL (JDBC) + MVC
--
--  CRUDs simples ..: categoria, fornecedor, funcionario, produto
--  CRUD principal .: venda + item_venda (caixa do supermercado)
--  Nao existe cadastro de cliente: o CPF na nota e opcional.
-- =============================================================
DROP DATABASE IF EXISTS supermercado_trabalho;
CREATE DATABASE supermercado_trabalho DEFAULT CHARACTER SET utf8mb4;
USE supermercado_trabalho;

-- CRUD 1
CREATE TABLE categoria (
    codigo    INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(60) NOT NULL UNIQUE,
    descricao VARCHAR(150)
);

-- CRUD 2
CREATE TABLE fornecedor (
    codigo       INT AUTO_INCREMENT PRIMARY KEY,
    razao_social VARCHAR(120) NOT NULL,
    cnpj         CHAR(14)     NOT NULL UNIQUE,
    telefone     VARCHAR(20),
    email        VARCHAR(100)
);

-- CRUD 3
CREATE TABLE funcionario (
    codigo   INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    cpf      CHAR(11)     NOT NULL UNIQUE,
    telefone VARCHAR(20),
    cargo    VARCHAR(40)  NOT NULL
);

-- CRUD 4 (chave estrangeira para categoria)
CREATE TABLE produto (
    codigo           INT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100)  NOT NULL,
    codigo_barras    VARCHAR(14)   NOT NULL UNIQUE,
    preco            DECIMAL(10,2) NOT NULL,
    estoque          INT           NOT NULL DEFAULT 0,
    codigo_categoria INT           NOT NULL,
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo)
);

-- CRUD PRINCIPAL: venda no caixa (chave estrangeira para funcionario)
CREATE TABLE venda (
    codigo             INT AUTO_INCREMENT PRIMARY KEY,
    cpf_cliente        CHAR(11)      NULL,          -- opcional
    codigo_funcionario INT           NOT NULL,
    data_venda         DATETIME      NOT NULL,
    forma_pagamento    VARCHAR(30)   NOT NULL,
    status             VARCHAR(20)   NOT NULL,      -- FINALIZADA ou CANCELADA
    total              DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (codigo_funcionario) REFERENCES funcionario (codigo)
);

-- itens da venda (liga venda e produto)
CREATE TABLE item_venda (
    codigo         INT AUTO_INCREMENT PRIMARY KEY,
    codigo_venda   INT           NOT NULL,
    codigo_produto INT           NOT NULL,
    quantidade     INT           NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (codigo_venda)   REFERENCES venda (codigo),
    FOREIGN KEY (codigo_produto) REFERENCES produto (codigo)
);

-- ================= DADOS PARA DEMONSTRACAO =================
INSERT INTO categoria (nome, descricao) VALUES
 ('Bebidas', 'Refrigerantes, sucos e aguas'),
 ('Mercearia', 'Arroz, feijao, massas e enlatados'),
 ('Laticinios', 'Leites, queijos e iogurtes'),
 ('Limpeza', 'Produtos de limpeza');

INSERT INTO fornecedor (razao_social, cnpj, telefone, email) VALUES
 ('Distribuidora Planalto de Bebidas Ltda', '12345678000195', '(61) 3333-1000', 'vendas@planalto.com'),
 ('Atacadao Central Alimentos S.A.',        '98765432000110', '(61) 3333-2000', 'pedidos@central.com'),
 ('Laticinios Cerrado Ltda',                '11222333000181', '(61) 3333-3000', 'comercial@cerrado.com');

INSERT INTO funcionario (nome, cpf, telefone, cargo) VALUES
 ('Carlos Souza',  '45678901234', '(61) 98888-1111', 'Operador de Caixa'),
 ('Fernanda Lima', '56789012345', '(61) 98888-2222', 'Operador de Caixa'),
 ('Ricardo Alves', '67890123456', '(61) 98888-3333', 'Gerente');

INSERT INTO produto (nome, codigo_barras, preco, estoque, codigo_categoria) VALUES
 ('Coca-Cola 2L',            '7894900011517', 11.99, 50, 1),
 ('Suco de Uva 1L',          '7894900530018',  9.90, 30, 1),
 ('Arroz Branco 5kg',        '7896006711117', 27.90, 40, 2),
 ('Feijao Carioca 1kg',      '7896006754015',  8.79, 60, 2),
 ('Leite Integral 1L',       '7898080640017',  5.49, 80, 3),
 ('Detergente Neutro 500ml', '7896098900208',  2.79, 70, 4);

INSERT INTO venda (cpf_cliente, codigo_funcionario, data_venda, forma_pagamento, status, total) VALUES
 ('12345678901', 1, '2026-11-02 10:15:00', 'PIX',               'FINALIZADA', 51.88),
 (NULL,          2, '2026-11-03 17:40:00', 'Dinheiro',          'FINALIZADA', 27.45),
 (NULL,          1, '2026-11-05 09:05:00', 'Cartao de Credito', 'CANCELADA',  11.99),
 ('34567890123', 2, '2026-11-06 19:30:00', 'Cartao de Debito',  'FINALIZADA', 36.69);

INSERT INTO item_venda (codigo_venda, codigo_produto, quantidade, preco_unitario) VALUES
 (1, 3, 1, 27.90), (1, 1, 2, 11.99),
 (2, 5, 5,  5.49),
 (3, 1, 1, 11.99),
 (4, 3, 1, 27.90), (4, 4, 1,  8.79);
