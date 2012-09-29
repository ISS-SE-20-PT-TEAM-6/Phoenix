CREATE  TABLE `phoenix`.`user-roles` (  `userId` VARCHAR(45) NOT NULL ,  `role` VARCHAR(45) NULL ,  PRIMARY KEY (`userId`) );
Insert into `phoenix`.`user-roles`
select id, role from `phoenix`.user;