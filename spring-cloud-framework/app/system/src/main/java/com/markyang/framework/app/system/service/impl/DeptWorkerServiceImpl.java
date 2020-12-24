package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.form.search.DeptWorkerSearchForm;
import com.markyang.framework.app.system.service.DeptWorkerService;
import com.markyang.framework.app.system.repository.DeptWorkerRepository;
import com.markyang.framework.pojo.entity.system.DeptWorker;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门职员(DeptWorker)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-28 13:25:33
 */
@Service
@AllArgsConstructor
public class DeptWorkerServiceImpl extends AbstractSearchableServiceImpl<DeptWorker, DeptWorkerRepository, DeptWorkerSearchForm> implements DeptWorkerService {


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public DeptWorker create() {
        return DeptWorker.builder().build();
    }

    /**
     * 通过部门获取信息
     * @param deptId 部门信息
     * @return 职员信息
     */
    @Override
    public List<DeptWorker> findByDeptId(String deptId) {
        return this.list(Wrappers.<DeptWorker>lambdaQuery().eq(DeptWorker::getDeptId, deptId));
    }

    /**
     * 删除职员部门信息
     * @param workerId 职员编号
     * @return 结果
     */
    @Override
    public boolean deleteByWorkerId(String workerId) {
        return this.remove(Wrappers.<DeptWorker>lambdaQuery().eq(DeptWorker::getWorkerId, workerId));
    }

}