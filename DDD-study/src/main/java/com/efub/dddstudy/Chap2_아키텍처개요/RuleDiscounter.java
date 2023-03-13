package com.efub.dddstudy.Chap2_아키텍처개요;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;
import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderLine;

import java.util.List;

public interface RuleDiscounter {
	Money applyRules(Customer customer, List<OrderLine> orderLines);
}
