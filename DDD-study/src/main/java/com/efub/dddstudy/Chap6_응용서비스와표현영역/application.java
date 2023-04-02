package com.efub.dddstudy.Chap6_응용서비스와표현영역;

public class application {
	public Result doSomeFunc(SomeReq req){
		//1. 리포지터리에서 애그리거트를 구한다.
		SomeAgg agg = someAggRepository.findById(req.getId());
		checkNull(agg);

		//2. 애그리거트의 도메인 기능을 실행한다.
		agg.doFunc(req.getValue());

		//3. 결과를 리턴한다.
		return createSuccessResult(agg);
	}

	public Result doCreation(CreateSomeReq req){
		//1. 데이터 중복 등 데이터가 유효한지 검사한다.
		validate(req);


		//2. 애그리거트를 생성한다.
		SomeAgg newAgg = CreateSome(req);

		//3. 리포지터리에 애그리거트를 저장한다.
		SomeAggRepository.save(newAgg);

		//4. 결과를 리턴한다.
		return createSuccessResult(newAgg);
	}

	public void blockMember(String[] blockingIds){
		if(blockingIds == null || blockingIds.length == 0) return;
		List<Member> members = memberRepository.findById(blockingIds);
		for(Member mem : members){
			mem.block();//트랜잭션 범위에서 실행되지 않으면 일부만 반영된다는 문제가 생김 -> 트랜잭션 범위 내에서 실행해야 함
		}
	}
}
