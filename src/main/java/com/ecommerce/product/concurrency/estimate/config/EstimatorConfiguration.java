package com.ecommerce.product.concurrency.estimate.config;

import com.ecommerce.product.concurrency.exception.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@RequiredArgsConstructor
public class EstimatorConfiguration {
    private final EstimatorProperties estimatorProperties;

    @Bean(name = "estimatorCommandThreadPool")
    public TaskExecutor createEstimatorCommandThreadPool(){
        try {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(estimatorProperties.getCorePoolSize()));
            threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(estimatorProperties.getMaxPoolSize()));
            threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt(estimatorProperties.getQueueCapacity()));
            threadPoolTaskExecutor.setKeepAliveSeconds(Integer.parseInt(estimatorProperties.getQueueCapacity()));
            threadPoolTaskExecutor.setThreadNamePrefix(estimatorProperties.getThreadNamePrefix());
            threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            threadPoolTaskExecutor.initialize();
            return threadPoolTaskExecutor;
        }catch (Exception e){
            throw new PropertyBindingException("프로퍼티에 잘못된 값을 사용했습니다.");
        }
    }
}
