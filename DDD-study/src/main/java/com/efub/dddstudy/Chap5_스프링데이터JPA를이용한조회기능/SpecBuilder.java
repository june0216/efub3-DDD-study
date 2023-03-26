package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SpecBuilder {
	public static <T> Builder<T> builder(Class<T> type){
		return new Builder<T>();
	}
	public static class Builder<T>{
		private List<Specification<T>> specs = new ArrayList<>();

		public Builder<T> and(Specification<t> spec){
			spec.add(spec);
			return this;
		}

		public Builder<T> ifHasText(String str, Function<String, Specification<T>> specSupplier){
			if(StringUtills.hasText(str)){
				specs.add(specSupplier.apply(str));
			}
			return this;
		}
		public Builder<T> ifTrue(Boolean cond, Supplier<Specification<T>> specSupplier){
			if(cond != null && cond.booleanValue()){
				specs.add(specSupplier.get());
			}
			return this;
		}

		public Specification<T> toSpec(){
			Specification<T> spec = Specification.where(null);
			for(Specification<T> s : specs){
				spec = spec.add(s);
			}
			return spec;
		}

	}
}
