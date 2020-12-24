package com.markyang.framework.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 框架SQL注入器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class FrameworkSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methods = super.getMethodList(mapperClass);
        methods.add(new AlwaysUpdateSomeColumnById());
        methods.add(new InsertBatchSomeColumn());
        methods.add(new LogicDeleteByIdWithFill());
        methods.add(new ExistsById());
        methods.add(new ExistsByWrapper());
        return methods;
    }
}
