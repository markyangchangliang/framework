package com.markyang.framework.pojo.rule.support;

import com.google.common.collect.Maps;
import org.jeasy.rules.api.Rules;

import java.util.Map;
import java.util.Objects;

/**
 * 规则容器
 *
 * @author yangchangliang
 * @version 1
 */
public class RulesContainer {

    Map<String, Rules> rulesMap = Maps.newHashMap();

    /**
     * 添加规则
     * @param rulesName 规则名称
     * @param rules 规则对象
     */
    public void addRules(String rulesName, Rules rules) {
        this.rulesMap.put(rulesName, rules);
    }

    /**
     * 删除规则
     * @param rulesName 规则名称
     */
    public void removeRules(String rulesName) {
        this.rulesMap.remove(rulesName);
    }

    /**
     * 获取规则
     * @param rulesName 规则名称
     * @return 规则对象
     */
    public Rules getRules(String rulesName) {
        Rules rules = this.rulesMap.get(rulesName);
        if (Objects.isNull(rules)) {
            throw new RulesNotFoundException("规则[" + rulesName + "]找不到");
        }
        return rules;
    }
}
