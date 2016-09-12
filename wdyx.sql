
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(28) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `authority` varchar(32) DEFAULT NULL,
  `operation` varchar(32) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `OPENID_UNIQUE` (`openid`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subscribe` int(1) DEFAULT NULL,
  `openid` varchar(28) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `language` varchar(16) DEFAULT NULL,
  `city` varchar(16) DEFAULT NULL,
  `province` varchar(16) DEFAULT NULL,
  `country` varchar(16) DEFAULT NULL,
  `headimgurl` varchar(256) DEFAULT NULL,
  `subscribe_time` int(11) DEFAULT NULL,
  `unionid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `OPENID_UNIQUE` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `stu_user`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `stu_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(28) DEFAULT NULL,
  `username` varchar(16) DEFAULT NULL,
  `EISPassword` varchar(32) DEFAULT NULL,
  `IPSPassword` varchar(32) DEFAULT NULL,
  `DLSPassword` varchar(32) DEFAULT NULL,
  `mode` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `OPENID_UNIQUE` (`openid`),
  UNIQUE KEY `USERNAME_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `bbs`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bbs` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(28) DEFAULT NULL,
  `msg` varchar(2048) DEFAULT NULL,
  `msgid` int(11) DEFAULT NULL COMMENT '为评论时有值',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `bbs_user`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bbs_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(28) DEFAULT NULL,
  `headimgurl` varchar(1024) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `timestamp` timestamp DEFAULT NULL COMMENT '上次被禁言时间戳',
  `forbidden` int(11) DEFAULT NULL COMMENT '禁言时间，forbidden<=0表示不禁言',
  PRIMARY KEY (`id`),
  UNIQUE KEY `OPENID_UNIQUE` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `notification`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(28) DEFAULT NULL,
  `msgid` int(11) DEFAULT NULL COMMENT '消息id',
  `read_flag` int(11) DEFAULT NULL COMMENT '是否已读：0默认 1已读',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `curse`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `curse` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `curse` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
