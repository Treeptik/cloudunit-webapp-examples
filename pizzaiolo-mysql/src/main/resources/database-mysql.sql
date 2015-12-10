CREATE TABLE  IF NOT EXISTS `pizza` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `price` DOUBLE NULL ,
  PRIMARY KEY (`id`) );

INSERT ignore INTO `pizza` (`id`, `name`, `price`) VALUES ('1', 'Italian', '7.5');
INSERT ignore INTO `pizza` (`id`, `name`, `price`) VALUES ('2', 'Thin Crust', '6');
INSERT ignore INTO `pizza` (`id`, `name`, `price`) VALUES ('3', 'Pepperoni', '6.2');
