-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第一步，升级字段命名，类型，增加最后修改人，最后修改时间字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 操作员
select 1 seq,table_name t_name,'' c_name, concat('alter table ',table_name,' change ',column_name,' created_by ',' bigint ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''','创建人ID',''';') update_sql
  from information_schema.`COLUMNS`
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
	 and column_name = 'operator'
union
-- 操作员
select 2 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' change ',column_name,' created_datetime ',' datetime ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''','创建日期时间',''';') update_sql
  from information_schema.`COLUMNS`
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
	 and column_name = 'update_date'
union
-- 增加最后修改人id
select 3 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' add last_modified_by bigint comment ''','最后修改人ID',''';')  update_sql
  from information_schema.tables
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
union
-- 最后修改日期时间
select 4 seq,table_name,'' column_name,concat('alter table ',table_name,' add last_modified_datetime datetime comment ''','最后修改日期时间',''';')  update_sql
  from information_schema.tables
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
union
-- 修正id
select 5 seq,table_name t_name,column_name c_name,concat('alter table ',
	table_name,
	' change ',
	column_name,
	' ',
	substring(column_name,1,length(column_name) - 2) ,
	'_id ',
	column_type,
	case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''',column_comment,''';') update_sql
  from information_schema.`COLUMNS`
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
   and column_name like '%id'
	 and column_name <> 'id'
union
-- 修改id长度为2的倍数
select 6 seq,table_name t_name,column_name c_name,concat('alter table ',
	table_name,
	' modify ',
	column_name,
	' varchar(',
			case
				when character_maximum_length = 10 then ' 16 '
				when character_maximum_length = 20 then ' 32 '
				when character_maximum_length = 30 then ' 32 '
				when character_maximum_length = 50 then ' 64 '
				when character_maximum_length = 100 then ' 128 '
				when character_maximum_length = 200 then ' 256 '
				when character_maximum_length = 255 then ' 256 '
				when character_maximum_length = 400 then ' 512 '
				when character_maximum_length = 500 then ' 512 '
				when character_maximum_length = 1000 then ' 1024 '
				when character_maximum_length = 2000 then ' 2048 '
				when character_maximum_length = 4000 then ' 4096 '
				when character_maximum_length = 5000 then ' 4096 '
				else ''
			end,
	')',
	case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''',column_comment,''';') update_sql
  from information_schema.`COLUMNS`  a
 where table_schema = 'spring-cloud-framework'
   and table_name like 'sys_%'
   and data_type = 'varchar'
	 and character_maximum_length in (10,20,30,50,100,200,255,400,500,1000,2000,4000,5000)
 order by seq,t_name,c_name;

-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第二步，修改字段中两个下划线的问题
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 修正中间有两个下划线的字段
select 5 seq,table_name t_name,column_name c_name,concat('alter table ',
                                                         table_name,
                                                         ' change ',
                                                         column_name,
                                                         ' ',
                                                         replace(column_name,'__','_') ,
                                                         ' ',
                                                         column_type,
                                                         case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''',column_comment,''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'sys_%'
  and locate('__',column_name) > 0;

-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第三步，手工修改个别字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 修改表名
rename table sys_worker_diagnose_fee to sys_worker_diagnose_fee;
-- 修改org表的简写字段名称
alter table sys_org change abb_name abbr_name varchar(128) null comment '简称';
alter table sys_org change submch_id sub_merchant_id varchar(64) null comment '商户编号';
alter table sys_org change qywx_dept_id wx_cp_dept_id int null comment '企业微信部门编号';
alter table sys_org change qywx_trans_status wx_cp_sync_status char null comment '企业微信同步状态';
alter table sys_org change qywx_trans_msg wx_cp_sync_msg varchar(512) null comment '企业微信同步消息';
alter table sys_org change exchanger_url exchange_url varchar(256) null comment '数据交换接口地址';
alter table sys_org change exchanger_secret exchange_secret varchar(128) null comment '数据交换接口密钥';
alter table sys_org change sub_merchant_id config json null comment '机构配置';
alter table sys_org add longitude decimal(9,6) null comment '经度' after combine_order;
alter table sys_org add latitude decimal(9,6) null comment '纬度' after longitude;
alter table sys_org modify created_by bigint null comment '创建人ID' after latitude;
alter table sys_org modify created_datetime datetime null comment '创建日期时间' after created_by;
-- 修改user表中的用户名字段
alter table sys_user change name username varchar(128) null comment '用户名';
-- 修改user表中的密码字段
alter table sys_user change pwd password varchar(64) null comment '密码';
-- 修改menu表中菜单类型
alter table sys_menu modify type char null comment '类型   0：目录   1：菜单   2：按钮';
alter table sys_menu change path uri varchar(128) null comment '路径';
alter table sys_menu change url permitted_ant_uris varchar(256) null comment '权限允许的Ant风格的Uri';
alter table sys_menu drop column component;
alter table sys_menu drop column perms;
alter table sys_worker change phone tel_phone varchar(32) null comment '联系电话';
alter table sys_worker change mphone mobile_phone varchar(32) null comment '移动电话';
alter table sys_worker change weixin wx varchar(128) null comment '微信';
alter table sys_worker change qywx_user_id wx_cp_user_id varchar(64) null comment '企业微信成员userid ';
alter table sys_worker change qual_status qualified_status char null comment '认证状态';
alter table sys_worker change qual_date qualified_date datetime null comment '认证日期';
-- 为用户表添加用户名索引，因为需要根据用户名加载用户信息
create index index_username on sys_user (username);

-- 更改系统选项表
drop index i_pk_sys_system_option on sys_system_option;
create unique index i_uk_sys_system_option
    on sys_system_option (value, `key`);
alter table sys_system_option add column id bigint not null auto_increment primary key comment '主键ID';
alter table sys_system_option modify app_id varchar(128) not null comment '应用ID' after id;

-- 修改菜单表
alter table sys_menu modify app_id varchar(64) not null comment '应用编号';
alter table sys_menu modify parent_id bigint not null comment '父菜单ID，一级菜单为0';
alter table sys_menu modify title varchar(128) not null comment '标题';
alter table sys_menu drop column name;
alter table sys_menu modify type char not null comment '类型   0：目录   1：菜单   2：按钮';
update sys_menu set target = '_self' where target is null;
alter table sys_menu modify target varchar(32) comment '打开目标' not null ;
alter table sys_menu modify seq varchar(32) not null comment '排序';
alter table sys_menu modify status char (1) comment '状态' default 'a' not null;

-- 修改sys_area表
alter table sys_area change fullname full_name varchar(512) not null comment '全名';
-- 机构增加问诊提示
alter table sys_org add column online_diagnose_tip varchar(1024) comment '在线问诊提示';
	 