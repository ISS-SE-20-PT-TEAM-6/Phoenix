Use phoenix;
DROP TABLE IF EXISTS `phoenix`.`user-roles`;
CREATE  TABLE `phoenix`.`user-roles` (  `userId` VARCHAR(40) NOT NULL ,  `role` VARCHAR(45) NULL ,  PRIMARY KEY (`userId`, `role`) );
Insert into `phoenix`.`user-roles`
select id, role from `phoenix`.user;