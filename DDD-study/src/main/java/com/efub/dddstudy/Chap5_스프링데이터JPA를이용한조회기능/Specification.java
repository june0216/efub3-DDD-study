package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.function.Predicate;

public interface Specification<T> extends Serializable {//여기서 T는 JPA 엔티티 타입을 의미
	static <T> Specification<T> not(@Nullable Specification<T> spec) { ... }//정적 메서드로 조건을 반대로 적용할 때 사용
	static <T> Specification<T> where(@Nullable Specification<T> spec) { ... }//nullException 발생 방지 가능
	default Specification<T> and(@Nullable Specification<T> other) { ... }//디폴트 매서드
	default Specification<T> or(@Nullable Specification<T> other) { ... }//디폴트 매서드

	@Nullable
	Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
	//JPA Criteria API 에서 조건을 표현하는 Predicate을 생성한다.

}
