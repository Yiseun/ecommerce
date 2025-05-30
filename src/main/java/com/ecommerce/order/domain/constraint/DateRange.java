package com.ecommerce.order.domain.constraint;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.Getter;

import java.time.Period;

@Getter
public class DateRange {
    private static final int MINIMUM_PERIOD_DATE = 1;
    private static final int MAXIMUM_PERIOD_DATE = 60;
    private final OrderLocalDate startDate;
    private final OrderLocalDate endDate;

    private DateRange(final OrderLocalDate startDate,
                      final OrderLocalDate endDate){
        validate(startDate,endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(final OrderLocalDate startDate,final OrderLocalDate endDate){
        final int period = Period.between(startDate.getLocalDate(),endDate.getLocalDate()).getDays();
        if(period<MINIMUM_PERIOD_DATE){
            throw new FailedCreationException("endDate는 반드시 startDate보다 커야합니다.");
        }
        if(period>MAXIMUM_PERIOD_DATE){
            throw new FailedCreationException("날짜간격은 최대 "+MAXIMUM_PERIOD_DATE+"일 입니다.");
        }
    }

    public static DateRange of(final OrderLocalDate startDate, final OrderLocalDate endDate){
        return new DateRange(startDate, endDate);
    }

}
