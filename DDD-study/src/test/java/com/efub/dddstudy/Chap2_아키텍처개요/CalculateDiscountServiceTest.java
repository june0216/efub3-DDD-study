package com.efub.dddstudy.Chap2_아키텍처개요;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CalculateDiscountServiceTest {
	public void noCustomer_thenExceptionShouldBeThrown(){
		//테스트 목적의 대역 객체
		CustomerRepository stubRepo = mock(CustomerRepository.class);//CustomerRepository의 대역객체
		when(stubRepo.findById("noCustId")).thenReturn(null);//null을 리턴하고 NoCustomerException을 발생시킨다.

		RuleDiscounter stubRule = (cust, lines) -> null;//RuleDiscounter의 대역객체

		//대용 객체를 주입받아 테스트 진행
		CalculateDiscountService calDisSvc = new CalculateDiscountService(stubRepo, stubRule);
		assertThrows(NoCustomerException.class, ()-> calDisSvc.calculateDiscount(someLines, "noCustId"));
	}

}