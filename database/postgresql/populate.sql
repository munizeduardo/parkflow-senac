-- ============================================================
-- ParkFlow - Dados de exemplo (populate)
-- Senha de todos os usuários de teste: 123456
-- ============================================================

---------------------------------------------------------------
-- Inserindo Usuários
---------------------------------------------------------------
INSERT INTO usuarios (nome, email, senha, perfil) VALUES
('Carlos Mendes', 'admin@parkflow.com', '$2b$12$XXAOqzLXQRxhQ3VOZOqL6.fXubIbZHJ2T1zhxZyPdxB1U997XMjrG', 'ADMIN'),
('André Oliveira', 'andre@parkflow.com', '$2b$12$XXAOqzLXQRxhQ3VOZOqL6.fXubIbZHJ2T1zhxZyPdxB1U997XMjrG', 'USUARIO'),
('Natally Amaral', 'natally@parkflow.com', '$2b$12$XXAOqzLXQRxhQ3VOZOqL6.fXubIbZHJ2T1zhxZyPdxB1U997XMjrG', 'USUARIO'),
('Rodrigo Langamer', 'rodrigo@parkflow.com', '$2b$12$XXAOqzLXQRxhQ3VOZOqL6.fXubIbZHJ2T1zhxZyPdxB1U997XMjrG', 'USUARIO');

---------------------------------------------------------------
-- Inserindo Veículos
---------------------------------------------------------------
INSERT INTO veiculos (placa, marca, modelo, cor, id_usuario) VALUES
('ABC1D23', 'Volkswagen', 'Golf', 'Prata', 2),
('XYZ9K87', 'Fiat', 'Argo', 'Branco', 2),
('DEF4G56', 'Toyota', 'Corolla', 'Preto', 3),
('GHI7J89', 'Honda', 'Civic', 'Cinza', 3),
('JKL2M34', 'Chevrolet', 'Onix', 'Vermelho', 4);

---------------------------------------------------------------
-- Inserindo Vagas
---------------------------------------------------------------
INSERT INTO vagas (codigo, setor, tipo, status) VALUES
('A01', 'A', 'COMUM', 'DISPONIVEL'),
('A02', 'A', 'COMUM', 'DISPONIVEL'),
('A03', 'A', 'PCD', 'DISPONIVEL'),
('A04', 'A', 'COBERTA', 'DISPONIVEL'),
('B01', 'B', 'COMUM', 'DISPONIVEL'),
('B02', 'B', 'COMUM', 'OCUPADA'),
('B03', 'B', 'COBERTA', 'DISPONIVEL'),
('B04', 'B', 'PCD', 'DISPONIVEL'),
('C01', 'C', 'COMUM', 'DISPONIVEL'),
('C02', 'C', 'COBERTA', 'DISPONIVEL'),
('C03', 'C', 'COMUM', 'INDISPONIVEL'),
('C04', 'C', 'COMUM', 'DISPONIVEL');

---------------------------------------------------------------
-- Inserindo Reservas
---------------------------------------------------------------
INSERT INTO reservas (id_usuario, id_veiculo, id_vaga, data_inicio, data_fim, status) VALUES
(2, 1, 1, '2026-09-10 08:00:00', '2026-09-10 12:00:00', 'ATIVA'),
(2, 2, 4, '2026-09-11 09:00:00', '2026-09-11 18:00:00', 'ATIVA'),
(3, 3, 5, '2026-09-12 14:00:00', '2026-09-12 17:00:00', 'ATIVA'),
(3, 3, 7, '2026-09-08 08:00:00', '2026-09-08 10:00:00', 'CONCLUIDA'),
(4, 5, 2, '2026-09-07 07:00:00', '2026-09-07 09:00:00', 'CANCELADA');
