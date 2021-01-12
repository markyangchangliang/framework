package com.markyang.framework.service.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.markyang.framework.pojo.common.support.PageDefault;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.common.FieldDictTranslatorService;
import com.markyang.framework.service.core.controller.hook.*;
import com.markyang.framework.service.core.controller.support.ControllerHookHolder;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.form.UpdateBaseForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.validator.core.FormValidate;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import com.markyang.framework.service.exception.CrudValidationException;
import com.markyang.framework.util.AuthUtils;
import com.markyang.framework.util.EntityUtils;
import com.markyang.framework.util.TypeCastUtils;
import com.markyang.framework.util.ValidationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 框架控制器基本实现
 *
 * @author yangchangliang
 * @version 1
 */
public class AbstractFrameworkController<E extends BaseEntity, SS extends SearchableService<E, S>, S extends SearchBaseForm, U extends UpdateBaseForm> implements FrameworkController<E, S, U> {

    /**
     * 数据字典翻译服务类
     */
    @Autowired
    private FieldDictTranslatorService fieldDictTranslatorService;

    /**
     * 具体的服务类由子类进行提供
     */
    @Autowired
    private SS service;

    /**
     * 获取单条数据
     * @param id 数据ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 1, author = "yangchangliang")
    @ApiOperation(value = "获取单条数据", notes = "根据id获取单条数据")
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<E> get(@ApiParam(name = "id", value = "数据ID", required = true, example = "1") @PathVariable String id) {
        // 带有ID的单条数据请求
        if (StringUtils.isBlank(id)) {
            return ResultVo.error("数据ID不能为空");
        }
        // 获取指定id的数据
        E entity = this.service.get(id).orElse(null);
        // 查询完成的后置处理
        HookContext context = HookContext.of();
        for (PostGetHook postGetHook : ControllerHookHolder.postGetHooks) {
            postGetHook.apply(entity, context);
        }
        // 处理结果
        this.afterGet(entity, true);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, entity);
    }

    /**
     * 请求一个实体对象或者一个实体对象列表
     * @return 一个实体对象或者一个实体对象列表
     */
    @ApiOperationSupport(order = 2, author = "yangchangliang")
    @ApiOperation(value = "获取多条数据", notes = "根据查询条件获取分页多条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数-页码", defaultValue = "1", paramType = "query", dataTypeClass = Integer.class, example = "1"),
        @ApiImplicitParam(name = "size", value = "分页参数-分页大小", defaultValue = "20", paramType = "query", dataTypeClass = Integer.class, example = "20"),
        @ApiImplicitParam(name = "sort", value = "分页参数-排序", defaultValue = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc", paramType = "query", dataTypeClass = String.class, example = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc"),
    })
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<PageVo<E>> get(@PageDefault(sort = TableConstants.CREATED_DATETIME_FIELD_NAME + FrameworkConstants.COMMA_SEPARATOR + FrameworkConstants.ORDER_DESC) @ApiIgnore Page<E> page, S searchForm) {
        // 处理分页排序条件
        List<OrderItem> orderItems = this.customizedSort();
        if (CollectionUtils.isNotEmpty(orderItems)) {
            // 自定义排序
            page.setOrders(orderItems);
        }

        // 判断是否是机构所属的
        IPage<E> entityPage;
        if (this.isOrgBelonged()) {
            // 机构所属
            entityPage = this.service.getSearchedPageOfOrg(searchForm, page, AuthUtils.getLoggedUserOrgId(), this.addAdditionalSearchCondition());
        } else if (this.isUserBelonged()) {
            // 用户所属
            entityPage = this.service.getSearchedPage(searchForm, page, AuthUtils.getLoggedUserId(), this.addAdditionalSearchCondition());
        } else {
            // 所有数据
            entityPage = this.service.getSearchedPage(searchForm, page, this.addAdditionalSearchCondition());
        }
        PageVo<E> pageVo = PageVo.of(entityPage);
        // 查询完成的后置处理
        HookContext context = HookContext.of();
        List<E> content = pageVo.getContent();
        for (E entity : content) {
            for (PostGetHook postGetHook : ControllerHookHolder.postGetHooks) {
                postGetHook.apply(entity, context);
            }
        }
        // 处理结果
        for (int i = 0, length = content.size(); i < length; i++) {
            this.afterGet(content.get(i), i == length - 1);
        }
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, pageVo);
    }

    /**
     * 解析代理对象
     * @return 代理对象
     */
    protected AbstractFrameworkController<E, SS, S, U> resolveProxy() {
        return TypeCastUtils.cast(AopContext.currentProxy());
    }

    /**
     * 添加方法
     * @param form 表单对象
     * @return 结果对象
     */
    @ApiOperationSupport(order = 3, author = "yangchangliang", ignoreParameters = "id")
    @ApiOperation(value = "添加数据", notes = "添加一条数据")
    @CacheEvict(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", allEntries = true)
    @FormValidate(FrameworkGroups.Add.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<?> add(@ApiParam(name = "form", value = "添加表单对象", required = true) @RequestBody U form) {
        E entity = this.service.create();
        // 做预处理
        HookContext context = HookContext.of();
        for (PreAddHook preAddHook : ControllerHookHolder.preAddHooks) {
            preAddHook.apply(entity, form, context);
        }
        // 代理对象
        AbstractFrameworkController<E, SS, S, U> proxy = this.resolveProxy();
        // 自定义处理
        proxy.beforeAdd(entity, form);
        // 复制属性
        BeanUtils.copyProperties(form, entity, this.handleIgnoredProperties(form));
        // 添加
        E result = this.service.add(entity);
        // 后预处理
        for (PostAddHook postAddHook : ControllerHookHolder.postAddHooks) {
            postAddHook.apply(result, form, context);
        }
        // 添加之后的自定义处理
        Object data = proxy.afterAdd(result, form);
        return ResultVo.success("添加成功", data);
    }

    /**
     * 更新方法
     * @param form 表单对象
     * @return 结果对象
     */
    @ApiOperationSupport(order = 4, author = "yangchangliang")
    @ApiOperation(value = "更新数据", notes = "更新一条数据")
    @CacheEvict(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", allEntries = true)
    @FormValidate(FrameworkGroups.Update.class)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<?> update(@ApiParam(name = "form", value = "更新表单对象", required = true) @RequestBody U form) {
        Optional<Object> idOptional = ValidationUtils.getIdValue(form);
        if (!idOptional.isPresent()) {
            throw new CrudValidationException("数据ID不存在");
        }
        Optional<E> optionalEntity = this.service.get(TypeCastUtils.cast(idOptional.get()));
        if (!optionalEntity.isPresent()) {
            throw new CrudValidationException("待修改的数据不存在");
        }
        E entity = optionalEntity.get();
        HookContext context = HookContext.of();
        // 做预处理
        for (PreUpdateHook preUpdateHook : ControllerHookHolder.preModifies) {
            preUpdateHook.apply(entity, form, context);
        }
        // 代理对象
        AbstractFrameworkController<E, SS, S, U> proxy = this.resolveProxy();
        // 自定义处理
        boolean copy = proxy.beforeUpdate(entity, form);
        // 复制属性
        if (copy) {
            BeanUtils.copyProperties(form, entity, this.handleIgnoredProperties(form));
        }
        // 添加
        E result = this.service.update(entity);
        // 后预处理
        for (PostUpdateHook postUpdateHook : ControllerHookHolder.postModifies) {
            postUpdateHook.apply(result, form, context);
        }
        // 添加之后的自定义处理
        Object data = proxy.afterUpdate(result, form);
        return ResultVo.success("更新成功", data);
    }

    /**
     * 处理忽略字段
     * @param form 表单对象
     * @return 忽略字段
     */
    protected String[] handleIgnoredProperties(U form) {
        String[] customized = this.ignoredProperties(form);
        if (ArrayUtils.isNotEmpty(customized)) {
            String[] fieldNames = new String[customized.length + 1];
            fieldNames[0] = TableConstants.ID_FIELD_NAME;
            System.arraycopy(customized, 0, fieldNames, 1, customized.length);
            return fieldNames;
        }
        return new String[] { TableConstants.ID_FIELD_NAME };
    }

    /**
     * 删除方法
     * @param id 实体ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 5, author = "yangchangliang")
    @ApiOperation(value = "删除数据", notes = "删除一条数据")
    @CacheEvict(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", allEntries = true)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<?> delete(@ApiParam(name = "id", value = "需删除数据的ID", required = true, example = "1") @PathVariable String id) {
        Optional<E> optionalEntity = this.service.get(id);
        if (!optionalEntity.isPresent()) {
            // 主键对应的记录不存在
            throw new CrudValidationException("需删除的数据不存在");
        }
        E entity = optionalEntity.get();
        // 删除前预处理
        HookContext context = HookContext.of();
        for (PreDeleteHook preDeleteHook : ControllerHookHolder.preDeleteHooks) {
            preDeleteHook.apply(entity, context);
        }
        // 代理对象
        AbstractFrameworkController<E, SS, S, U> proxy = this.resolveProxy();
        // 调用删除前逻辑
        proxy.beforeDelete(entity);
        this.service.delete(entity);
        // 删除后预处理
        for (PostDeleteHook postDeleteHook : ControllerHookHolder.postDeleteHooks) {
            postDeleteHook.apply(entity, context);
        }
        // 删除后的处理
        Object data = proxy.afterDelete(entity);
        return ResultVo.success("删除成功", data);
    }

    /**
     * 获取所有字典
     * @return 结果对象
     */
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @ApiOperationSupport(order = 6, author = "yangchangliang")
    @ApiOperation(value = "获取所有字典", notes = "根据当前功能获取其所有字典")
    @GetMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<Map<String, List<DictDto>>> getAllFieldDict() {
        Map<String, List<DictDto>> allFieldDict = this.fieldDictTranslatorService.getAllFieldDict(this.getAppId(), EntityUtils.resolveRawTableName(this.service.create().getClass()), true);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, allFieldDict);
    }

    /**
     * 获取单个字段的字典
     * @param fieldName 需获取数据字典的字段名称
     * @return 结果对象
     */
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.markyang.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @ApiOperationSupport(order = 7, author = "yangchangliang")
    @ApiOperation(value = "获取单个字段的字典", notes = "根据字段获取字典")
    @GetMapping(value = "/dict/{fieldName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<List<DictDto>> getFieldDict(@ApiParam(name = "fieldName", value = "需获取数据字典的字段名称", required = true, example = "status") @PathVariable String fieldName) {
        List<DictDto> dictList = this.fieldDictTranslatorService.getFieldDict(this.getAppId(), EntityUtils.resolveRawTableName(this.service.create().getClass()), fieldName, true);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, dictList);
    }

}
