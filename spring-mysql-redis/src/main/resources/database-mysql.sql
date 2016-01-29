CREATE TABLE  IF NOT EXISTS `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `rating` DOUBLE NULL ,
  PRIMARY KEY (`id`) );

INSERT ignore INTO `comment` (`id`, `name`, `rating`) VALUES ('1', 'Redis is awesome', '7.5');
INSERT ignore INTO `comment` (`id`, `name`, `rating`) VALUES ('2', 'Postgre is great', '6');
INSERT ignore INTO `comment` (`id`, `name`, `rating`) VALUES ('3', 'MySQL is not a database', '6.2');
