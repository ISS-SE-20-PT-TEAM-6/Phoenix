use phoenix;
DROP TABLE IF EXISTS `phoenix`.`schedule` ;
CREATE  TABLE `phoenix`.`schedule` (  `scheduleID` VARCHAR(12) NOT NULL ,  `programName` VARCHAR(45) 
NOT NULL ,  `programDate` DATE NOT NULL ,  `presenter` VARCHAR(45) NOT NULL ,  `producer` VARCHAR(45) 
NOT NULL ,  `startTime` TIME NOT NULL ,  `endTime` TIME NOT NULL ,  PRIMARY KEY (`scheduleID`) );
