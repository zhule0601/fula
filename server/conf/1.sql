CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `wx_uid` varchar(128) DEFAULT NULL COMMENT '微信公众号用户',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `state` varchar(32) DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wx_uid` (`wx_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';