CREATE TABLE `tarefa` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `descricao` datetime NOT NULL,
  `dataHoraCriacao` datetime NOT NULL,
  `concluida` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8