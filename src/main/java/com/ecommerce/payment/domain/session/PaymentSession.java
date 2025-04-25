package com.ecommerce.payment.domain.session;

import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class PaymentSession {
    private final PaymentKey paymentKey;
    private final String memberId;
    private final PaymentInfo paymentInfo;
    private final Price totalPrice;
    private final List<PurchaseItem> purchaseItems;

    public static PaymentSession of(final PaymentKey paymentKey,final String memberId, final PaymentInfo paymentInfo, final Price totalPrice,final List<PurchaseItem> purchaseItems){
        return new PaymentSession(paymentKey, memberId, paymentInfo, totalPrice, purchaseItems);
    }
}



//orderItemid가 비어있어서 NPE이 발생할수있다
//그대로사용 / orderItemId를 추가해서 wrapping 하면되네
//그럼 이름은? InitialPurchaseItem /
//purchaseItem / paymentItem
//오케이


//Q.PurchaseItem과 PaymentItem을 구분한 이유는 뭐죠?
//처음에 paymentKey를 발급받기위해 요청으로 전달한 아이템과
//나중에 구매가 완료되고 orderItemId를 받아서 완성된 아이템입니다
//Q.그냥 PurchaseItem 하나로 써도되지않나요? 왜 굳이 PaymentItem을 PurchaseItem으로 wrapping 해줬죠?
//만약 그렇게 할경우 PurchaseItem에 orderItemId가 생기게되는데
//이 값이 nullable하다는걸 인지하지못할 가능성도 있다고 판단했습니다
//그래서 확실하게 orderItemId가 전달된 이후에만 orderItemId가 포함된 PaymentItem이 생성되도록 했습니다
