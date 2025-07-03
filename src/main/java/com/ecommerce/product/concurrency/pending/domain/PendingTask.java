package com.ecommerce.product.concurrency.pending.domain;

import com.ecommerce.product.concurrency.exception.application.domain.InvalidConstructionException;
import com.ecommerce.product.domain.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class PendingTask {

    private final Long pendingTaskId;
    @EqualsAndHashCode.Include
    private final String orderId;
    @EqualsAndHashCode.Include
    private final String memberId;
    private final PendingTaskStatus pendingTaskStatus;
    @EqualsAndHashCode.Include
    private final List<TaskItem> taskItems;
    private final Long version;

    private PendingTask(final Long pendingTaskId,
                        final String orderId,
                        final String memberId,
                        final PendingTaskStatus pendingTaskStatus,
                        final List<TaskItem> taskItems,
                        final Long version){
        this.pendingTaskId = pendingTaskId;
        this.orderId = validateOrderId(orderId);
        this.memberId = validateMemberId(memberId);
        this.pendingTaskStatus = validate(pendingTaskStatus);
        this.taskItems = validate(taskItems);
        this.version = version;
    }

    private String validateOrderId(final String orderId){
        if(orderId==null){
            throw new InvalidConstructionException("orderId는 비어있을수 없습니다.");
        }
        return orderId;
    }
    private String validateMemberId(final String memberId){
        if(memberId==null){
            throw new InvalidConstructionException("memberId는 비어있을수 없습니다.");
        }
        return memberId;
    }
    private PendingTaskStatus validate(final PendingTaskStatus pendingTaskStatus){
        if(pendingTaskStatus==null){
            throw new InvalidConstructionException("pendingTaskStatus는 비어있을수 없습니다.");
        }
        return pendingTaskStatus;
    }
    private List<TaskItem> validate(final List<TaskItem> taskItems){
        if(taskItems==null){
            throw new InvalidConstructionException("taskItems는 비어있을수 없습니다.");
        }
        return taskItems;
    }
    public PendingTask getFailedTasks(){
        final List<TaskItem> resultTaskItem = taskItems.stream().filter(i->!i.isSuccess()).toList();
        return new PendingTask(this.pendingTaskId,this.orderId,this.memberId,this.pendingTaskStatus,resultTaskItem,this.version);
    }
    public boolean isUpdatableWithProduct(){
        return this.pendingTaskStatus.isApplyable();
    }
    public List<String> getProductIds(){
        return taskItems.stream().map(taskItem -> taskItem.getOrderItemId()).toList();
    }


    public PendingTask update(final PendingTask serverPendingTask){
        if(!this.equals(serverPendingTask)){
            throw new InvalidConstructionException("다른정보의 PendingTask는 변경할수없습니다.");
        }
        final PendingTaskStatus resultPendingTaskStatus = serverPendingTask.pendingTaskStatus.update(this.pendingTaskStatus);
        return new PendingTask(serverPendingTask.pendingTaskId,serverPendingTask.orderId,serverPendingTask.memberId,resultPendingTaskStatus,serverPendingTask.taskItems,serverPendingTask.version);
    }

    public PendingTask interact(final PendingTask pendingTask,final Map<Product,Product> productMap){
        if(!this.equals(pendingTask)){
            throw new InvalidConstructionException("같지않은 task를 수정할수없습니다.");
        }
        final PendingTaskStatus resultPendingTaskStatus = this.pendingTaskStatus.update(pendingTask.pendingTaskStatus);
        final Map<TaskItem,TaskItem> taskItemMap = pendingTask.getTaskItems().stream().collect(Collectors.toUnmodifiableMap(i->i, i->i));
        final List<TaskItem> resultTaskItems = this.taskItems.stream().map(thisTaskItem -> {
            final TaskItem taskItem = taskItemMap.get(thisTaskItem);
            return thisTaskItem.interact(taskItem,productMap);
        }).toList();
        return new PendingTask(pendingTask.pendingTaskId, pendingTask.orderId, pendingTask.memberId,resultPendingTaskStatus,resultTaskItems,pendingTask.version);
    }

    public static PendingTask init(final String orderId,final String memberId,final List<TaskItem> taskItems){
        return new PendingTask(null,orderId,memberId,PendingTaskStatus.init(),taskItems,null);
    }

    public static PendingTask of(final Long pendingTaskId,final String orderId,final String memberId,final PendingTaskStatus pendingTaskStatus,final List<TaskItem> taskItems,final Long version){
        return new PendingTask(pendingTaskId, orderId, memberId, pendingTaskStatus, taskItems, version);
    }
}
