package com.markyang.framework.app.base.upload.task;

import com.markyang.framework.app.base.upload.service.UploadService;
import com.markyang.framework.pojo.schedule.ScheduledTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 删除超时上传文件任务
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor(staticName = "of")
@Data
public class TimeoutUploadedFileDeleteTask implements ScheduledTask {
    public static final String NAME_PREFIX = "file_delete_";
    /**
     * 文件标识
     */
    private final String key;
    /**
     * 文件上传类型
     */
    private final String type;
    /**
     * 任务执行方法
     *
     * @param context            任务执行上下文
     * @param applicationContext Spring容器
     * @throws JobExecutionException 任务执行异常
     */
    @Override
    public void execute(JobExecutionContext context, ApplicationContext applicationContext) throws JobExecutionException {
        Map<String, UploadService> beans = applicationContext.getBeansOfType(UploadService.class);
        for (UploadService uploadService : beans.values()) {
            if (uploadService.support(this.type)) {
                // 删除文件
                uploadService.delete(this.key);
            }
        }
    }
}
