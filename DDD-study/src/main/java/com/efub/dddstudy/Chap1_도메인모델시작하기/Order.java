package com.efub.dddstudy.Chap1_도메인모델시작하기;

import java.util.List;

public class Order {
	private OrderNo id;// 식별자
	private Orderer orderer;

	private List<OrderLine> orderLines; // Order은 한 개 이상의 orderLine을 가질 수 있으므로 Order을 생성할 때 OrderLine를 리스트로 전달한다.
	private OrderState state;
	private ShippingInfo shippingInfo;
	private Money totalAmounts;


	public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo)
	{
		setOrderer(orderer);
		setOrderLines(orderLines);
		setShippingInfo(shippingInfo);// 주문할 때 배송지 정보를 반드시 지정해야 한다는 내용 -> Order 을 생성할 때 해당 정보도 함께 전달해야 함
	}

	private void setOrderer(Orderer orderer)//set 메소드는 접근 범위가 private이다.  -> 클래스 내부에서만 데이터 변경 용도로 사용
	{
		if(orderer == null) throw new IllegalArgumentException("no orderer");
		this.orderer = orderer;
	}

	private void setOrderLines(List<OrderLine> orderLines){
		//요구 사항에 정의한 제약 조건을 검사한다.
		verifyAtLeastOneOrMoreOrderLines(orderLines);//요구사항에서 최소 한 종류 이상의 상품을 주문해야 하므로 이 조건을 검사한다.
		this.orderLines = orderLines;
		calculateTotalAmounts();
	}

	public void setShippingInfo(ShippingInfo shippingInfo){
		if(shippingInfo == null){
			throw new IllegalArgumentException("no ShippingInfo"); //shippingInfo가 null이면 익셉션이 발생하는데 이렇게 하여 배송지 정보 필수라는 도메인 규칙을 구현한다.
		}
		this.shippingInfo = shippingInfo;
	}

	private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines){
		if(orderLines == null || orderLines.isEmpty()){
			throw new IllegalArgumentException("no OrderLine");
		}
	}
	private void calculateTotalAmounts()// 총 주문 금액 계산
	{
		int sum = orderLines.stream().mapToInt(x -> Integer.parseInt(String.valueOf(x.getAmounts()))).sum();
		this.totalAmounts = new Money(sum);
	}



	public void changeShipped(){}
	public void changeShippingInfo(ShippingInfo newShoppingInfo){
		verifyNotYetShipping();// 배송지 변겅은 출고 전에만 가능하다는 조건을 만족시키기 위해
		setShippingInfo(newShoppingInfo);
	}
	public void cancel(){
		verifyNotYetShipping();//주문 취소는 출고 전에만 가능하기 때문에 조건을 체크한다.
		this.state = OrderState.CANCELED;
	}

	private void verifyNotYetShipping()//조건 체크, 도메인이 "출고 전에 가능하다"는 조건을 명확하게 가지고 있으므로 함수명을 다음과 같이 함
	{
		if(state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING)
		{
			throw new IllegalStateException("already shipped");
		}
	}
	public void completePayment(){}

	public OrderNo getId()
	{
		return id;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return true;
		if(obj.getClass() != Order.class) return false;
		Order other = (Order) obj;
		if(this.id == null) return false;
		return this.id.equals(other.id);
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public enum OrderState { //주문 상태를 표현한다.-> 특정 조건아니 상태에 따라 규칙이 달리 적용되는 경우가 있으므로 상태 정보를 표현한다.
		PAYMENT_WAITING,
		PREPARING,
		SHIPPED,
		DELIVERING,
		DELIVERY_COMPLETED,
		CANCELED;
	}
}
