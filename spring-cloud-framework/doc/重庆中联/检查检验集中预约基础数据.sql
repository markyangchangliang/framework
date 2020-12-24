-- 诊室信息
create or replace view v_rs_room
as
select nvl(b.站点, 0) org_code,
       b.id his_dept_id,
       b.名称 his_dept_name,
       b.位置 his_dept_location
from 病人医嘱记录 a, 部门表 b
where a.诊疗类别 in ('C', 'D')
  and a.相关id is null
  and a.执行科室id = b.id
group by b.站点, b.id, b.名称,b.位置;

-- 项目信息
create or replace view v_rs_item
as
select distinct nvl(a.站点,0) org_code, d.名称 item_type, a.id item_id, a.名称 item_name
from 诊疗项目目录 a,
     病历单据应用 b,
     病历文件列表 c,
     诊疗项目类别 d
where a.id = b.诊疗项目id
  and b.病历文件id = c.id
  and c.种类 = '7'
  and a.类别 = d.编码
  and a.类别 in ('C', 'D')
  and c.通用 in ('1', '2');

-- 设备信息
create or replace view v_rs_equipment
as
select nvl(b.站点, 0) org_code, b.id, b.名称 name
from 病人医嘱记录 a, 部门表 b
where a.诊疗类别 in ('C', 'D')
  and a.相关id is null
  and a.执行科室id = b.id
group by b.站点, b.id, b.名称;

-- 设备项目对应关系
create or replace view v_rs_equipment_item
as
select nvl(b.站点, 0) org_code,
       b.id equipment_id,
       b.名称 equipment_name,
       c.id item_id,
       c.名称 item_name
from 病人医嘱记录 a, 部门表 b, 诊疗项目目录 c
where a.诊疗类别 in ('C', 'D')
  and a.相关id is null
  and a.执行科室id = b.id
  and a.诊疗项目id = c.id
group by b.站点, b.id, b.名称, c.id, c.名称;

-- 设备诊室对应关系
create or replace view v_rs_equipment_room
as
select nvl(b.站点, 0) org_code,
       b.id equipment_id,
       b.名称 equipment_name,
       b.id room_id,
       b.名称 room_name
from 病人医嘱记录 a, 部门表 b
where a.诊疗类别 in ('C', 'D')
  and a.相关id is null
  and a.执行科室id = b.id
group by b.站点, b.id, b.名称;

-- 设备项目人员对应关系
create or replace view v_rs_equipment_item_worker
as
select distinct a.org_id,
                a.equipment_id,
                a.equipment_name,
                listagg(a.item_id,',') within group(order by a.item_id) item_id,
                listagg(a.item_name,',') within group(order by a.item_id) item_name,
                a.worker_id,
                5  operation_time
from (select nvl(c.站点, 0) org_id,
             a.挂号id register_id,
             a.实际票号 bill_no,
             c.id equipment_id,
             c.名称 equipment_name,
             b.id || '-' || count(*) item_id,
             b.名称 || '(' || count(*) || ')' item_name,
             decode(a.收据费目,'化验费','检验费',a.收据费目) item_type,
             d.id worker_id
      from 门诊费用记录 a, 收费细目 b, 部门表 c, 人员表 d
      where a.收费细目id = b.id
        and a.执行部门id = c.id
        and a.执行人 = d.姓名
        and (a.收据费目 in ('检查费', '检验费','化验费') or
             c.名称 in ('检验科', '生殖中心', '妇产科门诊'))
        and a.执行时间 is not null
        and a.执行人 is not null
        and a.挂号id is not null
      group by c.站点,
               a.挂号id,
               a.实际票号,
               c.id,
               c.名称,
               b.id,
               b.名称,
               a.收据费目,
               d.id) a
group by a.org_id, a.register_id,a.bill_no,a.equipment_id,a.equipment_name,a.worker_id;

-- 测试数据
select chr(39) || no || chr(39) || ','
from 门诊费用记录
where 收费类别 in ('检查费', '检验费','化验费')
  and 发生时间 between
    to_date('2018-05-15 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and
    to_date('2018-05-15 23:59:59', 'yyyy-mm-dd hh24:mi:ss')
  and 收费细目id not in (select item_id from v_rs_equipment_item)
group by no