package com.efub.dddstudy.Chap3_애그리거트;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaOrderViewDao {
    @PersistenceContext
    private EntityManager em;
    publid List<OrderView> selectByOrderer(String ordererId)
    {
        String selectQuery = "select new com.myshop.order.application.dto.OrderView(o, m, p)"
                +"from Order o join o.orderLines ol, Member m, Product p "
                + "where o.orderer.memberId.id = :ordererId "
                +"and o.orderer.memberId = m.id"
                +"and index(ol) = 0 "
                +"and ol.productId = p.id"
                +"order by o.number.number desc";
        TypeOuery<Orderview> query = em.createQuery(selectQuery, OrderView.class);
        query.setParameter("ordererId", ordererId);
        return query.getResultList();
    }


}
