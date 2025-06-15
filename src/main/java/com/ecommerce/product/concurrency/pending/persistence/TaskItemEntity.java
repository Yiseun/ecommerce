package com.ecommerce.product.concurrency.pending.persistence;

import com.ecommerce.product.concurrency.pending.domain.TaskItem;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskItemId;
    private String orderItemId;
    private boolean isSuccess;
    private String productId;
    private String productName;
    private Long price;
    private Long quantity;

    public TaskItem toTaskItem(){
        final ProductInfo productInfo = ProductInfo.of(this.productId,this.productName,this.price.toString());
        final Product product = Product.of(productInfo, Quantity.from(this.quantity.toString()));
        return TaskItem.of(this.taskItemId,this.orderItemId,this.isSuccess,product);
    }
}
