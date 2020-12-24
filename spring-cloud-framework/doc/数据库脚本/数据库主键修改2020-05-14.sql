-- 查找和sys开头的表有外检关联的biglong字段
SELECT
    concat('alter table ',table_name,' modify column ',column_name,' varchar(128) ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''',column_comment,''';')
FROM
    information_schema.`COLUMNS`
WHERE
        table_schema = 'spring-cloud-framework-new'
  AND table_name not LIKE 'sys_%'
  AND data_type = 'bigint'
  and column_name in (SELECT
                          column_name
                      FROM
                          information_schema.`COLUMNS`
                      WHERE
                              table_schema = 'spring-cloud-framework-new'
                        AND table_name LIKE 'sys_%'
                        AND data_type = 'bigint'
                        and column_name not in ('id','time')
                      group by column_name	)
group by table_name,column_name,is_nullable,column_comment
ORDER BY
    table_name,
    column_name;

-- 查询sys开头的表，含有biglong类型的字段
SELECT
    concat('alter table ',table_name,' modify column ',column_name,' varchar(128) ', case when is_nullable = 'NO' then ' not null ' else '' end, ' comment ''',column_comment,''';')
FROM
    information_schema.`COLUMNS`
WHERE
        table_schema = 'spring-cloud-framework-new'
  AND table_name LIKE 'sys_%'
  AND data_type = 'bigint'
ORDER BY
    table_name,
    column_name;

-- 个别字段修改


alter table sys_worker modify id varchar(128) not null comment '编号';
alter table sys_worker modify created_by varchar(128) null comment '创建人ID';
alter table sys_worker modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table twr_treatment_record modify id varchar(128) not null comment '编号';
alter table twr_apply_file modify id varchar(128) not null comment '编号';
alter table twr_apply modify id varchar(128) not null comment '编号';
alter table sys_worker_diagnose_fee modify id varchar(128) not null comment '编号';
alter table sys_worker_diagnose_fee modify created_by varchar(128) null comment '创建人ID';
alter table sys_worker_diagnose_fee modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_user_role modify id varchar(128) not null comment '主键';
alter table sys_user_role modify role_id varchar(128) not null comment '角色编号';
alter table sys_user_role modify created_by varchar(128) null comment '创建人ID';
alter table sys_user_role modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_task modify id varchar(128) not null;
alter table sys_task modify created_by varchar(128) null comment '创建人ID';
alter table sys_task modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_system_option modify id varchar(128) not null comment '编号';
alter table sys_system_option modify created_by varchar(128) null comment '创建人ID';
alter table sys_system_option modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_rpt_sql_field modify id varchar(128) not null comment '编号';
alter table sys_rpt_sql_field modify rpt_sql_id varchar(128) not null comment '表编号';
alter table sys_rpt_sql_field modify created_by varchar(128) null comment '创建人ID';

alter table sys_rpt_sql_field modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_rpt_sql modify id varchar(128) not null comment '编号';

alter table sys_rpt_sql modify created_by varchar(128) null comment '创建人ID';

alter table sys_rpt_sql modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_role_menu modify id varchar(128) not null;

alter table sys_role_menu modify menu_id varchar(128) null comment '菜单ID';

alter table sys_role_menu modify created_by varchar(128) null comment '创建人ID';

alter table sys_role_menu modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_role_menu modify role_id varchar(128) null comment '角色ID';
alter table sys_role modify id varchar(128) not null;

alter table sys_role modify created_by varchar(128) null comment '创建人ID';

alter table sys_role modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_report_sql_field modify id varchar(128) not null comment '编号';

alter table sys_report_sql_field modify created_by varchar(128) null comment '创建人ID';

alter table sys_report_sql_field modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_report_sql_field modify rpt_sql_id varchar(128) not null comment '表编号';

alter table sys_org modify id varchar(128) not null comment '编号';

alter table sys_org modify parent_id varchar(128) not null comment '父编号';

alter table sys_org modify created_by varchar(128) null comment '创建人ID';

alter table sys_org modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_multi_dept modify id varchar(128) not null comment '编号';

alter table sys_multi_dept modify org_id varchar(128) not null comment '机构编号';

alter table sys_multi_dept modify dept_id varchar(128) not null comment '部门编号';

alter table sys_multi_dept modify worker_id varchar(128) not null comment '职员编号';

alter table sys_multi_dept modify created_by varchar(128) null comment '创建人ID';

alter table sys_multi_dept modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_menu modify id varchar(128) not null ;

alter table sys_menu modify parent_id varchar(128) not null comment '父菜单ID，一级菜单为0';

alter table sys_menu modify created_by varchar(128) null comment '创建人ID';

alter table sys_menu modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_log modify id varchar(128) not null;

alter table sys_log modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_log modify created_by varchar(128) not null comment '创建人ID';

alter table sys_job_log modify created_by varchar(128) null comment '创建人ID';

alter table sys_job_log modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_job modify id varchar(128) not null comment '编号';

alter table sys_job modify created_by varchar(128) null comment '创建人ID';

alter table sys_job modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_item_fee_ratio modify id varchar(128) not null comment '编号';

alter table sys_item_fee_ratio modify dept_id varchar(128) not null comment '部门编号';

alter table sys_item_fee_ratio modify created_by varchar(128) null comment '创建人ID';

alter table sys_item_fee_ratio modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_item_fee_ratio modify org_id varchar(128) not null comment '机构编号';

alter table sys_help modify menu_id varchar(128) not null comment '菜单编号';

alter table sys_help modify id varchar(128) not null comment '主键';

alter table sys_file modify id varchar(128) not null;

alter table sys_file modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_file modify created_by varchar(128) not null comment '创建人ID';
alter table sys_field_dict modify id varchar(128) not null comment '编号';

alter table sys_field_dict modify created_by varchar(128) null comment '创建人ID';

alter table sys_field_dict modify last_modified_by varchar(128) null comment '最后修改人ID';
alter table sys_export_table modify id varchar(128) not null comment '编号';

alter table sys_export_table modify created_by varchar(128) null comment '创建人ID';

alter table sys_export_table modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_export_field modify id varchar(128) not null comment '编号';

alter table sys_export_field modify export_table_id varchar(128) not null comment '表编号';

alter table sys_export_field modify created_by varchar(128) null comment '创建人ID';

alter table sys_export_field modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_dept_worker modify id varchar(128) not null comment '编号';

alter table sys_dept_worker modify dept_id varchar(128) not null comment '部门编号';

alter table sys_dept_worker modify worker_id varchar(128) not null comment '职员编号';

alter table sys_dept_worker modify created_by varchar(128) null comment '创建人ID';

alter table sys_dept_worker modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_dept modify id varchar(128) not null comment '编号';

alter table sys_dept modify parent_id varchar(128) not null comment '父编号';

alter table sys_dept modify org_id varchar(128) not null comment '机构编号';

alter table sys_dept modify created_by varchar(128) null comment '创建人ID';

alter table sys_dept modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_convenience_user_menu modify id varchar(128) not null comment '主键';

alter table sys_convenience_user_menu modify user_id varchar(128) not null comment '用户菜单';

alter table sys_convenience_user_menu modify menu_id varchar(128) not null comment '菜单ID';

alter table sys_convenience_user_menu modify created_by varchar(128) null comment '创建用户ID';

alter table sys_convenience_user_menu modify last_modified_by varchar(128) null comment '最后修改用户ID';

alter table sys_charge_item modify org_id varchar(128) not null comment '机构编号';

alter table sys_charge_item modify created_by varchar(128) null comment '创建人ID';

alter table sys_charge_item modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_audit_log modify id varchar(128) not null comment '主键';

alter table sys_audit_log modify created_by varchar(128) not null comment '创建人ID';

alter table sys_audit_log modify last_modified_by varchar(128) not null comment '最后修改人ID';

alter table sys_area modify created_by varchar(128) null comment '创建人ID';

alter table sys_area modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table sys_area modify id varchar(128) not null comment '主键';

alter table sys_app modify created_by varchar(128) null comment '创建人ID';

alter table sys_app modify last_modified_by varchar(128) null comment '最后修改人ID';

alter table pae_in_record modify id varchar(128) not null comment '编号';

alter table odt_patient_apply modify id varchar(128) not null comment '编号';

alter table odt_patient_apply modify patientid varchar(128) not null comment '患者编号';

alter table odt_file modify id varchar(128) not null comment '编号';
alter table oa_sms_template_yun modify id varchar(128) not null comment '编号';

alter table oa_sms_template_yun modify templateid varchar(128) not null comment '模板编号';

alter table oa_sms_template_yun modify sms_templateid varchar(128) not null comment '短信模板编号';

alter table oa_sms_template modify id varchar(128) not null comment '编号';

alter table oa_sms_setting modify id varchar(128) not null comment '编号';

alter table oa_sms_receiver modify id varchar(128) not null comment '编号';

alter table oa_sms_receiver modify smsid varchar(128) not null comment '短消息编号';

alter table oa_sms_receiver modify workerid varchar(128) not null comment '接收人';

alter table oa_sms_file modify id varchar(128) not null comment '编号';

alter table oa_sms_file modify smsid varchar(128) not null comment '短消息编号';

alter table oa_sms modify id varchar(128) not null comment '编号';

alter table oa_notify_org modify id varchar(128) not null comment '编号';

alter table oa_notify_org modify id varchar(128) not null comment '编号';

alter table oa_meeting_room modify id varchar(128) not null comment '编号';

alter table oa_meeting_file modify id varchar(128) not null comment '编号';

alter table oa_meeting_file modify meetingid varchar(128) not null comment '会议编号';

alter table cdm_disease modify id varchar(128) not null comment '编号';
alter table cdm_disease_form modify id varchar(128) not null comment '编号';
alter table cdm_disease_form modify diseaseid varchar(128) not null comment '编号';
alter table cdm_doctor_record modify id varchar(128) not null comment '编号';
alter table cdm_doctor_record modify family_memberid varchar(128) not null comment '家庭成员编号';
alter table cdm_drug modify id varchar(128) not null comment '编号';
alter table cdm_drug modify drugid varchar(128) null comment '药品编号';
alter table cdm_drug modify diseaseid varchar(128) not null comment '慢病分类编号';
alter table cdm_drug_remind modify id varchar(128) not null comment '编号';
alter table cdm_drug_remind modify family_memberid varchar(128) not null comment '家庭成员编号';
alter table cdm_drug_remind modify userid varchar(128) null comment '用户id';
alter table cdm_patient modify id varchar(128) not null comment '编号';
alter table cdm_patient modify userid varchar(128) null comment '用户编号';
alter table cdm_patient modify family_memberid varchar(128) not null comment '患者编号';
alter table cdm_patient modify diseaseid varchar(128) not null comment '慢病编号';
alter table cdm_patient modify operator varchar(128) null comment '操作员';
alter table cdm_patient_record modify id varchar(128) not null comment '编号';
alter table cdm_patient_record modify family_memberid varchar(128) not null comment '家庭成员编号';
alter table cdm_patient_record modify disease_formid varchar(128) null comment '慢病数据id';
alter table cdm_patient_record modify operator varchar(128) null comment '操作员';
alter table cdm_patient_remind modify id varchar(128) not null comment '编号';
alter table cdm_patient_survey modify id varchar(128) not null comment '编号';
alter table cdm_patient_survey modify family_memberid varchar(128) not null comment '家庭成员编号';
alter table cdm_patient_survey modify surveyid varchar(128) not null comment '调查问卷编号';
alter table cdm_patient_survey modify answerid varchar(128) not null comment '问卷答案编号';
alter table cdm_patient_survey modify operator varchar(128) null comment '操作员';
alter table drug_drugstore modify id varchar(128) not null comment '编号';
alter table drug_evaluate modify id varchar(128) not null comment '编号';
alter table drug_evaluate modify drugid varchar(128) not null comment '商品编号';
alter table drug_evaluate modify operator varchar(128) null comment '操作员';
alter table drug_file modify id varchar(128) not null comment '编号';
alter table drug_file modify drugid varchar(128) not null comment '商品编号';
alter table drug_goods modify id varchar(128) not null comment '编号';
alter table drug_supplier modify id varchar(128) not null comment '编号';
alter table equ_borrow_record modify id varchar(128) not null comment '编号';
alter table equ_borrow_record modify equipmentid varchar(128) not null comment '设备编号';
alter table equ_equipment modify id varchar(128) not null comment '编号';
alter table equ_equipment modify type varchar(128) not null comment '设备类别';
alter table equ_equipment_file modify id varchar(128) not null comment '编号';
alter table equ_equipment_file modify equipmentid varchar(128) not null comment '编号';
alter table equ_logistics_repair modify id varchar(128) not null comment '编号';
alter table equ_logistics_repair modify type varchar(128) not null comment '设备类别';
alter table equ_maintain modify id varchar(128) not null comment '编号';
alter table equ_maintain modify equipmentid varchar(128) not null comment '设备编号';
alter table equ_maintain modify maintain_schemeid varchar(128) not null comment '设备维护方案编号';
alter table equ_maintain_record modify id varchar(128) not null comment '编号';
alter table equ_maintain_record modify maintainid varchar(128) not null comment '设备维护编号';
alter table equ_maintain_record modify maintain_stepid varchar(128) not null comment '设备维护步骤编号';
alter table equ_maintain_scheme modify id varchar(128) not null comment '编号';
alter table equ_maintain_scheme modify type varchar(128) not null comment '类别';
alter table equ_maintain_step modify id varchar(128) not null comment '编号';
alter table equ_maintain_step modify maintain_schemeid varchar(128) not null comment '设备维护方案编号';
alter table equ_repair modify id varchar(128) not null comment '编号';
alter table equ_repair modify equipmentid varchar(128) not null comment '设备编号';
alter table equ_repair_file modify id varchar(128) not null comment '编号';
alter table equ_repair_file modify repairid varchar(128) not null comment '保修编号';
alter table equ_type modify id varchar(128) not null comment '编号';
alter table equ_type modify type varchar(128) not null comment '类型';
alter table equ_type modify repair_workerid varchar(128) null comment '维护人编号';
alter table exam_book modify id varchar(128) not null comment '编号';
alter table exam_knowledge_module modify id varchar(128) not null comment '编号';
alter table exam_knowledge_module modify parentid varchar(128) not null comment '父编号';
alter table exam_knowledge_module modify bookid varchar(128) not null comment '教材编号';
alter table exam_knowledge_module modify manager varchar(128) null comment '管理员';
alter table exam_paper modify id varchar(128) not null comment '编号';
alter table exam_paper modify taskid varchar(128) not null comment '考试科目编号';
alter table exam_paper modify affixid varchar(128) null comment '试卷附件';
alter table exam_paper_question modify id varchar(128) not null comment '编号';
alter table exam_paper_question modify paper_question_typeid varchar(128) not null comment '试卷题型编号';
alter table exam_paper_question modify questionid varchar(128) not null comment '原题编号';
alter table exam_paper_question modify question_itemid varchar(128) not null comment '原题编号';
alter table exam_paper_question_type modify id varchar(128) not null comment '编号';
alter table exam_paper_question_type modify paperid varchar(128) not null comment '考试试卷编号';
alter table exam_question modify id varchar(128) not null comment '编号';
alter table exam_question modify knowledge_moduleid varchar(128) not null comment '编号';
alter table exam_question modify audit_operator varchar(128) null comment '审核操作员';
alter table exam_question_item modify id varchar(128) not null comment '编号';
alter table exam_question_item modify questionid varchar(128) not null comment '双向细目表编号';
alter table exam_task modify id varchar(128) not null comment '编号';
alter table exam_worker modify id varchar(128) not null comment '编号';
alter table exam_worker modify exam_paperid varchar(128) not null comment '考试任务编号';
alter table hec_check_combo modify org_id varchar(128) not null comment '机构编号';
alter table hec_check_plan modify org_id varchar(128) not null comment '机构编号';
alter table hec_check_unit modify id varchar(128) not null comment '编号';
alter table hec_check_unit modify org_id varchar(128) not null comment '机构编号';
alter table hec_check_worker modify dept varchar(128) null comment '所属部门';
alter table his_inpatient modify id varchar(128) not null comment '编号';
alter table his_inpatient modify user_id varchar(128) null comment '用户id';
alter table his_material modify id varchar(128) not null comment '编号';
alter table his_material modify org_id varchar(128) not null comment '机构编号';
alter table his_outpatient modify id varchar(128) not null comment '编号';
alter table his_patient modify id varchar(128) not null comment '编号';
alter table his_schedule_plan modify id varchar(128) not null comment '编号';

























