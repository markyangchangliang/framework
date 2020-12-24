package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理(Dept)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 11:58:59
 */
public interface DeptRepository extends FrameworkRepository<Dept> {


    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    List<DictDto> getDepts(@Param("orgId") String orgId,
                           @Param("type") String type);
}