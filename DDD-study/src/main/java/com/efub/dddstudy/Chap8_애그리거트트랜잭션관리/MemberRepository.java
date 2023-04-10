package com.efub.dddstudy.Chap8_애그리거트트랜잭션관리;

import com.efub.dddstudy.Chap4_리포지터리와모델구현.MemberId;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface MemberRepository extends Repository<Member, MemberId>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="2000")}
	)
	@Query("select m from Member m where m.id = :id")
	Optional<Member> findByIdForUpdate(@Param("id") MemberId memberId);

}