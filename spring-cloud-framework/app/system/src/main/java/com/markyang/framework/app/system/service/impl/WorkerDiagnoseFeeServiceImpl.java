package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.WorkerDiagnoseFeeSearchForm;
import com.markyang.framework.app.system.service.FieldDictService;
import com.markyang.framework.app.system.service.WorkerDiagnoseFeeService;
import com.markyang.framework.app.system.repository.WorkerDiagnoseFeeRepository;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.WorkerDiagnoseFee;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 职员诊察费用(WorkerDiagnoseFee)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class WorkerDiagnoseFeeServiceImpl extends AbstractSearchableServiceImpl<WorkerDiagnoseFee, WorkerDiagnoseFeeRepository, WorkerDiagnoseFeeSearchForm> implements WorkerDiagnoseFeeService {

    private final FieldDictService fieldDictService;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public WorkerDiagnoseFee create() {
        return WorkerDiagnoseFee.builder().build();
    }

    @Override
    public void beforeAdd(WorkerDiagnoseFee entity) {
        if (Objects.isNull(entity)) {
            throw new UpdateDeniedException("信息为空！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void synchronization() {
        //获取字典表信息
        List<DictDto> fieldDictList = this.fieldDictService.getFieldDict(
            FrameworkConstants.REGIONAL_MEDICAL_APP_ID, "sys_worker", "positional");
        List<String> positionalValues = fieldDictList.parallelStream().map(DictDto::getValue).collect(Collectors.toList());
        //获取诊察表信息
        List<WorkerDiagnoseFee> listOfOrg = this.getListOfOrg(AuthUtils.getLoggedUserOrgId());
        List<String> collect = listOfOrg.parallelStream().map(WorkerDiagnoseFee::getPositional).collect(Collectors.toList());
        positionalValues.forEach(positional ->{
            if (!collect.contains(positional)) {
                WorkerDiagnoseFee workerDiagnoseFee = new WorkerDiagnoseFee();
                workerDiagnoseFee.setFee(BigDecimal.ZERO);
                workerDiagnoseFee.setOrgId(AuthUtils.getLoggedUserOrgId());
                workerDiagnoseFee.setCreatedBy(AuthUtils.getLoggedUserId());
                workerDiagnoseFee.setPositional(positional);
                this.add(workerDiagnoseFee);
            }
        });
    }
}
