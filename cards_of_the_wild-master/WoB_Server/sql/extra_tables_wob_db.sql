CREATE TABLE `card` (
  `card_id` int(10) NOT NULL AUTO_INCREMENT,
  `species_id` int(10) unsigned NOT NULL,
  `health` int(10) unsigned NOT NULL,
  `attack` int(10) unsigned NOT NULL,
  `level` int(10) unsigned NOT NULL,
  PRIMARY KEY (`card_id`),
  KEY `species_fk` (`species_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

ALTER TABLE `card`
  ADD CONSTRAINT `tbl_card_species_fk` FOREIGN KEY (`species_id`) REFERENCES `species` (`species_id`) ON DELETE CASCADE;
  
CREATE TABLE `cards_wins` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `player_id` int(10) unsigned NOT NULL,
  `wins` int(10) unsigned NOT NULL,
  `losses` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `player_fk` (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

ALTER TABLE `cards_wins`
  ADD CONSTRAINT `tbl_cards_wins_player_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE CASCADE;
