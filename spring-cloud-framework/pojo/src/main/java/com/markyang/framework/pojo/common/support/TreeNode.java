package com.markyang.framework.pojo.common.support;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构接口
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27 2:25 下午 星期五
 */
public interface TreeNode extends Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    String getId();

    /**
     * 获取父级ID
     *
     * @return ID
     */
    String getParentId();

    /**
     * 设置子节点
     *
     * @param children 孩子节点
     */
    void setChildren(List<? extends TreeNode> children);

    /**
     * 获取子节点
     *
     * @return 子节点
     */
    List<? extends TreeNode> getChildren();
}
