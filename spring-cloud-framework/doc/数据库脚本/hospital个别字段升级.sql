-- blog 相关升级
alter table blog_article change catclog_id category_id varchar(128) not null comment '分类编号';
alter table blog_article change pub_date publish_date datetime not null comment '发布日期';
rename table blog_catclog to blog_category;
alter table blog_article_comment
    add created_by varchar(128) null comment '创建人' after reply_date;
alter table blog_article_comment
    add created_datetime datetime null comment '创建时间' after created_by;
alter table blog_course_task change obj_id course_id varchar(128) not null comment '课程编号';
alter table blog_course_work change intro introduction varchar(4096) null comment '详细说明';
alter table blog_room change intro introduction text null comment '工作室简介';
alter table blog_course_work modify introduction text null comment '详细说明';
alter table pfse_survey_answer modify business_type char null comment '业务类型';
alter table his_outpatient add patient_id varchar(128) null comment '患者编号' after transfer_date;



