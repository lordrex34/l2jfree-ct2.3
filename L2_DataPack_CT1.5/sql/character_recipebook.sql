-- ---------------------------
-- Table structure for `character_recipebook`
-- ---------------------------
CREATE TABLE IF NOT EXISTS `character_recipebook` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `id` decimal(11) NOT NULL DEFAULT 0,
  `type` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`,`charId`)
) DEFAULT CHARSET=utf8;