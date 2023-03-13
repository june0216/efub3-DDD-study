package com.efub.dddstudy.Chap2_아키텍처개요;

import java.util.List;

public class DroolsRuleEngine_DIP적용전 {// 인프라스트럭처 영역
	private KieContainer kieContainer;

	public DroolsRuleEngine_DIP적용전()
	{
		kieContainer ks = kieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();

	}

	public void evalute(String sessionName, List<?> facts)
	{
		KieSession kSession = kContainer.newKieSesscion(sessionName);
		try{
			facts.forEach(x -> kSession.insert(x));
			kSession.fireAllRules();

		}
		finally {
			kSession.dispose();
		}
	}

}
