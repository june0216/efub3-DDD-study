package com.efub.dddstudy.Chap3_애그리거트;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class JpaProductRepository implements ProductRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findByCategoryId(CategoryId catId, int page, int size){//지정한 카테고리에 속한 상품 목록을 구할 수 있다.
        TypedQuery<Product> query = entityManager.createQuery(
                "select p from Product p"+
                        "where :catId member of p.categoryIds order by p.id.id desc",//값이 존재하는지 검색하기 위한 검색 조건이다.
                Product.class);
        query.setParameter("catId" , catId);
        query.setFirstResult((page-1) * size);
        query.setMaxResults(size);
        return query.getResultList();

    }

}
