-- ============================================================
-- ParkFlow - Sistema Integrado de Gestão e Reserva de Vagas
-- Esquema do banco de dados (PostgreSQL)
-- ============================================================

-- Tabela de Usuários
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('USUARIO', 'ADMIN')) DEFAULT 'USUARIO'
);

-- Tabela de Veículos
CREATE TABLE veiculos (
    id_veiculo SERIAL PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    cor VARCHAR(30),
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabela de Vagas
CREATE TABLE vagas (
    id_vaga SERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    setor VARCHAR(20),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('COMUM', 'COBERTA', 'PCD')) DEFAULT 'COMUM',
    status VARCHAR(20) NOT NULL CHECK (status IN ('DISPONIVEL', 'OCUPADA', 'INDISPONIVEL')) DEFAULT 'DISPONIVEL'
);

-- Tabela de Reservas
CREATE TABLE reservas (
    id_reserva SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL,
    id_vaga INT NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ATIVA', 'CANCELADA', 'CONCLUIDA')) DEFAULT 'ATIVA',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_veiculo) REFERENCES veiculos(id_veiculo) ON DELETE CASCADE,
    FOREIGN KEY (id_vaga) REFERENCES vagas(id_vaga) ON DELETE CASCADE
);
