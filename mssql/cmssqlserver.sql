
USE [cms]
GO
/****** Object:  Table [dbo].[wechat_user]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_user](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[originalId] [varchar](255) NULL,
	[wechatId] [varchar](255) NULL,
	[appId] [varchar](255) NULL,
	[appSecret] [varchar](255) NULL,
	[qrPath] [varchar](400) NULL,
	[type] [varchar](255) NULL,
	[access_token] [varchar](1024) NULL,
	[aeskey] [varchar](255) NULL,
	[encode] [varchar](255) NULL,
	[headerpic] [varchar](255) NULL,
	[token] [varchar](255) NULL,
	[QQ] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[lastUpdateTime] [datetime] NULL,
	[lastGetAccessTokenTime] [datetime] NULL,
	[lastOverdueTime] [datetime] NULL,
	[isUpdate] [bit] NULL,
	[version] [int] NULL,
	[email] [varchar](100) NULL,
	[fansCount] [int] NULL,
	[isStartingUsingCoupons] [bit] NULL,
	[isShowNearbyNews] [bit] NULL,
	[isOpenChat] [bit] NULL,
	[province] [varchar](50) NULL,
	[city] [varchar](100) NULL,
	[region] [varchar](150) NULL,
 CONSTRAINT [wechat_user_PK_wechat_push] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号原始id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'originalId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'wechatId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'AppID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'appId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'AppSecret' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'appSecret'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'二维码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'qrPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信号类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'access_token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'access_token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'消息加解密密钥' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'aeskey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'消息加密方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'encode'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'头像' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'headerpic'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'QQ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'QQ'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后一次修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'lastUpdateTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后一次获取accessToken时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'lastGetAccessTokenTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'倒数第二次获取accessToken实际到期时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'lastOverdueTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否已经提前更新' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'isUpdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'粉丝数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'fansCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用微信卡券' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'isStartingUsingCoupons'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用附近图文' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'isShowNearbyNews'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否开启聊天（小黄鸡）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'isOpenChat'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地区' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_user', @level2type=N'COLUMN',@level2name=N'region'
GO
/****** Object:  Table [dbo].[wechat_push]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_push](
	[id] [varchar](32) NOT NULL,
	[CREATE_PERSON] [varchar](255) NULL,
	[CREATE_TIME] [datetime] NULL,
	[PUSH_PERSON] [varchar](255) NULL,
	[PUSH_TIME] [datetime] NULL,
	[IS_PUSH] [int] NULL,
	[IS_SUCESSPUSH] [int] NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [wechat_push_PK_wechat_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'CREATE_PERSON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'CREATE_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'推送人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'PUSH_PERSON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'推送时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'PUSH_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否推送,1:是,0:否' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'IS_PUSH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否推送成功,1:成功,0:失败' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'IS_SUCESSPUSH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_push', @level2type=N'COLUMN',@level2name=N'site_id'
GO
/****** Object:  Table [dbo].[wechat_content]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_content](
	[id] [varchar](32) NOT NULL,
	[WEIXIN_TITLE] [varchar](255) NULL,
	[WEIXIN_DIGEST] [varchar](255) NULL,
	[WEIXIN_CONTENT] [text] NULL,
	[WEIXIN_CONTENTADDRESS] [varchar](255) NULL,
	[WEIXIN_PICTUREURL] [varchar](255) NULL,
	[WEIXIN_SORT] [varchar](255) NULL,
	[WEIXIN_TOP] [int] NULL,
	[CONTENT_ID] [varchar](32) NULL,
	[WEIXIN_ID] [varchar](32) NULL,
	[SHOW_COVER_PIC] [int] NULL,
	[WEIXIN_AUTHOR] [varchar](255) NULL,
	[WEIXIN_CREATETIME] [datetime] NULL,
 CONSTRAINT [wechat_content_PK_wechat_configure] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_TITLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容摘要' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_DIGEST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_CONTENT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_CONTENTADDRESS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_PICTUREURL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_SORT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 是否是头条, 1:是,0:否' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_TOP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'CONTENT_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示封面，1为显示，0为不显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'SHOW_COVER_PIC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_AUTHOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信内容创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_content', @level2type=N'COLUMN',@level2name=N'WEIXIN_CREATETIME'
GO
/****** Object:  Table [dbo].[wechat_configure]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_configure](
	[Id] [varchar](32) NOT NULL,
	[AppId] [varchar](255) NOT NULL,
	[Secret] [varchar](255) NOT NULL,
	[token] [varchar](255) NOT NULL,
	[AesKey] [varchar](255) NOT NULL,
	[siteid] [varchar](32) NOT NULL,
	[createtime] [datetime] NULL,
 CONSTRAINT [wechat_configure_PK_wechat_config] PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信公众账号appid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'AppId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信公众号的秘钥' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'Secret'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信公众号的token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信公众号的EncodingAESKey' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'AesKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_configure', @level2type=N'COLUMN',@level2name=N'createtime'
GO
/****** Object:  Table [dbo].[wechat_config]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_config](
	[id] [varchar](255) NOT NULL,
	[configName] [varchar](255) NULL,
	[configValue] [varchar](1000) NULL,
	[remark] [text] NULL,
	[version] [int] NULL,
 CONSTRAINT [wechat_config_PK_wechat_cert] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'参数名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config', @level2type=N'COLUMN',@level2name=N'configName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'参数值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config', @level2type=N'COLUMN',@level2name=N'configValue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信参数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_config'
GO
/****** Object:  Table [dbo].[wechat_cert]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_cert](
	[id] [int] NOT NULL,
	[whId] [varchar](32) NULL,
	[token] [varchar](32) NULL,
	[apiclient_cert] [varchar](255) NULL,
	[apiclient_key] [varchar](255) NULL,
	[rootca] [varchar](255) NULL,
	[lastUpdateTime] [datetime] NULL,
 CONSTRAINT [wechat_cert_PK_wechat_button] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'密钥Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'whId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'密钥' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'apiclient_cert'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'私钥' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'apiclient_key'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'rootca' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'rootca'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后一次修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert', @level2type=N'COLUMN',@level2name=N'lastUpdateTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'密钥管理' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_cert'
GO
/****** Object:  Table [dbo].[wechat_button]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wechat_button](
	[id] [varchar](32) NOT NULL,
	[wuId] [varchar](32) NULL,
	[token] [varchar](255) NULL,
	[pid] [varchar](32) NULL,
	[name] [varchar](40) NULL,
	[type] [varchar](32) NULL,
	[media_id] [varchar](256) NULL,
	[url] [varchar](256) NULL,
	[menuKey] [varchar](128) NULL,
	[level] [int] NULL,
	[isShow] [bit] NULL,
	[version] [int] NULL,
 CONSTRAINT [wechat_button_PK_t_tree] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'wuId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公众号token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'pid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'响应动作类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'media_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网页链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单KEY值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'menuKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'level'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'isShow'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信菜单' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'wechat_button'
GO
/****** Object:  Table [dbo].[t_tree]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_tree](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](20) NULL,
	[level] [int] NULL,
	[parentid] [varchar](32) NULL,
	[sort] [int] NULL,
	[pathids] [varchar](255) NULL,
	[parentids] [varchar](255) NULL,
 CONSTRAINT [t_tree_PK_t_stu] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'层级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'level'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父节点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'parentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父节点id,包含当前id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父节点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree', @level2type=N'COLUMN',@level2name=N'parentids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'测试树' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_tree'
GO
/****** Object:  Table [dbo].[t_stu]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_stu](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](10) NULL,
	[sex] [char](2) NULL,
	[birthday] [datetime] NULL,
	[class_id] [varchar](32) NULL,
 CONSTRAINT [t_stu_PK_t_class] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu', @level2type=N'COLUMN',@level2name=N'sex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'生日' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu', @level2type=N'COLUMN',@level2name=N'birthday'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'班级id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu', @level2type=N'COLUMN',@level2name=N'class_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'学生' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_stu'
GO
/****** Object:  Table [dbo].[t_class]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_class](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[number] [int] NULL,
 CONSTRAINT [t_class_PK_sina_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_class', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'班级名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_class', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'班级号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_class', @level2type=N'COLUMN',@level2name=N'number'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'班级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N't_class'
GO
/****** Object:  Table [dbo].[sina_content]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sina_content](
	[id] [varchar](32) NOT NULL,
	[sina_title] [varchar](255) NULL,
	[sina_digest] [varchar](255) NULL,
	[sina_thumb] [varchar](255) NULL,
	[sina_url] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
	[createtime] [datetime] NULL,
	[contentid] [varchar](32) NULL,
 CONSTRAINT [sina_content_PK_cms_workflow_step] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'sina_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'摘要' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'sina_digest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'sina_thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sina_content', @level2type=N'COLUMN',@level2name=N'contentid'
GO
/****** Object:  Table [dbo].[cms_workflow_step]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_workflow_step](
	[id] [varchar](32) NOT NULL,
	[step] [int] NULL,
	[roleid] [varchar](32) NULL,
	[gid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_workflow_step_PK_cms_workflow_auditing] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'步骤' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_step', @level2type=N'COLUMN',@level2name=N'step'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'权限' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_step', @level2type=N'COLUMN',@level2name=N'roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_step', @level2type=N'COLUMN',@level2name=N'gid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_step', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_step', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_workflow_auditing]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_workflow_auditing](
	[ID] [varchar](32) NOT NULL,
	[relationID] [varchar](32) NOT NULL,
	[type] [int] NULL,
	[step] [int] NULL,
	[auditingrole] [varchar](256) NULL,
	[opinion] [varchar](1024) NULL,
	[Auditor] [varchar](50) NULL,
	[AuditingDate] [datetime] NULL,
	[AuditingResult] [int] NULL,
	[OperatorOID] [varchar](100) NULL,
	[DelFlag] [int] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_workflow_auditing_PK_cms_workflow] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'relationID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'步骤' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'step'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核角色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'auditingrole'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核意见' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'opinion'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'Auditor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'AuditingDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核结果' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'AuditingResult'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'OperatorOID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'DelFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流审核表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow_auditing'
GO
/****** Object:  Table [dbo].[cms_workflow]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_workflow](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](30) NULL,
	[description] [varchar](255) NULL,
	[steps] [bigint] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_workflow_PK_cms_weibo] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'步骤' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow', @level2type=N'COLUMN',@level2name=N'steps'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_workflow', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_weibo]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_weibo](
	[id] [varchar](32) NULL,
	[site_id] [varchar](255) NULL,
	[client_ID] [varchar](255) NULL,
	[client_SERCRET] [varchar](255) NULL,
	[redirect_URI] [varchar](255) NULL,
	[baseURL] [varchar](255) NULL,
	[accessTokenURL] [varchar](255) NULL,
	[authorizeURL] [varchar](255) NULL,
	[rmURL] [varchar](255) NULL,
	[accessToken] [varchar](255) NULL,
	[userid] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_weibo', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_weibo', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'App Key' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_weibo', @level2type=N'COLUMN',@level2name=N'client_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'App Secret' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_weibo', @level2type=N'COLUMN',@level2name=N'client_SERCRET'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回调地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_weibo', @level2type=N'COLUMN',@level2name=N'redirect_URI'
GO
/****** Object:  Table [dbo].[cms_web_error]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_web_error](
	[id] [varchar](32) NULL,
	[name] [varchar](32) NULL,
	[email] [varchar](50) NULL,
	[telephone] [varchar](18) NULL,
	[work_unit] [varchar](100) NULL,
	[content] [text] NULL,
	[siteid] [varchar](32) NULL,
	[createtime] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'work_unit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_web_error', @level2type=N'COLUMN',@level2name=N'createtime'
GO
/****** Object:  Table [dbo].[cms_watermark]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_watermark](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](32) NULL,
	[imagepath] [varchar](90) NULL,
	[position] [varchar](32) NULL,
	[width] [varchar](32) NULL,
	[height] [varchar](32) NULL,
	[undiaphaneity] [varchar](32) NULL,
	[quality] [varchar](32) NULL,
	[state] [int] NULL,
	[defaultset] [int] NULL,
	[departid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_watermark_PK_cms_wap_expand] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片图路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'imagepath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'水印位置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'position'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'宽' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'width'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'高' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'height'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'水印不透明度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'undiaphaneity'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'水印质量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'quality'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'state'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认方案' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'defaultset'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所属id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'departid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_watermark', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_wap_expand]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_wap_expand](
	[id] [varchar](32) NOT NULL,
	[configid] [varchar](32) NULL,
	[modelids] [varchar](255) NULL,
	[catalogids] [varchar](1000) NULL,
 CONSTRAINT [cms_wap_expand_PK_cms_wap_config] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_expand', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主配置id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_expand', @level2type=N'COLUMN',@level2name=N'configid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_expand', @level2type=N'COLUMN',@level2name=N'modelids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_expand', @level2type=N'COLUMN',@level2name=N'catalogids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'WAP配置项扩展表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_expand'
GO
/****** Object:  Table [dbo].[cms_wap_config]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_wap_config](
	[id] [varchar](32) NOT NULL,
	[siteid] [varchar](32) NOT NULL,
	[wapstatus] [varchar](100) NULL,
	[wapname] [varchar](100) NULL,
	[waplogo] [varchar](255) NULL,
	[indexcolumncount] [varchar](50) NULL,
	[indexminweights] [varchar](50) NULL,
	[columncount] [varchar](50) NULL,
	[listcount] [varchar](50) NULL,
	[columnminweights] [varchar](50) NULL,
	[evaluatelistcount] [varchar](50) NULL,
	[indextemplate] [varchar](50) NULL,
	[listtemplate] [varchar](50) NULL,
	[contenttemplate] [varchar](255) NULL,
	[picturestemplate] [varchar](50) NULL,
	[evaluatetemplate] [varchar](50) NULL,
	[createdtime] [datetime] NULL,
	[video_template] [varchar](50) NULL,
	[surver_template] [varchar](50) NULL,
	[vote_template] [varchar](50) NULL,
 CONSTRAINT [cms_wap_config_PK_cms_vote_option] PRIMARY KEY CLUSTERED
(
	[id] ASC,
	[siteid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开启状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'wapstatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'wapname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站LOGO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'waplogo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页栏目内容条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'indexcolumncount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页显示最低权重' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'indexminweights'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目首页子栏目显示条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'columncount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表页条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'listcount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目显示最低权重' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'columnminweights'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论列表页条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'evaluatelistcount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'indextemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表页模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'listtemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章内容模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'contenttemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'组图内容模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'picturestemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'evaluatetemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'video_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'surver_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config', @level2type=N'COLUMN',@level2name=N'vote_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'WAP配置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_wap_config'
GO
/****** Object:  Table [dbo].[cms_vote_option]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_vote_option](
	[id] [varchar](32) NOT NULL,
	[voteid] [varchar](255) NULL,
	[optionName] [varchar](255) NULL,
	[optionLink] [varchar](255) NULL,
	[optionImg] [varchar](255) NULL,
	[optionTotal] [int] NULL,
	[optionSort] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_vote_option_PK_cms_vote_log_data] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'voteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'optionName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'optionLink'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'optionImg'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'初始票数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'optionTotal'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'optionSort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票选项表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_option'
GO
/****** Object:  Table [dbo].[cms_vote_log_data]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_vote_log_data](
	[id] [varchar](32) NOT NULL,
	[logid] [varchar](32) NULL,
	[optionid] [varchar](32) NULL,
	[ip] [varchar](15) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[county] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
	[voteid] [varchar](32) NULL,
 CONSTRAINT [cms_vote_log_data_PK_cms_vote_log] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票日志id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'logid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'optionid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区县' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'county'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data', @level2type=N'COLUMN',@level2name=N'voteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票日志选项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log_data'
GO
/****** Object:  Table [dbo].[cms_vote_log]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_vote_log](
	[id] [varchar](32) NOT NULL,
	[voteid] [varchar](32) NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[ip] [varchar](15) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[county] [varchar](255) NULL,
 CONSTRAINT [cms_vote_log_PK_cms_vote] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'voteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票日志表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote_log'
GO
/****** Object:  Table [dbo].[cms_vote]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_vote](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[voteType] [varchar](255) NULL,
	[votePattern] [varchar](255) NULL,
	[voteIntroduce] [varchar](255) NULL,
	[voteIpLimit] [varchar](255) NULL,
	[voteProvinceLimit] [varchar](255) NULL,
	[voteStartTime] [datetime] NULL,
	[voteEndTime] [datetime] NULL,
	[voteTotal] [int] NULL,
	[voteMinInterval] [varchar](255) NULL,
	[maxVotes] [int] NULL,
	[site_id] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_vote_PK_cms_video_sources] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票模式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'votePattern'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteIntroduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteIpLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否省份限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteProvinceLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总票数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteTotal'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间间隔' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'voteMinInterval'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最多票数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'maxVotes'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_vote'
GO
/****** Object:  Table [dbo].[cms_video_sources]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_video_sources](
	[id] [varchar](32) NOT NULL,
	[videoName] [varchar](225) NULL,
	[realName] [varchar](225) NULL,
	[transPath] [varchar](300) NULL,
	[localPath] [varchar](300) NULL,
	[defaultimage] [varchar](300) NULL,
	[type] [varchar](10) NULL,
	[timesize] [int] NULL,
	[createDate] [datetime] NULL,
	[mark] [varchar](500) NULL,
	[videosize] [bigint] NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cms_video_sources_PK_cms_video_alburm_article] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频原名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'videoName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传后的名字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'realName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转化后本地路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'transPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访问路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'localPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'defaultimage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时长' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'timesize'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'createDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'mark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频库' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_sources'
GO
/****** Object:  Table [dbo].[cms_video_alburm_article]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_video_alburm_article](
	[id] [varchar](32) NOT NULL,
	[alburmid] [varchar](32) NULL,
	[articleid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_video_alburm_article_PK_cms_video_alburm] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm_article', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频专辑id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm_article', @level2type=N'COLUMN',@level2name=N'alburmid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频文章id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm_article', @level2type=N'COLUMN',@level2name=N'articleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm_article', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频专辑关联表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm_article'
GO
/****** Object:  Table [dbo].[cms_video_alburm]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_video_alburm](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](225) NULL,
	[sorttype] [varchar](225) NULL,
	[createdate] [datetime] NULL,
	[site_id] [varchar](32) NULL,
	[relatearticle] [varchar](500) NULL,
 CONSTRAINT [cms_video_alburm_PK_cms_video_adv] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专辑名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序方式:1-正序，2-倒序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'sorttype'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'createdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联文章' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm', @level2type=N'COLUMN',@level2name=N'relatearticle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频专辑' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_alburm'
GO
/****** Object:  Table [dbo].[cms_video_adv]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_video_adv](
	[id] [varchar](32) NOT NULL,
	[headadvstatus] [int] NULL,
	[headadvresource] [varchar](255) NULL,
	[headadvlink] [varchar](255) NULL,
	[headadvtime] [int] NULL,
	[stopadvstatus] [int] NULL,
	[stopadvreource] [varchar](255) NULL,
	[stopadvlink] [varchar](255) NULL,
	[footadvstatus] [int] NULL,
	[footadvresource] [varchar](255) NULL,
	[footadvlink] [varchar](255) NULL,
	[footadvtime] [int] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_video_adv_PK_cms_video] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片头广告状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'headadvstatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片头广告资源地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'headadvresource'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片头广告链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'headadvlink'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片头广告时长' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'headadvtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'暂停广告状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'stopadvstatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'暂停广告资源地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'stopadvreource'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'暂停广告链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'stopadvlink'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片尾广告状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'footadvstatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片尾广告资源地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'footadvresource'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片尾广告链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'footadvlink'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'片尾广告时长' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'footadvtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频广告管理' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video_adv'
GO
/****** Object:  Table [dbo].[cms_video]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_video](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[videoname] [varchar](255) NULL,
	[videourl] [varchar](4000) NULL,
	[videoclassify] [varchar](255) NULL,
	[time] [varchar](255) NULL,
	[special] [varchar](255) NULL,
	[videoremark] [varchar](1000) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_video_PK_cms_version] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'videoname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'videourl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'videoclassify'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频时长' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频专辑' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'special'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'videoremark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_video'
GO
/****** Object:  Table [dbo].[cms_version]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_version](
	[ID] [varchar](32) NOT NULL,
	[loginpage] [varchar](100) NULL,
	[versioncode] [varchar](50) NULL,
	[versionname] [varchar](30) NULL,
	[versionnum] [varchar](20) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_version_PK_cms_user] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_version', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_user]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_user](
	[head_portrait] [varchar](225) NULL,
	[email] [varchar](50) NULL,
	[mobilePhone] [varchar](30) NULL,
	[officePhone] [varchar](20) NULL,
	[signatureFile] [varchar](100) NULL,
	[id] [varchar](32) NOT NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_user_PK_cms_typegroup] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统用户的头像' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_user', @level2type=N'COLUMN',@level2name=N'head_portrait'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_user', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`id`) REFER `lm_maven_cms/cms_base_user`(`ID`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_user'
GO
/****** Object:  Table [dbo].[cms_typegroup]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_typegroup](
	[ID] [varchar](32) NOT NULL,
	[typegroupcode] [varchar](50) NULL,
	[typegroupname] [varchar](50) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_typegroup_PK_cms_type] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_typegroup', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_type]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_type](
	[ID] [varchar](32) NOT NULL,
	[typecode] [varchar](50) NULL,
	[typename] [varchar](50) NULL,
	[typepid] [varchar](32) NULL,
	[typegroupid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_type_PK_cms_timetask] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_type', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`typegroupid`) REFER `lm_maven_cms/cms_typegroup`(`ID`); ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_type'
GO
/****** Object:  Table [dbo].[cms_timetask]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_timetask](
	[ID] [varchar](32) NOT NULL,
	[CREATE_BY] [varchar](32) NULL,
	[CREATE_DATE] [datetime] NULL,
	[CREATE_NAME] [varchar](32) NULL,
	[CRON_EXPRESSION] [varchar](100) NOT NULL,
	[IS_EFFECT] [varchar](1) NOT NULL,
	[IS_START] [varchar](1) NOT NULL,
	[TASK_DESCRIBE] [varchar](50) NOT NULL,
	[TASK_ID] [varchar](100) NOT NULL,
	[UPDATE_BY] [varchar](32) NULL,
	[UPDATE_DATE] [datetime] NULL,
	[UPDATE_NAME] [varchar](32) NULL,
 CONSTRAINT [cms_timetask_PK_cms_third_login] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_third_login]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_third_login](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[isopen] [varchar](100) NULL,
	[appid] [varchar](255) NULL,
	[appkey] [varchar](255) NULL,
	[returnurl] [varchar](255) NULL,
	[authorizeurl] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_third_login_PK_cms_territory] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'第三方名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否开启' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'isopen'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'appid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'appid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'appkey' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'appkey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回调地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'returnurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'授权请求地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'authorizeurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_third_login', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_territory]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_territory](
	[ID] [varchar](32) NOT NULL,
	[territorycode] [varchar](10) NOT NULL,
	[territorylevel] [smallint] NOT NULL,
	[territoryname] [varchar](50) NOT NULL,
	[territory_pinyin] [varchar](40) NULL,
	[territorysort] [varchar](3) NOT NULL,
	[x_wgs84] [float] NOT NULL,
	[y_wgs84] [float] NOT NULL,
	[territoryparentid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_territory_PK_cms_survey_option_ext] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_territory', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_survey_option_ext]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey_option_ext](
	[id] [varchar](32) NOT NULL,
	[optionsid] [varchar](255) NULL,
	[ext_optionName] [varchar](255) NULL,
	[ext_optionPicture] [varchar](255) NULL,
	[ext_dataType] [varchar](255) NULL,
	[ext_sort] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_survey_option_ext_PK_cms_survey_option] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'extid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'optionsid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'ext_optionName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'ext_optionPicture'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'ext_dataType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'ext_sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查选项内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option_ext'
GO
/****** Object:  Table [dbo].[cms_survey_option]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey_option](
	[id] [varchar](32) NOT NULL,
	[surveyid] [varchar](32) NULL,
	[optionName] [varchar](255) NULL,
	[data_type] [varchar](255) NULL,
	[picture] [varchar](255) NULL,
	[interpretation] [varchar](255) NULL,
	[optionSort] [varchar](255) NULL,
	[optionContent] [varchar](4000) NULL,
	[createdtime] [datetime] NULL,
	[isCheck] [int] NULL,
 CONSTRAINT [cms_survey_option_PK_cms_survey_log_ext] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'surveyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'optionName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'data_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'picture'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'interpretation'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'optionSort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'optionContent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否必选（0：不是 1：是）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option', @level2type=N'COLUMN',@level2name=N'isCheck'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查选项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_option'
GO
/****** Object:  Table [dbo].[cms_survey_log_ext]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey_log_ext](
	[id] [varchar](32) NOT NULL,
	[logid] [varchar](32) NULL,
	[optionid] [varchar](32) NULL,
	[ext] [text] NULL,
	[ip] [varchar](15) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[county] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_survey_log_ext_PK_cms_survey_log_data] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查日志id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'logid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'optionid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文本内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'ext'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区县' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'county'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查日志选项文本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_ext'
GO
/****** Object:  Table [dbo].[cms_survey_log_data]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey_log_data](
	[id] [varchar](32) NOT NULL,
	[logid] [varchar](32) NULL,
	[optionid] [varchar](32) NULL,
	[optionextid] [varchar](32) NULL,
	[reply] [text] NULL,
	[ip] [varchar](15) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[county] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_survey_log_data_PK_cms_survey_log] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查日志id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'logid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'optionid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'optionextid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'reply'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区县' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'county'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间 ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查日志选项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log_data'
GO
/****** Object:  Table [dbo].[cms_survey_log]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey_log](
	[id] [varchar](32) NOT NULL,
	[surveyid] [varchar](32) NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[ip] [varchar](15) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[county] [varchar](255) NULL,
 CONSTRAINT [cms_survey_log_PK_cms_survey] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'surveyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区县' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log', @level2type=N'COLUMN',@level2name=N'county'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查日志表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey_log'
GO
/****** Object:  Table [dbo].[cms_survey]    Script Date: 10/21/2016 15:10:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_survey](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[custom] [varchar](255) NULL,
	[pageBackground] [varchar](1000) NULL,
	[recipient] [varchar](255) NULL,
	[surveyStartTime] [datetime] NULL,
	[surveyEndTime] [datetime] NULL,
	[surveyPeoLimit] [varchar](255) NULL,
	[surveyIpLimit] [varchar](255) NULL,
	[isVip] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_survey_PK_cms_student] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自定义模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'custom'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页面背景图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'pageBackground'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接收人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'recipient'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'surveyStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'surveyEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'人数限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'surveyPeoLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'surveyIpLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否会员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'isVip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_survey'
GO
/****** Object:  Table [dbo].[cms_student]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_student](
	[ID] [varchar](32) NOT NULL,
	[classname] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[sex] [varchar](255) NULL,
 CONSTRAINT [cms_student_PK_cms_stu] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_stu]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_stu](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](32) NULL,
	[age] [int] NULL,
 CONSTRAINT [cms_stu_PK_cms_special] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_special]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_special](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[catalogName] [varchar](255) NULL,
	[special_listModel] [varchar](255) NULL,
	[special_listNews] [varchar](255) NULL,
	[special_listPages] [varchar](255) NULL,
	[special_endTime] [datetime] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_special_PK_cms_source] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_special', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_special', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'目录名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_special', @level2type=N'COLUMN',@level2name=N'catalogName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_special', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_special'
GO
/****** Object:  Table [dbo].[cms_source]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_source](
	[id] [varchar](32) NOT NULL,
	[name] [char](40) NULL,
	[logo] [char](100) NULL,
	[url] [char](100) NULL,
	[initials] [char](10) NULL,
	[count] [numeric](8, 0) NULL,
	[sort] [bigint] NULL,
	[site_id] [varchar](32) NULL,
	[enname] [varchar](32) NULL,
	[cnname] [varchar](32) NULL,
	[simplename] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_source_PK_cms_sms_log] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'logo' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'logo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'初始值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'initials'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'装载量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'英文名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'enname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中文全拼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'cnname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中文简拼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'simplename'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_source', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_sms_log]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_sms_log](
	[id] [varchar](32) NOT NULL,
	[mobile] [varchar](20) NULL,
	[content] [varchar](255) NULL,
	[sendTime] [datetime] NULL,
	[op] [varchar](100) NULL,
	[status] [int] NULL,
	[isResend] [int] NULL,
	[remark] [varchar](500) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_sms_log_PK_cms_site] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'mobile'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'sendTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发送人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'op'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否重发' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'isResend'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信日志' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sms_log'
GO
/****** Object:  Table [dbo].[cms_site]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_site](
	[id] [varchar](32) NOT NULL,
	[config_id] [varchar](32) NULL,
	[ftp_upload_id] [varchar](32) NULL,
	[domain] [varchar](50) NOT NULL,
	[site_path] [varchar](20) NOT NULL,
	[site_name] [varchar](100) NOT NULL,
	[short_name] [varchar](100) NOT NULL,
	[protocol] [varchar](20) NOT NULL,
	[dynamic_suffix] [varchar](10) NULL,
	[static_suffix] [varchar](10) NOT NULL,
	[static_dir] [varchar](50) NULL,
	[is_index_to_root] [char](1) NULL,
	[is_static_index] [char](1) NULL,
	[locale_admin] [varchar](10) NULL,
	[locale_front] [varchar](10) NULL,
	[tpl_solution] [varchar](50) NULL,
	[final_step] [tinyint] NULL,
	[after_check] [tinyint] NULL,
	[is_relative_path] [char](1) NULL,
	[is_recycle_on] [char](1) NULL,
	[domain_alias] [varchar](255) NULL,
	[domain_redirect] [varchar](255) NULL,
	[is_master] [tinyint] NULL,
	[index_template] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
	[ucenterisOpen] [int] NULL,
	[is_switch] [varchar](10) NULL,
 CONSTRAINT [cms_site_PK_cms_sensitivity] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'config_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传ftp' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'ftp_upload_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'域名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'domain'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'site_path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'site_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简短名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'short_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'协议' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'protocol'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'动态页后缀' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'dynamic_suffix'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'静态页后缀' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'static_suffix'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'静态页存放目录' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'static_dir'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否使用将首页放在根目录下' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_index_to_root'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否静态化首页' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_static_index'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'后台本地化' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'locale_admin'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'前台本地化' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'locale_front'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板方案' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'tpl_solution'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'终审级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'final_step'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核后(1:不能修改删除;2:修改后退回;3:修改后不变)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'after_check'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否使用相对路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_relative_path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否开启回收站' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_recycle_on'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'域名别名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'domain_alias'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'域名重定向' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'domain_redirect'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否主站' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_master'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'index_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员连接ucenter是否开启 （0：不开启 1：开启）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'ucenterisOpen'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否切换(0代表静态1代表动态)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site', @level2type=N'COLUMN',@level2name=N'is_switch'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_site'
GO
/****** Object:  Table [dbo].[cms_sensitivity]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_sensitivity](
	[id] [varchar](32) NOT NULL,
	[search] [varchar](255) NULL,
	[replacement] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_sensitivity_PK_cms_section_data] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'敏感词' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sensitivity', @level2type=N'COLUMN',@level2name=N'search'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'替换词' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sensitivity', @level2type=N'COLUMN',@level2name=N'replacement'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sensitivity', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sensitivity', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS敏感词表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_sensitivity'
GO
/****** Object:  Table [dbo].[cms_section_data]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_section_data](
	[id] [varchar](32) NOT NULL,
	[sectionid] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[title] [varchar](100) NOT NULL,
	[color] [varchar](7) NULL,
	[url] [varchar](100) NULL,
	[thumb] [varchar](100) NULL,
	[description] [text] NULL,
	[sort] [int] NOT NULL,
	[created] [datetime] NOT NULL,
	[createdby] [varchar](32) NOT NULL,
	[commended] [datetime] NULL,
	[commendedby] [numeric](7, 0) NULL,
 CONSTRAINT [cms_section_data_PK_cms_section_class_priv] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块数据id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'sectionid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'color'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'commended'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_data', @level2type=N'COLUMN',@level2name=N'commendedby'
GO
/****** Object:  Table [dbo].[cms_section_class_priv]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_section_class_priv](
	[id] [varchar](32) NOT NULL,
	[sectionclassid] [varchar](32) NULL,
	[roleid] [varchar](32) NULL,
 CONSTRAINT [cms_section_class_priv_PK_cms_section_class] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块权限id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class_priv', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class_priv', @level2type=N'COLUMN',@level2name=N'sectionclassid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'角色id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class_priv', @level2type=N'COLUMN',@level2name=N'roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块权限表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class_priv'
GO
/****** Object:  Table [dbo].[cms_section_class]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_section_class](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[sort] [tinyint] NOT NULL,
	[memo] [text] NULL,
	[levels] [int] NULL,
	[parentid] [varchar](32) NULL,
	[siteid] [varchar](32) NOT NULL,
 CONSTRAINT [cms_section_class_PK_cms_section] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块分类id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section_class', @level2type=N'COLUMN',@level2name=N'memo'
GO
/****** Object:  Table [dbo].[cms_section]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_section](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[pathids] [varchar](255) NULL,
	[type] [varchar](7) NOT NULL,
	[templepath] [varchar](50) NULL,
	[url] [varchar](50) NULL,
	[data] [text] NULL,
	[frequency] [smallint] NULL,
	[updatetime] [datetime] NULL,
	[sort] [int] NOT NULL,
	[createdtime] [datetime] NOT NULL,
	[createdby] [numeric](7, 0) NOT NULL,
	[modifiedtime] [datetime] NULL,
	[modifiedby] [varchar](32) NULL,
	[published] [datetime] NULL,
	[publishedby] [numeric](7, 0) NULL,
	[memo] [text] NULL,
	[num] [smallint] NULL,
	[classid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cms_section_PK_cms_role_user] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模版路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'templepath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所发布的html' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区块内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'data'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新频率' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'frequency'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'modifiedtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'modifiedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'published'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'publishedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'memo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'每页显示数据条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'classid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_section', @level2type=N'COLUMN',@level2name=N'site_id'
GO
/****** Object:  Table [dbo].[cms_role_user]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_role_user](
	[ID] [varchar](32) NOT NULL,
	[roleid] [varchar](32) NULL,
	[userid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_role_user_PK_cms_role_site] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_user', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_role_site]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_role_site](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[role_id] [varchar](32) NULL,
	[created_time] [datetime] NULL,
 CONSTRAINT [cms_role_site_PK_cms_role_function] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点权限ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_site', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_site', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'角色ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_site', @level2type=N'COLUMN',@level2name=N'role_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_site', @level2type=N'COLUMN',@level2name=N'created_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点权限表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_site'
GO
/****** Object:  Table [dbo].[cms_role_function]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_role_function](
	[ID] [varchar](32) NOT NULL,
	[operation] [varchar](4000) NULL,
	[functionid] [varchar](32) NULL,
	[roleid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_role_function_PK_cms_role_depart] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role_function', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_role_depart]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_role_depart](
	[ID] [varchar](32) NOT NULL,
	[roleid] [varchar](32) NULL,
	[departid] [varchar](32) NULL,
 CONSTRAINT [cms_role_depart_PK_cms_role] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_role]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_role](
	[ID] [varchar](32) NOT NULL,
	[rolecode] [varchar](32) NULL,
	[rolename] [varchar](100) NOT NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_role_PK_cms_relate_content] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_role', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_relate_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_relate_content](
	[id] [varchar](32) NOT NULL,
	[content_id] [varchar](255) NOT NULL,
	[relate_title] [char](80) NOT NULL,
	[relate_url] [char](100) NULL,
	[images] [char](100) NOT NULL,
	[relate_type] [varchar](255) NOT NULL,
	[relate_order] [int] NULL,
	[newdate] [datetime] NULL,
	[created] [datetime] NULL,
	[part] [varchar](255) NULL,
 CONSTRAINT [cms_relate_content_PK_cms_picturealone] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'content_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'relate_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'relate_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'images'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'relate_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'relate_order'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'表单新增时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'newdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区别内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content', @level2type=N'COLUMN',@level2name=N'part'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'相关内容表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_relate_content'
GO
/****** Object:  Table [dbo].[cms_picturealone]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_picturealone](
	[id] [varchar](32) NOT NULL,
	[picturegroupid] [varchar](32) NULL,
	[picture_url] [varchar](255) NULL,
	[picture_message] [varchar](255) NULL,
	[picture_width] [varchar](255) NULL,
	[picture_height] [varchar](255) NULL,
	[picture_sort] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_picturealone_PK_cms_picture_group] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单图片ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'组图ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picturegroupid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picture_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片信息' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picture_message'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'宽度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picture_width'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'长度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picture_height'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone', @level2type=N'COLUMN',@level2name=N'picture_sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picturealone'
GO
/****** Object:  Table [dbo].[cms_picture_group]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_picture_group](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[image] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[url] [text] NULL,
	[url_thumb] [varchar](255) NULL,
	[sort] [smallint] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_picture_group_PK_cms_pf_config] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'url_thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片组表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_picture_group'
GO
/****** Object:  Table [dbo].[cms_pf_config]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_pf_config](
	[id] [varchar](32) NOT NULL,
	[configKey] [varchar](100) NULL,
	[configValue] [varchar](100) NULL,
	[remark] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_pf_config_PK_cms_opintemplate] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config', @level2type=N'COLUMN',@level2name=N'configKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config', @level2type=N'COLUMN',@level2name=N'configValue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_pf_config'
GO
/****** Object:  Table [dbo].[cms_opintemplate]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_opintemplate](
	[ID] [varchar](32) NOT NULL,
	[descript] [varchar](100) NULL,
 CONSTRAINT [cms_opintemplate_PK_cms_operation] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_operation]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_operation](
	[ID] [varchar](32) NOT NULL,
	[operationcode] [varchar](50) NULL,
	[operationicon] [varchar](100) NULL,
	[operationname] [varchar](50) NULL,
	[status] [smallint] NULL,
	[functionid] [varchar](32) NULL,
	[iconid] [varchar](32) NULL,
	[operationurl] [varchar](300) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_operation_PK_cms_online] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'址地' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_operation', @level2type=N'COLUMN',@level2name=N'operationurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_operation', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`iconid`) REFER `lm_maven_cms/cms_icon`(`ID`); (`function' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_operation'
GO
/****** Object:  Table [dbo].[cms_online]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_online](
	[ID] [varchar](32) NOT NULL,
	[IP] [varchar](50) NOT NULL,
	[LOGINDATETIME] [datetime] NOT NULL,
	[LOGINNAME] [varchar](100) NOT NULL,
 CONSTRAINT [cms_online_PK_cms_note] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_note]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_note](
	[id] [varchar](32) NOT NULL,
	[userid] [varchar](32) NULL,
	[pid] [varchar](255) NULL,
	[token] [varchar](250) NULL,
	[note_phone] [varchar](250) NULL,
	[onte_account] [varchar](250) NULL,
	[note_password] [varchar](250) NULL,
	[note_stauts] [varchar](250) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_note_PK_cms_mood] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'uid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'userid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'pid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'pid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信平台手机号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'note_phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信平台账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'onte_account'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信平台密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'note_password'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信平台状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'note_stauts'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短信设置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_note'
GO
/****** Object:  Table [dbo].[cms_mood]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_mood](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](20) NULL,
	[image] [varchar](100) NULL,
	[sort] [smallint] NULL,
	[site_id] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_mood_PK_cms_modelmanage] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'情表名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mood', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mood', @level2type=N'COLUMN',@level2name=N'image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mood', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mood', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mood', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_modelmanage]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_modelmanage](
	[id] [varchar](32) NOT NULL,
	[model_name] [varchar](255) NULL,
	[priority] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_modelmanage_PK_cms_model_item] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_modelmanage', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_modelmanage', @level2type=N'COLUMN',@level2name=N'model_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_modelmanage', @level2type=N'COLUMN',@level2name=N'priority'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型管理' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_modelmanage'
GO
/****** Object:  Table [dbo].[cms_model_item]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_model_item](
	[id] [varchar](32) NOT NULL,
	[model_id] [varchar](32) NULL,
	[field] [varchar](255) NULL,
	[data_type] [int] NULL,
	[priority] [int] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
	[item_label] [varchar](255) NULL,
 CONSTRAINT [cms_model_item_PK_cms_model_ext] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'model_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字段名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'field'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'data_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'priority'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item', @level2type=N'COLUMN',@level2name=N'item_label'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型项目' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_item'
GO
/****** Object:  Table [dbo].[cms_model_ext]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_model_ext](
	[id] [varchar](32) NOT NULL,
	[model_id] [varchar](32) NULL,
	[channel_id] [varchar](32) NULL,
	[content_id] [varchar](32) NULL,
	[modelitem_id] [varchar](32) NULL,
	[attr_name] [varchar](250) NULL,
	[attr_value] [varchar](250) NULL,
	[attr_token] [varchar](250) NULL,
	[data_type] [varchar](250) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_model_ext_PK_cms_model] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'model_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'channel_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'content_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项项目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'modelitem_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext', @level2type=N'COLUMN',@level2name=N'attr_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model_ext'
GO
/****** Object:  Table [dbo].[cms_model]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_model](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](20) NULL,
	[alias] [varchar](15) NULL,
	[description] [varchar](255) NULL,
	[template_list] [varchar](100) NULL,
	[template_show] [varchar](100) NULL,
	[sort] [smallint] NULL,
	[disabled] [smallint] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_model_PK_cms_message_management] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'lid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'别名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'alias'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板列表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'template_list'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'template_show'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'不可用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'disabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_model'
GO
/****** Object:  Table [dbo].[cms_message_management]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_message_management](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](50) NULL,
	[content] [text] NULL,
	[reply] [text] NULL,
	[add_time] [datetime] NULL,
	[ischeck] [varchar](10) NULL,
	[memberid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
	[reply_time] [datetime] NULL,
	[createdtime] [datetime] NULL,
	[reply_user] [varchar](50) NULL,
	[reply_status] [varchar](10) NULL,
	[board_Id] [varchar](32) NULL,
	[sort] [varchar](10) NULL,
 CONSTRAINT [cms_message_management_PK_cms_message_board] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'reply'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'add_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'ischeck'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'memberid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'reply_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'reply_user'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复状态(0未回复1已回复)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'reply_status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言板Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'board_Id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言管理表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_management'
GO
/****** Object:  Table [dbo].[cms_message_board]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_message_board](
	[id] [varchar](32) NULL,
	[name_board] [varchar](50) NULL,
	[code] [varchar](50) NULL,
	[description] [varchar](300) NULL,
	[is_open] [varchar](10) NULL,
	[message_login] [varchar](10) NULL,
	[numbers] [varchar](50) NULL,
	[time] [varchar](50) NULL,
	[time_select] [varchar](50) NULL,
	[is_replace] [varchar](10) NULL,
	[replace_word] [varchar](50) NULL,
	[createtime] [datetime] NULL,
	[site_id] [varchar](32) NULL,
	[sort] [varchar](10) NULL,
	[time_limit] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言分类Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言板名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'name_board'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'别名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言板描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否开放留言(0:否1:是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'is_open'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言是否登录(0:否1:是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'message_login'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'numbers'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间选择' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'time_select'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否替换(敏感词0:否1:是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'is_replace'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'替换词' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'replace_word'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message_board', @level2type=N'COLUMN',@level2name=N'sort'
GO
/****** Object:  Table [dbo].[cms_message]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_message](
	[id] [varchar](32) NOT NULL,
	[title] [varchar](100) NULL,
	[name] [varchar](50) NULL,
	[phone] [varchar](20) NULL,
	[address] [varchar](255) NULL,
	[email] [varchar](50) NULL,
	[content] [text] NULL,
	[reply] [text] NULL,
	[add_time] [datetime] NULL,
	[ischeck] [varchar](10) NULL,
	[memberid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
	[reply_time] [datetime] NULL,
	[createdtime] [datetime] NULL,
	[reply_user] [varchar](50) NULL,
	[reply_status] [varchar](10) NULL,
	[departid] [varchar](10) NULL,
 CONSTRAINT [cms_message_PK_cms_memo] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'reply'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'add_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'ischeck'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'memberid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'reply_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'reply_user'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复状态(0未回复1已回复)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'reply_status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message', @level2type=N'COLUMN',@level2name=N'departid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_message'
GO
/****** Object:  Table [dbo].[cms_memo]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_memo](
	[id] [varchar](32) NOT NULL,
	[userid] [varchar](32) NULL,
	[content] [varchar](1024) NULL,
	[createTime] [datetime] NULL,
 CONSTRAINT [cms_memo_PK_cms_membergroups] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_memo', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_memo', @level2type=N'COLUMN',@level2name=N'userid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'便签内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_memo', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'便签创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_memo', @level2type=N'COLUMN',@level2name=N'createTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'便签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_memo'
GO
/****** Object:  Table [dbo].[cms_membergroups]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_membergroups](
	[id] [varchar](32) NOT NULL,
	[userGroupsName] [varchar](255) NULL,
	[numbers] [int] NULL,
	[state] [int] NULL,
	[remark] [varchar](255) NULL,
	[defalut_lv] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_membergroups_PK_cms_member_depart] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户主名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'userGroupsName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'个数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'numbers'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'state'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认等级 0不是默认 1为默认' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'defalut_lv'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员组' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_membergroups'
GO
/****** Object:  Table [dbo].[cms_member_depart]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_member_depart](
	[ID] [varchar](32) NOT NULL,
	[memberId] [varchar](32) NULL,
	[departId] [varchar](32) NULL,
 CONSTRAINT [cms_member_depart_PK_cms_member] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member_depart', @level2type=N'COLUMN',@level2name=N'memberId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'部门id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member_depart', @level2type=N'COLUMN',@level2name=N'departId'
GO
/****** Object:  Table [dbo].[cms_member]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_member](
	[id] [varchar](32) NOT NULL,
	[userName] [varchar](255) NULL,
	[memberLevel] [varchar](255) NULL,
	[realName] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[repassword] [varchar](255) NULL,
	[faceImg] [varchar](255) NULL,
	[email] [varchar](255) NULL,
	[birthday] [date] NULL,
	[sex] [int] NULL,
	[address] [varchar](255) NULL,
	[qq] [varchar](255) NULL,
	[cellphone] [varchar](255) NULL,
	[telephone] [varchar](255) NULL,
	[thirdLogin_uid] [varchar](255) NULL,
	[thirdType] [varchar](255) NULL,
	[thirdToken] [varchar](255) NULL,
	[memberGroupsId] [varchar](32) NULL,
	[cityid] [varchar](32) NULL,
	[city] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[point] [int] NULL,
	[msn] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[lastlogin] [datetime] NULL,
	[logincount] [int] NULL,
	[mp] [int] NULL,
	[is_cheked] [int] NULL,
	[registerip] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[login_type] [varchar](11) NULL,
	[card_id] [varchar](50) NULL,
	[card_path] [varchar](255) NULL,
 CONSTRAINT [cms_member_PK_cms_mail] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'userName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'memberLevel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'真实姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'realName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'password'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'确认密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'repassword'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'头像' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'faceImg'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'出生日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'birthday'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'sex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'qq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'qq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'cellphone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 第三登录返回的UID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'thirdLogin_uid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户类型：新浪-sina,腾训-qq,本站会员为空 ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'thirdType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'获取用户token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'thirdToken'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员级别id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'memberGroupsId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地域id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'cityid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'城市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'city'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'昵称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'积分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'point'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'msn' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'msn'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'个人说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后登录时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'lastlogin'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'登录次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'logincount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'消费积分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'mp'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否通过验证' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'is_cheked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注册ip' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'registerip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注册时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'登录类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'login_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员卡号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'card_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员卡访问路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member', @level2type=N'COLUMN',@level2name=N'card_path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员中心' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_member'
GO
/****** Object:  Table [dbo].[cms_mail]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_mail](
	[id] [varchar](32) NOT NULL,
	[userid] [int] NULL,
	[pid] [varchar](250) NULL,
	[token] [varchar](250) NULL,
	[mail_phone] [varchar](250) NULL,
	[smtp_account] [varchar](250) NULL,
	[smtp_password] [varchar](250) NULL,
	[mail_stauts] [varchar](250) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_mail_PK_cms_lottery_record] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'uid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'userid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'pid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'pid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮件地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'mail_phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'SMTP账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'smtp_account'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'SMTP密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'smtp_password'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮件平台状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'mail_stauts'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮件设置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_mail'
GO
/****** Object:  Table [dbo].[cms_lottery_record]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_lottery_record](
	[id] [varchar](32) NOT NULL,
	[lid] [varchar](32) NULL,
	[usenums] [tinyint] NULL,
	[wecha_id] [varchar](32) NULL,
	[token] [varchar](30) NULL,
	[islottery] [int] NULL,
	[wecha_name] [varchar](60) NULL,
	[phone] [varchar](15) NULL,
	[sn] [varchar](13) NULL,
	[time] [datetime] NULL,
	[prize] [varchar](50) NULL,
	[sendstutas] [int] NULL,
	[sendtime] [datetime] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_lottery_record_PK_cms_lottery] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'动活id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'lid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户使用次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'usenums'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信唯一识别码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'wecha_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否中奖' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'islottery'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'wecha_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'机手号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中奖后序列号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'sn'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中奖日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已中奖项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'prize'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发奖日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'sendtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery_record', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_lottery]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_lottery](
	[id] [varchar](32) NOT NULL,
	[joinnum] [int] NULL,
	[click] [int] NULL,
	[token] [varchar](30) NULL,
	[keyword] [varchar](10) NULL,
	[starpicurl] [varchar](255) NULL,
	[title] [varchar](60) NULL,
	[txt] [varchar](60) NULL,
	[sttxt] [varchar](200) NULL,
	[statdate] [datetime] NULL,
	[enddate] [datetime] NULL,
	[info] [varchar](200) NULL,
	[aginfo] [varchar](200) NULL,
	[endtite] [varchar](60) NULL,
	[endpicurl] [varchar](255) NULL,
	[endinfo] [varchar](60) NULL,
	[fist] [varchar](50) NULL,
	[fistnums] [int] NULL,
	[fistlucknums] [int] NULL,
	[second] [varchar](50) NULL,
	[type] [tinyint] NULL,
	[secondnums] [int] NULL,
	[secondlucknums] [int] NULL,
	[third] [varchar](50) NULL,
	[thirdnums] [int] NULL,
	[thirdlucknums] [int] NULL,
	[allpeople] [int] NULL,
	[canrqnums] [int] NULL,
	[parssword] [int] NULL,
	[renamesn] [varchar](50) NULL,
	[renametel] [varchar](60) NULL,
	[displayjpnums] [int] NULL,
	[createdate] [datetime] NULL,
	[status] [int] NULL,
	[four] [varchar](50) NULL,
	[fournums] [int] NULL,
	[fourlucknums] [int] NULL,
	[five] [varchar](50) NULL,
	[fivenums] [int] NULL,
	[fivelucknums] [int] NULL,
	[six] [varchar](50) NULL,
	[sixnums] [int] NULL,
	[sixlucknums] [int] NULL,
	[zjpic] [varchar](150) NULL,
	[url] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cms_lottery_PK_cms_log] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'参与人数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'joinnum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'键关词' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'keyword'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'填写活动开始图片网址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'starpicurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户输入兑奖时候的显示信息' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'txt'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简介' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'sttxt'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'statdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'enddate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'info'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'重复抽奖回复' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'aginfo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动结束公告主题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'endtite'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'一等奖奖品设置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'fist'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'一等奖奖品数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'fistnums'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'一等奖中奖号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'fistlucknums'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'二等奖奖品设置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'second'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'个人限制抽奖次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'canrqnums'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动创建日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'createdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'六等奖' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'six'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动展示页面地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_lottery', @level2type=N'COLUMN',@level2name=N'site_id'
GO
/****** Object:  Table [dbo].[cms_log]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_log](
	[ID] [varchar](32) NOT NULL,
	[broswer] [varchar](100) NULL,
	[logcontent] [text] NOT NULL,
	[loglevel] [smallint] NULL,
	[note] [text] NULL,
	[operatetime] [datetime] NOT NULL,
	[operatetype] [smallint] NULL,
	[userid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_log_PK_cms_link] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_log', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`userid`) REFER `lm_maven_cms/cms_user`(`id`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_log'
GO
/****** Object:  Table [dbo].[cms_link]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_link](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[linkname] [varchar](255) NULL,
	[linkurl] [varchar](4000) NULL,
	[linkremark] [varchar](1000) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_link_PK_cms_interview_guest] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'linkname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'linkurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'linkremark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_link'
GO
/****** Object:  Table [dbo].[cms_interview_guest]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_interview_guest](
	[guestid] [varchar](32) NOT NULL,
	[interviewid] [varchar](255) NULL,
	[guest_name] [varchar](255) NULL,
	[guest_url] [varchar](255) NULL,
	[guest_introduce] [varchar](255) NULL,
	[guest_picture] [varchar](255) NULL,
	[guest_token] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_interview_guest_PK_cms_interview] PRIMARY KEY CLUSTERED
(
	[guestid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'嘉宾id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guestid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'interviewid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'嘉宾姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guest_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'嘉宾介绍网址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guest_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'嘉宾介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guest_introduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'嘉宾图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guest_picture'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest', @level2type=N'COLUMN',@level2name=N'guest_token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈嘉宾表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview_guest'
GO
/****** Object:  Table [dbo].[cms_interview]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_interview](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[interview_issue] [varchar](255) NULL,
	[interview_state] [varchar](255) NULL,
	[interview_introduce] [varchar](255) NULL,
	[interview_way] [varchar](255) NULL,
	[interview_picture] [varchar](255) NULL,
	[interview_startTime] [datetime] NULL,
	[interview_endTime] [datetime] NULL,
	[interview_place] [varchar](255) NULL,
	[interview_compere] [varchar](255) NULL,
	[custom_model] [varchar](255) NULL,
	[netfriend_speak] [varchar](255) NULL,
	[visitor_speak] [varchar](255) NULL,
	[speak_check] [varchar](255) NULL,
	[speak_startTime] [datetime] NULL,
	[speak_endTime] [datetime] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_interview_PK_cms_icon] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'期号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_issue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_state'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_introduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_way'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_picture'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_startTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_endTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈地点' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_place'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主持人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'interview_compere'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自定义模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'custom_model'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网友发言' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'netfriend_speak'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'游客发言' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'visitor_speak'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发言审核' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'speak_check'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发言开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'speak_startTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发言结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview', @level2type=N'COLUMN',@level2name=N'speak_endTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访谈表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_interview'
GO
/****** Object:  Table [dbo].[cms_icon]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_icon](
	[ID] [varchar](32) NOT NULL,
	[extend] [varchar](255) NULL,
	[iconclas] [varchar](200) NULL,
	[content] [image] NULL,
	[name] [varchar](100) NOT NULL,
	[path] [text] NULL,
	[type] [smallint] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_icon_PK_cms_guestbook] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_icon', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_guestbook]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_guestbook](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](255) NULL,
	[content] [varchar](255) NULL,
	[messagePerson] [varchar](255) NULL,
	[personId] [int] NULL,
	[messageTime] [datetime] NULL,
	[qq] [varchar](255) NULL,
	[cellphone] [varchar](255) NULL,
	[telephone] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_guestbook_PK_cms_function] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'messagePerson'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言人id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'personId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'messageTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'qq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'qq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'cellphone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_guestbook'
GO
/****** Object:  Table [dbo].[cms_function]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_function](
	[ID] [varchar](32) NOT NULL,
	[functioniframe] [smallint] NULL,
	[functionlevel] [smallint] NULL,
	[functionname] [varchar](50) NOT NULL,
	[functionorder] [varchar](255) NULL,
	[functionurl] [varchar](100) NULL,
	[parentfunctionid] [varchar](32) NULL,
	[iconclass] [varchar](32) NULL,
	[privurl] [varchar](1000) NULL,
	[createdtime] [datetime] NULL,
	[pathids] [varchar](255) NULL,
	[parentids] [varchar](255) NULL,
 CONSTRAINT [cms_function_PK_cms_friendlink_ctg] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_function', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父节点id,包含当前id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_function', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父节点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_function', @level2type=N'COLUMN',@level2name=N'parentids'
GO
/****** Object:  Table [dbo].[cms_friendlink_ctg]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_friendlink_ctg](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[friendlinkctg_name] [varchar](50) NULL,
	[priority] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_friendlink_ctg_PK_cms_friendlink] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink_ctg', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink_ctg', @level2type=N'COLUMN',@level2name=N'friendlinkctg_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink_ctg', @level2type=N'COLUMN',@level2name=N'priority'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink_ctg', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS友情链接类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink_ctg'
GO
/****** Object:  Table [dbo].[cms_friendlink]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_friendlink](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[gid] [varchar](32) NULL,
	[site_name] [varchar](150) NULL,
	[domain] [varchar](255) NULL,
	[logo] [varchar](150) NULL,
	[site_email] [varchar](100) NULL,
	[description] [varchar](255) NULL,
	[views] [int] NULL,
	[is_enabled] [char](1) NULL,
	[priority] [int] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_friendlink_PK_cms_finance_files] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类别id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'gid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'site_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'domain'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图标' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'logo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'site_email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点击次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'views'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'is_enabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'priority'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS友情链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_friendlink'
GO
/****** Object:  Table [dbo].[cms_finance_files]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_finance_files](
	[id] [varchar](32) NOT NULL,
	[financeId] [varchar](32) NULL,
 CONSTRAINT [cms_finance_files_PK_cms_finance] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`id`) REFER `lm_maven_cms/cms_attachment`(`ID`); (`financ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_finance_files'
GO
/****** Object:  Table [dbo].[cms_finance]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_finance](
	[ID] [varchar](32) NOT NULL,
	[APPROFILETYPE] [varchar](255) NULL,
	[BUYMONEY] [real] NULL,
	[BUYPROJECTNO] [varchar](255) NULL,
	[BUYPROJECTORG] [varchar](255) NULL,
	[BUYUSE] [varchar](255) NULL,
	[BUYYEAR] [varchar](255) NULL,
	[CATEGORY] [varchar](255) NULL,
	[COLLECTORG] [varchar](255) NULL,
	[EXPENSEACCOUNT] [varchar](255) NULL,
	[HAPPENYEAR] [int] NULL,
	[MONEYAREA] [varchar](255) NULL,
	[MONEYSOURCE] [varchar](255) NULL,
	[MONEYTOTAL] [real] NULL,
	[MONEYUSE] [varchar](255) NULL,
	[PAYTIME] [datetime] NULL,
	[ZBWNO] [varchar](255) NULL,
 CONSTRAINT [cms_finance_PK_cms_fileno] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_fileno]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_fileno](
	[ID] [varchar](32) NOT NULL,
	[filenobefore] [varchar](32) NULL,
	[filenonum] [int] NULL,
	[filenotype] [varchar](32) NULL,
	[filenoYear] [date] NULL,
 CONSTRAINT [cms_fileno_PK_cms_email_log] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_email_log]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_email_log](
	[id] [varchar](32) NOT NULL,
	[title] [varchar](255) NULL,
	[fromMail] [varchar](255) NULL,
	[toMail] [varchar](255) NULL,
	[content] [varchar](1000) NULL,
	[sendTime] [datetime] NULL,
	[op] [varchar](100) NULL,
	[status] [int] NULL,
	[isResend] [int] NULL,
	[remark] [varchar](500) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_email_log_PK_cms_email_address] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发邮件箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'fromMail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'toMail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'sendTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'操作人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'op'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否重发' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'isResend'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮件日志' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_log'
GO
/****** Object:  Table [dbo].[cms_email_address]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_email_address](
	[id] [varchar](32) NULL,
	[name] [varchar](32) NULL,
	[email] [varchar](50) NULL,
	[telephone] [varchar](18) NULL,
	[work_unit] [varchar](100) NULL,
	[content] [text] NULL,
	[siteid] [varchar](32) NULL,
	[createtime] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'work_unit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_email_address', @level2type=N'COLUMN',@level2name=N'createtime'
GO
/****** Object:  Table [dbo].[cms_document]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_document](
	[documentstate] [smallint] NULL,
	[documenttitle] [varchar](100) NULL,
	[pictureindex] [image] NULL,
	[showhome] [smallint] NULL,
	[id] [varchar](32) NOT NULL,
	[typeid] [varchar](32) NULL,
 CONSTRAINT [cms_document_PK_cms_doc_type] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`id`) REFER `lm_maven_cms/cms_attachment`(`ID`); (`typeid' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_document'
GO
/****** Object:  Table [dbo].[cms_doc_type]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_type](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[sort] [int] NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
 CONSTRAINT [cms_doc_type_PK_cms_doc_returnvalue] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_type'
GO
/****** Object:  Table [dbo].[cms_doc_returnvalue]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_returnvalue](
	[id] [varchar](32) NOT NULL,
	[docid] [varchar](32) NULL,
	[name] [varchar](255) NULL,
	[type] [varchar](255) NULL,
	[isrequired] [int] NULL,
	[default_value] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
	[code] [varchar](255) NULL,
 CONSTRAINT [cms_doc_returnvalue_PK_cms_doc_param] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'docid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否必须' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'isrequired'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'default_value'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue', @level2type=N'COLUMN',@level2name=N'code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_returnvalue'
GO
/****** Object:  Table [dbo].[cms_doc_param]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_param](
	[id] [varchar](32) NOT NULL,
	[docid] [varchar](32) NULL,
	[name] [varchar](255) NULL,
	[type] [int] NULL,
	[isrequired] [varchar](255) NULL,
	[example_value] [varchar](255) NULL,
	[defalut_value] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
 CONSTRAINT [cms_doc_param_PK_cms_doc_entity_ref] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'docid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否必须' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'isrequired'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'示例值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'example_value'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'defalut_value'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'输入参数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_param'
GO
/****** Object:  Table [dbo].[cms_doc_entity_ref]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_entity_ref](
	[id] [varchar](32) NOT NULL,
	[docid] [varchar](32) NULL,
	[entityid] [varchar](32) NULL,
	[sort] [int] NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
 CONSTRAINT [cms_doc_entity_ref_PK_cms_doc_entity_property] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'docid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'entityid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体与文档关联表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_ref'
GO
/****** Object:  Table [dbo].[cms_doc_entity_property]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_entity_property](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[type] [varchar](255) NULL,
	[entityid] [varchar](32) NULL,
	[sort] [int] NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
 CONSTRAINT [cms_doc_entity_property_PK_cms_doc_entity] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'属性名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'entityid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体属性' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity_property'
GO
/****** Object:  Table [dbo].[cms_doc_entity]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc_entity](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[code] [varchar](255) NULL,
	[sort] [int] NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
 CONSTRAINT [cms_doc_entity_PK_cms_doc] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实体' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc_entity'
GO
/****** Object:  Table [dbo].[cms_doc]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_doc](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[tag] [varchar](255) NULL,
	[tag_demo] [varchar](1000) NULL,
	[typeid] [varchar](32) NULL,
	[api_address] [varchar](255) NULL,
	[return_example_value] [text] NULL,
	[pid] [varchar](32) NULL,
	[sort] [int] NULL,
	[description] [varchar](255) NULL,
	[status] [int] NULL,
	[createdtime] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[updatecount] [int] NULL,
	[updatetime] [datetime] NULL,
	[updateby] [varchar](255) NULL,
	[platform] [varchar](255) NULL,
 CONSTRAINT [cms_doc_PK_cms_diymen_class] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'tag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标签试一试demo' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'tag_demo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'typeid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'api地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'api_address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'返回示例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'return_example_value'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父文档id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'pid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'添加人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'updatecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'updatetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'updateby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档所属平台' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc', @level2type=N'COLUMN',@level2name=N'platform'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_doc'
GO
/****** Object:  Table [dbo].[cms_diymen_class]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_diymen_class](
	[id] [varchar](32) NOT NULL,
	[token] [varchar](60) NULL,
	[pid] [varchar](32) NOT NULL,
	[title] [varchar](30) NOT NULL,
	[keyword] [varchar](30) NULL,
	[is_show] [tinyint] NOT NULL,
	[sort] [tinyint] NOT NULL,
	[url] [varchar](255) NULL,
 CONSTRAINT [cms_diymen_class_PK_cms_depart_channel] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'pid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主菜单名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联关键字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'keyword'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示（0,1）1:显示 0:不显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'is_show'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'外链URL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'DIY菜单' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_diymen_class'
GO
/****** Object:  Table [dbo].[cms_depart_channel]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_depart_channel](
	[ID] [varchar](32) NOT NULL,
	[departId] [varchar](32) NULL,
	[channelId] [varchar](32) NULL,
 CONSTRAINT [cms_depart_channel_PK_cms_depart] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cms_depart]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_depart](
	[ID] [varchar](32) NOT NULL,
	[departname] [varchar](100) NOT NULL,
	[description] [text] NULL,
	[parentdepartid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_depart_PK_cms_demo] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_depart', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`parentdepartid`) REFER `lm_maven_cms/cms_depart`(`ID`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_depart'
GO
/****** Object:  Table [dbo].[cms_demo]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_demo](
	[ID] [varchar](32) NOT NULL,
	[democode] [text] NULL,
	[demoorder] [smallint] NULL,
	[demotitle] [varchar](200) NULL,
	[demourl] [varchar](200) NULL,
	[demopid] [varchar](32) NULL,
 CONSTRAINT [cms_demo_PK_cms_custom] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`demopid`) REFER `lm_maven_cms/cms_demo`(`ID`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_demo'
GO
/****** Object:  Table [dbo].[cms_custom]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_custom](
	[ID] [varchar](32) NOT NULL,
	[sustomlevel] [bigint] NOT NULL,
	[customname] [varchar](50) NOT NULL,
	[sustomsort] [varchar](3) NULL,
	[description] [varchar](256) NULL,
	[sustomparentid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_custom_PK_cms_copy_content_ref] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_custom', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_copy_content_ref]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_copy_content_ref](
	[id] [varchar](32) NOT NULL,
	[LOCK_FLAG] [int] NULL,
	[MAIN_CONTENT_ID] [varchar](32) NULL,
	[SUB_CONTENT_ID] [varchar](32) NULL,
	[CREATE_TIME] [datetime] NULL,
	[CREATE_BY] [varchar](32) NULL,
	[UPDATE_TIME] [datetime] NULL,
	[UPDATE_BY] [varchar](32) NULL,
	[REMARKS] [varchar](255) NULL,
	[DEL_FLAG] [int] NULL,
 CONSTRAINT [cms_copy_content_ref_PK_cms_content_tag] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 锁定标记（0：正常；1：锁定）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'LOCK_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主稿件' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'MAIN_CONTENT_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'子稿件' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'SUB_CONTENT_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'CREATE_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'CREATE_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'UPDATE_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'UPDATE_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'REMARKS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标记（0：正常；1：删除）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_copy_content_ref', @level2type=N'COLUMN',@level2name=N'DEL_FLAG'
GO
/****** Object:  Table [dbo].[cms_content_tag]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_tag](
	[id] [varchar](32) NOT NULL,
	[tag_name] [varchar](50) NULL,
	[ref_counter] [int] NULL,
	[site_id] [varchar](32) NULL,
	[enname] [varchar](32) NULL,
	[cnname] [varchar](32) NULL,
	[simplename] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_content_tag_PK_cms_content_extractor_rule] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'tag名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'tag_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'被引用的次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'ref_counter'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'英文名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'enname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中文全拼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'cnname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中文简拼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag', @level2type=N'COLUMN',@level2name=N'simplename'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS内容TAG表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_tag'
GO
/****** Object:  Table [dbo].[cms_content_extractor_rule]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_extractor_rule](
	[id] [varchar](255) NOT NULL,
	[websiteName] [varchar](255) NULL,
	[uriKeywordsFragment] [varchar](255) NULL,
	[nesBodyTag] [varchar](255) NULL,
	[newsBodyClass] [varchar](255) NULL,
	[newsBodyId] [varchar](255) NULL,
	[beginTag] [varchar](255) NULL,
	[endTag] [varchar](255) NULL,
	[scheme] [varchar](255) NULL,
	[attrName] [varchar](255) NULL,
	[attrValue] [varchar](255) NULL,
	[version] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'规则Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网址名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'websiteName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网页网址关键字片段' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'uriKeywordsFragment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网页主题内容标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'nesBodyTag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网页主题内容标签Class属性' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'newsBodyClass'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网页主题内容标签Id属性' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'newsBodyId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'beginTag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'endTag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'抓取方案' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'scheme'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自定义属性名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'attrName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自定义属性值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'attrValue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'cms内容抓取规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_extractor_rule'
GO
/****** Object:  Table [dbo].[cms_content_cat_priv]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_cat_priv](
	[id] [varchar](32) NOT NULL,
	[catid] [varchar](32) NULL,
	[roleid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_content_cat_priv_PK_cms_content_cat_default] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目权限ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat_priv', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'PC栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat_priv', @level2type=N'COLUMN',@level2name=N'catid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'角色ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat_priv', @level2type=N'COLUMN',@level2name=N'roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'PC栏目权限表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat_priv'
GO
/****** Object:  Table [dbo].[cms_content_cat_default]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_cat_default](
	[id] [varchar](32) NOT NULL,
	[channelId] [varchar](32) NULL,
 CONSTRAINT [cms_content_cat_default_PK_cms_content_cat] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat_default', @level2type=N'COLUMN',@level2name=N'channelId'
GO
/****** Object:  Table [dbo].[cms_content_cat]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_cat](
	[id] [varchar](32) NOT NULL,
	[parentid] [varchar](32) NULL,
	[parentids] [varchar](255) NULL,
	[pathids] [varchar](255) NULL,
	[name] [varchar](20) NULL,
	[alias] [varchar](20) NULL,
	[workflowid] [varchar](32) NULL,
	[model] [varchar](255) NULL,
	[template_index] [varchar](2000) NULL,
	[template_list] [varchar](2000) NULL,
	[template_date] [varchar](255) NULL,
	[path] [varchar](100) NULL,
	[thumb] [char](60) NULL,
	[wap_url] [varchar](100) NULL,
	[url] [varchar](255) NULL,
	[iscreateindex] [smallint] NULL,
	[urlrule_index] [varchar](255) NULL,
	[urlrule_list] [varchar](255) NULL,
	[urlrule_show] [varchar](255) NULL,
	[enablecontribute] [smallint] NULL,
	[keywords] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[posts] [numeric](8, 0) NULL,
	[comments] [numeric](8, 0) NULL,
	[pv] [bigint] NULL,
	[sort] [smallint] NULL,
	[disabled] [smallint] NULL,
	[htmlcreated] [smallint] NULL,
	[extendJSON] [varchar](4000) NULL,
	[jsonId] [varchar](32) NULL,
	[isArticleSelected] [varchar](200) NULL,
	[pagesize] [int] NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](50) NULL,
	[modified] [datetime] NULL,
	[modifiedby] [varchar](50) NULL,
	[levels] [int] NULL,
	[site_id] [varchar](32) NULL,
	[list_model] [text] NULL,
	[urlrule_content] [text] NULL,
	[iscontribute] [int] NULL,
	[iscomment] [int] NULL,
	[contentcat_title] [text] NULL,
	[watermark] [text] NULL,
	[isshow] [int] NULL,
	[viplevel] [text] NULL,
	[contentcat_spell] [text] NULL,
	[contentcat_abbreviation] [text] NULL,
	[is_leaf] [int] NULL,
	[index_template] [varchar](50) NULL,
	[iscatend] [int] NULL,
	[list_message] [int] NULL,
	[rss_template] [varchar](255) NULL,
	[isSendMobile] [int] NULL,
	[mobileChannel] [varchar](255) NULL,
	[static_url] [varchar](255) NULL,
	[dynamic_url] [varchar](255) NULL,
	[link_url] [varchar](255) NULL,
	[is_link_url] [int] NULL,
 CONSTRAINT [cms_content_cat_PK_cms_content_article] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'parentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'parentids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'当前栏目的路径id  包含所有父级栏目id以及当前栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'别名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'alias'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'workflowid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'model'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板索引' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'template_index'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板列表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'template_list'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'template_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目生成路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'wap访问的URL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'wap_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否创建索引' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'iscreateindex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL索引规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'urlrule_index'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL列表规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'urlrule_list'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL显示规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'urlrule_show'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'enablecontribute' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'enablecontribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关键字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'keywords'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'posts' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'posts'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'comments'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'pv'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'不可用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'disabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否已创建HTML' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'htmlcreated'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扩展字段JSON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'extendJSON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'JSONID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'jsonId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章选中' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'isArticleSelected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'pagesize'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'modified'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'modifiedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'等级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'levels'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表页模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'list_model'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容页URL规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'urlrule_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否允许用户投稿' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'iscontribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否允许评论' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'iscomment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'contentcat_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'水印方案' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'watermark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'isshow'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员查看等级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'viplevel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目拼音' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'contentcat_spell'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目缩写' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'contentcat_abbreviation'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是单页栏目' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'is_leaf'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目首页模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'index_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'根栏目' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'iscatend'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表页每页信息数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'list_message'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'rss 模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'rss_template'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否默认推送' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'isSendMobile'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动频道' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'mobileChannel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'静态地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'static_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'动态地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'dynamic_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'link_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是链接地址(0:否1:是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat', @level2type=N'COLUMN',@level2name=N'is_link_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_cat'
GO
/****** Object:  Table [dbo].[cms_content_article]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_article](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[content] [text] NULL,
	[pagecount] [tinyint] NOT NULL,
	[saveremoteimage] [tinyint] NOT NULL,
	[words_count] [smallint] NOT NULL,
	[site_id] [varchar](32) NULL,
	[memberid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_content_article_PK_cms_content_accessory] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'pagecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'保存远程图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'saveremoteimage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'words_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article', @level2type=N'COLUMN',@level2name=N'memberid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_article'
GO
/****** Object:  Table [dbo].[cms_content_accessory]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content_accessory](
	[id] [varchar](32) NOT NULL,
	[content_id] [varchar](32) NULL,
	[accessory_name] [varchar](255) NULL,
	[accessory_url] [varchar](255) NULL,
	[accessory_type] [varchar](255) NULL,
	[accessory_remark] [varchar](255) NULL,
	[accessory_download] [int] NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_content_accessory_PK_cms_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'content_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'accessory_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'accessory_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'accessory_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'accessory_remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件下载量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'accessory_download'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章附件表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content_accessory'
GO
/****** Object:  Table [dbo].[cms_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_content](
	[id] [varchar](32) NOT NULL,
	[catid] [varchar](32) NOT NULL,
	[pathids] [varchar](1000) NULL,
	[modelid] [varchar](32) NOT NULL,
	[classify] [varchar](255) NULL,
	[title] [char](80) NOT NULL,
	[subtitle] [varchar](120) NULL,
	[citetitle] [varchar](255) NULL,
	[color] [text] NULL,
	[thumb] [char](255) NULL,
	[tags] [char](60) NULL,
	[author] [varchar](50) NULL,
	[editor] [varchar](15) NULL,
	[sourceid] [varchar](255) NULL,
	[url] [char](255) NULL,
	[wapurl] [varchar](255) NULL,
	[weight] [tinyint] NULL,
	[status] [varchar](255) NULL,
	[constants] [varchar](255) NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[published] [datetime] NULL,
	[publishedby] [varchar](255) NULL,
	[unpublished] [bigint] NULL,
	[unpublishedby] [numeric](7, 0) NULL,
	[modified] [datetime] NULL,
	[modifiedby] [varchar](255) NULL,
	[checked] [datetime] NULL,
	[checkedby] [varchar](255) NULL,
	[locked] [datetime] NULL,
	[lockedby] [varchar](255) NULL,
	[noted] [datetime] NULL,
	[notedby] [varchar](255) NULL,
	[note] [tinyint] NULL,
	[workflow_step] [tinyint] NULL,
	[workflow_roleid] [varchar](255) NULL,
	[iscontribute] [varchar](255) NULL,
	[spaceid] [varchar](32) NULL,
	[related] [tinyint] NULL,
	[pv] [numeric](7, 0) NULL,
	[allowcomment] [varchar](255) NULL,
	[docExtendJson] [varchar](4000) NULL,
	[digest] [varchar](4000) NULL,
	[attribute] [varchar](255) NULL,
	[extendCount] [int] NULL,
	[correlation] [varchar](255) NULL,
	[description] [varchar](50) NULL,
	[catName] [varchar](50) NULL,
	[voteid] [varchar](50) NULL,
	[site_id] [varchar](32) NULL,
	[grade] [int] NULL,
	[publishedbyid] [varchar](255) NULL,
	[syn_catid] [varchar](255) NULL,
	[memberid] [varchar](32) NULL,
	[two_code] [varchar](255) NULL,
	[content_mark] [varchar](255) NULL,
	[isposter] [int] NULL,
	[is_headline] [int] NULL,
	[is_top] [int] NULL,
	[lock_content] [varchar](32) NULL,
	[static_url] [varchar](255) NULL,
	[dynamic_url] [varchar](255) NULL,
 CONSTRAINT [cms_content_PK_cms_config] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'catid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'pathids' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'modelid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'classify'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'subtitle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'引题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'citetitle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'color'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'tags'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'author'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'编辑人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'editor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'sourceid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'wap页面访问路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'wapurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'宽度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'weight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'constants'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'published'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'publishedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'unpublished' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'unpublished'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'unpublishedby' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'unpublishedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'modified'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'modifiedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'checked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'checkedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'锁定时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'locked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'锁定人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'lockedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下线时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'noted'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下线人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'notedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注解' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'note'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流步骤' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'workflow_step'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流角色ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'workflow_roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否投稿' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'iscontribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专栏ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'spaceid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'related' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'related'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'pv'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否允许评论' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'allowcomment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扩展字段json' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'docExtendJson'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'摘要' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'digest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'属性' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'attribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扩张字段数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'extendCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'相关' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'correlation'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'catName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'voteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'grade'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'publishedbyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'同时发布栏目id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'syn_catid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'memberid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'二维码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'two_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'content_mark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否广告(0否1是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'isposter'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否为头条' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'is_headline'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'置顶' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'is_top'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'稿件是否锁定（true：锁定，false:不锁定）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'lock_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'静态地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'static_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'动态地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content', @level2type=N'COLUMN',@level2name=N'dynamic_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_content'
GO
/****** Object:  Table [dbo].[cms_config]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_config](
	[ID] [varchar](32) NOT NULL,
	[code] [varchar](100) NULL,
	[content] [text] NULL,
	[name] [varchar](100) NOT NULL,
	[note] [text] NULL,
	[userid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_config_PK_cms_complaints_report] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_config', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`userid`) REFER `lm_maven_cms/cms_user`(`id`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_config'
GO
/****** Object:  Table [dbo].[cms_complaints_report]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_complaints_report](
	[id] [varchar](32) NULL,
	[name] [varchar](32) NULL,
	[email] [varchar](50) NULL,
	[telephone] [varchar](18) NULL,
	[work_unit] [varchar](100) NULL,
	[content] [text] NULL,
	[siteid] [varchar](32) NULL,
	[createtime] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'work_unit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_complaints_report', @level2type=N'COLUMN',@level2name=N'createtime'
GO
/****** Object:  Table [dbo].[cms_company]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_company](
	[id] [varchar](32) NOT NULL,
	[pid] [varchar](32) NULL,
	[token] [varchar](250) NULL,
	[company_name] [varchar](250) NULL,
	[company_shortname] [varchar](250) NULL,
	[company_cell] [varchar](250) NULL,
	[company_phone] [varchar](250) NULL,
	[company_url] [varchar](250) NULL,
	[latitude] [float] NULL,
	[longitude] [float] NULL,
	[intro] [varchar](4000) NULL,
	[taxis] [varchar](250) NULL,
	[isbranch] [varchar](250) NULL,
	[logourl] [varchar](250) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_company_PK_cms_commentary] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'company_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司简称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'company_shortname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'company_cell'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'company_phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'company_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'纬度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'latitude'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'经度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'longitude'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简介' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'intro'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'taxis'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分支' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'isbranch'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Logo地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'logourl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司信息' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_company'
GO
/****** Object:  Table [dbo].[cms_commentary]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_commentary](
	[id] [varchar](32) NOT NULL,
	[userName] [varchar](255) NULL,
	[memberLevel] [varchar](255) NULL,
	[contentId] [varchar](32) NULL,
	[comment_type] [varchar](50) NULL,
	[title] [varchar](255) NULL,
	[content] [varchar](255) NULL,
	[commentaryPerson] [varchar](255) NULL,
	[personId] [varchar](32) NULL,
	[commentaryTime] [datetime] NULL,
	[qq] [varchar](255) NULL,
	[cellphone] [varchar](255) NULL,
	[telephone] [varchar](255) NULL,
	[ischeck] [varchar](10) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_commentary_PK_cms_collect] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'userName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'memberLevel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'contentId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评价类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'comment_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章主题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'commentaryPerson'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论人ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'personId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'commentaryTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'qq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'qq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'cellphone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'ischeck'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_commentary'
GO
/****** Object:  Table [dbo].[cms_collect]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_collect](
	[id] [varchar](32) NOT NULL,
	[memberLevel] [varchar](255) NULL,
	[title] [varchar](255) NULL,
	[url] [varchar](255) NULL,
	[content] [varchar](255) NULL,
	[collectPerson] [varchar](255) NULL,
	[collectPersonId] [varchar](32) NULL,
	[collectTime] [date] NULL,
	[contentId] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_collect_PK_cms_cmskeyword] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会员级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'memberLevel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收藏人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'collectPerson'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收藏人ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'collectPersonId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'collectTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'contentId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收藏' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_collect'
GO
/****** Object:  Table [dbo].[cms_cmskeyword]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_cmskeyword](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[keyword_name] [varchar](100) NULL,
	[url] [varchar](255) NULL,
	[is_disabled] [tinyint] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_cmskeyword_PK_cms_classify] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_cmskeyword', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_cmskeyword', @level2type=N'COLUMN',@level2name=N'keyword_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_cmskeyword', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否禁用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_cmskeyword', @level2type=N'COLUMN',@level2name=N'is_disabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS内容关键词表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_cmskeyword'
GO
/****** Object:  Table [dbo].[cms_classify]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_classify](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](60) NOT NULL,
	[info] [varchar](90) NOT NULL,
	[sorts] [varchar](6) NOT NULL,
	[img] [char](255) NOT NULL,
	[url] [char](255) NOT NULL,
	[status] [varchar](1) NOT NULL,
	[token] [varchar](30) NOT NULL,
	[level] [int] NULL,
	[parentid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_classify_PK_cms_business_consulting] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'info'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'显示顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'sorts'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类图片外链地址（宽720高400）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'外链网站或活动' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'level'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上级分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'parentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_classify', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
/****** Object:  Table [dbo].[cms_business_consulting]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_business_consulting](
	[id] [varchar](32) NOT NULL,
	[business_class] [varchar](32) NULL,
	[departid] [varchar](32) NULL,
	[name] [varchar](20) NULL,
	[phone] [varchar](18) NULL,
	[message] [text] NULL,
	[message_time] [datetime] NULL,
	[reply_count] [text] NULL,
	[reply_time] [datetime] NULL,
	[createtime] [datetime] NULL,
	[reply_status] [varchar](10) NULL,
	[ischeck] [varchar](10) NULL,
	[siteid] [varchar](32) NULL,
 CONSTRAINT [cms_business_consulting_PK_cms_branch] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'业务类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'business_class'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复部门id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'departid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'咨询人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'message'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'message_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'reply_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'reply_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复状态(0未回复1已回复)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'reply_status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核状态(0未审核1已审核)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'ischeck'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_business_consulting', @level2type=N'COLUMN',@level2name=N'siteid'
GO
/****** Object:  Table [dbo].[cms_branch]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_branch](
	[id] [varchar](32) NOT NULL,
	[pid] [varchar](32) NULL,
	[token] [varchar](250) NULL,
	[company_name] [varchar](250) NULL,
	[company_shortname] [varchar](250) NULL,
	[company_cell] [varchar](250) NULL,
	[company_phone] [varchar](250) NULL,
	[company_url] [varchar](250) NULL,
	[latitude] [float] NULL,
	[longitude] [float] NULL,
	[intro] [varchar](4000) NULL,
	[taxis] [varchar](250) NULL,
	[isbranch] [varchar](250) NULL,
	[logourl] [varchar](250) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_branch_PK_cms_base_user] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'company_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司简称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'company_shortname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'company_cell'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'company_phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'company_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'纬度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'latitude'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'经度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'longitude'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简介' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'intro'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排列' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'taxis'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分支' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'isbranch'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Logo地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'logourl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司分支机构' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_branch'
GO
/****** Object:  Table [dbo].[cms_base_user]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_base_user](
	[ID] [varchar](32) NOT NULL,
	[activitiSync] [smallint] NULL,
	[browser] [varchar](20) NULL,
	[password] [varchar](100) NULL,
	[realname] [varchar](50) NULL,
	[signature] [image] NULL,
	[status] [smallint] NULL,
	[userkey] [varchar](200) NULL,
	[username] [varchar](50) NOT NULL,
	[departid] [varchar](32) NULL,
 CONSTRAINT [cms_base_user_PK_cms_attachment] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`departid`) REFER `lm_maven_cms/cms_depart`(`ID`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_base_user'
GO
/****** Object:  Table [dbo].[cms_attachment]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_attachment](
	[ID] [varchar](32) NOT NULL,
	[attachmentcontent] [image] NULL,
	[attachmenttitle] [varchar](100) NULL,
	[businesskey] [varchar](32) NULL,
	[createdate] [datetime] NULL,
	[extend] [varchar](32) NULL,
	[note] [text] NULL,
	[realpath] [varchar](100) NULL,
	[subclassname] [text] NULL,
	[swfpath] [text] NULL,
	[BUSENTITYNAME] [varchar](100) NULL,
	[INFOTYPEID] [varchar](32) NULL,
	[USERID] [varchar](32) NULL,
 CONSTRAINT [cms_attachment_PK_cms_attach_picture] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'InnoDB free: 6144 kB; (`USERID`) REFER `lm_maven_cms/cms_user`(`id`)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attachment'
GO
/****** Object:  Table [dbo].[cms_attach_picture]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_attach_picture](
	[id] [varchar](32) NOT NULL,
	[realName] [varchar](225) NULL,
	[localName] [varchar](225) NULL,
	[thumbnailPath] [varchar](225) NULL,
	[localPath] [varchar](255) NULL,
	[createDate] [datetime] NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cms_attach_picture_PK_cms_apply_public] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片原名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'realName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传后的名字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'localName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'thumbnailPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'原图路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'localPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'createDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'网站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片库' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_attach_picture'
GO
/****** Object:  Table [dbo].[cms_apply_public]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_apply_public](
	[id] [varchar](32) NOT NULL,
	[bjj_sel] [varchar](10) NULL,
	[gr_name] [varchar](50) NULL,
	[gr_danwei] [varchar](100) NULL,
	[gr_zj] [varchar](100) NULL,
	[gr_haoma] [varchar](100) NULL,
	[gr_contact] [varchar](100) NULL,
	[gr_fax] [varchar](100) NULL,
	[gr_address] [varchar](100) NULL,
	[gr_post] [varchar](20) NULL,
	[gr_email] [varchar](50) NULL,
	[fr_name] [varchar](50) NULL,
	[fr_daibiao] [varchar](50) NULL,
	[fr_xingming] [varchar](50) NULL,
	[fr_contact] [varchar](50) NULL,
	[fr_post] [varchar](50) NULL,
	[fr_address] [varchar](100) NULL,
	[fr_fax] [varchar](50) NULL,
	[fr_email] [varchar](50) NULL,
	[Datetime] [datetime] NULL,
	[content] [text] NULL,
	[xingshi] [varchar](100) NULL,
	[xt_name] [varchar](100) NULL,
	[xt_suyinhao] [varchar](100) NULL,
	[xt_yongtu] [text] NULL,
	[xt_jmfy] [varchar](10) NULL,
	[xt_huoqufs] [varchar](50) NULL,
	[isstat] [varchar](50) NULL,
	[publisher_ip] [varchar](50) NULL,
	[createtime] [datetime] NULL,
	[siteid] [varchar](32) NULL,
	[remarks] [text] NULL,
 CONSTRAINT [cms_apply_public_PK_cms_advertising_space] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请人类型(0公民1 法人或其他组织)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'bjj_sel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民工作单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_danwei'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民证件名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_zj'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_contact'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民传真' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_fax'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民联系地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民邮政编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_post'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公民电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'gr_email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人代表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_daibiao'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人联系人姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_xingming'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人联系人电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_contact'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人邮政编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_post'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人联系地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人传真' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_fax'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'法人电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'fr_email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'Datetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所需信息的内容描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所需信息的指定提供载体形式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xingshi'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所需信息的名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xt_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所需信息的索引号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xt_suyinhao'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所需信息的用途' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xt_yongtu'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否申请减免费用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xt_jmfy'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'获取信息的方式（可多选）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'xt_huoqufs'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请公开状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'isstat'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布者IP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'publisher_ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'siteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_apply_public', @level2type=N'COLUMN',@level2name=N'remarks'
GO
/****** Object:  Table [dbo].[cms_advertising_space]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_advertising_space](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[ad_name] [varchar](100) NULL,
	[description] [varchar](255) NULL,
	[is_enabled] [char](1) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_advertising_space_PK_cms_advertising] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'属所网站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising_space', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising_space', @level2type=N'COLUMN',@level2name=N'ad_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising_space', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising_space', @level2type=N'COLUMN',@level2name=N'is_enabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS广告版位表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising_space'
GO
/****** Object:  Table [dbo].[cms_advertising]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_advertising](
	[id] [varchar](32) NOT NULL,
	[gid] [varchar](32) NULL,
	[site_id] [varchar](32) NULL,
	[ad_name] [varchar](100) NULL,
	[category] [varchar](50) NULL,
	[ad_code] [varchar](1000) NULL,
	[ad_weight] [int] NULL,
	[display_count] [bigint] NULL,
	[click_count] [bigint] NULL,
	[start_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[is_enabled] [char](1) NULL,
	[img_url] [varchar](255) NULL,
	[iheight] [varchar](30) NULL,
	[iweight] [varchar](30) NULL,
	[urladress] [varchar](50) NULL,
	[urlpoint] [varchar](50) NULL,
	[urltarget] [int] NULL,
	[wordcontent] [varchar](50) NULL,
	[wordsize] [varchar](30) NULL,
	[wordcolor] [varchar](50) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_advertising_PK_cms_activity_people] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告栏位id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'gid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所属网站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'ad_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'category'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'ad_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告权重' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'ad_weight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'展现次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'display_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点击次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'click_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'start_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'end_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'is_enabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片上传地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'img_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'度高' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'iheight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'度宽' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'iweight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'urladress'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接链提示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'urlpoint'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接链目标' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'urltarget'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文字内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'wordcontent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字文大小' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'wordsize'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文字颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising', @level2type=N'COLUMN',@level2name=N'wordcolor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS广告表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_advertising'
GO
/****** Object:  Table [dbo].[cms_activity_people]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_activity_people](
	[id] [varchar](32) NOT NULL,
	[activityids] [varchar](32) NULL,
	[IP] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[createby] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_people', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_people', @level2type=N'COLUMN',@level2name=N'activityids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_people', @level2type=N'COLUMN',@level2name=N'IP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_people', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_people', @level2type=N'COLUMN',@level2name=N'createby'
GO
/****** Object:  Table [dbo].[cms_activity_optioncontent]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_activity_optioncontent](
	[id] [varchar](32) NOT NULL,
	[is_enableds] [int] NULL,
	[is_required] [int] NULL,
	[is_receptionShow] [int] NULL,
	[optionids] [varchar](32) NULL,
	[activityids] [varchar](32) NULL,
	[createtime] [datetime] NULL,
 CONSTRAINT [cms_activity_optioncontent_PK_cms_activity_option_ext] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'is_enableds'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否必填' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'is_required'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否前台显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'is_receptionShow'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'optionids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'activityids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_optioncontent', @level2type=N'COLUMN',@level2name=N'createtime'
GO
/****** Object:  Table [dbo].[cms_activity_option_ext]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_activity_option_ext](
	[id] [varchar](32) NOT NULL,
	[optiondids] [varchar](32) NULL,
	[ext_text] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[IP] [varchar](255) NULL,
	[logids] [varchar](255) NULL,
	[ext_checkbox] [varchar](255) NULL,
	[ext_select] [varchar](255) NULL,
	[ext_picture] [varchar](255) NULL,
	[ext_file] [varchar](255) NULL,
 CONSTRAINT [cms_activity_option_ext_PK_cms_activity_option] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'optiondids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文本内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'ext_text'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'IP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'报名人id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'logids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'多选内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'ext_checkbox'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下拉内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'ext_select'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片上传' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'ext_picture'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文件上传' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option_ext', @level2type=N'COLUMN',@level2name=N'ext_file'
GO
/****** Object:  Table [dbo].[cms_activity_option]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_activity_option](
	[id] [varchar](32) NOT NULL,
	[option_name] [varchar](255) NULL,
	[option_mark] [varchar](255) NULL,
	[date_type] [varchar](255) NULL,
	[textsize_limit] [varchar](255) NULL,
	[optional_value] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[validation] [varchar](255) NULL,
	[is_showdelete] [int] NULL,
	[is_enabled] [int] NULL,
	[sort] [varchar](11) NULL,
	[is_show] [int] NULL,
	[selectsize_limit] [varchar](255) NULL,
	[filesize_limit] [varchar](255) NULL,
 CONSTRAINT [cms_activity_option_PK_cms_activity] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'option_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字段标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'option_mark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'date_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文本大小限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'textsize_limit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'可选值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'optional_value'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'验证规则' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'validation'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示删除(1:否,0：是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'is_showdelete'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用(1:是，0：否)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'is_enabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否默认显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'is_show'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'选项大小限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'selectsize_limit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文件大小限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity_option', @level2type=N'COLUMN',@level2name=N'filesize_limit'
GO
/****** Object:  Table [dbo].[cms_activity]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_activity](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[activityStartTime] [datetime] NULL,
	[activityEndTime] [datetime] NULL,
	[activityApplyStartTime] [datetime] NULL,
	[activityApplyEndTime] [datetime] NULL,
	[activityPleNum] [varchar](255) NULL,
	[activityIpLimit] [varchar](11) NULL,
	[activitySex] [varchar](11) NULL,
	[activityBackground] [varchar](255) NULL,
	[activityDataType] [varchar](255) NULL,
	[activityAddress] [varchar](255) NULL,
	[activityContent] [varchar](1000) NULL,
	[activityRemark] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
	[activityPeople] [varchar](255) NULL,
	[activityaddressPoint] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cms_activity_PK_cms_acquisition_temp] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动报名开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityApplyStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动报名截止时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityApplyEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'人数限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityPleNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityIpLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activitySex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页面背景图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityBackground'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityDataType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityAddress'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityContent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityRemark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发起人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityPeople'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'坐标点' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'activityaddressPoint'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活动表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_activity'
GO
/****** Object:  Table [dbo].[cms_acquisition_temp]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_acquisition_temp](
	[id] [varchar](32) NOT NULL,
	[site_id] [int] NULL,
	[channel_url] [varchar](255) NOT NULL,
	[content_url] [varchar](255) NOT NULL,
	[title] [varchar](255) NULL,
	[percents] [int] NOT NULL,
	[description] [varchar](20) NOT NULL,
	[seq] [int] NOT NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_acquisition_temp_PK_cms_acquisition_replace] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'channel_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'content_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'百分比' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'percents'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp', @level2type=N'COLUMN',@level2name=N'seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集进度临时表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_temp'
GO
/****** Object:  Table [dbo].[cms_acquisition_replace]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_acquisition_replace](
	[id] [varchar](32) NOT NULL,
	[replace_new] [varchar](1000) NULL,
	[replace_old] [varchar](1000) NULL,
	[createtime] [datetime] NULL,
	[acquisition_id] [varchar](32) NULL,
 CONSTRAINT [cms_acquisition_replace_PK_cms_acquisition_history] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容替换为' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace', @level2type=N'COLUMN',@level2name=N'replace_new'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'将什么内容替换' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace', @level2type=N'COLUMN',@level2name=N'replace_old'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace', @level2type=N'COLUMN',@level2name=N'acquisition_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据采集关联表  内容替换' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_replace'
GO
/****** Object:  Table [dbo].[cms_acquisition_history]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_acquisition_history](
	[id] [varchar](32) NOT NULL,
	[channel_url] [varchar](255) NOT NULL,
	[content_url] [varchar](255) NOT NULL,
	[title] [varchar](255) NULL,
	[description] [varchar](20) NOT NULL,
	[acquisition_id] [varchar](32) NULL,
	[content_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_acquisition_history_PK_cms_acquisition_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'channel_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'content_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集源' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'acquisition_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'content_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集历史记录表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_history'
GO
/****** Object:  Table [dbo].[cms_acquisition_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_acquisition_content](
	[id] [varchar](32) NOT NULL,
	[title] [varchar](150) NOT NULL,
	[short_title] [varchar](150) NULL,
	[author] [varchar](100) NULL,
	[origin] [varchar](100) NULL,
	[origin_url] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[release_date] [datetime] NULL,
	[media_path] [varchar](255) NULL,
	[media_type] [varchar](20) NULL,
	[title_color] [varchar](10) NULL,
	[is_bold] [tinyint] NULL,
	[title_img] [varchar](100) NULL,
	[content_img] [varchar](100) NULL,
	[type_img] [varchar](100) NULL,
	[link] [varchar](255) NULL,
	[tpl_content] [varchar](100) NULL,
	[need_regenerate] [tinyint] NULL,
	[txt] [text] NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_acquisition_content_PK_cms_acquisition] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简短标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'short_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'author'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'origin'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'origin_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'release_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'媒体路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'media_path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'媒体类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'media_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'title_color'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否加粗' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'is_bold'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'title_img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'content_img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'type_img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'外部链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'link'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'指定模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'tpl_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'需要重新生成静态页' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'need_regenerate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'txt'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS内容扩展表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition_content'
GO
/****** Object:  Table [dbo].[cms_acquisition]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cms_acquisition](
	[id] [varchar](32) NOT NULL,
	[site_id] [varchar](32) NULL,
	[channel_id] [varchar](32) NULL,
	[type_id] [varchar](32) NULL,
	[user_id] [varchar](32) NULL,
	[acq_name] [varchar](50) NULL,
	[start_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[status] [int] NULL,
	[curr_num] [int] NULL,
	[curr_item] [int] NULL,
	[total_item] [int] NULL,
	[pause_time] [int] NULL,
	[page_encoding] [varchar](20) NULL,
	[plan_list] [text] NULL,
	[dynamic_addr] [varchar](255) NULL,
	[dynamic_start] [int] NULL,
	[dynamic_end] [int] NULL,
	[linkset_start] [varchar](255) NULL,
	[linkset_end] [varchar](255) NULL,
	[link_start] [varchar](255) NULL,
	[link_end] [varchar](255) NULL,
	[title_start] [varchar](255) NULL,
	[title_end] [varchar](255) NULL,
	[keywords_start] [varchar](255) NULL,
	[keywords_end] [varchar](255) NULL,
	[description_start] [varchar](255) NULL,
	[description_end] [varchar](255) NULL,
	[content_start] [varchar](255) NULL,
	[content_end] [varchar](255) NULL,
	[pagination_start] [varchar](255) NULL,
	[pagination_end] [varchar](255) NULL,
	[queue] [int] NULL,
	[repeat_check_type] [varchar](20) NULL,
	[img_acqu] [tinyint] NULL,
	[content_prefix] [varchar](255) NULL,
	[img_prefix] [varchar](255) NULL,
	[view_start] [varchar](255) NULL,
	[view_end] [varchar](255) NULL,
	[view_id_start] [varchar](255) NULL,
	[view_id_end] [varchar](255) NULL,
	[view_link] [varchar](255) NULL,
	[releaseTime_start] [varchar](255) NULL,
	[releaseTime_end] [varchar](255) NULL,
	[author_start] [varchar](255) NULL,
	[author_end] [varchar](255) NULL,
	[origin_start] [varchar](255) NULL,
	[origin_end] [varchar](255) NULL,
	[releaseTime_format] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cms_acquisition_PK_cm_vote] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'acq_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'start_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'停止时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'end_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'当前状态(0:静止;1:采集;2:暂停)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'当前号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'curr_num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'当前条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'curr_item'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'每页总条数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'total_item'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'暂停时间(毫秒)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'pause_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页面编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'page_encoding'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采集列表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'plan_list'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'动态地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'dynamic_addr'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页码开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'dynamic_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页码结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'dynamic_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容链接区开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'linkset_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容链接区结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'linkset_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容链接开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'link_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容链接结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'link_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'title_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'title_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关键字开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'keywords_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关键字结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'keywords_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'description_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'description_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'content_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'content_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容分页开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'pagination_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容分页结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'pagination_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'队列' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'queue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'重复类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'repeat_check_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否采集图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'img_acqu'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容地址补全url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'content_prefix'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片地址补全url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'img_prefix'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'view_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'view_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id前缀' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'view_id_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id后缀' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'view_id_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量动态访问地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'view_link'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'releaseTime_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'releaseTime_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'author_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'author_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源开始' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'origin_start'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源结束' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'origin_end'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间格式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition', @level2type=N'COLUMN',@level2name=N'releaseTime_format'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'CMS采集表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cms_acquisition'
GO
/****** Object:  Table [dbo].[cm_vote]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_vote](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[voteType] [varchar](255) NULL,
	[votePattern] [varchar](255) NULL,
	[voteIntroduce] [varchar](255) NULL,
	[voteIpLimit] [varchar](255) NULL,
	[voteProvinceLimit] [varchar](255) NULL,
	[voteStartTime] [datetime] NULL,
	[voteEndTime] [datetime] NULL,
	[voteTotal] [int] NULL,
	[voteMinInterval] [varchar](255) NULL,
	[maxVotes] [int] NULL,
	[site_id] [varchar](32) NULL,
	[voteid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_vote_PK_cm_video_classify] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票模式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'votePattern'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteIntroduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteIpLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否省份限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteProvinceLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总票数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteTotal'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'时间间隔' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteMinInterval'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最多票数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'maxVotes'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票PCId' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'voteid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建世界' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投票表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_vote'
GO
/****** Object:  Table [dbo].[cm_video_classify]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_video_classify](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[sort] [varchar](255) NULL,
	[marker] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_video_classify_PK_cm_video] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video_classify', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video_classify', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video_classify', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video_classify', @level2type=N'COLUMN',@level2name=N'marker'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动视频分类表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video_classify'
GO
/****** Object:  Table [dbo].[cm_video]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_video](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[videoname] [varchar](255) NULL,
	[videourl] [varchar](4000) NULL,
	[videoclassify] [varchar](255) NULL,
	[time] [varchar](255) NULL,
	[special] [varchar](255) NULL,
	[videoremark] [varchar](1000) NULL,
	[site_id] [varchar](32) NULL,
	[videoclassifyid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_video_PK_cm_update_version] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [cm_video_contentid] ON [dbo].[cm_video]
(
	[contentid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'videoname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'videourl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'videoclassify'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频时长' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频专辑' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'special'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'videoremark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频分类id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'videoclassifyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_video'
GO
/****** Object:  Table [dbo].[cm_update_version]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_update_version](
	[ID] [varchar](32) NOT NULL,
	[sys_type] [varchar](20) NULL,
	[version] [varchar](20) NULL,
	[account] [varchar](3000) NULL,
	[down_url] [varchar](200) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_update_version_PK_cm_sys_iso_token] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version', @level2type=N'COLUMN',@level2name=N'sys_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version', @level2type=N'COLUMN',@level2name=N'account'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下载地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version', @level2type=N'COLUMN',@level2name=N'down_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'版本升级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_update_version'
GO
/****** Object:  Table [dbo].[cm_sys_iso_token]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_sys_iso_token](
	[ID] [varchar](32) NOT NULL,
	[token] [varchar](100) NOT NULL,
	[sys_type] [varchar](50) NULL,
	[phone_name] [varchar](50) NULL,
	[phone_type] [varchar](50) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_sys_iso_token_PK_cm_survey] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token', @level2type=N'COLUMN',@level2name=N'token'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token', @level2type=N'COLUMN',@level2name=N'sys_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机品牌' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token', @level2type=N'COLUMN',@level2name=N'phone_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'手机型号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token', @level2type=N'COLUMN',@level2name=N'phone_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'获取IOS设备的token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_sys_iso_token'
GO
/****** Object:  Table [dbo].[cm_survey]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_survey](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[custom] [varchar](255) NULL,
	[pageBackground] [varchar](1000) NULL,
	[recipient] [varchar](255) NULL,
	[surveyStartTime] [datetime] NULL,
	[surveyEndTime] [datetime] NULL,
	[surveyPeoLimit] [varchar](255) NULL,
	[surveyIpLimit] [varchar](255) NULL,
	[isVip] [varchar](255) NULL,
	[site_id] [varchar](32) NULL,
	[surveyid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_survey_PK_cm_suggest] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自定义模板' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'custom'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页面背景图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'pageBackground'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接收人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'recipient'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'surveyStartTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'surveyEndTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'人数限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'surveyPeoLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'IP限制' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'surveyIpLimit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否会员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'isVip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查PCId' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'surveyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调查表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_survey'
GO
/****** Object:  Table [dbo].[cm_suggest]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_suggest](
	[id] [varchar](32) NOT NULL,
	[user_id] [varchar](32) NULL,
	[suggest_content] [text] NULL,
	[suggest_type] [varchar](32) NULL,
	[email] [varchar](255) NULL,
	[tel] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_suggest_PK_cm_simplespecial_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'user_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'反馈意见内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'suggest_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'反馈类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'suggest_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest', @level2type=N'COLUMN',@level2name=N'tel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'意见反馈表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_suggest'
GO
/****** Object:  Table [dbo].[cm_simplespecial_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_simplespecial_content](
	[id] [varchar](32) NOT NULL,
	[simplespecialid] [varchar](32) NULL,
	[contentid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_simplespecial_content_PK_cm_simplespecial] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简单专题id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial_content', @level2type=N'COLUMN',@level2name=N'simplespecialid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial_content', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial_content', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简单专题内容关联表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial_content'
GO
/****** Object:  Table [dbo].[cm_simplespecial]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_simplespecial](
	[id] [varchar](32) NOT NULL,
	[is_top] [int] NULL,
	[special_name] [varchar](255) NULL,
	[special_content] [text] NULL,
	[special_thub] [varchar](255) NULL,
	[special_create] [datetime] NULL,
	[special_created] [varchar](255) NULL,
	[special_mark] [varchar](255) NULL,
	[special_state] [varchar](255) NULL,
	[special_type] [varchar](255) NULL,
	[special_slide] [varchar](255) NULL,
	[sort] [int] NULL,
 CONSTRAINT [cm_simplespecial_PK_cm_relate_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'置顶' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'is_top'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_thub'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_create'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_mark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_state'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专题头图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'special_slide'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简单专题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_simplespecial'
GO
/****** Object:  Table [dbo].[cm_relate_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_relate_content](
	[id] [varchar](32) NOT NULL,
	[content_id] [varchar](255) NOT NULL,
	[relate_title] [char](80) NOT NULL,
	[relate_url] [char](100) NULL,
	[images] [char](100) NOT NULL,
	[relate_type] [varchar](255) NOT NULL,
	[relate_order] [int] NULL,
	[newdate] [datetime] NULL,
	[created] [datetime] NULL,
	[part] [varchar](255) NULL,
 CONSTRAINT [cm_relate_content_PK_cm_picturealone] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'content_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'relate_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'URL路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'relate_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'images'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'relate_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顺序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'relate_order'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'表单新增时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'newdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区别内容id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content', @level2type=N'COLUMN',@level2name=N'part'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'相关内容表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_relate_content'
GO
/****** Object:  Table [dbo].[cm_picturealone]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_picturealone](
	[id] [varchar](32) NOT NULL,
	[picturegroupid] [varchar](32) NULL,
	[picture_url] [varchar](255) NULL,
	[picture_message] [varchar](255) NULL,
	[picture_width] [varchar](255) NULL,
	[picture_height] [varchar](255) NULL,
	[picture_sort] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_picturealone_PK_cm_picture_group] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单图片ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'组图ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picturegroupid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picture_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片信息' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picture_message'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'宽度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picture_width'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'长度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picture_height'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone', @level2type=N'COLUMN',@level2name=N'picture_sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picturealone'
GO
/****** Object:  Table [dbo].[cm_picture_group]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_picture_group](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[image] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[url] [text] NULL,
	[url_thumb] [varchar](255) NULL,
	[sort] [smallint] NULL,
	[site_id] [varchar](32) NULL,
	[pictureclassifyid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_picture_group_PK_cm_picture_classify] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [cm_picture_group_contentid] ON [dbo].[cm_picture_group]
(
	[contentid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'url_thumb'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片分类id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'pictureclassifyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片组表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_group'
GO
/****** Object:  Table [dbo].[cm_picture_classify]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_picture_classify](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](255) NULL,
	[sort] [varchar](255) NULL,
	[marker] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_picture_classify_PK_cm_mobile_channel_priv] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_classify', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_classify', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_classify', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_classify', @level2type=N'COLUMN',@level2name=N'marker'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动图片分类表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_picture_classify'
GO
/****** Object:  Table [dbo].[cm_mobile_channel_priv]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_mobile_channel_priv](
	[id] [varchar](32) NOT NULL,
	[channelid] [varchar](32) NULL,
	[roleid] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_mobile_channel_priv_PK_cm_mobile_channel] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目权限ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel_priv', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel_priv', @level2type=N'COLUMN',@level2name=N'channelid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'角色ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel_priv', @level2type=N'COLUMN',@level2name=N'roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动栏目权限表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel_priv'
GO
/****** Object:  Table [dbo].[cm_mobile_channel]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_mobile_channel](
	[id] [varchar](32) NOT NULL,
	[name] [varchar](50) NULL,
	[pathids] [varchar](255) NULL,
	[parentid] [varchar](32) NULL,
	[parentids] [varchar](255) NULL,
	[cat_id] [varchar](255) NULL,
	[channel_ico] [varchar](255) NULL,
	[slide_show] [int] NULL,
	[slide_number] [int] NULL,
	[channel_states] [int] NULL,
	[channel_top] [int] NULL,
	[pv] [int] NULL,
	[comments] [int] NULL,
	[issued_amount] [int] NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](50) NULL,
	[modified] [datetime] NULL,
	[modifiedby] [varchar](50) NULL,
	[sort] [int] NULL,
	[levels] [int] NULL,
	[workflowid] [varchar](32) NULL,
	[channel_type] [varchar](255) NULL,
	[path] [varchar](100) NULL,
	[url] [varchar](100) NULL,
	[constants] [varchar](11) NULL,
	[channelHot] [int] NULL,
	[site_id] [varchar](32) NULL,
 CONSTRAINT [cm_mobile_channel_PK_cm_message] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'频道id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'频道名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父频道id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'parentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所有父频道id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'parentids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'cat_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图标' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'channel_ico'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'显示幻灯片(0代表 禁用   1代表 启用)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'slide_show'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'幻灯片数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'slide_number'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态(0代表 禁用   1代表 启用)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'channel_states'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'头条频道(0代表 否   1代表 是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'channel_top'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'pv'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'comments'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发稿量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'issued_amount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'modified'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'modifiedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'级别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'levels'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'workflowid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'channel_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'生成路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'path'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'constants'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'热门频道 0：不是 1：是' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'channelHot'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动频道' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_mobile_channel'
GO
/****** Object:  Table [dbo].[cm_message]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_message](
	[id] [varchar](32) NOT NULL,
	[inform_date] [datetime] NULL,
	[title] [varchar](100) NULL,
	[inform_content] [text] NULL,
	[inform_detail] [varchar](255) NULL,
	[inform_type] [varchar](11) NULL,
	[entironment_type] [char](1) NULL,
	[entironment_name] [varchar](100) NULL,
	[target] [int] NULL,
	[url] [varchar](150) NULL,
	[img_url] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_message_PK_cm_link] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'通知id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'通知时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'inform_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'通知内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'inform_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'通知页面' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'inform_detail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'通知类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'inform_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'运行环境类型(0,测试环境1,正式环境)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'entironment_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'运行换环境名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'entironment_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'推送目标（1苹果,2安卓,3苹果+安卓）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'target'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'连接的URL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片的URL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message', @level2type=N'COLUMN',@level2name=N'img_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动通知表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_message'
GO
/****** Object:  Table [dbo].[cm_link]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_link](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[linkname] [varchar](255) NULL,
	[linkurl] [varchar](4000) NULL,
	[linkremark] [varchar](1000) NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_link_PK_cm_invitation] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [cm_link_contentid] ON [dbo].[cm_link]
(
	[contentid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'linkname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'linkurl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'linkremark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'点站id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'链接表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_link'
GO
/****** Object:  Table [dbo].[cm_invitation]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_invitation](
	[id] [varchar](32) NOT NULL,
	[contents_id] [varchar](32) NULL,
	[invitation_type] [varchar](50) NULL,
	[user_id] [varchar](32) NULL,
	[user_name] [varchar](100) NULL,
	[contact] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[lastreply] [datetime] NULL,
	[reply_name] [varchar](100) NULL,
	[title] [varchar](255) NULL,
	[content] [text] NULL,
	[ip] [varchar](15) NULL,
	[display] [varchar](255) NULL,
	[p_index] [tinyint] NULL,
	[disabled] [varchar](255) NULL,
	[grade] [int] NULL,
	[img] [varchar](255) NULL,
	[contents_title] [varchar](255) NULL,
	[contents_url] [varchar](255) NULL,
 CONSTRAINT [cm_invitation_PK_cm_feedback] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'comment_id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'contents_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'跟帖类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'invitation_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'user_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'user_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'contact'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'createtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后回复时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'lastreply'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复者名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'reply_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'display' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'display'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'p_index' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'p_index'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'disabled' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'disabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'grade'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'contents_title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation', @level2type=N'COLUMN',@level2name=N'contents_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动内容跟帖表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_invitation'
GO
/****** Object:  Table [dbo].[cm_feedback]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_feedback](
	[ID] [varchar](32) NOT NULL,
	[content] [varchar](255) NOT NULL,
	[email] [varchar](30) NULL,
	[version] [varchar](20) NULL,
	[system] [varchar](30) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_feedback_PK_cm_content_article] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'意见反馈内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback', @level2type=N'COLUMN',@level2name=N'email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback', @level2type=N'COLUMN',@level2name=N'version'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback', @level2type=N'COLUMN',@level2name=N'system'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'意见反馈' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_feedback'
GO
/****** Object:  Table [dbo].[cm_content_article]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_content_article](
	[id] [varchar](32) NOT NULL,
	[contentid] [varchar](32) NULL,
	[content] [text] NULL,
	[pagecount] [tinyint] NOT NULL,
	[saveremoteimage] [tinyint] NOT NULL,
	[words_count] [smallint] NOT NULL,
	[site_id] [varchar](32) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_content_article_PK_cm_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'contentid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'pagecount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'保存远程图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'saveremoteimage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'words_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文章表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content_article'
GO
/****** Object:  Table [dbo].[cm_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_content](
	[id] [varchar](32) NOT NULL,
	[catid] [varchar](32) NOT NULL,
	[pathids] [varchar](100) NULL,
	[modelid] [varchar](32) NULL,
	[classify] [varchar](255) NULL,
	[title] [char](80) NOT NULL,
	[subtitle] [varchar](120) NULL,
	[color] [text] NULL,
	[thumb] [varchar](255) NULL,
	[tags] [char](60) NULL,
	[author] [varchar](50) NULL,
	[editor] [varchar](15) NULL,
	[sourceid] [varchar](255) NULL,
	[url] [varchar](255) NULL,
	[weight] [tinyint] NULL,
	[status] [varchar](255) NULL,
	[created] [datetime] NULL,
	[createdby] [varchar](255) NULL,
	[published] [datetime] NULL,
	[publishedby] [varchar](255) NULL,
	[unpublished] [bigint] NULL,
	[unpublishedby] [numeric](7, 0) NULL,
	[modified] [datetime] NULL,
	[modifiedby] [varchar](255) NULL,
	[checked] [datetime] NULL,
	[checkedby] [varchar](255) NULL,
	[locked] [datetime] NULL,
	[lockedby] [varchar](255) NULL,
	[noted] [datetime] NULL,
	[notedby] [varchar](255) NULL,
	[note] [tinyint] NULL,
	[workflow_step] [tinyint] NULL,
	[workflow_roleid] [varchar](255) NULL,
	[iscontribute] [varchar](255) NULL,
	[spaceid] [numeric](7, 0) NULL,
	[related] [tinyint] NULL,
	[pv] [numeric](7, 0) NULL,
	[allowcomment] [varchar](255) NULL,
	[docExtendJson] [varchar](4000) NULL,
	[digest] [varchar](4000) NULL,
	[attribute] [varchar](255) NULL,
	[extendCount] [int] NULL,
	[correlation] [varchar](255) NULL,
	[description] [varchar](50) NULL,
	[channelName] [varchar](50) NULL,
	[iscollect] [varchar](50) NULL,
	[site_id] [varchar](32) NULL,
	[grade] [int] NULL,
	[publishedbyid] [varchar](255) NULL,
	[list_thumbnail] [varchar](255) NULL,
	[content_thumbnail] [varchar](255) NULL,
	[slide_thumbnail] [varchar](255) NULL,
	[sort_dateTime] [datetime] NULL,
	[content_type] [varchar](255) NULL,
	[two_code] [varchar](255) NULL,
	[content_mark] [varchar](255) NULL,
	[relevanceid] [varchar](32) NULL,
	[is_top] [int] NULL,
	[is_top_pic] [int] NULL,
	[isposter] [int] NULL,
 CONSTRAINT [cm_content_PK_cm_comments] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'栏目ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'catid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'pathids' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'pathids'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模型ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'modelid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'classify'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'短标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'subtitle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'color'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标签' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'tags'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'author'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'编辑人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'editor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'sourceid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'宽度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'weight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'created'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'createdby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'published'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'publishedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'unpublished' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'unpublished'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'unpublishedby' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'unpublishedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'modified'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'modifiedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'checked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'checkedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'锁定时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'locked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'锁定人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'lockedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注解时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'noted'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注解人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'notedby'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注解' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'note'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流步骤' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'workflow_step'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流角色ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'workflow_roleid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否投稿' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'iscontribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'专栏ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'spaceid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'related' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'related'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'pv'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否允许评论' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'allowcomment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扩展字段json' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'docExtendJson'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'摘要' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'digest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'属性' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'attribute'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扩张字段数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'extendCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'相关' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'correlation'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'频道名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'channelName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否收藏' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'iscollect'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'site_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'grade'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'publishedbyid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'list_thumbnail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'content_thumbnail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'幻灯片缩略图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'slide_thumbnail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'sort_dateTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动内容类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'content_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'二维码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'two_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容标记' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'content_mark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'relevanceid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'置顶' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'is_top'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动头图 0不是头图 1是头图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'is_top_pic'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否广告(0否1是)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content', @level2type=N'COLUMN',@level2name=N'isposter'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_content'
GO
/****** Object:  Table [dbo].[cm_comments]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_comments](
	[id] [varchar](32) NOT NULL,
	[contents_id] [varchar](32) NULL,
	[comment_type] [varchar](50) NULL,
	[user_id] [numeric](8, 0) NULL,
	[user_name] [varchar](100) NULL,
	[contact] [varchar](255) NULL,
	[time] [datetime] NULL,
	[lastreply] [datetime] NULL,
	[reply_name] [varchar](100) NULL,
	[title] [varchar](255) NULL,
	[comment] [text] NULL,
	[ip] [varchar](15) NULL,
	[display] [varchar](255) NULL,
	[p_index] [tinyint] NULL,
	[disabled] [varchar](255) NULL,
	[grade] [int] NULL,
	[img] [varchar](255) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_comments_PK_cm_advertisemen_starting] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'comment_id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'contents_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评价类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'comment_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'user_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'user_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'contact'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后回复时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'lastreply'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'回复者名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'reply_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'comment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ip' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'ip'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'display' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'display'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'p_index' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'p_index'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'disabled' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'disabled'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'grade'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments', @level2type=N'COLUMN',@level2name=N'img'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'移动内容评价表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_comments'
GO
/****** Object:  Table [dbo].[cm_advertisemen_starting]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_advertisemen_starting](
	[ID] [varchar](32) NOT NULL,
	[img_height] [int] NULL,
	[img_width] [int] NULL,
	[img_url] [varchar](160) NULL,
	[createdtime] [datetime] NULL,
	[jump] [int] NULL,
	[showtime] [float] NULL,
	[display] [int] NULL,
	[siteId] [varchar](32) NULL,
	[sort] [int] NULL,
 CONSTRAINT [cm_advertisemen_starting_PK_cm_advertisemen_list_and_mobile_channel] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片高度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'img_height'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片宽度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'img_width'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'img_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'createdtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'判断是否跳转' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'jump'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'显示时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'showtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'display'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'站点id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'siteId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting', @level2type=N'COLUMN',@level2name=N'sort'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'启动画面广告' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_starting'
GO
/****** Object:  Table [dbo].[cm_advertisemen_list_and_mobile_channel]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_advertisemen_list_and_mobile_channel](
	[id] [varchar](32) NOT NULL,
	[channelid] [varchar](32) NOT NULL,
	[advertisemen_listid] [varchar](32) NOT NULL,
 CONSTRAINT [cm_advertisemen_list_and_mobile_channel_PK_cm_advertisemen_list] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list_and_mobile_channel', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'频道ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list_and_mobile_channel', @level2type=N'COLUMN',@level2name=N'channelid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告列表ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list_and_mobile_channel', @level2type=N'COLUMN',@level2name=N'advertisemen_listid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告列表和频道关系表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list_and_mobile_channel'
GO
/****** Object:  Table [dbo].[cm_advertisemen_list]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_advertisemen_list](
	[id] [varchar](32) NOT NULL,
	[title] [varchar](16) NULL,
	[connect_url] [varchar](160) NULL,
	[channelid] [varchar](32) NULL,
	[brief] [varchar](100) NULL,
	[img_url] [varchar](160) NULL,
	[slide_url] [varchar](160) NULL,
	[order_time] [datetime] NULL,
	[is_comment] [tinyint] NULL,
	[create_datetime] [datetime] NULL,
	[create_user] [varchar](16) NULL,
	[issue_user] [varchar](16) NULL,
	[issue_datetime] [datetime] NULL,
	[visit_num] [int] NULL,
	[adverti_status] [int] NULL,
 CONSTRAINT [cm_advertisemen_list_PK_cm_advertisemen_content] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'连接地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'connect_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'频道ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'channelid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'摘要' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'brief'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'img_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表幻灯片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'slide_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'order_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否开启评论' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'is_comment'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'create_datetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'create_user'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'issue_user'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'issue_datetime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'访问量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'visit_num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list', @level2type=N'COLUMN',@level2name=N'adverti_status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'列表页广告' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_list'
GO
/****** Object:  Table [dbo].[cm_advertisemen_content]    Script Date: 10/21/2016 15:10:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cm_advertisemen_content](
	[ID] [varchar](32) NOT NULL,
	[is_inside] [tinyint] NULL,
	[img_url] [varchar](160) NULL,
	[connect_url] [varchar](160) NULL,
	[advertisement_code] [varchar](3000) NULL,
	[createdtime] [datetime] NULL,
 CONSTRAINT [cm_advertisemen_content_PK_] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是内置广告' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_content', @level2type=N'COLUMN',@level2name=N'is_inside'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告图片地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_content', @level2type=N'COLUMN',@level2name=N'img_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告连接地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_content', @level2type=N'COLUMN',@level2name=N'connect_url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'广告广告代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_content', @level2type=N'COLUMN',@level2name=N'advertisement_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容页广告' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'cm_advertisemen_content'
GO
/****** Object:  Default [DF__wechat_us__isUpd__4EDDB18F]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_user] ADD  DEFAULT ((1)) FOR [isUpdate]
GO
/****** Object:  Default [DF__wechat_us__fansC__4FD1D5C8]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_user] ADD  DEFAULT ((0)) FOR [fansCount]
GO
/****** Object:  Default [DF__wechat_us__isSta__50C5FA01]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_user] ADD  DEFAULT ((1)) FOR [isStartingUsingCoupons]
GO
/****** Object:  Default [DF__wechat_us__isSho__51BA1E3A]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_user] ADD  DEFAULT ((1)) FOR [isShowNearbyNews]
GO
/****** Object:  Default [DF__wechat_us__isOpe__52AE4273]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_user] ADD  DEFAULT ((1)) FOR [isOpenChat]
GO
/****** Object:  Default [DF__wechat_push__id__4C0144E4]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_push] ADD  DEFAULT ('') FOR [id]
GO
/****** Object:  Default [DF__wechat_bu__isSho__4183B671]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[wechat_button] ADD  DEFAULT ((1)) FOR [isShow]
GO
/****** Object:  Default [DF__t_class__id__3AD6B8E2]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[t_class] ADD  DEFAULT ('') FOR [id]
GO
/****** Object:  Default [DF__cms_workfl__step__361203C5]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[cms_workflow_step] ADD  DEFAULT ((1)) FOR [step]
GO
/****** Object:  Default [DF__cms_workf__steps__314D4EA8]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[cms_workflow] ADD  DEFAULT ((0)) FOR [steps]
GO
/****** Object:  Default [DF__cms_video__times__1F2E9E6D]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[cms_video_sources] ADD  DEFAULT ((0)) FOR [timesize]
GO
/****** Object:  Default [DF__cms_surve__isChe__056ECC6A]    Script Date: 10/21/2016 15:10:46 ******/
ALTER TABLE [dbo].[cms_survey_option] ADD  DEFAULT ((0)) FOR [isCheck]
GO
/****** Object:  Default [DF__cms_special__id__7720AD13]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_special] ADD  DEFAULT ('') FOR [id]
GO
/****** Object:  Default [DF__cms_sourc__count__73501C2F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_source] ADD  DEFAULT ((0)) FOR [count]
GO
/****** Object:  Default [DF__cms_source__sort__74444068]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_source] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_site__config__603D47BB]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('0') FOR [config_id]
GO
/****** Object:  Default [DF__cms_site__protoc__61316BF4]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('http://') FOR [protocol]
GO
/****** Object:  Default [DF__cms_site__dynami__6225902D]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('.jhtml') FOR [dynamic_suffix]
GO
/****** Object:  Default [DF__cms_site__static__6319B466]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('.html') FOR [static_suffix]
GO
/****** Object:  Default [DF__cms_site__is_ind__640DD89F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('0') FOR [is_index_to_root]
GO
/****** Object:  Default [DF__cms_site__is_sta__6501FCD8]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('0') FOR [is_static_index]
GO
/****** Object:  Default [DF__cms_site__locale__65F62111]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('zh_CN') FOR [locale_admin]
GO
/****** Object:  Default [DF__cms_site__locale__66EA454A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('zh_CN') FOR [locale_front]
GO
/****** Object:  Default [DF__cms_site__tpl_so__67DE6983]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('default') FOR [tpl_solution]
GO
/****** Object:  Default [DF__cms_site__final___68D28DBC]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ((2)) FOR [final_step]
GO
/****** Object:  Default [DF__cms_site__after___69C6B1F5]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ((2)) FOR [after_check]
GO
/****** Object:  Default [DF__cms_site__is_rel__6ABAD62E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('1') FOR [is_relative_path]
GO
/****** Object:  Default [DF__cms_site__is_rec__6BAEFA67]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('1') FOR [is_recycle_on]
GO
/****** Object:  Default [DF__cms_site__domain__6CA31EA0]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ('') FOR [domain_alias]
GO
/****** Object:  Default [DF__cms_site__is_mas__6D9742D9]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ((0)) FOR [is_master]
GO
/****** Object:  Default [DF__cms_site__ucente__6E8B6712]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_site] ADD  DEFAULT ((0)) FOR [ucenterisOpen]
GO
/****** Object:  Default [DF__cms_secti__conte__5A846E65]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section_data] ADD  DEFAULT ('0') FOR [contentid]
GO
/****** Object:  Default [DF__cms_sectio__sort__5B78929E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section_data] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_sectio__sort__54CB950F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section_class] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_secti__sitei__55BFB948]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section_class] ADD  DEFAULT ('1') FOR [siteid]
GO
/****** Object:  Default [DF__cms_sectio__type__4F12BBB9]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section] ADD  DEFAULT ('auto') FOR [type]
GO
/****** Object:  Default [DF__cms_secti__frequ__5006DFF2]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section] ADD  DEFAULT ((0)) FOR [frequency]
GO
/****** Object:  Default [DF__cms_sectio__sort__50FB042B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_section__num__51EF2864]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_section] ADD  DEFAULT ((0)) FOR [num]
GO
/****** Object:  Default [DF__cms_relat__conte__3FD07829]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_relate_content] ADD  DEFAULT ('0') FOR [content_id]
GO
/****** Object:  Default [DF__cms_relat__relat__40C49C62]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_relate_content] ADD  DEFAULT ('') FOR [relate_title]
GO
/****** Object:  Default [DF__cms_relat__image__41B8C09B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_relate_content] ADD  DEFAULT ('60') FOR [images]
GO
/****** Object:  Default [DF__cms_relat__relat__42ACE4D4]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_relate_content] ADD  DEFAULT ('6') FOR [relate_type]
GO
/****** Object:  Default [DF__cms_pictur__sort__3B0BC30C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_picture_group] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_note__note_p__308E3499]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_note] ADD  DEFAULT ('0') FOR [note_password]
GO
/****** Object:  Default [DF__cms_model__prior__2BC97F7C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_modelmanage] ADD  DEFAULT ((1)) FOR [priority]
GO
/****** Object:  Default [DF__cms_model__data___27F8EE98]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_model_item] ADD  DEFAULT ((1)) FOR [data_type]
GO
/****** Object:  Default [DF__cms_model__prior__28ED12D1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_model_item] ADD  DEFAULT ((1)) FOR [priority]
GO
/****** Object:  Default [DF__cms_model__sort__22401542]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_model] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_model__disab__2334397B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_model] ADD  DEFAULT ((0)) FOR [disabled]
GO
/****** Object:  Default [DF__cms_messag__sort__1E6F845E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_message_board] ADD  DEFAULT ('0') FOR [sort]
GO
/****** Object:  Default [DF__cms_membe__numbe__17C286CF]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_membergroups] ADD  DEFAULT ((0)) FOR [numbers]
GO
/****** Object:  Default [DF__cms_membe__faceI__12FDD1B2]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_member] ADD  DEFAULT ('') FOR [faceImg]
GO
/****** Object:  Default [DF__cms_mail__smtp_p__10216507]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_mail] ADD  DEFAULT ('0') FOR [smtp_password]
GO
/****** Object:  Default [DF__cms_lotte__usenu__0C50D423]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_lottery_record] ADD  DEFAULT ((0)) FOR [usenums]
GO
/****** Object:  Default [DF__cms_lotte__prize__0D44F85C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_lottery_record] ADD  DEFAULT ('') FOR [prize]
GO
/****** Object:  Default [DF__cms_lotte__renam__0880433F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_lottery] ADD  DEFAULT ('') FOR [renamesn]
GO
/****** Object:  Default [DF__cms_lotte__zjpic__09746778]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_lottery] ADD  DEFAULT ('') FOR [zjpic]
GO
/****** Object:  Default [DF__cms_link__site_i__03BB8E22]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_link] ADD  DEFAULT ('0') FOR [site_id]
GO
/****** Object:  Default [DF__cms_funct__funct__793DFFAF]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_function] ADD  DEFAULT ('0') FOR [functionorder]
GO
/****** Object:  Default [DF__cms_frien__prior__76619304]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_friendlink_ctg] ADD  DEFAULT ((10)) FOR [priority]
GO
/****** Object:  Default [DF__cms_frien__views__719CDDE7]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_friendlink] ADD  DEFAULT ((0)) FOR [views]
GO
/****** Object:  Default [DF__cms_frien__is_en__72910220]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_friendlink] ADD  DEFAULT ('1') FOR [is_enabled]
GO
/****** Object:  Default [DF__cms_frien__prior__73852659]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_friendlink] ADD  DEFAULT ((10)) FOR [priority]
GO
/****** Object:  Default [DF__cms_conte__ref_c__4B7734FF]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_tag] ADD  DEFAULT ((1)) FOR [ref_counter]
GO
/****** Object:  Default [DF__cms_conte__versi__498EEC8D]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_extractor_rule] ADD  DEFAULT ((0)) FOR [version]
GO
/****** Object:  Default [DF__cms_conte__paren__31B762FC]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ('0,') FOR [parentids]
GO
/****** Object:  Default [DF__cms_conte__pathi__32AB8735]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ('0,') FOR [pathids]
GO
/****** Object:  Default [DF__cms_conte__iscre__339FAB6E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((1)) FOR [iscreateindex]
GO
/****** Object:  Default [DF__cms_conte__enabl__3493CFA7]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [enablecontribute]
GO
/****** Object:  Default [DF__cms_conte__posts__3587F3E0]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [posts]
GO
/****** Object:  Default [DF__cms_conte__comme__367C1819]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [comments]
GO
/****** Object:  Default [DF__cms_content___pv__37703C52]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [pv]
GO
/****** Object:  Default [DF__cms_conten__sort__3864608B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cms_conte__disab__395884C4]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [disabled]
GO
/****** Object:  Default [DF__cms_conte__htmlc__3A4CA8FD]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((1)) FOR [htmlcreated]
GO
/****** Object:  Default [DF__cms_conte__pages__3B40CD36]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((10)) FOR [pagesize]
GO
/****** Object:  Default [DF__cms_conte__iscon__3C34F16F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [iscontribute]
GO
/****** Object:  Default [DF__cms_conte__iscom__3D2915A8]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [iscomment]
GO
/****** Object:  Default [DF__cms_conte__issho__3E1D39E1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [isshow]
GO
/****** Object:  Default [DF__cms_conte__is_le__3F115E1A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [is_leaf]
GO
/****** Object:  Default [DF__cms_conte__iscat__40058253]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((1)) FOR [iscatend]
GO
/****** Object:  Default [DF__cms_conte__list___40F9A68C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((10)) FOR [list_message]
GO
/****** Object:  Default [DF__cms_conte__isSen__41EDCAC5]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((1)) FOR [isSendMobile]
GO
/****** Object:  Default [DF__cms_conte__is_li__42E1EEFE]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_cat] ADD  DEFAULT ((0)) FOR [is_link_url]
GO
/****** Object:  Default [DF__cms_conte__pagec__2CF2ADDF]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_article] ADD  DEFAULT ((0)) FOR [pagecount]
GO
/****** Object:  Default [DF__cms_conte__saver__2DE6D218]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_article] ADD  DEFAULT ((1)) FOR [saveremoteimage]
GO
/****** Object:  Default [DF__cms_conte__words__2EDAF651]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content_article] ADD  DEFAULT ((0)) FOR [words_count]
GO
/****** Object:  Default [DF__cms_conte__weigh__1EA48E88]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((60)) FOR [weight]
GO
/****** Object:  Default [DF__cms_conte__statu__1F98B2C1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ('10') FOR [status]
GO
/****** Object:  Default [DF__cms_conten__note__208CD6FA]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [note]
GO
/****** Object:  Default [DF__cms_conte__iscon__2180FB33]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ('0') FOR [iscontribute]
GO
/****** Object:  Default [DF__cms_conte__relat__22751F6C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [related]
GO
/****** Object:  Default [DF__cms_content__pv__236943A5]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [pv]
GO
/****** Object:  Default [DF__cms_conte__allow__245D67DE]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ('0') FOR [allowcomment]
GO
/****** Object:  Default [DF__cms_conte__grade__25518C17]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [grade]
GO
/****** Object:  Default [DF__cms_conte__ispos__2645B050]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [isposter]
GO
/****** Object:  Default [DF__cms_conte__is_he__2739D489]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [is_headline]
GO
/****** Object:  Default [DF__cms_conte__is_to__282DF8C2]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_content] ADD  DEFAULT ((0)) FOR [is_top]
GO
/****** Object:  Default [DF__cms_compa__taxis__17F790F9]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_company] ADD  DEFAULT ('0') FOR [taxis]
GO
/****** Object:  Default [DF__cms_compa__isbra__18EBB532]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_company] ADD  DEFAULT ('0') FOR [isbranch]
GO
/****** Object:  Default [DF__cms_cmske__is_di__114A936A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_cmskeyword] ADD  DEFAULT ((0)) FOR [is_disabled]
GO
/****** Object:  Default [DF__cms_classi__name__0B91BA14]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_classify] ADD  DEFAULT ('') FOR [name]
GO
/****** Object:  Default [DF__cms_classif__img__0C85DE4D]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_classify] ADD  DEFAULT ('') FOR [img]
GO
/****** Object:  Default [DF__cms_classif__url__0D7A0286]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_classify] ADD  DEFAULT ('') FOR [url]
GO
/****** Object:  Default [DF__cms_class__statu__0E6E26BF]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_classify] ADD  DEFAULT ('') FOR [status]
GO
/****** Object:  Default [DF__cms_branc__taxis__05D8E0BE]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_branch] ADD  DEFAULT ('0') FOR [taxis]
GO
/****** Object:  Default [DF__cms_branc__isbra__06CD04F7]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_branch] ADD  DEFAULT ('0') FOR [isbranch]
GO
/****** Object:  Default [DF__cms_base___usern__02FC7413]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_base_user] ADD  DEFAULT ('') FOR [username]
GO
/****** Object:  Default [DF__cms_adver__ad_we__75A278F5]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_advertising] ADD  DEFAULT ((1)) FOR [ad_weight]
GO
/****** Object:  Default [DF__cms_adver__displ__76969D2E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_advertising] ADD  DEFAULT ((0)) FOR [display_count]
GO
/****** Object:  Default [DF__cms_adver__click__778AC167]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_advertising] ADD  DEFAULT ((0)) FOR [click_count]
GO
/****** Object:  Default [DF__cms_adver__is_en__787EE5A0]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_advertising] ADD  DEFAULT ('1') FOR [is_enabled]
GO
/****** Object:  Default [DF__cms_acqui__chann__66603565]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_temp] ADD  DEFAULT ('') FOR [channel_url]
GO
/****** Object:  Default [DF__cms_acqui__conte__6754599E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_temp] ADD  DEFAULT ('') FOR [content_url]
GO
/****** Object:  Default [DF__cms_acqui__perce__68487DD7]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_temp] ADD  DEFAULT ((0)) FOR [percents]
GO
/****** Object:  Default [DF__cms_acqui__descr__693CA210]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_temp] ADD  DEFAULT ('') FOR [description]
GO
/****** Object:  Default [DF__cms_acquisi__seq__6A30C649]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_temp] ADD  DEFAULT ((0)) FOR [seq]
GO
/****** Object:  Default [DF__cms_acqui__chann__5FB337D6]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_history] ADD  DEFAULT ('') FOR [channel_url]
GO
/****** Object:  Default [DF__cms_acqui__conte__60A75C0F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_history] ADD  DEFAULT ('') FOR [content_url]
GO
/****** Object:  Default [DF__cms_acqui__descr__619B8048]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_history] ADD  DEFAULT ('') FOR [description]
GO
/****** Object:  Default [DF__cms_acqui__is_bo__5BE2A6F2]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_content] ADD  DEFAULT ((0)) FOR [is_bold]
GO
/****** Object:  Default [DF__cms_acqui__need___5CD6CB2B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition_content] ADD  DEFAULT ((1)) FOR [need_regenerate]
GO
/****** Object:  Default [DF__cms_acqui__site___4CA06362]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('0') FOR [site_id]
GO
/****** Object:  Default [DF__cms_acqui__chann__4D94879B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('0') FOR [channel_id]
GO
/****** Object:  Default [DF__cms_acqui__type___4E88ABD4]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('0') FOR [type_id]
GO
/****** Object:  Default [DF__cms_acqui__user___4F7CD00D]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('0') FOR [user_id]
GO
/****** Object:  Default [DF__cms_acqui__acq_n__5070F446]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('') FOR [acq_name]
GO
/****** Object:  Default [DF__cms_acqui__statu__5165187F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [status]
GO
/****** Object:  Default [DF__cms_acqui__curr___52593CB8]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [curr_num]
GO
/****** Object:  Default [DF__cms_acqui__curr___534D60F1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [curr_item]
GO
/****** Object:  Default [DF__cms_acqui__total__5441852A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [total_item]
GO
/****** Object:  Default [DF__cms_acqui__pause__5535A963]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [pause_time]
GO
/****** Object:  Default [DF__cms_acqui__page___5629CD9C]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('GBK') FOR [page_encoding]
GO
/****** Object:  Default [DF__cms_acqui__queue__571DF1D5]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [queue]
GO
/****** Object:  Default [DF__cms_acqui__repea__5812160E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ('NONE') FOR [repeat_check_type]
GO
/****** Object:  Default [DF__cms_acqui__img_a__59063A47]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cms_acquisition] ADD  DEFAULT ((0)) FOR [img_acqu]
GO
/****** Object:  Default [DF__cm_simple__is_to__398D8EEE]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_simplespecial] ADD  DEFAULT ((0)) FOR [is_top]
GO
/****** Object:  Default [DF__cm_simples__sort__3A81B327]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_simplespecial] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cm_relate_co__id__32E0915F]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_relate_content] ADD  DEFAULT ('') FOR [id]
GO
/****** Object:  Default [DF__cm_relate__conte__33D4B598]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_relate_content] ADD  DEFAULT ('0') FOR [content_id]
GO
/****** Object:  Default [DF__cm_relate__relat__34C8D9D1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_relate_content] ADD  DEFAULT ('') FOR [relate_title]
GO
/****** Object:  Default [DF__cm_relate__image__35BCFE0A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_relate_content] ADD  DEFAULT ('60') FOR [images]
GO
/****** Object:  Default [DF__cm_relate__relat__36B12243]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_relate_content] ADD  DEFAULT ('6') FOR [relate_type]
GO
/****** Object:  Default [DF__cm_picture__sort__2E1BDC42]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_picture_group] ADD  DEFAULT ((0)) FOR [sort]
GO
/****** Object:  Default [DF__cm_mobile__paren__25869641]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_mobile_channel] ADD  DEFAULT ('0,') FOR [parentids]
GO
/****** Object:  Default [DF__cm_mobile__chann__267ABA7A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_mobile_channel] ADD  DEFAULT ((0)) FOR [channel_top]
GO
/****** Object:  Default [DF__cm_mobile__chann__276EDEB3]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_mobile_channel] ADD  DEFAULT ((0)) FOR [channelHot]
GO
/****** Object:  Default [DF__cm_message__id__20C1E124]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_message] ADD  DEFAULT ('0') FOR [id]
GO
/****** Object:  Default [DF__cm_messag__entir__21B6055D]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_message] ADD  DEFAULT ('1') FOR [entironment_type]
GO
/****** Object:  Default [DF__cm_messag__targe__22AA2996]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_message] ADD  DEFAULT ((1)) FOR [target]
GO
/****** Object:  Default [DF__cm_conten__pagec__164452B1]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content_article] ADD  DEFAULT ((0)) FOR [pagecount]
GO
/****** Object:  Default [DF__cm_conten__saver__173876EA]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content_article] ADD  DEFAULT ((1)) FOR [saveremoteimage]
GO
/****** Object:  Default [DF__cm_conten__words__182C9B23]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content_article] ADD  DEFAULT ((0)) FOR [words_count]
GO
/****** Object:  Default [DF__cm_conten__weigh__07F6335A]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((60)) FOR [weight]
GO
/****** Object:  Default [DF__cm_conten__statu__08EA5793]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ('6') FOR [status]
GO
/****** Object:  Default [DF__cm_content__note__09DE7BCC]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [note]
GO
/****** Object:  Default [DF__cm_conten__iscon__0AD2A005]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ('0') FOR [iscontribute]
GO
/****** Object:  Default [DF__cm_conten__relat__0BC6C43E]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [related]
GO
/****** Object:  Default [DF__cm_content__pv__0CBAE877]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [pv]
GO
/****** Object:  Default [DF__cm_conten__allow__0DAF0CB0]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ('0') FOR [allowcomment]
GO
/****** Object:  Default [DF__cm_conten__exten__0EA330E9]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [extendCount]
GO
/****** Object:  Default [DF__cm_conten__iscol__0F975522]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ('0') FOR [iscollect]
GO
/****** Object:  Default [DF__cm_conten__grade__108B795B]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [grade]
GO
/****** Object:  Default [DF__cm_conten__is_to__117F9D94]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [is_top]
GO
/****** Object:  Default [DF__cm_conten__is_to__1273C1CD]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [is_top_pic]
GO
/****** Object:  Default [DF__cm_conten__ispos__1367E606]    Script Date: 10/21/2016 15:10:47 ******/
ALTER TABLE [dbo].[cm_content] ADD  DEFAULT ((0)) FOR [isposter]
GO
