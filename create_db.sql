CREATE TABLE `springjdbc`.`Integracao` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `info` VARCHAR(50) NULL,
  `detalhe` VARCHAR(50) NULL,
  `data_hora` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `status` CHAR(1) NULL,
  PRIMARY KEY (`codigo`));
  
 DROP TRIGGER IF EXISTS `springjdbc`.`Integracao_BEFORE_INSERT`;

DELIMITER $$
USE `springjdbc`$$
CREATE DEFINER=`root`@`%` TRIGGER `Integracao_BEFORE_INSERT` BEFORE INSERT ON `Integracao` FOR EACH ROW BEGIN

	DECLARE randomico long;
    DECLARE impar int;
    DECLARE detalhe VARCHAR(50);
    SELECT FLOOR(RAND()*10) INTO randomico;
    SELECT MOD(randomico,2) INTO impar;
	SET NEW.`detalhe` = randomico ;
    SET NEW.`status` = case when impar=0 then 'S' else 'E' end;

END
  
