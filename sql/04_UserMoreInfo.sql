ALTER TABLE `phoenix`.`user` ADD COLUMN `address1` VARCHAR(45) NULL  AFTER `role` , 
ADD COLUMN `address2` VARCHAR(45) NULL  AFTER `address1`,
ADD COLUMN `address3` VARCHAR(45) NULL  AFTER `address2`,
ADD COLUMN `city` VARCHAR(45) NULL  AFTER `address3`,
ADD COLUMN `state` VARCHAR(45) NULL  AFTER `city`,
ADD COLUMN `country` VARCHAR(45) NULL  AFTER `state`,
ADD COLUMN `homephone` VARCHAR(45) NULL  AFTER `country`,
ADD COLUMN `mobile` VARCHAR(45) NULL  AFTER `homephone`,
ADD COLUMN `email` VARCHAR(45) NULL  AFTER `mobile` ;