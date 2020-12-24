package com.markyang.framework.config.feign;

import feign.Contract;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.httpclient.ApacheHttpClient;
import feign.optionals.OptionalDecoder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.commons.httpclient.DefaultApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.commons.httpclient.DefaultApacheHttpClientFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Feign配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class FeignConfig {

    private final Timer connectionManagerTimer = new Timer(
        "FrameworkFeignApacheHttpClientConfiguration.connectionManagerTimer", true);
    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private final List<AnnotatedParameterProcessor> parameterProcessors;
    private final List<FeignFormatterRegistrar> feignFormatterRegistrars;

    public FeignConfig(ObjectFactory<HttpMessageConverters> messageConverters, List<AnnotatedParameterProcessor> parameterProcessors, List<FeignFormatterRegistrar> feignFormatterRegistrars) {
        this.messageConverters = messageConverters;
        this.parameterProcessors = parameterProcessors;
        this.feignFormatterRegistrars = feignFormatterRegistrars;
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder() {
        return new OptionalDecoder(
            new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)));
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder feignEncoder() {
        return new SpringEncoder(this.messageConverters);
    }

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract(ConversionService feignConversionService) {
        return new SpringMvcContract(this.parameterProcessors, feignConversionService);
    }

    @Bean
    public FormattingConversionService feignConversionService() {
        FormattingConversionService conversionService = new DefaultFormattingConversionService();
        for (FeignFormatterRegistrar feignFormatterRegistrar : this.feignFormatterRegistrars) {
            feignFormatterRegistrar.registerFormatters(conversionService);
        }
        return conversionService;
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    public CloseableHttpClient httpClient() {
        final HttpClientConnectionManager connectionManager = new DefaultApacheHttpClientConnectionManagerFactory()
            .newConnectionManager(false, 200, 50, 900L, TimeUnit.SECONDS, null);
        this.connectionManagerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectionManager.closeExpiredConnections();
            }
        }, 30000, 6000);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
            // 链接建立的超时时间
            .setConnectTimeout(10000)
            // 响应超时时间，超过此时间不再读取响应
            .setSocketTimeout(30000)
            // http client中从connection pool中获得一个connection的超时时间
            .setConnectionRequestTimeout(30000)
            .setRedirectsEnabled(true)
            .build();
        return new DefaultApacheHttpClientFactory(HttpClientBuilder.create())
            .createBuilder()
            .setConnectionManager(connectionManager)
            .setDefaultRequestConfig(defaultRequestConfig)
            .build();
    }

    public ApacheHttpClient apacheHttpClient(HttpClient httpClient) {
        return new ApacheHttpClient(httpClient);
    }
}
