package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.OrgSearchForm;
import com.markyang.framework.app.system.form.update.OrgUpdateForm;
import com.markyang.framework.app.system.service.OrgService;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理(Org)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "机构管理控制器")
@ApiSort(1)
@CacheName("org")
@RestController
@RequestMapping("/org")
@AllArgsConstructor
@Slf4j
public class OrgController extends AbstractSystemController<Org, OrgService, OrgSearchForm, OrgUpdateForm> {

    private final OrgService orgService;

    /**
     * 获取机构数
     * @param parentId 父级编号
     * @return 结果对象
     */
    @ApiOperation(value = "获取机构树")
    @GetMapping(value = "/tree", produces = "application/json;charset=UTF-8")
    public ResultVo<List<TreeNode>> getOrgTree(@RequestParam(required = false, value = "parentId") String parentId) {
        return this.orgService.getOrgTree(parentId);
    }

}