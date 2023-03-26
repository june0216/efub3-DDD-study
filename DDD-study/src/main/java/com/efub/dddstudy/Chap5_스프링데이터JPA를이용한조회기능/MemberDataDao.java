package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface MemberDataDao extends JpaRepository<MemberData, String> {
	List<MemberData> findByNameLike(String name, Pageable pageable);
}

Sort sort = Sort.by("name").descending();
PageRequest pageReq = PageRequest.of(1, 2, sort);
List<MemberData> user = memberDataDao.findByNameLike("사용자%", pageReq);