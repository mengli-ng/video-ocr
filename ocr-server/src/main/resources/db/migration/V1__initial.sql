SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `language` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `detect_left` float NOT NULL,
  `detect_top` float NOT NULL,
  `detect_width` float NOT NULL,
  `detect_height` float NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `task_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `text` longtext,
  PRIMARY KEY (`id`),
  KEY `FK_task_result_task_id` (`task_id`),
  CONSTRAINT `FK_task_result_task_id` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `browse_file_path` varchar(255) DEFAULT NULL,
  `browse_file_url` varchar(255) DEFAULT NULL,
  `thumbnail_file_path` varchar(255) DEFAULT NULL,
  `thumbnail_file_url` varchar(255) DEFAULT NULL,
  `video_info` longtext,
  `status` varchar(255) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_video_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into `language` ( `code`, `name`) values ( 'chi_sim', '中文简体');
insert into `language` ( `code`, `name`) values ( 'chi_tra', '中文繁体');

SET FOREIGN_KEY_CHECKS = 1;
