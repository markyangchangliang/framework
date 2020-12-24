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
              ' ',
              case when column_type = 'varchar(100)' then 'varchar(128)' else column_type end ,
              ' ',
              case when is_nullable = 'NO' then ' not null ' else '' end,
              ' comment ''',
              '创建人ID',''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'his_%'
  and column_name = 'operator'
union
-- 操作日期
select 2 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' change ',column_name,' created_datetime ',' datetime ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''','创建日期时间',''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'his_%'
  and column_name = 'update_date'
union
-- 增加最后修改人id
select 3 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' add last_modified_by bigint comment ''','最后修改人ID',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'his_%'
union
-- 最后修改日期时间
select 4 seq,table_name,'' column_name,concat('alter table ',table_name,' add last_modified_datetime datetime comment ''','最后修改日期时间',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'his_%'
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
  and table_name like 'his_%'
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
  and table_name like 'his_%'
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
  and table_name like 'his_%'
  and locate('__',column_name) > 0;


-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第三步，手工修改个别字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
alter table his_patient change trans_date transfer_date datetime null comment '传输日期';
alter table his_inpatient change trans_date transfer_date datetime null comment '传输日期';
alter table his_material modify name varchar(256) not null comment '名称';
alter table his_material modify specification varchar(64) null comment '地址';
alter table his_material modify unit varchar(32) null comment '单位';
alter table his_material change authorizeNo authorization_no varchar(128) null comment 'authorizeNo';
alter table his_material change producerName producer_name varchar(256) null comment '生产厂家';
alter table his_material modify price decimal(8,2) null comment '状态';
alter table his_material modify attribute varchar(64) null comment '操作日期';
alter table his_outpatient change trans_date transfer_date datetime null comment '传输日期';

alter table his_outpatient add (seq	int		comment	'序号',
                                call_seq	int		comment	'叫号序号',
                                begin_time	time		comment	'开始时间',
                                end_time	time		comment	'结束时间',
                                call_status	char(1)	not null	comment	'叫号状态'
    );

alter table his_inpatient add resident_seq	int		comment	'住院次数';
-- 修改HIS开头的表，科室、患者、医生字段加his_前缀
alter table his_inpatient change dept_id his_dept_id varchar(32)  comment '科室编号';
alter table his_inpatient change patient_id his_patient_id varchar(32)  not null  comment '患者编号';
alter table his_inpatient change patient_id his_patient_id varchar(32)  not null  comment '患者编号';
alter table his_outpatient change dept_id his_dept_id varchar(32)  comment '挂号科室';
alter table his_outpatient change doctor_id his_doctor_id varchar(32)  comment '挂号医生';
alter table his_outpatient change patient_id his_patient_id varchar(32)  not null  comment '病人id ';
alter table his_schedule_plan change dept_id his_dept_id varchar(32)  not null  comment '科室编号';
alter table his_inpatient change user_id patient_id varchar(128) null comment '用户id';

alter table his_outpatient add (his_dept_name varchar(128) not null comment '科室名称', his_doctor_name varchar(128) not null comment '医生姓名');