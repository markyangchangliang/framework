--ZLHIS 数据库密码 his 客户端密码 aqa
--YHIS  数据库密码 142 客户端密码 aqa

select 'select * from ' || owner || '.' || table_name || ';', a.*
from all_tables a
where table_name like '%健康卡%';

--病人基本信息
select * from 病人信息 where 病人id = '214971';

--住院病人信息
select * from YHIS.病案主页 where 病人id = '197878' and 出院日期 is null;

select 入院方式 from YHIS.病案主页 group by 入院方式;
--查询报告
select  * from 检验报告记录;
--查询处方信息  记录状态 0=未缴费、1=已缴费、2=退费记录、3=退费原始记录
select * from 门诊费用记录


select * from 病人医嘱记录 where 诊疗类别 in('D')  and 医嘱状态 = 8 and 执行终止时间 is not null and 挂号单 is not null order by 开嘱时间 desc;
-- 住院检查报告
select * from 病人医嘱报告 where 医嘱id in(
    select id from 病人医嘱记录 where 诊疗类别 in('D')  and 医嘱状态 = 8 and 执行终止时间 is not null and 病人id =5314271 and 主页id = 2
);
-- 门诊检查报告
select * from 病人医嘱报告 where 医嘱id in(
    select id from 病人医嘱记录 where 诊疗类别 in('D')  and 医嘱状态 = 8 and 执行终止时间 is not null and 病人id =5391184 and 挂号单 = 'U0033620'
);
-- 住院检验报告
select * from 检验标本记录 where 病人id = 5314271 and 主页id = 2 and 审核时间 is not null;
-- 门诊检验报告
select * from 检验标本记录 where 病人id = 5348965 and 挂号单 = 'U0033932' and 审核时间 is not null;
-- 检验标本id 为检验标本记录表的id
select * from 检验普通结果 where 检验标本id = 14923341;


select * from 电子病历记录 where id = 9164572

-- 项目科室对应（检验项目）
select distinct c.id dept_id, c.名称 dept_name, b.id item_id, b.名称 item_name
from 收费执行科室 a, 收费细目 b, 部门表 c
where a.收费细目id = b.id
  and a.执行科室id = c.id
  and b.类别 in ('C', 'D')