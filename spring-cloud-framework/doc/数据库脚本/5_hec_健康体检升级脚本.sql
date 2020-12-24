-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第一步，升级字段命名，类型，增加最后修改人，最后修改时间字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 操作员
select 1 seq,
       table_name t_name,
       '' c_name,
       concat('alter table ',
              table_name,
              ' change ',
              column_name,
              ' created_by ',
              ' varchar(128) ',
              case when is_nullable = 'NO' then ' not null ' else '' end,
              ' comment ''',
              '创建人ID',''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'hec_%'
  and column_name = 'operator'
union
-- 操作日期
select 2 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' change ',column_name,' created_datetime ',' datetime ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''','创建日期时间',''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'hec_%'
  and column_name = 'update_date'
union
-- 增加最后修改人id
select 3 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' add last_modified_by varchar(128) comment ''','最后修改人ID',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'hec_%'
union
-- 最后修改日期时间
select 4 seq,table_name,'' column_name,concat('alter table ',table_name,' add last_modified_datetime datetime comment ''','最后修改日期时间',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'hec_%'
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
  and table_name like 'hec_%'
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
  and table_name like 'hec_%'
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
  and table_name like 'hec_%'
  and locate('__',column_name) > 0;


-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第三步，手工修改个别字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------

-- 体检套餐表
alter table hec_check_combo modify price decimal(8,2) not null comment '价格';
alter table hec_check_combo modify view_num bigint not null comment '浏览量';
alter table hec_check_combo change is_need required char not null comment '是否必选';
-- 体检项目表
alter table hec_check_combo_item modify price decimal(8,2) not null comment '价格';
-- 体检计划任务
alter table hec_check_plan_task change package_begin_date combo_begin_date char(10) null comment '预约套餐开始时间';
alter table hec_check_plan_task change package_end_date combo_end_date char(10) null comment '预约套餐结束时间';
-- 体检计划
alter table hec_check_plan change appoint specified char null comment '指定套餐';
-- 单位计划
alter table hec_check_plan_client change client_id unit_id varchar(128) not null comment '体检单位';
rename table hec_check_plan_client to hec_check_plan_unit;
-- 计划任务单位
alter table hec_check_plan_task_client change group_client_id group_unit_id varchar(256) not null comment '体检单位编号';
rename table hec_check_plan_task_client to hec_check_plan_task_unit;
-- 体检任务职员
alter table hec_check_plan_task_worker change check_plan_task_client_id check_plan_task_unit_id varchar(128) not null comment '体检任务单位编号';
alter table hec_check_plan_task_worker change trans_flag sync_flag char not null comment '同步标记';
alter table hec_check_plan_task_worker change trans_date sync_date date null comment '同步日期';
-- 体检结果
alter table hec_check_result
    add created_by varchar(128) null comment '创建人ID' after item_seq;
alter table hec_check_result change regcode reg_code varchar(32) not null comment '体检号';
alter table hec_check_result change regname reg_name varchar(64) not null comment '姓名';
alter table hec_check_result change combocode combo_code varchar(32) not null comment '组合项目编号';
alter table hec_check_result change comboname combo_name varchar(128) not null comment '组合项目名称';
alter table hec_check_result change resultmemo result_memo varchar(2048) null comment '检查结果';
alter table hec_check_result change isconclusion concluded varchar(16) null comment '是否总结';
alter table hec_check_result change quit quited char not null comment '是否弃检';
alter table hec_check_result change quitcause quit_cause varchar(300) null comment '弃检原因';
alter table hec_check_result change itemcode item_code varchar(32) not null comment '项目编号';
alter table hec_check_result change itemname item_name varchar(128) not null comment '项目名称';
alter table hec_check_result change reslutvalue reslut_value varchar(2048) null comment '结果值';
alter table hec_check_result change resultflag result_flag char null comment '结果标志';
alter table hec_check_result change resultunit result_unit varchar(32) null comment '结果单位';
alter table hec_check_result change upperrange upper_range varchar(14) null comment '最大值';
alter table hec_check_result change lowerrange lower_range varchar(14) null comment '最小值';
alter table hec_check_result change checkdoc check_doctor varchar(64) null comment '检查医生';
alter table hec_check_result change checktime check_time datetime null comment '检查时间';
alter table hec_check_result change auditdoc audit_doctor varchar(64) null comment '审核医生';
alter table hec_check_result change updatetime audit_time datetime null comment '审核时间';
alter table hec_check_result change itemtypedisorder item_type_seq int null comment '项目类型序号';
alter table hec_check_result change combodisorder combo_seq int null comment '组合项目序号';
alter table hec_check_result change itemdisorder item_seq int null comment '项目序号';
-- 职员项目
alter table hec_check_worker_combo modify total_fee decimal(8,2) null comment '总费用';
-- 体检单位
alter table hec_client change manage_name manager_name varchar(256) not null comment '管理员名称';
alter table hec_client change manage_phone manager_phone varchar(32) not null comment '管理员电话';
alter table hec_client change manage_weixin manager_wx varchar(128) null comment '管理员微信';
rename table hec_client to hec_check_unit;
-- 体检职级
alter table hec_level modify min_fee decimal(8,2) not null comment '最小价格';
alter table hec_level modify fee decimal(8,2) not null comment '价格';
rename table hec_level to hec_check_level;
-- 体检职员
alter table hec_worker change client_id unit_id varchar(128) not null comment '体检单位编号';
rename table hec_worker to hec_check_worker;

alter table hec_check_combo change introduce introduction text null comment '介绍';
alter table hec_check_combo_item change introduce introduction text null comment '简介';
alter table hec_check_plan_task_worker change reg_code physical_exam_code varchar(128) null comment '体检号';
alter table hec_check_result change reg_code physical_exam_code varchar(32) not null comment '体检号';
alter table hec_check_result change reg_name name varchar(64) not null comment '姓名';
alter table hec_check_result change reslut_value result_value varchar(2048) null comment '结果值';
alter table hec_check_unit change pwd password varchar(32) not null comment '管理员密码';

