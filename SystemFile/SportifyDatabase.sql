-- MySQL Script generated by MySQL Workbench
-- Sun Jan  2 12:23:48 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- -----------------------------------------------------
-- Schema sql11462781
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sql11462781
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sql11462781` DEFAULT CHARACTER SET utf8 ;
USE `sql11462781` ;
-- -----------------------------------------------------
-- Table `sql11462781`.`user`
-- -----------------------------------------------------
DECLARE
    admin CONSTANT VARCHAR(13) = 'administrator';
CREATE TABLE IF NOT EXISTS `sql11462781`.`user` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(32) NOT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `ruolo` ENUM(admin, 'gym', 'user') NOT NULL,
  `birthday` DATE NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11462781`.`sport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11462781`.`sport` (
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('outdoor', 'indoor') NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11462781`.`gym`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11462781`.`gym` (
  `name` VARCHAR(45) NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  `address` VARCHAR(65) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `phone` INT NULL DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC),
  INDEX `owner_idx` (`owner` ASC),
  CONSTRAINT `owner`
    FOREIGN KEY (`owner`)
    REFERENCES `sql11462781`.`user` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11462781`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11462781`.`review` (
  `gym` VARCHAR(45) NOT NULL,
  `review` LONGTEXT NOT NULL,
  `writer` VARCHAR(45) NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gym`, `writer`, `timestamp`),
  UNIQUE INDEX `writer_UNIQUE` (`writer` ASC),
  UNIQUE INDEX `gym_UNIQUE` (`gym` ASC),
  CONSTRAINT `gym`
    FOREIGN KEY (`gym`)
    REFERENCES `sql11462781`.`gym` (`name`),
  CONSTRAINT `writer`
    FOREIGN KEY (`writer`)
    REFERENCES `sql11462781`.`user` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11462781`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11462781`.`course` (
  `sport` VARCHAR(45) NOT NULL,
  `gym` VARCHAR(45) NOT NULL,
  `time` TIME NOT NULL,
  PRIMARY KEY (`sport`, `gym`, `time`),
  INDEX `gym_idx` (`gym` ASC),
  CONSTRAINT `gym_name`
    FOREIGN KEY (`gym`)
    REFERENCES `sql11462781`.`gym` (`name`),
  CONSTRAINT `sport`
    FOREIGN KEY (`sport`)
    REFERENCES `sql11462781`.`sport` (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

USE `sql11462781` ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

DELIMITER $$
USE `sql11462781`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(in var_username varchar(45), in var_pass varchar(45), out var_role INT)
BEGIN
	declare var_user_role ENUM(admin, 'gym');
    
    select `ruolo` from `user`
		where `username` = var_username
        and `password` = md5(var_pass)
        into var_user_role;
        
	-- See the corresponding enum in the client
		if var_user_role = admin then
			set var_role = 1;
		elseif var_user_role = 'gym' then
			set var_role = 2;
		elseif var_user_role = 'user' then
			set var_role = 4;
		else
			set var_role = 3;
		end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure lista_palestre
-- -----------------------------------------------------

DELIMITER $$
USE `sql11462781`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `lista_palestre`()
BEGIN
	declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;
    
    set transaction isolation level read committed;
    start transaction;
    
		select 
			`name`,
			`address`,
			`latitude`,
			`longitude`,
            `phone`
		from `gym`
    
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure add_review
-- -----------------------------------------------------

DELIMITER $$
USE `sql11462781`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_review`(in var_gym varchar(45), in var_review longtext, in var_writer varchar(45))
BEGIN

	declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

	set transaction isolation level repeatable read;
    start transaction;
		insert into `review` (`gym`, `review`, `writer`)
			values (var_gym, var_review, var_writer);
	commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure add_course
-- -----------------------------------------------------

DELIMITER $$
USE `sql11462781`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_course`(in var_sport varchar(45), in var_gym varchar(45), in var_time time)
BEGIN

	declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

	set transaction isolation level repeatable read;
    start transaction;
		insert into `course` (`sport`, `gym`, `time`)
			values (var_sport, var_gym, var_time);
	commit;
END$$

DELIMITER ;
CREATE USER administrator IDENTIFIED BY 'w47730t461014';

CREATE USER 'gym' IDENTIFIED BY 'test';

CREATE USER 'login' IDENTIFIED BY 'test';

GRANT EXECUTE ON procedure `sql11462781`.`login` TO 'login';
CREATE USER 'user' IDENTIFIED BY 'test';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
