package com.efub.dddstudy.Chap1_도메인모델시작하기;

import lombok.Setter;

public class OrderLine {

	private Product product;// 한 상품을
	private Money price;//얼마에
	private int quantity;// 몇 개 살지 담고 있다.
	private Money amounts;

	public OrderLine(Product product, Money price, int quantity, Money amounts) {
		this.product = product;
		this.price = new Money(price.getValue());// Money가 불변 객체가 아니라면 price 파라미터가 변경될 때 발생하는 문제를 방지하기 위해
		//데이터르 복사한 새로운 객체를 생성해야 한다.
		this.quantity = quantity;
		this.amounts = amounts;
	}

/*	private int calculateAmounts(){// 구매 가격을 계산하는 로직
		return price * quantity;
	}*/
	public Money getAmounts(){
		return amounts;
	}

	public class Product{

	}

}
