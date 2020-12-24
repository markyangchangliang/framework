package com.markyang.framework.service.rule;

import com.markyang.framework.pojo.rule.support.FrameworkFacts;
import com.markyang.framework.pojo.rule.support.RulesContainer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.springframework.stereotype.Service;

/**
 * 规则执行服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
@AllArgsConstructor
public class RulesExecutorService {

    private final RulesEngine rulesEngine;

    private final Facts beanFacts;

    private final RulesContainer rulesContainer;

    /**
     * 执行规则
     * @param rules 规则对象
     * @param facts 已知事实
     */
    public void execute(Rules rules, Facts facts) {
        this.rulesEngine.fire(rules, new FrameworkFacts(this.beanFacts, facts));
    }

    /**
     * 执行规则
     * @param rulesName 规则名称
     * @param facts 已知事实
     */
    public void execute(String rulesName, Facts facts) {
        this.rulesEngine.fire(this.rulesContainer.getRules(rulesName), new FrameworkFacts(this.beanFacts, facts));
    }

    /**
     * 执行规则
     * @param rules 规则对象
     */
    public void execute(Rules rules) {
        this.rulesEngine.fire(rules, new FrameworkFacts(this.beanFacts));
    }

    /**
     * 执行规则
     * @param rulesName 规则名称
     */
    public void execute(String rulesName) {
        this.rulesEngine.fire(this.rulesContainer.getRules(rulesName), new FrameworkFacts(this.beanFacts));
    }
}
