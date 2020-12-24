package com.markyang.framework.config.exception;

import com.netflix.client.ClientException;
import com.netflix.zuul.exception.ZuulException;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 框架全局错误控制器
 * @author yangchangliang
 */
@RestController
@RequestMapping("${server.error.path:/error}")
@Slf4j
@AllArgsConstructor
public class FrameworkErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultVo<?>> handleError(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(webRequest, true);
        Throwable error = this.errorAttributes.getError(webRequest);
        if (error instanceof ZuulException) {
            while (Objects.nonNull(error.getCause())) {
                error = error.getCause();
            }
        }
        String message = "程序异常[" + attributes.get("message") + "]";
        if (error instanceof ClientException) {
            message = "服务暂不可用";
        }
        log.error("出现错误：{}", message);
        return new ResponseEntity<>(ResultVo.error(message), HttpStatus.valueOf((Integer) attributes.get("status")));
    }

}
