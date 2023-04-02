package com.efub.dddstudy.Chap7_도메인서비스;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;

public class DiscountCalcalculationService {
	public Money calculationDiscountAmounts(
			List<OrderLine> orderLines,
			List<Coupon> coupons,
			MemberGrade grade)
	{
		Money couponDiscount =
				coupons.stream()
						.map(coupon -> calculationDiscount(coupon))
						.reduce(Money(0), (v1, v2) -> v1.add(v2)); // 1) 쿠폰 할인 적용
		Money membershipDiscount
				= calculationDiscount(Orderer.getMember().getGrade()); // 2) 멤버쉽 등급 할인 적용
		return couponDiscount.add(membershipDiscount);
	}

	private Money calculationDiscount(Coupon coupon){

	}
	private Money calculateDiscount(MemberGrade grade){

	}

}
