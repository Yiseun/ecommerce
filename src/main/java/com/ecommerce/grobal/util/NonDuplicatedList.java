package com.ecommerce.grobal.util;

import com.ecommerce.grobal.EcommerceException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class NonDuplicatedList<T> {
    private final List<T> list;

    public NonDuplicatedList(final List<T> list){
        this.list = validate(list);
    }

    private List<T> validate(final List<T> list){
        final Set<T> set  = new HashSet<>(list);
        if(list.size()!=set.size()){
            throw new EcommerceException("중복된값들을 합산하여 요청해주세요");
        }
        return list;
    }

    public Stream<T> getStream(){
        return list.stream();
    }

}
