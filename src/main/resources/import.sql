
-- Dumping database structure for spn_intellisense
CREATE DATABASE IF NOT EXISTS `spn_intellisense` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `spn_intellisense`;

-- Dumping structure for table spn_intellisense.agency
CREATE TABLE IF NOT EXISTS `agency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` char(5) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.enrollment
CREATE TABLE IF NOT EXISTS `enrollment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sponsor_id` bigint(20) NOT NULL,
  `payment_date` datetime NOT NULL,
  `effective_date` datetime NOT NULL,
  `contribution_amount` double NOT NULL,
  `misc_amount` double DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `en_spon_key` (`sponsor_id`),
  CONSTRAINT `en_spon_key` FOREIGN KEY (`sponsor_id`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.parish
CREATE TABLE IF NOT EXISTS `parish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `status` char(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `agency_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `status` char(5) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `par_ind` (`agency_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsee
CREATE TABLE IF NOT EXISTS `sponsee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enrollment_id` bigint(20) NOT NULL DEFAULT '0',
  `expiration_month` char(5) NOT NULL DEFAULT '0',
  `expiration_year` char(5) NOT NULL DEFAULT '0',
  `student_id` bigint(20) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_sponsee_enrollment` (`enrollment_id`),
  KEY `FK_sponsee_student` (`student_id`),
  CONSTRAINT `FK_sponsee_enrollment` FOREIGN KEY (`enrollment_id`) REFERENCES `enrollment` (`id`),
  CONSTRAINT `FK_sponsee_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsor
CREATE TABLE IF NOT EXISTS `sponsor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parish_id` bigint(20) NOT NULL DEFAULT '0',
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_initial` varchar(20) DEFAULT NULL,
  `day_of_birth` int(11) DEFAULT NULL,
  `month_of_birth` int(11) DEFAULT NULL,
  `is_active` char(5) NOT NULL DEFAULT 'Y',
  `email_address` varchar(50) DEFAULT NULL,
  `appartment_number` char(10) DEFAULT NULL,
  `street` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` char(5) NOT NULL,
  `postal_code` char(5) NOT NULL,
  `has_any_co_sponser` char(5) NOT NULL DEFAULT 'N',
  `co_sponser_name` varchar(100) DEFAULT 'N',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parish_key` (`parish_id`),
  CONSTRAINT `parish_key` FOREIGN KEY (`parish_id`) REFERENCES `parish` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.student
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL DEFAULT '0',
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `gender` char(5) NOT NULL,
  `date_of_birth` datetime NOT NULL,
  `address` varchar(150) NOT NULL,
  `hobbies` varchar(200) DEFAULT NULL,
  `status` char(5) NOT NULL DEFAULT 'Y',
  `contributions` varchar(200) DEFAULT NULL,
  `talent` varchar(200) DEFAULT NULL,
  `recent_achivements` varchar(200) DEFAULT NULL,
  `profile_picture` blob,
  `softlocked` char(5) NOT NULL DEFAULT 'N',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_key` (`project_id`),
  CONSTRAINT `project_key` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
