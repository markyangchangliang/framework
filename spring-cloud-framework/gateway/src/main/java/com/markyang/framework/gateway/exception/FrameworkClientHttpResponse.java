package com.markyang.framework.gateway.exception;

import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.util.JsonUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * http响应
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor(staticName = "of")
public class FrameworkClientHttpResponse implements ClientHttpResponse {

    private final HttpStatus status;

    private final ResultVo<Object> resultVo;

    /**
     * Get the HTTP status code as an {@link HttpStatus} enum value.
     * <p>For status codes not supported by {@code HttpStatus}, use
     * {@link #getRawStatusCode()} instead.
     *
     * @return the HTTP status as an HttpStatus enum value (never {@code null})
     * @see HttpStatus#valueOf(int)
     * @since #getRawStatusCode()
     */
    @NonNull
    @Override
    public HttpStatus getStatusCode() {
        return this.status;
    }

    /**
     * Get the HTTP status code (potentially non-standard and not
     * resolvable through the {@link HttpStatus} enum) as an integer.
     *
     * @return the HTTP status as an integer value
     * @see #getStatusCode()
     * @see HttpStatus#resolve(int)
     * @since 3.1.1
     */
    @Override
    public int getRawStatusCode() {
        return this.status.value();
    }

    /**
     * Get the HTTP status text of the response.
     *
     * @return the HTTP status text
     */
    @NonNull
    @Override
    public String getStatusText() {
        return this.status.getReasonPhrase();
    }

    /**
     * Close this response, freeing any resources created.
     */
    @Override
    public void close() {
    }

    /**
     * Return the body of the message as an input stream.
     *
     * @return the input stream body (never {@code null})
     */
    @NonNull
    @Override
    public InputStream getBody() {
        return IOUtils.toInputStream(JsonUtils.toJson(this.resultVo), StandardCharsets.UTF_8);
    }

    /**
     * Return the headers of this message.
     *
     * @return a corresponding HttpHeaders object (never {@code null})
     */
    @NonNull
    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
