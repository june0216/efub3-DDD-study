package com.efub.dddstudy.Chap1_도메인모델시작하기;

public class Order_주문상태와다른정보함께사용_배송지변경가능여부판단 {
	/*배송지 변경이 가능한지 판단할 규칙이 주문 상태와 다른 정보를 함께 사용한다면
	해당 방법(Order state 만)으로는 배송지 변경 가능 여부를 판단할 수 없으므로
	Order에서 로직을 구현해야 한다. */
	private OrderState state;
	private ShippingInfo shippingInfo;

	public void changeShippingInfo(ShippingInfo newShippingInfo) {
		if (!isShippingChangeable()) //주문 도메인의 규칙에 따라 변경 가능성 달라짐
		{
			throw new IllegalStateException("Can't change shipping in " + state);
		}
		this.shippingInfo = newShippingInfo;
	}

	private boolean isShippingChangeable() { //배송지를 변경할 수 있는지 검사할 수 있는 메서드
		return (state == OrderState.PAYMENT_WAITING ||
				state == OrderState.PREPARING);
	}

	public enum OrderState { //주문 상태를 표현한다.
		PAYMENT_WAITING,
		PREPARING,
		SHIPPED,
		DELIVERING,
		DELIVERY_COMPLETED;
	}
}
