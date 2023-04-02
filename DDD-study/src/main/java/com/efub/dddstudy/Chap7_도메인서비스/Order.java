package com.efub.dddstudy.Chap7_도메인서비스;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;

public class Order {
	public void calculateAmounts(
			DiscountCalcalculationService disCalsvc,
			MemberGrade grade
	){
		Money totalAmounts = getTotalAmounts();
		Money discountAmounts =
				disCalsvc.calculationDiscountAmounts(this.orderLines, this.coupons,grade);
		this.paymentAmounts = totalAmounts.minus(discountAmounts);
	}
}
