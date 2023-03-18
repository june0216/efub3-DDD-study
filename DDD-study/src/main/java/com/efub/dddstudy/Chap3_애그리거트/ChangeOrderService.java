package com.efub.dddstudy.Chap3_애그리거트;

import com.efub.dddstudy.Chap1_도메인모델시작하기.ShippingInfo;
import org.springframework.transaction.annotation.Transactional;

public class ChangeOrderService {
	// 두 개 이상의 애그리거트를 변경 -> 응용 서비스에서 각 애그러그트의 상태를 변경해야 한다.
	@Transactional
	public void changeShippingInfo(OrderId id, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr)
	{
		Order order = orderRepository.findById(id);
		if(order == null) throw new OrderNotFoundException();
		order.shipTo(newShippingInfo);// 주문 정보 변경
		if(useNewShippingAddrAsMemberAddr)
		{
			//Member member = findMember(order.getOrderer());
			Member member = memberRepository.findById(order.getOrderer().getMemberId());
			member.changeAddress(newShippingInfo.getAddress());// 회원 정보 변경

		}

	}
}
