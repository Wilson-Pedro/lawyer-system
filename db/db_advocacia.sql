-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 24/08/2025 às 20:08
-- Versão do servidor: 10.6.15-MariaDB
-- Versão do PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `db_advocacia`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbl_assistido`
--

CREATE TABLE `tbl_assistido` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `registro` datetime(6) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `endereco_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbl_endereco`
--

CREATE TABLE `tbl_endereco` (
  `id` bigint(20) NOT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `numero_da_casa` int(11) DEFAULT NULL,
  `registro` datetime(6) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbl_estagiario`
--

CREATE TABLE `tbl_estagiario` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `periodo` enum('ESTAGIO_I','ESTAGIO_II','ESTAGIO_III','ESTAGIO_IV') DEFAULT NULL,
  `registro` datetime(6) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbl_processo`
--

CREATE TABLE `tbl_processo` (
  `id` bigint(20) NOT NULL,
  `assunto` varchar(255) DEFAULT NULL,
  `numero_do_processo` int(11) DEFAULT NULL,
  `partes_envolvidas` varchar(255) DEFAULT NULL,
  `prazo_final` date DEFAULT NULL,
  `registro` datetime(6) DEFAULT NULL,
  `responsavel` varchar(255) DEFAULT NULL,
  `status_do_processo` enum('DECISORIA','EXECUTOIA','INSTRUTORIA','POSTULATORIA','RECURSAL') DEFAULT NULL,
  `ultima_atualizacao` datetime(6) DEFAULT NULL,
  `vara` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `tbl_assistido`
--
ALTER TABLE `tbl_assistido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoxmvkj33byi5mfguplwbjn7cp` (`endereco_id`);

--
-- Índices de tabela `tbl_endereco`
--
ALTER TABLE `tbl_endereco`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tbl_estagiario`
--
ALTER TABLE `tbl_estagiario`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tbl_processo`
--
ALTER TABLE `tbl_processo`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tbl_assistido`
--
ALTER TABLE `tbl_assistido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbl_endereco`
--
ALTER TABLE `tbl_endereco`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbl_estagiario`
--
ALTER TABLE `tbl_estagiario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbl_processo`
--
ALTER TABLE `tbl_processo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `tbl_assistido`
--
ALTER TABLE `tbl_assistido`
  ADD CONSTRAINT `FKoxmvkj33byi5mfguplwbjn7cp` FOREIGN KEY (`endereco_id`) REFERENCES `tbl_endereco` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
