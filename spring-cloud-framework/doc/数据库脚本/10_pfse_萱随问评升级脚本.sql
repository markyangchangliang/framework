alter table pfse_survey_answer add business_type varchar(1) null comment '业务类型';
alter table pfse_survey_answer add business_id varchar(128) null comment '业务编号';
ALTER TABLE pfse_survey_answer ADD COLUMN `patient_id` varchar(128) NOT NULL COMMENT '患者编号';
drop index i_pfse_out_record$medical_cardid on pfse_out_record;
alter table pfse_out_record drop column medical_card_id;
drop index i_pfse_in_record$medical_cardid on pfse_in_record;
alter table pfse_in_record drop column medical_card_id;

