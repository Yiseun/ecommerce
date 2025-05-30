package com.ecommerce.order.persistence.constraint;

import com.ecommerce.order.domain.constraint.Constraint;
import com.ecommerce.order.exception.application.domain.FailedCreationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequest implements Pageable {
    private static final int MAXIMUM_SIZE = 50;
    private static final int MINIMUM_SIZE = 1;
    private static final int FIXED_OFFSET = 0;
    private static final int FIXED_PAGE_NUMBER = 0;
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "orderId");
    private final int pageSize;

    private PageRequest(final String size){
        this.pageSize = parse(size);
    }

    private int parse(final String size){
        try{
            final int sizeIntvalue = Integer.parseInt(size);
            if(sizeIntvalue<MINIMUM_SIZE){
                return MINIMUM_SIZE;
            }
            if(sizeIntvalue>MAXIMUM_SIZE){
                return MAXIMUM_SIZE;
            }
            return sizeIntvalue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("size는 숫자여야 합니다.");
        }
    }

    @Override
    public int getPageNumber() {
        return FIXED_PAGE_NUMBER;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public long getOffset() {
        return FIXED_OFFSET;
    }

    @Override
    public Sort getSort() {
        return DEFAULT_SORT;
    }

    @Override
    public Pageable next() {
        return this;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public static PageRequest from(final Constraint constraint){
        return new PageRequest(constraint.getSize());
    }
}
