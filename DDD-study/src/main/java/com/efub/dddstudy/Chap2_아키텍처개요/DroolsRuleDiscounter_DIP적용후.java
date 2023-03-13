package com.efub.dddstudy.Chap2_아키텍처개요;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;
import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderLine;

import java.util.List;

public class DroolsRuleDiscounter_DIP적용후 implements RuleDiscounter{
	private KieContainer kieContainer;

	public DroolsRuleEngine_DIP적용후()
	{
		kieContainer ks = kieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();

	}

	public Money applyRulew(Customer customer, List<OrderLine> orderLines)
	{
		KieSession kSession = kContainer.newKieSesscion(sessionName);
		try{
			facts.forEach(x -> kSession.insert(x));
			kSession.fireAllRules();

		}
		finally {
			kSession.dispose();
		}
		return money.toImmutableMoney();
	}
}
