-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.62 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for spn_intellisense
CREATE DATABASE IF NOT EXISTS `spn_intellisense` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `spn_intellisense`;

-- Dumping structure for table spn_intellisense.agency
CREATE TABLE IF NOT EXISTS `agency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` char(5) NOT NULL DEFAULT '1' COMMENT '1 active 0 non active',
  `createdBy` bigint(5) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_execution
CREATE TABLE IF NOT EXISTS `batch_job_execution` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `batch_job_instance` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_execution_context
CREATE TABLE IF NOT EXISTS `batch_job_execution_context` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` mediumtext,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_execution_params
CREATE TABLE IF NOT EXISTS `batch_job_execution_params` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_execution_seq
CREATE TABLE IF NOT EXISTS `batch_job_execution_seq` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_instance
CREATE TABLE IF NOT EXISTS `batch_job_instance` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_job_seq
CREATE TABLE IF NOT EXISTS `batch_job_seq` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_step_execution
CREATE TABLE IF NOT EXISTS `batch_step_execution` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_step_execution_context
CREATE TABLE IF NOT EXISTS `batch_step_execution_context` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `batch_step_execution` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.batch_step_execution_seq
CREATE TABLE IF NOT EXISTS `batch_step_execution_seq` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.center
CREATE TABLE IF NOT EXISTS `center` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '0',
  `regionId` bigint(5) NOT NULL DEFAULT '0',
  `status` char(2) NOT NULL DEFAULT '1' COMMENT '1 for active 0 for non active',
  PRIMARY KEY (`id`),
  KEY `FK_center_region` (`regionId`),
  CONSTRAINT `FK_center_region` FOREIGN KEY (`regionId`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.enrollment
CREATE TABLE IF NOT EXISTS `enrollment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sponsorId` bigint(20) NOT NULL,
  `jobId` bigint(5) DEFAULT NULL,
  `paymentDate` datetime NOT NULL,
  `effectiveDate` datetime NOT NULL,
  `contributionAmount` double NOT NULL,
  `miscAmount` double DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0 for active 1 for non active',
  `receiptId` int(11) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdBy` bigint(5) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `en_spon_key` (`sponsorId`),
  KEY `FK_enrollment_receipt` (`receiptId`),
  CONSTRAINT `en_spon_key` FOREIGN KEY (`sponsorId`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.file_upload
CREATE TABLE IF NOT EXISTS `file_upload` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `referenceId` bigint(5) DEFAULT NULL,
  `userId` bigint(5) NOT NULL,
  `initiativeId` int(5) NOT NULL,
  `fileName` varchar(200) NOT NULL,
  `fileData` mediumblob,
  `status` varchar(1) NOT NULL DEFAULT 'U',
  `batchExecutionStatus` int(1) DEFAULT '0' COMMENT '0 Not Ran - 1 Ran - 2 Error',
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` char(5) NOT NULL DEFAULT 'ST' COMMENT 'ST - Student, SP Sponsor',
  `uploaduri` varchar(250) DEFAULT NULL,
  `jobId` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_file_upload_project` (`referenceId`),
  KEY `FK_file_upload_initiative` (`initiativeId`),
  CONSTRAINT `FK_file_upload_initiative` FOREIGN KEY (`initiativeId`) REFERENCES `initiative` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for function spn_intellisense.getNextSponsorSeq
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getNextSponsorSeq`(sParishId VARCHAR(10), sSponsorCode INT(6)) RETURNS varchar(10) CHARSET latin1
BEGIN
  DECLARE nLast_val INT;
  SET nLast_val = (SELECT seq_val FROM sponsor_sequence WHERE parishId = sParishId);
  IF nLast_val IS NULL THEN
	IF sSponsorCode IS NULL THEN
		SET nLast_val = 5001;
	ELSE
		SET nLast_val = sSponsorCode;
	END IF;
	   INSERT INTO sponsor_sequence (parishId,seq_val) VALUES (sParishId, nLast_Val);
  ELSE
	  IF (sSponsorCode>=nLast_val) THEN
			SET nLast_val = sSponsorCode;
		ELSE
			SET nLast_val = nLast_val + 1;
		END IF;     
     UPDATE sponsor_sequence SET seq_val = nLast_val WHERE parishId = sParishId;
  END IF;
	 -- SET @ret = (SELECT concat(sParishId,'-',lpad(nLast_val,6,'0')));
	 -- RETURN @ret;
	 RETURN nLast_val;
END//
DELIMITER ;

-- Dumping structure for function spn_intellisense.getNextStudentSeq
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getNextStudentSeq`(sProjectId VARCHAR(10), sStudentCode INT(6)) RETURNS varchar(10) CHARSET latin1
BEGIN
  DECLARE nLast_val INT;
  SET nLast_val = (SELECT seq_val FROM student_sequence WHERE projectId = sProjectId);
  IF nLast_val IS NULL THEN
    IF sStudentCode IS NULL THEN
		SET nLast_val = 5001;
	ELSE
		SET nLast_val = sStudentCode;
	END IF;
	 INSERT INTO student_sequence (projectId,seq_val) VALUES (sProjectId, nLast_Val);
	 ELSE
	  IF (sStudentCode>=nLast_val) THEN
			SET nLast_val = sStudentCode;
		ELSE
			SET nLast_val = nLast_val + 1;
		END IF;
    UPDATE student_sequence SET seq_val = nLast_val WHERE projectId = sProjectId;
	 END IF;
	 -- SET @ret = (SELECT concat(sProjectId,'-',lpad(nLast_val,6,'0')));
	 -- RETURN @ret;
	 RETURN nLast_val;
END//
DELIMITER ;

-- Dumping structure for table spn_intellisense.initiative
CREATE TABLE IF NOT EXISTS `initiative` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  `code` varchar(10) NOT NULL DEFAULT '0',
  `active` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.organization
CREATE TABLE IF NOT EXISTS `organization` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `code` varchar(15) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 for active 1 for non',
  `createdby` bigint(5) NOT NULL,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0 for internal 1 for external',
  `updatedby` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.parish
CREATE TABLE IF NOT EXISTS `parish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `city` varchar(50) DEFAULT NULL,
  `status` char(50) NOT NULL DEFAULT '1' COMMENT '1 active 0 non active',
  `centerId` bigint(5) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdBy` bigint(5) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_parish_center` (`centerId`),
  CONSTRAINT `FK_parish_center` FOREIGN KEY (`centerId`) REFERENCES `center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.parish_project
CREATE TABLE IF NOT EXISTS `parish_project` (
  `ppId` bigint(5) NOT NULL AUTO_INCREMENT,
  `parishId` bigint(5) NOT NULL,
  `projectId` bigint(5) NOT NULL,
  `status` char(2) NOT NULL DEFAULT '1' COMMENT '1 active 0 non active',
  PRIMARY KEY (`ppId`),
  KEY `FK_parish_project_parish` (`parishId`),
  KEY `FK_parish_project_project` (`projectId`),
  CONSTRAINT `FK_parish_project_parish` FOREIGN KEY (`parishId`) REFERENCES `parish` (`id`),
  CONSTRAINT `FK_parish_project_project` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `agencyId` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `contactNumber` varchar(20) DEFAULT NULL,
  `contactEmail` varchar(100) DEFAULT NULL,
  `status` char(5) NOT NULL DEFAULT '1',
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdBy` bigint(5) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `par_ind` (`agencyId`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`agencyId`) REFERENCES `agency` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.receipt
CREATE TABLE IF NOT EXISTS `receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receivedfrom` varchar(150) NOT NULL DEFAULT '0',
  `address` varchar(250) NOT NULL DEFAULT '0',
  `parish` varchar(250) NOT NULL DEFAULT '0',
  `missionname` varchar(100) NOT NULL DEFAULT '0',
  `total` varchar(100) NOT NULL DEFAULT '0',
  `paymentmethod` varchar(100) NOT NULL DEFAULT '0',
  `secretary` varchar(100) DEFAULT '0',
  `treasurer` varchar(100) DEFAULT '0',
  `createdby` bigint(5) NOT NULL DEFAULT '0',
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1262 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.receipts
CREATE TABLE IF NOT EXISTS `receipts` (
  `receiptId` bigint(5) unsigned NOT NULL AUTO_INCREMENT,
  `rdate` varchar(15) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `middleName` varchar(10) DEFAULT NULL,
  `lastName` varchar(250) DEFAULT NULL,
  `fullName` varchar(250) DEFAULT NULL,
  `transaction` varchar(250) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `currencyType` char(4) NOT NULL DEFAULT 'USD',
  `initiativeId` bigint(5) DEFAULT NULL,
  `streetAddress` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `zipCode` varchar(12) DEFAULT NULL,
  `receiptType` int(2) DEFAULT NULL COMMENT '0 for parish 1 for org, 2 for individual',
  `referenceId` bigint(5) DEFAULT NULL COMMENT 'parish or org id',
  `email1` varchar(150) DEFAULT NULL,
  `email2` varchar(150) DEFAULT NULL,
  `phone1` varchar(15) DEFAULT NULL,
  `phone2` varchar(15) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0 for donation 1 for sponsor',
  `fundUtilized` int(11) NOT NULL DEFAULT '0' COMMENT '0 for accepted 1 for processed',
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createdby` bigint(5) NOT NULL,
  `updatedBy` bigint(5) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 for active 1 for void',
  PRIMARY KEY (`receiptId`)
) ENGINE=InnoDB AUTO_INCREMENT=1278 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.region
CREATE TABLE IF NOT EXISTS `region` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '0',
  `status` char(2) NOT NULL DEFAULT '1' COMMENT '1 for active 0 for non active',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsee
CREATE TABLE IF NOT EXISTS `sponsee` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `enrollmentId` bigint(10) NOT NULL DEFAULT '0',
  `expirationMonth` int(2) NOT NULL DEFAULT '0',
  `expirationYear` int(4) NOT NULL DEFAULT '0',
  `studentId` bigint(5) NOT NULL DEFAULT '0',
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_sponsee_enrollment` (`enrollmentId`),
  KEY `FK_sponsee_student` (`studentId`),
  CONSTRAINT `FK_sponsee_enrollment` FOREIGN KEY (`enrollmentId`) REFERENCES `enrollment` (`id`),
  CONSTRAINT `FK_sponsee_student` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsor
CREATE TABLE IF NOT EXISTS `sponsor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parishId` bigint(20) NOT NULL DEFAULT '0',
  `jobId` bigint(5) DEFAULT '0',
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `nickName` varchar(50) DEFAULT NULL,
  `middleInitial` varchar(20) DEFAULT NULL,
  `sponsorCode` varchar(10) DEFAULT NULL,
  `dayOfBirth` int(11) DEFAULT NULL,
  `monthOfBirth` int(11) DEFAULT NULL,
  `sponsorStatus` int(1) NOT NULL DEFAULT '0' COMMENT '0 for active',
  `emailAddress` varchar(100) DEFAULT NULL,
  `emailAddress2` varchar(100) DEFAULT NULL,
  `appartmentNumber` char(10) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` char(20) DEFAULT NULL,
  `postalCode` char(9) DEFAULT NULL,
  `phone1` varchar(15) DEFAULT NULL,
  `phone2` varchar(15) DEFAULT NULL,
  `hasAnyCoSponser` char(5) NOT NULL DEFAULT 'N',
  `coSponserName` varchar(100) DEFAULT 'N',
  `transactionRemarks` varchar(150) DEFAULT 'N',
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdBy` bigint(5) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parish_key` (`parishId`),
  CONSTRAINT `parish_key` FOREIGN KEY (`parishId`) REFERENCES `parish` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1681 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsor_maxout
CREATE TABLE IF NOT EXISTS `sponsor_maxout` (
  `sponsorId` bigint(5) NOT NULL,
  `enrollmentId` bigint(5) NOT NULL,
  `maxOut` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id` int(5) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `FK_sponsor_maxout_enrollment` (`enrollmentId`),
  KEY `FK_sponsor_maxout_sponsor` (`sponsorId`),
  CONSTRAINT `FK_sponsor_maxout_enrollment` FOREIGN KEY (`enrollmentId`) REFERENCES `enrollment` (`id`),
  CONSTRAINT `FK_sponsor_maxout_sponsor` FOREIGN KEY (`sponsorId`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=411 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.sponsor_sequence
CREATE TABLE IF NOT EXISTS `sponsor_sequence` (
  `parishId` varchar(10) NOT NULL,
  `seq_val` int(10) unsigned NOT NULL,
  PRIMARY KEY (`parishId`,`seq_val`),
  UNIQUE KEY `parishId_seq_val` (`parishId`,`seq_val`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.student
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectId` bigint(20) NOT NULL DEFAULT '0',
  `jobId` bigint(8) DEFAULT NULL,
  `studentName` varchar(100) NOT NULL,
  `studentCode` varchar(10) DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `gender` char(15) NOT NULL,
  `grade` varchar(20) DEFAULT NULL,
  `favColor` varchar(50) DEFAULT NULL,
  `favGame` varchar(200) DEFAULT NULL,
  `nameOfGuardian` varchar(50) DEFAULT NULL,
  `occupationOfGuardian` varchar(50) DEFAULT NULL,
  `baseLanguage` varchar(50) DEFAULT NULL,
  `dateOfBirth` varchar(50) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `hobbies` varchar(200) DEFAULT NULL,
  `status` char(5) NOT NULL DEFAULT '0',
  `talent` varchar(200) DEFAULT NULL,
  `recentAchivements` varchar(200) DEFAULT NULL,
  `profilePicture` longblob,
  `softlocked` char(5) NOT NULL DEFAULT 'N',
  `imageLinkRef` varchar(50) DEFAULT NULL,
  `uploadstatus` varchar(5) NOT NULL DEFAULT 'N',
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdBy` bigint(5) NOT NULL,
  `updatedDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_key` (`projectId`),
  CONSTRAINT `project_key` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2253 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.student_maxout
CREATE TABLE IF NOT EXISTS `student_maxout` (
  `studentId` bigint(5) NOT NULL,
  `enrollmentId` bigint(5) NOT NULL,
  `maxOut` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id` int(5) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `FK_student_maxout_enrollment` (`enrollmentId`),
  KEY `FK_student_maxout_student` (`studentId`),
  CONSTRAINT `FK_student_maxout_enrollment` FOREIGN KEY (`enrollmentId`) REFERENCES `enrollment` (`id`),
  CONSTRAINT `FK_student_maxout_student` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.student_sequence
CREATE TABLE IF NOT EXISTS `student_sequence` (
  `projectId` varchar(10) NOT NULL,
  `seq_val` int(10) unsigned NOT NULL,
  PRIMARY KEY (`projectId`,`seq_val`),
  UNIQUE KEY `projectId_seq_val` (`projectId`,`seq_val`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(2) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `middleName` varchar(50) DEFAULT NULL,
  `salutation` varchar(10) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table spn_intellisense.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `username_role` (`username`,`role`),
  CONSTRAINT `FK_user_roles_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for trigger spn_intellisense.custom_sponsor_bi
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `custom_sponsor_bi` BEFORE INSERT ON `sponsor` FOR EACH ROW BEGIN
SET NEW.sponsorCode = getNextSponsorSeq(NEW.parishId, NEW.sponsorCode);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger spn_intellisense.custom_student_bi
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `custom_student_bi` BEFORE INSERT ON `student` FOR EACH ROW BEGIN
DECLARE x INT;
SET NEW.studentCode = getNextStudentSeq(NEW.projectId, NEW.studentCode);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
