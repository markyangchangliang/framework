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
  and table_name like 'pat_%'
  and column_name = 'operator'
union
-- 操作日期
select 2 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' change ',column_name,' created_datetime ',' datetime ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''','创建日期时间',''';') update_sql
from information_schema.`COLUMNS`
where table_schema = 'spring-cloud-framework'
  and table_name like 'pat_%'
  and column_name = 'update_date'
union
-- 增加最后修改人id
select 3 seq,table_name t_name,'' c_name,concat('alter table ',table_name,' add last_modified_by varchar(128) comment ''','最后修改人ID',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'pat_%'
union
-- 最后修改日期时间
select 4 seq,table_name,'' column_name,concat('alter table ',table_name,' add last_modified_datetime datetime comment ''','最后修改日期时间',''';')  update_sql
from information_schema.tables
where table_schema = 'spring-cloud-framework'
  and table_name like 'pat_%'
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
  and table_name like 'pat_%'
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
  and table_name like 'pat_%'
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
  and table_name like 'pat_%'
  and locate('__',column_name) > 0;


-- ------------------------------------------------------------------------------------------------------------------------------------------------------
-- 第三步，手工修改个别字段
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE pat_online_diagnosis
    MODIFY COLUMN `fee` decimal(8, 2) DEFAULT NULL COMMENT '费用' AFTER `register_id`;
ALTER TABLE pat_address
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `is_default`,
    ADD COLUMN `created_datetime` datetime COMMENT '创建日期时间' AFTER `created_by`;
ALTER TABLE pat_clinic_pay
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `status`,
    ADD COLUMN `created_datetime` datetime COMMENT '创建日期时间' AFTER `created_by`;
ALTER TABLE pat_service_reservation
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `evaluate_content`,
    ADD COLUMN `created_datetime` datetime COMMENT '创建日期时间' AFTER `created_by`;
ALTER TABLE pat_patient
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `last_login_date`,
    ADD COLUMN `created_datetime` datetime COMMENT '创建日期时间' AFTER `created_by`;
ALTER TABLE pat_wx_mini_app_message
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `post_date`,
    ADD COLUMN `created_datetime` datetime COMMENT '创建日期时间' AFTER `created_by`;
ALTER TABLE pat_wx_mp_user
    ADD COLUMN `created_by` varchar(128) COMMENT '创建ID' AFTER `nickname`;
alter table pat_wx_mp_user modify id varchar(128) comment '编号';

-- 修改患者表字段
alter table pat_patient change weixin wx_open_id varchar(128) null comment '微信OpenId';
alter table pat_patient change union_id wx_union_id varchar(128) null comment '开放平台标识';
alter table pat_patient change nickname wx_nickname varchar(256) null comment '微信昵称';
alter table pat_patient add mobile_phone varchar(16) null comment '手机号' after ali_union_id;
-- 患者表新增字段
alter table pat_patient
    add ali_nickname varchar(256) null comment '支付宝昵称' after wx_nickname;
alter table pat_patient
    add ali_open_id varchar(128) null comment '支付宝OpenId' after wx_open_id;
alter table pat_patient
    add ali_union_id varchar(128) null comment '支付宝UnionId' after wx_union_id;
-- 更改表名
rename table pat_user to pat_patient;
rename table pat_user_family_member to pat_patient_family_member;
-- 更改患者家庭成员关联表
alter table pat_patient_family_member change user_id patient_id varchar(128) not null comment '患者用户编号';
-- 患者地址表
alter table pat_address change user_id patient_id varchar(128) not null comment '用户编号';
alter table pat_address change mphone mobile_phone varchar(32) not null comment '手机号码';
-- 临床支付表
alter table pat_clinic_pay change user_id patient_id varchar(128) null comment '患者用户编号';
-- 家庭医生表
alter table pat_family_doctor change user_id patient_id varchar(128) not null comment '患者用户编号';
-- 家庭成员表
alter table pat_family_member change mphone mobile_phone varchar(32) null comment '联系电话';
-- 患者项目预约表
alter table pat_item_reservation change obj_id business_id varchar(128) null comment '业务编号';
alter table pat_item_reservation change reser_org_id reservation_org_id bigint not null comment '预约机构编号';
alter table pat_item_reservation change user_id patient_id varchar(128) null comment '患者用户编号';
-- 互联网诊断表
alter table pat_online_diagnosis change user_id patient_id varchar(128) not null comment '患者用户编号';
-- 患者订单表
alter table pat_order change user_id patient_id varchar(128) not null comment '用户编号';
-- 患者挂号表
alter table pat_register change user_id patient_id varchar(128) null comment '患者用户编号';
-- 住院支付表
alter table pat_resident_pay change user_id patient_id varchar(128) null comment '患者用户编号';
-- 服务预约表
alter table pat_service_reservation change user_id patient_id varchar(128) not null comment '患者用户编号';
alter table pat_service_reservation change phone1 alternative_phone varchar(32) null comment '备用联系电话';
-- 购物车表
rename table pat_shopping_card to pat_shopping_cart;
alter table pat_shopping_cart change user_id patient_id varchar(128) not null comment '用户编号';
-- 微信小程序消息
alter table pat_wx_mini_app_message change user_id patient_id varchar(128) not null comment '用户id ';

-- 微信订单表
rename table pay_order to pay_wx_order;
alter table pay_wx_order change spbill_create_ip sp_bill_creation_ip varchar(64) not null comment '终端ip ';

-- 微信公众号
rename table pat_wxpub_user to pat_wx_mp_user;
rename table pat_wxpub_sms_receiver to pat_wx_mp_message_receiver;
rename table pat_wx_sms to pat_wx_mini_app_message;
rename table pat_wx_sms_receiver to pat_wx_mini_app_message_receiver;
alter table pat_wx_mini_app_message_receiver change sm_send_result send_result int null comment '消息发送结果';
alter table pat_wx_mini_app_message_receiver change sm_send_errmsg send_error_msg varchar(256) null comment '消息发送错误消息';
alter table pat_wx_mp_message_receiver change sm_send_result send_result int null comment '消息发送结果';
alter table pat_wx_mp_message_receiver change sm_send_errmsg send_error_msg varchar(256) null comment '消息发送错误消息';

drop table pat_payment;

-- 增加挂号、门诊缴费、住院缴费支付编号
alter table pat_resident_pay add payment_id varchar(128) null comment '支付编号';
alter table pat_clinic_pay add payment_id varchar(128) null comment '支付编号';
alter table pat_register add payment_id varchar(128) null comment '支付编号';

-- 操作日志表名
rename table pat_item_reservation_oplog to pat_item_reservation_operation_log;

alter table pat_clinic_pay modify fee decimal(8,2) not null comment '费用';
alter table pat_resident_pay modify fee decimal(8,2) not null comment '费用';
alter table pat_service_reservation_item modify fee decimal(8,2) not null comment '项目费用';
alter table pat_patient change reg_date register_date datetime null comment '注册日期';

-- 修改门诊支付为处方支付
rename table pat_clinic_pay to pat_prescription_pay;
update sys_field_dict set table_name = 'pat_prescription_pay' where table_name = 'pat_clinic_pay';