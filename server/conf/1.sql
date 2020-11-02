DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE customer_info
(
    id            BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    name          VARCHAR(20)                        NOT NULL COMMENT '用户名',
    role_id       VARCHAR(200)                       NOT NULL COMMENT 'roles,可以是多种 role',
    last_role     VARCHAR(10)                        NOT NULL COMMENT '上次使用的role',
    phone         VARCHAR(50) COMMENT '手机号',
    email         VARCHAR(50) COMMENT '邮箱',
    sex           INT(1) COMMENT '性别:0-女,1-男',
    gmt_create    TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_customer_info_id (id),
    UNIQUE KEY uk_name (name),
    UNIQUE KEY uk_phone (phone),
    UNIQUE KEY uk_email (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT '用户信息表';

DROP TABLE IF EXISTS `customer_login`;
CREATE TABLE customer_login
(
    customer_info_id BIGINT(20) COMMENT '用户ID',
    login_name       VARCHAR(20) NOT NULL COMMENT '用户名',
    password         CHAR(32)    NOT NULL COMMENT 'md5加密的密码',
    state            INT(1)      NOT NULL DEFAULT 1 COMMENT '用户状态,1-正常',
    gmt_create       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    UNIQUE KEY uk_id_name (customer_info_id, login_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT '用户登录表';

DROP TABLE IF EXISTS `customer_login_log`;
CREATE TABLE customer_login_log
(
    id               BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '登陆日志ID',
    customer_info_id BIGINT(20) UNSIGNED                NOT NULL COMMENT '登陆用户ID',
    gmt_create       TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户登陆时间',
    login_ip         INT UNSIGNED                       NOT NULL COMMENT '登陆IP',
    login_type       TINYINT                            NOT NULL COMMENT '登陆类型：0未成功，1成功',
    PRIMARY KEY pk_login_id (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT '用户登陆日志表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    id           BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    role_name    varchar(20)                                 DEFAULT NULL COMMENT '角色名',
    role_name_cn varchar(20)                                 DEFAULT NULL COMMENT '角色中文名',
    gmt_create   TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    state        INT(1)                                      DEFAULT '1' COMMENT '是否有效  0无效 1有效  ',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT '后台角色表';

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    id              BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    resource_name   varchar(255) DEFAULT '' COMMENT '资源',
    permission_code varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
    permission_name varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='后台权限表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    id            BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    role_id       BIGINT(20)                                  DEFAULT NULL COMMENT '角色id',
    permission_id BIGINT(20)                                  DEFAULT NULL COMMENT '权限id',
    gmt_create    TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified  TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    state         INT(1)                                      DEFAULT '1' COMMENT '是否有效: 0无效 1有效 ',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8 COMMENT ='角色-权限关联表';

/*

校验两个方面:
1. 你是谁
2. 你能做什么

用户登录以及权限表结构设计
1. 用户登录表,
2. 用户基本信息表,
3. 用户角色表 (假设用户有多个角色,1.取权限并集;2.提供角色切换的能力)
4. 角色权限表 (只校验权限, 不校验角色)

这里只是列出了最简单的RBAC去哪模型, 参考建议
(RBAC 基于角色-权限-资源的权限控制管理)[https://www.google.com/search?q=RBAC]
(设计方案)[https://mp.ofweek.com/cloud/a456714352097]

关键词: 用户组-用户-角色-权限-资源,
默认给用户添加角色
手动添加到用户组
用户申请权限
(用户组=部门?)
 */

