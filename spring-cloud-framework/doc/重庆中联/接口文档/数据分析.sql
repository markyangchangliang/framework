--检查检验项目
select distinct a.类别,a.id,a.名称
  from 诊疗项目目录 a, 病历单据应用 b, 病历文件列表 c
 where a.id = b.诊疗项目id
   and b.病历文件id = c.id
   and c.种类 = '7'
   and a.类别 in ('C', 'D')
   and c.通用 in( '1','2');

--检验记录表
SELECT * FROM 检验报告记录

--检查记录表
SELECT * FROM 影像报告记录

select * from all_tables where owner = 'ZLHIS' and table_name like '%检查记录%'