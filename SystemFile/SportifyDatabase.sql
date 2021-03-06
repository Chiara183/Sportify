-- MySQL Script generated by MySQL Workbench
-- Sun Feb  6 22:21:08 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sql11470947
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sql11470947
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sql11470947` DEFAULT CHARACTER SET utf8 ;
USE `sql11470947` ;

-- -----------------------------------------------------
-- Table `sql11470947`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11470947`.`user` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(32) NOT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `ruolo` ENUM('administrator', 'gym', 'user') NOT NULL,
  `birthday` DATE NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11470947`.`sport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11470947`.`sport` (
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('outdoor', 'indoor') NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11470947`.`gym`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11470947`.`gym` (
  `name` VARCHAR(45) NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  `address` VARCHAR(65) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `phone` CHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC),
  INDEX `owner_idx` (`owner` ASC),
  CONSTRAINT `owner`
    FOREIGN KEY (`owner`)
    REFERENCES `sql11470947`.`user` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11470947`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11470947`.`review` (
  `gym` VARCHAR(45) NOT NULL,
  `review` LONGTEXT NOT NULL,
  `writer` VARCHAR(45) NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gym`, `writer`, `timestamp`),
  UNIQUE INDEX `writer_UNIQUE` (`writer` ASC),
  UNIQUE INDEX `gym_UNIQUE` (`gym` ASC),
  CONSTRAINT `gym`
    FOREIGN KEY (`gym`)
    REFERENCES `sql11470947`.`gym` (`name`),
  CONSTRAINT `writer`
    FOREIGN KEY (`writer`)
    REFERENCES `sql11470947`.`user` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `sql11470947`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql11470947`.`course` (
  `sport` VARCHAR(45) NOT NULL,
  `gym` VARCHAR(45) NOT NULL,
  `time` TIME NOT NULL,
  PRIMARY KEY (`sport`, `gym`, `time`),
  INDEX `gym_idx` (`gym` ASC),
  CONSTRAINT `gym_name`
    FOREIGN KEY (`gym`)
    REFERENCES `sql11470947`.`gym` (`name`),
  CONSTRAINT `sport`
    FOREIGN KEY (`sport`)
    REFERENCES `sql11470947`.`sport` (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
