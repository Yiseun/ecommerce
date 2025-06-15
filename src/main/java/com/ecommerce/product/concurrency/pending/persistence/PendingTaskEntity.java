package com.ecommerce.product.concurrency.pending.persistence;

import com.ecommerce.product.concurrency.pending.domain.PendingTask;
import com.ecommerce.product.concurrency.pending.domain.PendingTaskStatus;
import com.ecommerce.product.concurrency.pending.domain.TaskItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PendingTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendingTaskId;
    @Column(unique = true)
    private String orderId;
    private String memberId;
    private String pendingTaskStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TaskItemEntity> taskItemEntities;
    @Version
    private Long version;

    public PendingTask toPendingTask(){
        final List<TaskItem> taskItems = taskItemEntities.stream()
                .map(taskItemEntity -> taskItemEntity.toTaskItem())
                .toList();
        return PendingTask.of(this.pendingTaskId,this.orderId,this.memberId, PendingTaskStatus.from(this.pendingTaskStatus),taskItems,this.version);
    }

    public static PendingTaskEntity from(final PendingTask pendingTask){
        final List<TaskItemEntity> taskItemEntities = pendingTask.getTaskItems().stream().map(taskItem ->
                TaskItemEntity.builder()
                        .taskItemId(taskItem.getTaskItemId())
                        .orderItemId(taskItem.getOrderItemId())
                        .isSuccess(taskItem.isSuccess())
                        .productId(taskItem.getProduct().getProductInfo().getProductId().toString())
                        .productName(taskItem.getProduct().getProductInfo().getProductName())
                        .price(taskItem.getProduct().getProductInfo().getPrice())
                        .quantity(taskItem.getProduct().getQuantity().getValue())
                        .build()
        ).toList();
        return PendingTaskEntity.builder()
                .pendingTaskId(pendingTask.getPendingTaskId())
                .orderId(pendingTask.getOrderId())
                .memberId(pendingTask.getMemberId())
                .pendingTaskStatus(pendingTask.getPendingTaskStatus().toString())
                .taskItemEntities(taskItemEntities)
                .version(pendingTask.getVersion())
                .build();
    }
}
