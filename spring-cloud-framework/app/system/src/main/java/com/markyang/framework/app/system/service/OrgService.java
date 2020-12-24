package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.OrgSearchForm;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 机构管理(Org)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 14:52:12
 */
public interface OrgService extends SearchableService<Org, OrgSearchForm> {

    /**
     * 按照编号获取属性菜单
     *
     * @param parentId 父级id
     * @return 返回集
     */
    ResultVo<List<TreeNode>> getOrgTree(String parentId);
}