package com.ecommerce.grobal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class AsyncConfiguration {
    @Bean(name = "taskExecutor")
    public TaskExecutor createDefaultAsyncThreadPool(){
        return new SimpleAsyncTaskExecutor();
    }

}
