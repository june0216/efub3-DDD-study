package com.efub.dddstudy.Chap3_애그리거트;

import com.efub.dddstudy.Chap1_도메인모델시작하기.*;

import java.util.List;

public class Order {
	//주문 도메인 모델의 데이터
	private OrderNo id;// 식별자
	private Orderer orderer;
	private ShippingInfo shippingInfo;


	private Money totalAmounts;
	private List<OrderLine> orderLines;

	private OrderLines orderLines;
	public void changeOrderLines(List<OrderLine> newLines)
	{
		orderLines.changeOrderLines(newLines);
		this.totalAmounts = orderLines.getTotalAmounts();
	}

	private void calculateTotalAmounts()
	{
		int sum = orderLines.stream()
				.mapToInt(ol -> ol.getPrice() * ol.getQuantity()).sum();
		this.totalAmounts = new Money(sum);
	}




	//애그리거트 루트는 도메인 규칙을 구현한 기능을 제공한다.
	public void changeShippingInfo(ShippingInfo newShippingInfo){
		verifyNotYetShipped();
		setShippingInfo(newShippingInfo); // 새로운 객체 할당
	}
	private void verifyNotYetShipped()
	{
		if(state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PAYMENT_WAITING && state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PREPARING)
			throw new IllegalArgumentException("already shipped");
	}// 배송지 정보를 변경할 수 있는지 여부를 확인하는 도메인 규칙


	private void setShippingInfo(ShippingInfo newShippingInfo){
		if(newShippingInfo == null) throw new IllegalArgumentException();
		// 벨류가 불변이면 새로운 객체를 할당해서 변경해야 한다. -> this.shippingInfo.setAdress(newShippingInfo.getAddress())와 같은 코드 사용 불가
		this.shippingInfo = newShippingInfo;
	}
}
