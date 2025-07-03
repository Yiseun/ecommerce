package com.ecommerce.product.concurrency.pending.domain;

import com.ecommerce.product.concurrency.exception.application.domain.InvalidConstructionException;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.exception.application.domain.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class TaskItem {
    private final Long taskItemId;
    @EqualsAndHashCode.Include
    private final String orderItemId;
    private final boolean isSuccess;
    @EqualsAndHashCode.Include
    private final Product product;

    private TaskItem(final Long taskItemId,
                     final String orderItemId,
                     final boolean isSuccess,
                     final Product product){
        this.taskItemId = taskItemId;
        this.orderItemId = validate(orderItemId);
        this.isSuccess = isSuccess;
        this.product = validate(product);
    }

    private String validate(final String orderItemId){
        if(orderItemId==null){
            throw new InvalidConstructionException("orderItemId는 비어있을수 없습니다.");
        }
        return orderItemId;
    }

    private Product validate(final Product product){
        if(product==null){
            throw new InvalidConstructionException("product는 비어있을수 없습니다.");
        }
        return product;
    }
    public boolean isSuccess(){
        return this.isSuccess;
    }


    public TaskItem update(final Map<Product,Product> productMap){
        final Product serverProduct =productMap.get(this.product);
        if(serverProduct==null){
            return new TaskItem(this.taskItemId,this.orderItemId,false,this.product);
        }
        try{
            final Product resultProduct = serverProduct.update(this.product);
            productMap.put(this.product,resultProduct);
            return new TaskItem(this.taskItemId,this.orderItemId,true,this.product);
        }catch (DomainException e){
            return new TaskItem(this.taskItemId,this.orderItemId,false,this.product);
        }
    }

    public TaskItem interact(final TaskItem taskItem,final Map<Product,Product> productMap){
        if(!this.equals(taskItem)){
            throw new InvalidConstructionException("같지않은 taskItem을 사용할수 없습니다.");
        }
        final Product originProduct = productMap.get(this.product);
        if(originProduct==null){
            return new TaskItem(this.taskItemId,this.orderItemId,false,this.product);
        }
        try {
            final Product resultProduct = originProduct.update(this.product);
            productMap.put(this.product,resultProduct);
            return new TaskItem(this.taskItemId,this.orderItemId,true,this.product);
        }catch (DomainException e){
            return new TaskItem(this.taskItemId,this.orderItemId,false,this.product);
        }
    }

    public static TaskItem init(final String orderItemId,final Product product){
        return new TaskItem(null,orderItemId, false, product);
    }

    public static TaskItem of(final Long taskItemId,final String orderItemId,final boolean isSuccess,final Product product){
        return new TaskItem(taskItemId, orderItemId, isSuccess, product);
    }
}
