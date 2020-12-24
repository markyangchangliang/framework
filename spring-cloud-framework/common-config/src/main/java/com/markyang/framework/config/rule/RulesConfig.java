package com.markyang.framework.config.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markyang.framework.pojo.rule.support.FactBean;
import com.markyang.framework.pojo.rule.support.RulesContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.JsonRuleDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.util.Map;

/**
 * 规则配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@Slf4j
public class RulesConfig {

    @Primary
    @Bean
    public RulesEngine defaultRulesEngine() {
        return new DefaultRulesEngine();
    }

    @Bean
    public RulesEngine inferenceRulesEngine() {
        return new InferenceRulesEngine();
    }

    @Bean
    public Facts facts(ApplicationContext applicationContext) {
        Facts facts = new Facts();
        Map<String, Object> factBeans = applicationContext.getBeansWithAnnotation(FactBean.class);
        factBeans.entrySet().parallelStream()
            .forEach(entry -> facts.put(entry.getKey(), entry.getValue()));
        return facts;
    }

    @Bean
    public RulesContainer rulesContainer(ApplicationContext applicationContext, ObjectMapper objectMapper) throws Exception {
        Resource[] resources = applicationContext.getResources("classpath*:rules/**/*.json");
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new JsonRuleDefinitionReader(objectMapper));
        RulesContainer rulesContainer = new RulesContainer();
        for (Resource resource : resources) {
            Rules rules = ruleFactory.createRules(new InputStreamReader(resource.getInputStream()));
            rulesContainer.addRules(FilenameUtils.getBaseName(resource.getFilename()), rules);
        }
        return rulesContainer;
    }
}
