create database lanHouse;
use lanHouse;

CREATE TABLE Consoles (
idConsole int auto_increment PRIMARY KEY,
    nomeConsole varchar(50),
    qtdControles int,
    empresa varchar(20)
);

CREATE TABLE Cliente (
idCliente int auto_increment PRIMARY KEY,
	usuario varchar(15),
    senha varchar(16),
    cpf varchar(15),
    nome varchar(100),
    telefone varchar(20),
    dataRegistro date,
    dataNascimento date,
    CONSTRAINT cliente_unico UNIQUE (usuario,senha)
);

CREATE TABLE Jogos (
    idJogo int auto_increment PRIMARY KEY,
    nome varchar(200),
    desenvolvedora varchar(50),
    idConsole int,
    capa longblob,
    foreign key(idConsole) references Consoles(idConsole) on delete cascade
);

CREATE TABLE Genero (
    genero_PK int auto_increment PRIMARY KEY,
    genero varchar(50)
);

create table jogoGenero(
	 genero_PK int,
     idJogo int,
     primary key(genero_PK,idJogo),
     foreign key(genero_PK) references Genero (genero_PK) on delete cascade,
     foreign key(idJogo) references Jogos (idJogo) on delete cascade
);

CREATE TABLE Reserva (
idReserva int auto_increment PRIMARY KEY,
    idCliente int,
    estado boolean,
    dataReserva date,
    tempo time,
    idJogo int,
    preco float,
    CONSTRAINT Reserva_unica UNIQUE (dataReserva,tempo),
    foreign key (idJogo) references Jogos(idJogo) on delete cascade,
    foreign key (idCliente) references Cliente(idCliente) on delete cascade
);


select * from Jogos;
select * from jogoGenero;
select * from Consoles;
select * from Cliente;
select * from Reserva;

select Consoles.nomeConsole from Jogos inner join Consoles on Jogos.idConsole = Consoles.idConsole where Jogos.idJogo=4258;
select Genero.genero from Genero inner join jogoGenero on Genero.genero_PK = jogoGenero.genero_PK where jogoGenero.idJogo=4258;
select Jogos.nome, Genero.genero from Jogos inner join jogoGenero on Jogos.idJogo = jogoGenero.idJogo inner join Genero on jogoGenero.genero_PK = Genero.genero_PK;

INSERT INTO Genero (genero_PK, genero) VALUES (1, 'Ação');
INSERT INTO Genero (genero_PK, genero) VALUES (2, 'Aventura');
INSERT INTO Genero (genero_PK, genero) VALUES (3, 'Simulação de Construção e Gestão');
INSERT INTO Genero (genero_PK, genero) VALUES (4, 'RPG');
INSERT INTO Genero (genero_PK, genero) VALUES (5, 'Quebra-Cabeça');
INSERT INTO Genero (genero_PK, genero) VALUES (6, 'Estratégia');
INSERT INTO Genero (genero_PK, genero) VALUES (7, 'Corrida');
INSERT INTO Genero (genero_PK, genero) VALUES (8, 'Tiro');
INSERT INTO Genero (genero_PK, genero) VALUES (9, 'Simulação de Vida');
INSERT INTO Genero (genero_PK, genero) VALUES (10, 'Luta');
INSERT INTO Genero (genero_PK, genero) VALUES (11, 'Esportes');
INSERT INTO Genero (genero_PK, genero) VALUES (12, 'Sandbox');
INSERT INTO Genero (genero_PK, genero) VALUES (13, 'Simulador de Voo');
INSERT INTO Genero (genero_PK, genero) VALUES (14, 'MMO');
INSERT INTO Genero (genero_PK, genero) VALUES (15, 'Plataforma');
INSERT INTO Genero (genero_PK, genero) VALUES (16, 'Furtividade');
INSERT INTO Genero (genero_PK, genero) VALUES (17, 'Música');
INSERT INTO Genero (genero_PK, genero) VALUES (18, 'Terror');
INSERT INTO Genero (genero_PK, genero) VALUES (19, 'Simulação de Veículos');
INSERT INTO Genero (genero_PK, genero) VALUES (20, 'Tabuleiro');
INSERT INTO Genero (genero_PK, genero) VALUES (21, 'Educação');
INSERT INTO Genero (genero_PK, genero) VALUES (22, 'Família');
INSERT INTO Genero (genero_PK, genero) VALUES (23, 'Festa');
INSERT INTO Genero (genero_PK, genero) VALUES (24, 'Produtividade');
INSERT INTO Genero (genero_PK, genero) VALUES (25, 'Quiz');
INSERT INTO Genero (genero_PK, genero) VALUES (26, 'Utilitário');
INSERT INTO Genero (genero_PK, genero) VALUES (27, 'Console Virtual');
INSERT INTO Genero (genero_PK, genero) VALUES (28, 'Não Oficial');

INSERT INTO Cliente (usuario, senha, cpf, nome, telefone, dataRegistro, dataNascimento) VALUES
('admin','admin',null,null,null,null,null),
('humbertob', 'senha123', '733.524.079-40', 'Humberto Bezerra', '(85)92130-0676', '2010-01-06', '1992-07-14'),
('edileneb', 'senha456', '874.901.489-79', 'Edilene Batista', '(69)93618-8786', '2023-04-30', '1990-04-05'),
('reginac', 'senha789', '513.653.093-63', 'Regina Caldeira Jimenes', '(65)93350-2167', '2022-11-02', '1992-08-13'),
('thiagoc', 'senha101', '768.039.516-00', 'Thiago Adílson de Casanova', '(88)93437-8701', '2009-07-30', '1997-03-25'),
('henriqueb', 'senha202', '752.864.844-25', 'Henrique João de Galindo', '(88)93052-8675', '2009-07-30', '1995-12-04'),
('malenan', 'senha303', '318.363.773-12', 'Malena Noelí Delatorre de Meireles', '(88)93627-1916', '2019-04-13', '2001-12-09'),
('pamelar', 'senha404', '425.558.144-42', 'Pâmela Rebeca Aragão Beltrão de Castro', '(88)93518-5434', '2016-10-22', '1998-03-18'),
('lilianb', 'senha505', '419.636.482-93', 'Lilian Rosana Barreto', '(21)92518-6339', '2012-09-21', '2007-09-15'),
('cauec', 'senha606', '643.126.793-12', 'Cauê Correia Flores', '(88)93135-4317', '2017-01-23', '2009-03-10'),
('fernandoc', 'senha707', '077.100.213-01', 'Fernando Campos', '(88)92679-6049', '2006-03-16', '1997-10-25'),
('isaacb', 'senha808', '531.325.848-60', 'Isaac Bezerra Fragoso', '(88)93447-6213', '2019-01-10', '2003-09-10'),
('agostinhom', 'senha909', '831.257.456-59', 'Agostinho Abreu Marques', '(79)93772-0479', '2008-10-19', '1994-11-27'),
('daianef', 'senha010', '926.787.357-10', 'Daiane Ávila de Ferreira', '(85)93565-2219', '2018-01-18', '2000-08-11'),
('eloala', 'senha111', '171.098.075-33', 'Eloá Laís Azevedo', '(55)93122-2388', '2023-03-18', '2002-08-01'),
('emiliab', 'senha212', '183.681.969-22', 'Emília Laura Barros Queirós de Alcântara', '(71)92582-5780', '2014-07-25', '1998-08-25');

INSERT INTO Reserva (idCliente, estado, dataReserva, tempo, idJogo, preco) VALUES 
(4, true, '2024-09-15', '01:00:00', 4223, 2.00),
(2, false, '2024-09-16', '02:00:00', 6768, 5.00),
(3, false, '2024-09-17', '01:00:00', 62525, 2.00),
(6, true, '2024-09-18', '03:00:00', 114622, 6.00),
(4, true, '2024-09-19', '01:00:00', 26267, 2.00);

drop database lanHouse;