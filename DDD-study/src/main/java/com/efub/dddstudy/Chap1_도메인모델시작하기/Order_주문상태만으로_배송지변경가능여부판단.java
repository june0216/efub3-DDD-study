package com.efub.dddstudy.Chap1_도메인모델시작하기;
/*핵심 = 배송지 변경 가능 여부를 판단하는 기능과 같이 중요 업무 규칙을 주문 도메인 모델인 Order나 OrderState에서 구현한다는 점이다.
=> 핵심 규칙을 구현한 코드는 도메인 모델에만 위치하기 때문에 규칙이 바뀌거나 규칙을 확장해야할 때 다른 코드에 영향을 덜 주고 변경 내역을 모델에 반영할 수 있게 된다.
 */

public class Order_주문상태만으로_배송지변경가능여부판단 {
	/*배송지 변경이 가능한지 판단할 규칙이 주문 상태만 사용한다면
	해당 방법(Order state 만)으로는 배송지 변경 가능 여부를 판단*/
	private OrderState state;
	private ShippingInfo shippingInfo;

	public void changeShippingInfo(ShippingInfo newShippingInfo)
	{
		if(!state.isShippingChangeable()) //주문 도메인의 규칙에 따라 변경 가능성 달라짐
		{
			throw new IllegalStateException("Can't change shipping in " + state);
		}
		this.shippingInfo = newShippingInfo;
	}

	public enum OrderState{ //주문 상태를 표현한다.
		PAYMENT_WAITING {//주문 대기중 -> 베송지 변경 가능
			public boolean isShippingChangeable() { //배송지를 변경할 수 있는지 검사할 수 있는 메서드
				return true;
			}
		},
		PREPARING{// 상품 준비중 -> 배송지 변경 가능
			public boolean isShippingChangeable() {
				return true;
			}

		},
		SHIPPED, DELIVERING, DELIVERY_COMPLETED;
		public boolean isShippingChangeable() {
			return false;
		}


		}
	}


