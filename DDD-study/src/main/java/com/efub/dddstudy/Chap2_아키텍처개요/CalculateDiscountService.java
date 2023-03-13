package com.efub.dddstudy.Chap2_아키텍처개요;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;
import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderLine;

import java.util.List;

public class CalculateDiscountService {
	private RuleDiscounter ruleDiscounter;
	private CustomerRepository customerRepository;

	public CalculateDiscountService(CustomerRepository customerRepository, RuleDiscounter ruleDiscounter){
		this.customerRepository = customerRepository;
		this.ruleDiscounter = ruleDiscounter;
	}

	public Money calculateDiscount(List<OrderLine> orderLines, String customId){
		Customer customer = findCustomer(customId);
		return ruleDiscounter.applyRules(customer, orderLines);
	}

	private Customer findCustomer(String customerId)
	{
		Customer customer = customerRepository.findById(customerId);
		if(customer == null) throw new NoCustomerException();
		return customer;
	}


/*	DIP 적용 전
	private DroolsRuleEngine_DIP적용전 ruleEngine;
	public CalculateDiscountService()
	{
		ruleEngine = new DroolsRuleEngine();
	}
	public Money calculateDiscount(List<OrderLine> orderLines, String customId){
		Customer customer = findCustomer(customId);
		MutableMoney money = new MutableMoney(0);// Drools에 특화된 코드 -> 연산 결과를 받기 위해 추가한 타입
		List<?> facts = Arrays.asList(customer, money);// Drools에 특화된 코드 -> 룰에 필요한 데이터
		facts.addAll(orderLines);
		ruleEngine.evalute("discountCalculation", facts);// Drools에 특화된 코드 -> Drools의 세션 이름
		return money.toImmutableMoney();
	}

 */

}
