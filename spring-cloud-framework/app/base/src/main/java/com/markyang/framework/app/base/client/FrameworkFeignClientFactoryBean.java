package com.markyang.framework.app.base.client;

import com.markyang.framework.pojo.common.client.BaseUrlTarget;
import com.markyang.framework.pojo.common.client.FrameworkFeignClient;
import com.markyang.framework.pojo.common.exception.FrameworkException;
import com.markyang.framework.pojo.constant.FeignClientConstants;
import com.markyang.framework.util.ReflectionOperationUtils;
import com.markyang.framework.util.TypeCastUtils;
import feign.Contract;
import feign.Retryer;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * Client工厂Bean
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
public class FrameworkFeignClientFactoryBean<T> implements FactoryBean<T> {
    private final Class<T> targetClass;
    private final FrameworkFeignClient annotation;
    private final ApplicationContext applicationContext;
    /**
     * Return an instance (possibly shared or independent) of the object
     * managed by this factory.
     * <p>As with a {@link BeanFactory}, this allows support for both the
     * Singleton and Prototype design pattern.
     * <p>If this FactoryBean is not fully initialized yet at the time of
     * the call (for example because it is involved in a circular reference),
     * throw a corresponding {@link FactoryBeanNotInitializedException}.
     * <p>As of Spring 2.0, FactoryBeans are allowed to return {@code null}
     * objects. The factory will consider this as normal value to be used; it
     * will not throw a FactoryBeanNotInitializedException in this case anymore.
     * FactoryBean implementations are encouraged to throw
     * FactoryBeanNotInitializedException themselves now, as appropriate.
     *
     * @return an instance of the bean (can be {@code null})
     * @throws Exception in case of creation errors
     * @see FactoryBeanNotInitializedException
     */
    @Override
    public T getObject() throws Exception {
        HystrixFeign.Builder builder = HystrixFeign.builder()
            .encoder(this.applicationContext.getBean("feignEncoder", Encoder.class))
            .decoder(this.applicationContext.getBean("feignDecoder", Decoder.class))
            .contract(this.applicationContext.getBean("feignContract", Contract.class))
            .retryer(this.applicationContext.getBean("feignRetryer", Retryer.class))
            .client(FeignClientConstants.APACHE_HTTP_CLIENT);

        Target<T> target;
        if (StringUtils.isNotBlank(annotation.baseUrl())) {
            // 忽略Target
            target = BaseUrlTarget.create(annotation.baseUrl(), this.targetClass);
        } else {
            Method method = ReflectionOperationUtils.getMethod(annotation.targetClass(), "create", Class.class);
            if (Objects.isNull(method)) {
                throw new FrameworkException("Client Target类[" + annotation.targetClass().getName() + "]不存在静态方法create");
            }
            Optional<Object> o = ReflectionOperationUtils.invokeMethod(method, null, this.targetClass);
            if (!o.isPresent()) {
                throw new FrameworkException("Client Target类[" + annotation.targetClass().getName() + "]静态方法create只能返回[" + Target.class.getName() + "]类型");
            }
            target = TypeCastUtils.cast(o.get());
        }
        if (Objects.equals(Void.class, annotation.fallbackClass())) {
            return builder.target(target);
        }
        // 获取fallback对象
        T fallbackBean = TypeCastUtils.cast(this.applicationContext.getBean(annotation.fallbackClass()));
        return builder.target(target, fallbackBean);
    }

    /**
     * Return the type of object that this FactoryBean creates,
     * or {@code null} if not known in advance.
     * <p>This allows one to check for specific types of beans without
     * instantiating objects, for example on autowiring.
     * <p>In the case of implementations that are creating a singleton object,
     * this method should try to avoid singleton creation as far as possible;
     * it should rather estimate the type in advance.
     * For prototypes, returning a meaningful type here is advisable too.
     * <p>This method can be called <i>before</i> this FactoryBean has
     * been fully initialized. It must not rely on state created during
     * initialization; of course, it can still use such state if available.
     * <p><b>NOTE:</b> Autowiring will simply ignore FactoryBeans that return
     * {@code null} here. Therefore it is highly recommended to implement
     * this method properly, using the current state of the FactoryBean.
     *
     * @return the type of object that this FactoryBean creates,
     * or {@code null} if not known at the time of the call
     * @see ListableBeanFactory#getBeansOfType
     */
    @Override
    public Class<?> getObjectType() {
        return this.targetClass;
    }

    /**
     * Is the object managed by this factory a singleton? That is,
     * will {@link #getObject()} always return the same object
     * (a reference that can be cached)?
     * <p><b>NOTE:</b> If a FactoryBean indicates to hold a singleton object,
     * the object returned from {@code getObject()} might get cached
     * by the owning BeanFactory. Hence, do not return {@code true}
     * unless the FactoryBean always exposes the same reference.
     * <p>The singleton status of the FactoryBean itself will generally
     * be provided by the owning BeanFactory; usually, it has to be
     * defined as singleton there.
     * <p><b>NOTE:</b> This method returning {@code false} does not
     * necessarily indicate that returned objects are independent instances.
     * An implementation of the extended {@link SmartFactoryBean} interface
     * may explicitly indicate independent instances through its
     * {@link SmartFactoryBean#isPrototype()} method. Plain {@link FactoryBean}
     * implementations which do not implement this extended interface are
     * simply assumed to always return independent instances if the
     * {@code isSingleton()} implementation returns {@code false}.
     * <p>The default implementation returns {@code true}, since a
     * {@code FactoryBean} typically manages a singleton instance.
     *
     * @return whether the exposed object is a singleton
     * @see #getObject()
     * @see SmartFactoryBean#isPrototype()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
