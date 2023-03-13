package com.efub.dddstudy.Chap1_도메인모델시작하기;

public class Money {// 돈을 의미하는 벨류
	private int value;
	public Money(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public Money add(Money money)
	{
		return new Money(this.value + money.value);
	}
	public Money multiply(int multiplier)
	{
		return new Money(value * multiplier);
	}


}
