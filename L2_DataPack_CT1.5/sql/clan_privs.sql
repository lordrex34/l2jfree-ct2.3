-- ----------------------------
-- Table structure for `clan_privs`
-- ----------------------------
CREATE TABLE IF Not EXISTS `clan_privs` (
  `clan_id` INT NOT NULL DEFAULT 0,
  `rank` INT NOT NULL DEFAULT 0,
  `party` INT NOT NULL DEFAULT 0,
  `privilleges` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`clan_id`,`rank`,`party`)
) DEFAULT CHARSET=utf8;