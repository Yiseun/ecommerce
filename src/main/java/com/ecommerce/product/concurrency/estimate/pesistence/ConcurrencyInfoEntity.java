package com.ecommerce.product.concurrency.estimate.pesistence;

import com.ecommerce.product.concurrency.estimate.domain.ConcurrencyInfo;
import com.ecommerce.product.concurrency.estimate.domain.ThreadCount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcurrencyInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concurrencyId;
    @Column(unique = true)
    private Long productId;
    private Long activeThreadCount;

    public ConcurrencyInfo toConcurrencyInfo(){
        final ThreadCount threadCount = ThreadCount.from(this.activeThreadCount);
        return ConcurrencyInfo.of(this.concurrencyId,this.productId,threadCount);
    }

    public static ConcurrencyInfoEntity from(final ConcurrencyInfo concurrencyInfo){
        return new ConcurrencyInfoEntity(concurrencyInfo.getConcurrencyId(),concurrencyInfo.getProductId(),concurrencyInfo.getThreadCount().getActiveThreadCount());
    }
}
