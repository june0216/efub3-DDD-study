package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import com.efub.dddstudy.Chap1_도메인모델시작하기.ShippingInfo;
import org.springframework.transaction.annotation.Transactional;

public class ChangeOrderService {
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
