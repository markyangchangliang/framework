package com.markyang.framework.util;

import com.google.common.collect.Maps;
import com.markyang.framework.pojo.common.support.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 构建工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class BuilderUtils {

    @Data
    @AllArgsConstructor
    public static class MapBuilder<K, V> {
        private Map<K, V> map;

        public MapBuilder<K, V> put(K k, V v) {
            map.put(k, v);
            return this;
        }

        public Map<K, V> build() {
            return map;
        }
    }

    /**
     * 构建HashMap
     * @param <K> 键泛型
     * @param <V> 值泛型
     * @return builder对象
     */
    public static<K, V> MapBuilder<K, V> newHashMapBuilder() {
        return new MapBuilder<>(Maps.newHashMap());
    }

    @NoArgsConstructor
    public static class TreeBuilder {
        private List<? extends TreeNode> nodes;
        private String topLevelId;

        public TreeBuilder nodes(List<? extends TreeNode> nodes) {
            this.nodes = nodes;
            return this;
        }

        public TreeBuilder topLevelId(String topLevelId) {
            this.topLevelId = topLevelId;
            return this;
        }

        public <T extends TreeNode> List<T> build() {
            if (Objects.isNull(this.topLevelId) || CollectionUtils.isEmpty(this.nodes)) {
                return Collections.emptyList();
            }
            return TypeCastUtils.cast(this.nodes
                .parallelStream()
                .filter(node -> Objects.equals(node.getParentId(), this.topLevelId))
                .peek(this::buildSub)
                .collect(Collectors.toList()));
        }

        private void buildSub(TreeNode node) {
            List<TreeNode> children = this.nodes.parallelStream().filter(n -> Objects.equals(n.getParentId(), node.getId())).collect(Collectors.toList());
            children.parallelStream().forEach(this::buildSub);
            node.setChildren(CollectionUtils.isEmpty(children) ? null : children);
        }

        public <T extends TreeNode> Optional<T> singleBuild() {
            List<? extends TreeNode> treeNode = this.build();
            if (CollectionUtils.isEmpty(treeNode)) {
                return Optional.empty();
            }
            return Optional.of(TypeCastUtils.cast(treeNode.get(0)));
        }
    }

    /**
     * 创建一个树
     * @param nodes 节点
     * @return 树构建器
     */
    public static  TreeBuilder newTreeBuilder(List<? extends TreeNode> nodes) {
        return new TreeBuilder().nodes(nodes);
    }
}
