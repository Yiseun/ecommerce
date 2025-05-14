package com.ecommerce.grobal;

import com.ecommerce.grobal.exception.InvalidTimezoneException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class EcommerceTimeConfiguration {
    private final TimeProperty timeProperty;

    @PostConstruct
    public void createTime(){
        if(!ZoneId.getAvailableZoneIds().contains(timeProperty.getTimeZone())){
            throw new InvalidTimezoneException("유효하지않은 타임존입니다.");
        }
        TimeZone.setDefault(TimeZone.getTimeZone(timeProperty.getTimeZone()));
    }
}
