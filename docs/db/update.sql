/*lmcms 更改数据库sql 2015年10月26日 18:41:20*/

/*2015年10月26日 12:11:17 liuzhen 新增稿件锁定字段*/
ALTER TABLE `cms_content`
ADD COLUMN `lock_content`  varchar(32) NULL DEFAULT 'false' COMMENT '稿件是否锁定（true：锁定，false:不锁定）';

/*2015年10月26日 18:41:49 liuzhen 增加稿件复制或引用关联表*/
drop table if exists cms_copy_content_ref;

/*==============================================================*/
/* Table: cms_copy_content_ref                                  */
/*==============================================================*/
create table cms_copy_content_ref
(
   id                   varchar(32) not null,
   lock_flag            int(1) comment '锁定标记（0：正常；1：锁定）',
   main_content_id      varchar(32) comment '主稿件',
   sub_content_id       varchar(32) comment '子稿件',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '修改时间',
   update_by            varchar(32) comment '修改人',
   remarks              varchar(255) comment '备注',
   del_flag             int(1) comment '删除标记（0：正常；1：删除）',
   primary key (id)
);

alter table cms_copy_content_ref comment '稿件复制或引用关联表';

/*2015年11月26日10:16:55 孙要旭 创建   wechat_push(微信表), wechat_content(微信内容表)*/
/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50096
Source Host           : 127.0.0.1:3306
Source Database       : lm_maven_cms

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-11-26 10:16:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wechat_content
-- ----------------------------
DROP TABLE IF EXISTS `wechat_content`;
CREATE TABLE `wechat_content` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `WEIXIN_TITLE` varchar(255) default NULL COMMENT '内容标题',
  `WEIXIN_DIGEST` varchar(255) default NULL COMMENT '内容摘要',
  `WEIXIN_CONTENT` varchar(255) default NULL COMMENT '内容',
  `WEIXIN_CONTENTADDRESS` varchar(255) default NULL COMMENT '内容地址',
  `WEIXIN_PICTUREURL` varchar(255) default NULL COMMENT '图片路径',
  `WEIXIN_SORT` varchar(255) default NULL COMMENT '排序',
  `WEIXIN_TOP` int(11) default NULL COMMENT ' 是否是头条, 1:是,0:否',
  `CONTENT_ID` varchar(32) default NULL COMMENT '文章内容id',
  `WEIXIN_ID` varchar(32) default NULL COMMENT '微信id',
  `SHOW_COVER_PIC` int(11) default NULL COMMENT '是否显示封面，1为显示，0为不显示',
  `WEIXIN_AUTHOR` varchar(255) default NULL COMMENT '作者',
  `WEIXIN_CREATETIME` datetime default NULL COMMENT '微信内容创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_push
-- ----------------------------
DROP TABLE IF EXISTS `wechat_push`;
CREATE TABLE `wechat_push` (
  `id` varchar(32) NOT NULL default '' COMMENT '微信id',
  `CREATE_PERSON` varchar(255) default NULL COMMENT '创建人',
  `CREATE_TIME` datetime default NULL COMMENT '创建时间',
  `PUSH_PERSON` varchar(255) default NULL COMMENT '推送人',
  `PUSH_TIME` datetime default NULL COMMENT '推送时间',
  `IS_PUSH` int(11) default NULL COMMENT '是否推送,1:是,0:否',
  `IS_SUCESSPUSH` int(11) default NULL COMMENT '是否推送成功,1:成功,0:失败',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*2015年12月1日11:52:54 孙要旭将字段 ‘WEIXIN_CONTENT’ varchar改为text类型*/
ALTER TABLE `wechat_content`
MODIFY COLUMN `WEIXIN_CONTENT`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容' AFTER `WEIXIN_DIGEST`;
/*孙要旭 2015年12月2日15:47:05 创建微信公众账号配置表 wechat_configure*/
/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50096
Source Host           : 127.0.0.1:3306
Source Database       : lm_maven_cms

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-12-02 15:46:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wechat_configure
-- ----------------------------
DROP TABLE IF EXISTS `wechat_configure`;
CREATE TABLE `wechat_configure` (
  `Id` varchar(32) NOT NULL COMMENT 'id',
  `AppId` varchar(255) NOT NULL COMMENT '微信公众账号appid',
  `Secret` varchar(255) NOT NULL COMMENT '微信公众号的秘钥',
  `token` varchar(255) NOT NULL COMMENT '微信公众号的token',
  `AesKey` varchar(255) NOT NULL COMMENT '微信公众号的EncodingAESKey',
  `siteid` varchar(32) NOT NULL COMMENT '站点id',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*孙要旭 2015年12月2日17:25:27 表'weChat_configure' 添加字段'createtime' 类型为datetime*/
ALTER TABLE `weChat_configure`
ADD COLUMN `createtime`  datetime NULL COMMENT '创建时间' AFTER `siteid`;
/*孙要旭 2015年12月4日17:44:47 将cms数据库所有表的ENGINE类型改为InnoDB  下方是查询类型为myisam的查询语句*/
SELECT CONCAT('CHECK TABLE ',table_name) FROM information_schema.tables WHERE table_schema='lm_maven_cms' AND ENGINE='myisam'; 
/*孙要旭 2015年12月4日17:49:21 修改ENGINE类型为InnoDB*/
ALTER TABLE `cms_lottery`
ENGINE=InnoDB;

ALTER TABLE `cms_lottery_record`
ENGINE=InnoDB;

ALTER TABLE `cms_mood`
ENGINE=InnoDB;

/*孙要旭 2015年12月5日17:17:26 表'wechat_push'添加字段'site_id'*/
ALTER TABLE `wechat_push`
ADD COLUMN `site_id`  varchar(32) NULL COMMENT '站点id' AFTER `IS_SUCESSPUSH`;


/*孙要旭 2015年12月5日12:15:232  创建表'sina_content'*/
CREATE TABLE `sina_content` (
`id`  varchar(32) NOT NULL ,
`sina_title`  varchar(255) NULL COMMENT '标题' ,
`sina_digest`  varchar(255) NULL COMMENT '摘要' ,
`sina_thumb`  varchar(255) NULL COMMENT '图片路径' ,
`sina_url`  varchar(255) NULL ,
`site_id`  varchar(32) NULL COMMENT '站点id' ,
`createtime`  datetime NULL COMMENT '创建时间' ,
`contentid`  varchar(32) NULL COMMENT '内容id' ,
PRIMARY KEY (`id`)
)
;
/*罗海明 2015年12月5日12:15:232  创建表'cms_weibo'*/
CREATE TABLE `cms_weibo` (
  `id` varchar(32) DEFAULT NULL COMMENT 'id',
  `site_id` varchar(255) DEFAULT NULL COMMENT '站点id',
  `client_ID` varchar(255) DEFAULT NULL COMMENT 'App Key',
  `client_SERCRET` varchar(255) DEFAULT NULL COMMENT 'App Secret',
  `redirect_URI` varchar(255) DEFAULT NULL COMMENT '回调地址',
  `baseURL` varchar(255) DEFAULT NULL,
  `accessTokenURL` varchar(255) DEFAULT NULL,
  `authorizeURL` varchar(255) DEFAULT NULL,
  `rmURL` varchar(255) DEFAULT NULL,
  `accessToken` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*孙要旭 2015年12月7日11:29:09 表'cms_picture_group' 字段'site_id'改为 varchar*/
ALTER TABLE `cms_picture_group` 
MODIFY COLUMN `site_id`  varchar(32) NULL DEFAULT NULL COMMENT '站点id' AFTER `sort`;
/*孙要旭2015年12月28日11:37:45 删除测试表*/
drop table cms_student;
drop table t_class;
drop table t_stu;
drop table t_tree;
/*孙要旭 2015年12月28日11:41:13 表'cms_content' 添加字段'static_url'(静态地址)和'dynamic_url'动态地址*/
ALTER TABLE `cms_content`
ADD COLUMN `static_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '静态地址' AFTER `lock_content`,
ADD COLUMN `dynamic_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '动态地址' AFTER `static_url`;
/*孙要旭 2015年12月28日11:41:13 表'cms_content_cat' 添加字段'static_url'(静态地址)和'dynamic_url'动态地址*/
ALTER TABLE `cms_content_cat`
ADD COLUMN `static_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '静态地址' AFTER `mobileChannel`,
ADD COLUMN `dynamic_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '动态地址' AFTER `static_url`;
/*孙要旭 2015年12月28日11:41:13 表'cms_site' 添加字段'is_switch'(是否切换(0代表静态1代表动态))*/
ALTER TABLE `cms_site`
ADD COLUMN `is_switch`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '是否切换(0代表静态1代表动态)' AFTER `ucenterisOpen`;
/*孙要旭 2016年1月5日10:49:51表'cms_wap_expand'修改字段长度'catalogids'为1000*/
ALTER TABLE `cms_wap_expand`
MODIFY COLUMN `catalogids`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '栏目' AFTER `modelids`;
/*孙要旭 2016年1月5日10:49:51表'cms_content_cat'修改字段长度'url'为255*/
ALTER TABLE `cms_content_cat`
MODIFY COLUMN `url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url' AFTER `wap_url`;


/*孙要旭 2016年1月9日16:46:55 设置站点切换默认值为0*/
update  cms_site set is_switch='0' 
/*孙要旭 2016年1月11日18:14:43 修改菜单管理里面自定义属性改为维度管理*/
/*孙要旭 2016年1月14日18:41:32 修改字段'articleid'为varchar类型*/
ALTER TABLE `cms_video_alburm_article`
MODIFY COLUMN `articleid`  varchar(32) NULL DEFAULT NULL COMMENT '视频文章id' AFTER `alburmid`;
/*孙要旭 2016年1月15日09:58:19 修改字段'uid'为uuid*/
ALTER TABLE `cms_weibo`
CHANGE COLUMN `uid` `userid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `accessToken`;
/*孙要旭2016年1月15日17:48:52 修改字段'time'为'createtime','comment'为'content'*/
ALTER TABLE `cm_invitation`
CHANGE COLUMN `time` `createtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `contact`,
CHANGE COLUMN `comment` `content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容' AFTER `title`;
/*孙要旭2016年1月15日17:48:52 修改字段'number'为'numbers'*/
ALTER TABLE `cms_membergroups`
CHANGE COLUMN `number` `numbers`  int(11) NULL DEFAULT 0 COMMENT '个数' AFTER `userGroupsName`;

ALTER TABLE `cms_note`
CHANGE COLUMN `uid` `userid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'uid' AFTER `id`;

ALTER TABLE `cms_mail`
CHANGE COLUMN `uid` `userid`  int(32) NULL DEFAULT NULL COMMENT 'uid' AFTER `id`;


ALTER TABLE `cms_attach_picture`
MODIFY COLUMN `id`  varchar(32) BINARY NOT NULL COMMENT 'id' FIRST ;

/*孙要旭 2016年1月25日11:59:59 将表'cms_section_data' 中'contentid'的类型改为varchar*/
ALTER TABLE `cms_section_data`
MODIFY COLUMN `contentid`  varchar(32) NULL DEFAULT 0 COMMENT '关联内容id' AFTER `sectionid`;
/*孙要旭 2016年1月27日14:32:33 创建表'cms_copy_content_ref'*/
DROP TABLE IF EXISTS `cms_copy_content_ref`;
CREATE TABLE `cms_copy_content_ref` (
  `id` varchar(32) NOT NULL,
  `LOCK_FLAG` int(11) DEFAULT NULL COMMENT ' 锁定标记（0：正常；1：锁定）',
  `MAIN_CONTENT_ID` varchar(32) DEFAULT NULL COMMENT '主稿件',
  `SUB_CONTENT_ID` varchar(32) DEFAULT NULL COMMENT '子稿件',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int(11) DEFAULT NULL COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*葛瀚洋 2016年2月1日11:55:52 创建 cms_member_depart 会员部门表*/
DROP TABLE IF EXISTS `cms_member_depart`;
CREATE TABLE `cms_member_depart` (
  `ID` varchar(32) NOT NULL,
  `memberId` varchar(32) DEFAULT NULL COMMENT '会员id',
  `departId` varchar(32) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*葛瀚洋 2016年2月1日12:10:11 创建 cms_depart_channel 部门栏目表*/
DROP TABLE IF EXISTS `cms_depart_channel`;
CREATE TABLE `cms_depart_channel` (
  `ID` varchar(32) NOT NULL,
  `departId` varchar(32) DEFAULT NULL,
  `channelId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*葛瀚洋 2016年2月1日12:10:11 创建 cms_role_depart 部门角色表*/
DROP TABLE IF EXISTS `cms_role_depart`;
CREATE TABLE `cms_role_depart` (
  `ID` varchar(32) NOT NULL,
  `roleid` varchar(32) DEFAULT NULL,
  `departid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*葛瀚洋 2016年2月26日15:56:05 创建cms_content_cat_default 存储浏览权限 栏目的id*/
DROP TABLE IF EXISTS `cms_content_cat_default`;
CREATE TABLE `cms_content_cat_default` (
  `id` varchar(32) NOT NULL,
  `channelId` varchar(32) DEFAULT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*2016年4月22日 13:25:53 增加菜单*/
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('40288187543bc37a01543bc59df40005', NULL, '1', '会员部门', '110', 'memberMngAction.do?list', '297ebc2d50a21ef70150a6ff40860015', 'icon-folder-close', NULL, '2016-04-22 10:21:27', NULL, NULL);
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('40288187543bc37a01543bc574510003', NULL, '1', '会员权限', '110', 'departChannelAction.do?list', '297ebc2d50a21ef70150a6ff40860015', 'icon-folder-close', NULL, '2016-04-22 10:21:16', NULL, NULL);
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('40288187543bc37a01543bc523380001', NULL, '1', '默认权限设置', '110', 'departChannelAction.do?setDefault', '297ebc2d50a21ef70150a6ff40860015', 'icon-folder-close', NULL, '2016-04-22 10:20:55', NULL, NULL);
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('4028818453f3d20f0153f3d3dcab0001', NULL, '1', '网易云直播', '110', 'netEaseColudLiveController/getChannelList.do', '402882494abd18ea014abd2ffaa00007', 'icon-folder-close', NULL, '2016-04-08 11:04:21', NULL, NULL);

/*2016年3月17日 14:26:23 liuzhen 修改url字段长度*/
alter table cm_content modify column url varchar(255) ;

/*王宇 2016年3月24日 将表'cms_link' 中'site_id'的类型改为varchar*/
ALTER TABLE `cms_link`
MODIFY COLUMN `site_id`  varchar(32) NULL DEFAULT 0 COMMENT '站点id' AFTER `linkremark`;

/*王宇 2016年3月29日15:43:26 表'cms_message'添加字段'reply_user'*/
ALTER TABLE `cms_message`
ADD COLUMN `reply_user`  varchar(50) NULL COMMENT '回复人' AFTER `CREATEDTIME`;

/*2016年4月14日 14:20:34 liuzhen 投票数据增加字段*/
ALTER TABLE `cms_vote_log_data` ADD COLUMN `voteid` varchar(32) NULL COMMENT '投票id';
/*2016年4月21日10:26:17 syx  栏目表添加字段*/
ALTER TABLE `cms_content_cat`
ADD COLUMN `link_url`  varchar(255) NULL COMMENT '链接地址' AFTER `dynamic_url`,
ADD COLUMN `is_link_url`  int(1) NULL COMMENT '是否是链接地址(0:否1:是)' AFTER `link_url`;
ALTER TABLE `cms_content_cat`
MODIFY COLUMN `is_link_url`  int(1) NULL DEFAULT 0 COMMENT '是否是链接地址(0:否1:是)' AFTER `link_url`;
/*2016年4月21日16:15:32 syx 添加业务咨询表*/
/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50623
Source Host           : 127.0.0.1:3306
Source Database       : ybkj_cms

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2016-04-21 16:13:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_business_consulting
-- ----------------------------
DROP TABLE IF EXISTS `cms_business_consulting`;
CREATE TABLE `cms_business_consulting` (
  `id` varchar(32) NOT NULL COMMENT '主键Id',
  `business_class` varchar(32) DEFAULT NULL COMMENT '业务类别',
  `departid` varchar(32) DEFAULT NULL COMMENT '回复部门id',
  `name` varchar(20) DEFAULT NULL COMMENT '咨询人',
  `phone` varchar(18) DEFAULT NULL COMMENT '联系方式',
  `message` text COMMENT '留言内容',
  `message_time` datetime DEFAULT NULL COMMENT '留言时间',
  `reply_count` text COMMENT '回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `reply_status` varchar(10) DEFAULT NULL COMMENT '回复状态(0未回复1已回复)',
  `ischeck` varchar(10) DEFAULT NULL COMMENT '审核状态(0未审核1已审核)',
  `siteid` varchar(32) DEFAULT NULL COMMENT '站点Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cms_typegroup` (`ID`, `typegroupcode`, `typegroupname`, `createdtime`) VALUES ('402881ed53c54eeb0153c61628500002', 'depart', '部门列表', '2016-03-30 13:54:14');
INSERT INTO `cms_typegroup` (`ID`, `typegroupcode`, `typegroupname`, `createdtime`) VALUES ('402881ed53cb63740153cb6b3aa00001', 'business_class', '业务类别', '2016-03-31 14:45:16');

INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('402881ed53cab7fe0153cb5c10ec0003', NULL, '1', '业务咨询', '110', 'businessConsultingController.do?businessConsulting', '402881ea452582c101452583a4900018', 'icon-folder-close', NULL, '2016-03-31 14:28:42', NULL, NULL);
/**2016年4月25日10:24:56 syx cms_message添加字段*/
ALTER TABLE `cms_message`
ADD COLUMN `reply_user`  varchar(50) NULL COMMENT '回复人' AFTER `createdtime`,
ADD COLUMN `reply_status`  varchar(10) NULL COMMENT '回复状态(0未回复1已回复)' AFTER `reply_user`,
ADD COLUMN `departid`  varchar(10) NULL COMMENT '回复部门' AFTER `reply_status`;
/**2016年4月28日16:30:39 syx 添加字段time_limit*/
ALTER TABLE `cms_message_board`
ADD COLUMN `time_limit`  datetime NULL COMMENT '限制时间' AFTER `sort`;

/*2016/5/3* wangyu 增加置顶字段*/
ALTER TABLE `cm_simplespecial`
ADD COLUMN `is_top`  integer(22)  DEFAULT 0 COMMENT '置顶' AFTER `ID`;

/*2016/5/3* wangyu 增加排序字段*/
ALTER TABLE cm_simplespecial ADD sort INT (11) DEFAULT 0 COMMENT '排序';

/*王宇 2016年5月3日 表'cms_content'添加字段'voteid'*/
ALTER TABLE `cms_content`
ADD COLUMN `voteid`  varchar(50) NULL COMMENT '投票id' AFTER `catName`;

/* 王宇2016/5/3 表'cms_vote_log_data'添加字段'voteid'*/
ALTER TABLE `cms_vote_log_data`
ADD COLUMN `voteid`  varchar(32) NULL DEFAULT NULL COMMENT '投票id' AFTER `logid`;

/**syx2016年5月4日18:04:04 删除表的主外键和约束关系*/
ALTER TABLE `cms_user` DROP FOREIGN KEY `cms_user_ibfk_1`;

ALTER TABLE `cms_user`
DROP INDEX `FK_2cuji5h6yorrxgsr8ojndlmal`;

ALTER TABLE `cms_function`
DROP INDEX `FK_brd7b3keorj8pmxcv8bpahnxp`,
DROP INDEX `FK_q5tqo3v4ltsp1pehdxd59rccx`;

ALTER TABLE `cms_base_user` DROP FOREIGN KEY `cms_base_user_ibfk_1`;

ALTER TABLE `cms_base_user`
DROP INDEX `FK_15jh1g4iem1857546ggor42et`;


ALTER TABLE `cms_depart` DROP FOREIGN KEY `cms_depart_ibfk_1`;

ALTER TABLE `cms_depart`
DROP INDEX `FK_knnm3wb0bembwvm0il7tf6686`;

ALTER TABLE `cms_attachment` DROP FOREIGN KEY `cms_attachment_ibfk_1`;

ALTER TABLE `cms_attachment`
DROP INDEX `FK_mnq23hlc835n4ufgjl7nkn3bd`;

ALTER TABLE `cms_cmskeyword`
DROP INDEX `fk_jc_keyword_site`;

ALTER TABLE `cms_config` DROP FOREIGN KEY `cms_config_ibfk_1`;

ALTER TABLE `cms_config`
DROP INDEX `FK_m3q8r50ror4fl7fjkvd82tqgn`;

ALTER TABLE `cms_content_tag`
DROP INDEX `ak_tag_name`;

ALTER TABLE `cms_demo` DROP FOREIGN KEY `cms_demo_ibfk_1`;

ALTER TABLE `cms_demo`
DROP INDEX `FK_fni8e3v88wcf2sahhlv57u4nm`;

ALTER TABLE `cms_document` DROP FOREIGN KEY `cms_document_ibfk_1`;

ALTER TABLE `cms_document` DROP FOREIGN KEY `cms_document_ibfk_2`;

ALTER TABLE `cms_document`
DROP INDEX `FK_qr3qlmgkflj35m5ci1xv0vvg3`,
DROP INDEX `FK_f2mc12eu0umghp2i70apmtxjl`;

ALTER TABLE `cms_finance_files` DROP FOREIGN KEY `cms_finance_files_ibfk_1`;

ALTER TABLE `cms_finance_files` DROP FOREIGN KEY `cms_finance_files_ibfk_2`;

ALTER TABLE `cms_finance_files`
DROP INDEX `FK_ij2p74feypwcda4n0n96pyd10`,
DROP INDEX `FK_28m7vvi0cy5r5keke68b6f7rt`;

ALTER TABLE `cms_link`
DROP INDEX `contentid`;

ALTER TABLE `cms_log` DROP FOREIGN KEY `cms_log_ibfk_1`;

ALTER TABLE `cms_log`
DROP INDEX `FK_oe64k4852uylhyc5a00rfwtay`;

ALTER TABLE `cms_lottery`
DROP INDEX `token`;

ALTER TABLE `cms_operation` DROP FOREIGN KEY `cms_operation_ibfk_1`;

ALTER TABLE `cms_operation` DROP FOREIGN KEY `cms_operation_ibfk_2`;

ALTER TABLE `cms_operation`
DROP INDEX `FK_pceuy41wr2fjbcilyc7mk3m1f`,
DROP INDEX `FK_ny5de7922l39ta2pkhyspd5f`;

ALTER TABLE `cms_picture_group`
DROP INDEX `contentid`;

ALTER TABLE `cms_site`
DROP INDEX `ak_domain`;

ALTER TABLE `cms_source`
DROP INDEX `name`,
DROP INDEX `initial`;

ALTER TABLE `cms_type` DROP FOREIGN KEY `cms_type_ibfk_1`;

ALTER TABLE `cms_type` DROP FOREIGN KEY `cms_type_ibfk_2`;

ALTER TABLE `cms_type`
DROP INDEX `FK_nw2b22gy7plh7pqows186odmq`,
DROP INDEX `FK_3q40mr4ebtd0cvx79matl39x1`;

ALTER TABLE `cms_video`
DROP INDEX `contentid`;

ALTER TABLE `cms_workflow_step`
DROP INDEX `roleid`;

/*2016年5月20日 16:13:06 liuzhen 访谈表字段类型调整*/
ALTER TABLE `cms_interview`
MODIFY COLUMN `contentid`  varchar(32) NULL DEFAULT NULL COMMENT '内容ID' ;

/*2016年8月26日 09:47:59 liuzhen 增加拓展字段管理菜单*/
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('402882e856bf734f0156bf8b9f1e0001', NULL, '1', '拓展字段', '110', 'modelManageController.do?table', '402881ea452582c101452583a4900018', 'icon-folder-close', NULL, '2016-08-25 10:33:33', NULL, NULL);

/*2016年8月26日 09:48:30 liuzhen 删除多余字段    修改priority字段为int类型*/
ALTER TABLE `cms_modelmanage`
DROP COLUMN `model_path`,
DROP COLUMN `tpl_channel_prefix`,
DROP COLUMN `tpl_content_prefix`,
DROP COLUMN `title_img_width`,
DROP COLUMN `title_img_height`,
DROP COLUMN `content_img_width`,
DROP COLUMN `content_img_height`,
DROP COLUMN `has_content`,
DROP COLUMN `is_disabled`,
DROP COLUMN `is_def`,
MODIFY COLUMN `priority`  int NULL DEFAULT 1 COMMENT '排列顺序';

/*2016年8月26日 09:48:30 liuzhen 删除多余字段*/
ALTER TABLE `cms_model`
DROP COLUMN `posts`,
DROP COLUMN `comments`,
DROP COLUMN `pv`;


ALTER TABLE `cms_model_item`
MODIFY COLUMN `priority`  int NULL DEFAULT 1 COMMENT '排列顺序' AFTER `item_label`;

ALTER TABLE `cms_model_item`
DROP COLUMN `item_label`,
DROP COLUMN `is_single`,
DROP COLUMN `is_display`,
DROP COLUMN `def_value`,
DROP COLUMN `opt_value`,
DROP COLUMN `text_size`,
DROP COLUMN `area_cols`,
DROP COLUMN `help`,
DROP COLUMN `help_position`,
DROP COLUMN `is_channel`,
DROP COLUMN `is_custom`;

/** 16/10/26  wangyu 更新内容表，栏目表的path、pathids长度**/
ALTER TABLE `cms_content`
MODIFY COLUMN `pathids`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'pathids' AFTER `catid`;

ALTER TABLE `cms_content_cat`
MODIFY COLUMN `pathids`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0,' COMMENT '当前栏目的路径id  包含所有父级栏目id以及当前栏目id' AFTER `parentids`,
MODIFY COLUMN `path`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '栏目生成路径' AFTER `template_date`;

/**syx 2016年11月22日14:08:28*/
ALTER TABLE `cms_classify`
CHANGE COLUMN `level` `levels`  int(11) NULL DEFAULT NULL COMMENT '分类级别' AFTER `token`;

ALTER TABLE `cm_comments`
CHANGE COLUMN `comment` `comments`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容' AFTER `title`;

/**syx 2016年12月2日14:01:25 修改字符长度转oracle*/
ALTER TABLE `cm_content`
MODIFY COLUMN `title`  char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题' AFTER `classify`;

ALTER TABLE `cm_picture_group`
DROP INDEX `contentid`;
ALTER TABLE `cm_video`
DROP INDEX `contentid`;

ALTER TABLE `cms_content`
MODIFY COLUMN `title`  char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题' AFTER `classify`,
MODIFY COLUMN `subtitle`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短标题' AFTER `title`;

ALTER TABLE `cms_content`
MODIFY COLUMN `author`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者' AFTER `tags`;

ALTER TABLE `cms_content_cat`
MODIFY COLUMN `name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '栏目名称' AFTER `pathids`;

ALTER TABLE `cms_doc_returnvalue`
MODIFY COLUMN `description`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `default_value`;

ALTER TABLE `cms_lottery`
MODIFY COLUMN `endinfo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `endpicurl`;

ALTER TABLE `cms_operation` DROP FOREIGN KEY `cms_operation_ibfk_1`;

ALTER TABLE `cms_operation` DROP FOREIGN KEY `cms_operation_ibfk_2`;

ALTER TABLE `cms_operation`
DROP INDEX `FK_pceuy41wr2fjbcilyc7mk3m1f`,
DROP INDEX `FK_ny5de7922l39ta2pkhyspd5f`;

ALTER TABLE `cms_picture_group`
DROP INDEX `contentid`;

ALTER TABLE `cms_site`
DROP INDEX `ak_domain`;

ALTER TABLE `cms_source`
DROP INDEX `name`,
DROP INDEX `initial`;

ALTER TABLE `cms_type` DROP FOREIGN KEY `cms_type_ibfk_1`;

ALTER TABLE `cms_type` DROP FOREIGN KEY `cms_type_ibfk_2`;

ALTER TABLE `cms_type`
DROP INDEX `FK_nw2b22gy7plh7pqows186odmq`,
DROP INDEX `FK_3q40mr4ebtd0cvx79matl39x1`;

ALTER TABLE `cms_video`
DROP INDEX `contentid`;

ALTER TABLE `cms_workflow_step`
DROP INDEX `roleid`;

/*2016年5月20日 16:13:06 liuzhen 访谈表字段类型调整*/
ALTER TABLE `cms_interview`
MODIFY COLUMN `contentid`  varchar(32) NULL DEFAULT NULL COMMENT '内容ID' ;

/*2016年8月26日 09:47:59 liuzhen 增加拓展字段管理菜单*/
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('402882e856bf734f0156bf8b9f1e0001', NULL, '1', '拓展字段', '110', 'modelManageController.do?table', '402881ea452582c101452583a4900018', 'icon-folder-close', NULL, '2016-08-25 10:33:33', NULL, NULL);

/*2016年8月26日 09:48:30 liuzhen 删除多余字段    修改priority字段为int类型*/
ALTER TABLE `cms_modelmanage`
DROP COLUMN `model_path`,
DROP COLUMN `tpl_channel_prefix`,
DROP COLUMN `tpl_content_prefix`,
DROP COLUMN `title_img_width`,
DROP COLUMN `title_img_height`,
DROP COLUMN `content_img_width`,
DROP COLUMN `content_img_height`,
DROP COLUMN `has_content`,
DROP COLUMN `is_disabled`,
DROP COLUMN `is_def`,
MODIFY COLUMN `priority`  int NULL DEFAULT 1 COMMENT '排列顺序';

/*2016年8月26日 09:48:30 liuzhen 删除多余字段*/
ALTER TABLE `cms_model`
DROP COLUMN `posts`,
DROP COLUMN `comments`,
DROP COLUMN `pv`;


ALTER TABLE `cms_model_item`
MODIFY COLUMN `priority`  int NULL DEFAULT 1 COMMENT '排列顺序';

ALTER TABLE `cms_model_item`
DROP COLUMN `is_single`,
DROP COLUMN `is_display`,
DROP COLUMN `def_value`,
DROP COLUMN `opt_value`,
DROP COLUMN `text_size`,
DROP COLUMN `area_cols`,
DROP COLUMN `help`,
DROP COLUMN `help_position`,
DROP COLUMN `is_channel`,
DROP COLUMN `is_custom`;

/*syx 2016年11月17日16:18:20 修改字段类型*/
ALTER TABLE `cms_vote`
MODIFY COLUMN `site_id`  varchar(32) NULL DEFAULT NULL COMMENT '站点id' AFTER `maxVotes`;

/*syx 2016年11月22日16:05:30*/
ALTER TABLE `cm_comments`
CHANGE COLUMN `comment` `comments`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容' AFTER `title`;

/**刘林建 2017年3月23日10:18:38 添加字段 */
ALTER TABLE `cms_content`
ADD COLUMN `supportcount`  int(11) NULL COMMENT '增加赞同数' AFTER `dynamic_url`,
ADD COLUMN `opposecount`  int(11) NULL COMMENT '取消点赞数' AFTER `supportcount`;

/**liulinjian 2017年3月27日11:07:49 添加字段*/
ALTER TABLE `cms_commentary`
ADD COLUMN `tipOffCount`  tinyint(1) NULL DEFAULT 0 COMMENT '举报数量' AFTER `createdtime`,
ADD COLUMN `auditorName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人' AFTER `tipOffCount`,
ADD COLUMN `auditorId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID' AFTER `auditorName`,
ADD COLUMN `auditorCreateTime`  datetime NULL DEFAULT NULL COMMENT '审核时间' AFTER `auditorId`,
ADD COLUMN `supportcount`  int(11) NULL DEFAULT 0 COMMENT '点赞数' AFTER `auditorCreateTime`,
ADD COLUMN `opposecount`  int(11) NULL DEFAULT 0 COMMENT '贬赞数' AFTER `supportcount`;

/**liulinjian 2017年3月27日11:40:10 创建表*/
CREATE TABLE `cms_reply` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '跟帖ID' ,
`replyPerson`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跟帖人' ,
`replyTime`  datetime NULL DEFAULT NULL COMMENT '跟帖时间' ,
`replyContent`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跟帖内容 ' ,
`siteId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '站点Id' ,
`supportcount`  int(11) NULL DEFAULT 0 COMMENT '点赞数' ,
`opposecount`  int(11) NULL DEFAULT 0 COMMENT '贬赞数 ' ,
`ischeck`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态' ,
`auditorId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人Id' ,
`auditorName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人' ,
`auditorCreateTime`  datetime NULL DEFAULT NULL COMMENT '审核时间' ,
PRIMARY KEY (`id`)
)
;

/** liulinjian 2017年3月27日11:48:15 添加字段*/
ALTER TABLE `cms_reply`
ADD COLUMN `returnPerson`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复人' AFTER `auditorCreateTime`,
ADD COLUMN `returnId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复Id' AFTER `returnPerson`,
ADD COLUMN `returnContent`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容' AFTER `returnId`,
ADD COLUMN `returnTime`  datetime NULL DEFAULT NULL COMMENT '回复时间' AFTER `returnContent`;

/**liulinjian 2017年3月31日16:47:52 改变状态的类型*/
ALTER TABLE `cms_commentary`
MODIFY COLUMN `ischeck`  tinyint(1) NULL DEFAULT 0 COMMENT '审核状态，默认未审核' AFTER `telephone`;

/** liulinjian 2017年4月5日10:34:24 增加字段*/
ALTER TABLE `cms_reply`
MODIFY COLUMN `id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id' FIRST ,
ADD COLUMN `replyId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跟帖ID' AFTER `returnTime`;

/** liulinjian 2017年4月6日14:31:36 增加字段*/
ALTER TABLE `cms_commentary`
ADD COLUMN `replycount`  int(11) NULL DEFAULT 0 COMMENT '跟帖数目' AFTER `opposecount`;

/**liulinjian 2017年4月7日09:00:17 增加字段*/
ALTER TABLE `cms_content`
ADD COLUMN `commentcount`  int(11) NULL DEFAULT 0 COMMENT '评论数量' AFTER `opposecount`;

/**liulinjian 2017年4月7日17:34:32 改变字段*/
ALTER TABLE `cms_reply`
CHANGE COLUMN `replyId` `commentId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论ID' AFTER `returnTime`;

/**liuilinjian 2017年4月7日20:06:36 增加字段*/
ALTER TABLE `cms_reply`
ADD COLUMN `returncount`  int(11) NULL DEFAULT 0 COMMENT '回复数量' AFTER `commentId`;

/**liulinjian 2017年4月10日17:16:44 增加字段*/
ALTER TABLE `cms_reply`
ADD COLUMN `replyPersonId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跟帖人id' AFTER `returncount`;

/**liulinjian 2017年4月14日15:24:43 增加字段*/
ALTER TABLE `cms_commentary`
ADD COLUMN `siteId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '站点ID' AFTER `replycount`;

/**liulinjian 2017年4月17日15:07:31 增加字段*/
ALTER TABLE `cms_reply`
ADD COLUMN `replyId`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被回复贴的id' AFTER `replyPerson`;

/**liulinjian 2017年4月17日16:56:05 删除字段*/
ALTER TABLE `cms_content`
DROP COLUMN `commentcount`;
/**syx 2017年4月14日10:05:21 修改字段长度*/
ALTER TABLE `cms_relate_content`
MODIFY COLUMN `relate_url`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL路径' AFTER `relate_title`;

/**liulinjian 2017年4月17日16:56:05 删除字段*/
ALTER TABLE `cms_content`
DROP COLUMN `commentcount`;
/**syx 2017-4-18 10:46:30 删除多余字段*/
ALTER TABLE `cms_commentary`
DROP COLUMN `memberLevel`,
DROP COLUMN `qq`,
DROP COLUMN `cellphone`,
DROP COLUMN `telephone`,
DROP COLUMN `tipOffCount`;

/**syx 2017年4月18日16:30:48添加评论管理*/
INSERT INTO `cms_function` (`ID`, `functioniframe`, `functionlevel`, `functionname`, `functionorder`, `functionurl`, `parentfunctionid`, `iconclass`, `privurl`, `createdtime`, `pathids`, `parentids`) VALUES ('402881c05b80183b015b80305f220005', NULL, '1', '评论管理', '110', 'commentaryController.do?commentaryList', '402881ea452582c101452583a4900018', 'icon-folder-close', NULL, '2017-04-18 16:31:45', NULL, NULL);


/**hejiping 2017年4月26日11:16:10 新增评论广告表**/
CREATE TABLE `cms_comment_advertising` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `site_id` varchar(32) NOT NULL COMMENT '站点id',
  `name` varchar(255) DEFAULT NULL COMMENT '广告名称',
  `image_url` varchar(255) DEFAULT NULL COMMENT '资源图片路径',
  `image_width` double DEFAULT NULL COMMENT '图片宽',
  `image_heigh` double DEFAULT NULL COMMENT '图片高',
  `link_remark` varchar(1000) DEFAULT NULL COMMENT '链接提示',
  `link_target` tinyint(1) DEFAULT '0' COMMENT '链接目标(1:新窗口 0:原窗口)',
  `link_url` varchar(1000) DEFAULT NULL COMMENT '链接地址',
  `weight` int(11) DEFAULT '0' COMMENT '权重',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `is_using` tinyint(1) DEFAULT '0' COMMENT '是否启用(1:是 0:否 )',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*syx 2017年4月28日16:44:50  删除手机内容广告和列表广告*/
drop table cm_advertisemen_content;
drop table cm_advertisemen_list;

DELETE FROM cms_function where id='402881e947f353c80147f359a16b0007';
DELETE FROM cms_role_function where functionid='402881e947f353c80147f359a16b0007';


/**syx 删除cms_classify 2017年4月28日17:26:25*/
drop table cms_classify;











