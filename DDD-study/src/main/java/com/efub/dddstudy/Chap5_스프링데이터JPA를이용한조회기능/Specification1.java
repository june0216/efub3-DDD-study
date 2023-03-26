package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

public interface Specification1<T> {
	public boolean isSatisfiedBy(T agg); // agg 는 검사 대상이 되는 객체, 애그리거트 루트가 된다.
}
