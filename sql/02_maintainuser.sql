
-- --------------------------------------------------------
-- Modify User tables
-- --------------------------------------------------------

ALTER TABLE `phoenix`.`user` ADD COLUMN (
	`address1` VARCHAR(100) NULL ,
	`address2` VARCHAR(100) NULL ,
	`address3` VARCHAR(100) NULL ,
	`city` VARCHAR(45) NULL ,
	`state` VARCHAR(45) NULL ,
	`country` VARCHAR(2) NULL,
	`homephone` VARCHAR(20) NULL,
	`mobile` VARCHAR(20) NULL,
	`email` VARCHAR(128) NULL
);


UPDATE `phoenix`.`user` SET address1 = '6 Vivekanandar Street', address2 = 'Dubai Cross Street', address3='Dubai Main Road',city='Dubai',state='Dubai',country='AE',homephone='+11 11223344', mobile='+11 58542246',email='dilbert@dilbert.com' where id='dilbert';
UPDATE `phoenix`.`user` SET address1 = '11 Bugis Street', address2 = '#04-156', city='Singapore',state='Singapore',country='AE',homephone='+65 11223344', mobile='+65 13590876',email='catbert@catbert.com' where id='catbert';
UPDATE `phoenix`.`user` SET address1 = '1 Commercial Avenue', address2 = 'Wayne Furnitures', address3='Second Floor',city='Los Angeles',state='California',country='US',homephone='+1 11223344', mobile='+1 34253225',email='dogbert@dogbert.com' where id='dogbert';
UPDATE `phoenix`.`user` SET address1 = '21 Heng Mui Keng Terrace', address2 = 'Institute of Systems Science', address3='National University of Singapore',city='Singapore',state='Singapore',country='SG',homephone='+65 11223344', mobile='+65 11221413',email='pointyhead@nus.edu.sg' where id='pointyhead';
