# Chap3. 애그리거트

# 3.1 애그리거트

### 애그리거트의 필요성

- 상위 수준에서 모델을 정리하면 도메인 모델의 복잡한 관계를 이해하는 데 도움이 된다.
    - 개별 객체 수준에서 모델을 바라보면 상위 수준에서 관계를 파악하기 어렵다. ( ERD 데이블 모두 나열하는 느낌)
    - 상위 수준에서 모델이 어떻게 엮여 있는지 알아야 전체 모델을 망가뜨리지 않으면서 추가 요구사항을 모델에 반영 가능 (세부적인 모델만 이해한 상태로는 코드를 수정하는 것이 꺼려지기 때문이다)

⇒ 복잡한 도메인을 이해하고 관리하기 쉬운 단위로 만들기

→ 상위 수준에서 모델을 조망하는 방법 필요

⇒ “애그리거트”

### 장점

- 관련된 객체를 하나의 군으로 묶어준다. (모델을 이해하는 데 도움이 된다)
    - → 모델 간의 관계를 개별 수준과 상위 수준에서 모두 이해할 수 있다.
- 일관성을 관리하는 기준이 된다.
    - 복잡한 도메인을 단순한 구조로 만들어준다.
    - → 도메인 기능을 확장하고 변경하는 데 필요한 노력(개발 시간)이 줄어든다.

### 특징

- 애그리거트는 관련된 모델을 하나로 모았기 때문에 한 애그리거트에 속한 객체는 유사하거나 동일한 라이프 사이클을 갖는다.
    - ex) 주문 애그리거트 → Order, OrderLine, Orderer 와 같은 관련 객체를 함께 생성해야 한다.
    - → 관련 객체는 함께 생성, 함께 삭제해야 하낟.
- 애그리거트는 경계를 갖는다.
    - 한 애그리거트에 속한 객체는 다른 애그리거트에 속하지 않는다.
    - 애그리거트는 독립된 객체 군이며 각 애그리거트는 자기 자신을 관리할 뿐 다른 애그리거트를 관리하지 않는다.
        - ex) 주문 애그리거트에서 회원의 비밀번호를 변경하지 않는다
    - 경계를 설정할 때 기본이 되는 것 = 도메인 규칙과 요구사항
        - 도메인 규칙에 따라 함께 생성되는 구성요소는 한 애그리거트에 속할 가능성이 높다.
        - 사용자 요구 사항에 따라 주문 상품의 개수와 배송지를 함께 변경하기도 한다.
            - 함께 변경되는 빈도가 높은 객체는 한 애그리거트에 속할 가능성이 높다.
        - 보통 “A가 B를 갖는다”라는 개념이 성립할 때는 한 애그리거트에 속하는 것이지만 아닌 경우도 있다.
            - ex) 리뷰와 상품의 관계 = 상품이 리뷰를 갖지만 리뷰의 변경이 상품에 영향을 주지 않고 상품의 변경이 리뷰에 영향을 주지 않기 때문에 다른 애그리거트에 속한다.


# 3.2 애그리거트 루트

- 애그리거트는 여러 객체로 구성되기 떄문에 한 객체만 상태가 정상이면 안 된다.
    - 도메인 규칙을 지키려면 애그리거트에 속한 모든 객체가 정상 상태를 가져야 한다.
    - 애그리거트에 속한 모든 객체가 일관된 상태를 유지하려면
        - → 애그리거트 전체를 관리할 주체가 필요
        - → 이 책임을 지는 것이 바로 “애그리거트의 루트 엔티티”이다.
- 애그리거트 루트 엔티티
    - 애그리거트의 대표 엔티티이다.
    - 애그리거트에 속한 객체는 애그리거트 루트 엔티티에 직접 또는 간접적으로 속하게 된다.
    - ex) 주문 애그리거트에서 Order 엔티티가 루트 역할을 한다.

## 3.2.1 도메인 규칙과 일관성

- 단순히 객체를 포함하는 것이 아니라 일관성이 꺠지지 않게 하는 것이다.
- 주문 애그리거트는 배송지 변경, 상품 변경과 같은 기능을 제공하고 애그리거트 루트은 Order가 이 기능을 구현한 메서드를 제공한다.

    ```java
    public class Order{
    		//애그리거트 루트는 도메인 규칙을 구현한 기능을 제공한다. 
    	public void changeShippingInfo(ShippingInfo newShippingInfo){
    		verifyNotYetShipped();
    		setShippingInfo(newShippingInfo); // 새로운 객체 할당
    	}
    	private void verifyNotYetShipped()
    	{
    		if(state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PAYMENT_WAITING && state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PREPARING)
    			throw new IllegalArgumentException("already shipped");
    	}// 배송지 정보를 변경할 수 있는지 여부를 확인하는 도메인 규칙
    
    	...
    }
    ```


- **애그리거트 외부에서 애그리거트에 속한 객체를 직접 변경하면 안 된다.**

    ```java
    //Order에서 ShippingInfo를 가져와 직접 정보를 변경하고 있다. 
    ShippingInfo si = order.getShippingInfo();
    si.setAddress(newAddress);
    ```

    - 주문 상태에 상관없이 배송지 주소를 변경 → 논리적인 데이터 일관성이 깨지게 된다.
    - 일관성을 지키기 위해 다음과 같이 상태 확인 로직을 응용서비스에서 구현할 수 있지만 동일한 검사 로직을 **여러 응용 서비스에서 중복으로 구현할 가능성이 높아져** 유지 보수에 도움이 되지 않는다.

        ```java
        ShippingInfo si = order.getShippingInfo();
        
        if(state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PAYMENT_WAITING && state != com.efub.dddstudy.Chap1_도메인모델시작하기.Order.OrderState.PREPARING)
        			throw new IllegalArgumentException("already shipped");
        
        si.setAddress(newAddress);
        ```

- 해결 방법
    - 1) 단순히 필드를 변경하는 것은 set 메서드를 공개(public) 범위로 만들지 않는다.

      ```java
      // 도메인 모델에서 공개 set 메서드는 가급적 피해야 한다. 
      public void setName(String name)
      {
              this.name=name;
      }
      ```

        - 공개 set 메서드는 도메인의 의미나 의도를 표현하지 못하고 도메인 로직을 도메인 객체가 아닌 응용 영역이나 표현 영역으로 분산시킨다.
        - 도메인 로직이 한 곳에 응집되지 않으므로 코드를 유지 보수할 때에도 분석하고 수정하는 데 더 많은 시간이 필요하다.
        - 공개 set 메서드를 사용하지 않으면 의미가 드러나는 메서드를 사용해서 구현할 가능성이 높아진다.
            - ex) changePassword 처럼 의미가 잘 드러나는 이름을 사용하는 빈도가 높아진다.

      ```java
      ShippingInfo si = order.getShippingInfo();
      si.setAddress(newAddress); // ShippingInfo가 불변이면 이 코드는 컴파일 에러 
      ```

    - 2) 밸류 타입은 불변으로 구현한다.
        - 공개 set 메서드는 만들지 않는 것의 연장으로 밸류는 불변 타입으로 구현한다.
            - 벨류 객체의 값을 변경할 수 없으면 애그리거트 루트에서 밸류 객체를 구해도 애그리거트 외부에서 벨류 객체의 상태를 변경할 수 없다. → 새로운 벨류 객체를 할당하는 방법 뿐이다.

      ```java
      private void setShippingInfo(ShippingInfo newShippingInfo){
              if(newShippingInfo == null) throw new IllegalArgumentException();
              // 벨류가 불변이면 새로운 객체를 할당해서 변경해야 한다. -> this.shippingInfo.setAdress(newShippingInfo.getAddress())와 같은 코드 사용 불가 
              this.shippingInfo = newShippingInfo;
          }
      ```


## 3.2.2 애그리거트 루트의 기능 구현

- 애그리거트 루트는 애그리거트 내부의 다른 객체를 조합해서 기능을 완성한다.
    - Order는 총 주문 금액을 구하기 위해 OrderLine 목록을 사용한다.

    ```java
    public class Order{
    	private Money totalAmounts;
    	private List<OrderLine> orderLines;
    	
    	private void calculateTotalAmounts()
    	{
    		int sum = orderLines.stream()
    				.mapToInt(ol -> ol.getPrice() * ol.getQuantity()).sum();
    		this.totalAmounts = new Money(sum);
    	}
    }
    ```

- 애그리거트 루트가 구성요소의 상태만 참조하는 것은 아니다.
    - 기능 실행을 위임하기도 한다.
        - OrderLines 코드

            ```java
            public class OrderLines { 
            	private List<OrderLine> lines;
            	
            	public Money getTotalAmounts(){}
            	public void changeOrderLines(List<OrderLine> newLines)
            	{
            		this.lines = lines;
            	}
            	
            }
            ```

        - Order 코드

            ```java
            	private OrderLines orderLines;
            	public void changeOrderLines(List<OrderLine> newLines)
            	{
            		orderLines.changeOrderLines(newLines);
            		this.totalAmounts = orderLines.getTotalAmounts();
            	}
            ```

            - OrderLines에서 changeOrderLines 기능을 제공하고 있는데 이때, Order 클래스가 getOrderLines() 와 같이 OrderLines를 구할 수 있는 메서드를 제공하면 애그리거트 외부에서 OrderLines의 기능을 실행할 수 있게 된다.

                ```java
                OrderLines lines = order.getOrderLines();
                
                //외부에서 애그리거트 내부 상태 변경!
                lines.changeOrderLines(newOrderLines);
                //OrderLine 목록이 바뀌는데 총합은 계산하지 않는 버그 
                ```

              ⇒ OrderLines를 불변으로 구하거나, 패키지는 protected 범위로 한정해서 외부에서 실행할 수 없도록 한다. (한 애그리거트에 속하는 모델은 한 패키지에 속하기 때문이다)


            ### 3.2.3 트랜잭션 범위
            
            - 트랜잭션 범위는 작을수록 좋다.
            
            한 개 테이블을 수정하면 트랜잭션 충돌을 막기 위해 잠그는 대상이 한 개 테이블의 한 행으로 한정되지만, 세 개의 테이블을 수정하면 잠금 대상이 더 많아진다. 
            
            잠금 대상이 많아진다는 것은 그만큼 동시에 처리할 수 있는 트랜잭션 개수가 줄어든다는 것을 의미하고 이것은 전체적인 성능(처리량)을 떨어뜨린다. 
            
            - 한 트랜잭션에서 한 애그리거트만 수정
                - 애그리거트에서 다른 애그리거트를 변경하지 않는다는 의미
                - 한 애그리거트에서 다른 애그리거트를 수정하면 결과적으로 두 개의 애그리거트를 한 트랜잭션에서 수정하게 되므로, 애그리거트 내부에서 다른 애그리거트의 상태를 변경하는 기능을 실행하면 안 된다.
                    - ⇒ 애그리거트가 자신의 책임 범위를 넘어 다른 애그리거트의 상태까지 관리하는 꼴
                        - 애그리거트는 최대한 독립적이어야 한다.
                        - 결합도가 높아지면 높아질수록 향후 수정 비용이 증가하는 단점
                    
                    ex) 배송지 정보를 변경하면서 동시에 배송지정보를 회원 주소로 설정하는 기능 → 주문 애그리거트에서 회원 애그리거트의 정보를 변경하는 것이므로 안 된다. 
                    
            - 만약 부득이하게 한 트랜잭션으로 두 개 이상의 애그리거트 수정해야 한다면
                - 애그리거트에서 다른 애그리거트를 직접 수정하지 말고 **응용 서비스**에서 두 애그리거트를 수정하도록 구현한다.
                    
                    ```java
                    public class ChangeOrderService {
                    	// 두 개 이상의 애그리거트를 변경 -> 응용 서비스에서 각 애그러그트의 상태를 변경해야 한다. 
                    	@Transactional
                    	public void changeShippingInfo(OrderId id, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr)
                    	{
                    		Order order = orderRepository.findById(id);
                    		if(order == null) throw new OrderNotFoundException();
                    		order.shipTo(newShippingInfo);// 주문 정보 변경
                    		if(useNewShippingAddrAsMemberAddr)
                    		{
                    			Member member = findMember(order.getOrderer());
                    			member.changeAddress(newShippingInfo.getAddress());// 회원 정보 변경 
                    					
                    		}
                    		
                    	}
                    }
                    ```
                    
                    - 도메인 이벤트를 사용하면 한 트랜잭션에서 한 개의 애그리거트를 수정하면서도 동기나 비동기로 다른 애그리거트의 상태를 변경하는 코드를 작성할 수 있다.
            - 두 개 이상의 애그리거트를 변경하는 것을 고려할 수 있는 경우
                - 1) 팀 표준: 팀이나 조직의 표준에 따라 사용자 유스케이스와 관련된 응용 서비스의 기능을 한 트랜잭션으로 실행해야 하는 경우
                - 2) 기술 제약 : 기술적으로 이벤트 방식을 도입할 수 없는 경우 → 한 트랜잭션에서 다수의 애그리거트를 수정해서 일관성을 처리해야 한다.
                - 3) UI 구현의 편리 : 운영자의 편리함을 위해 주문 목록 화면에서 여러 주문 상태를 한 번에 변경하고 싶을 것 → 한 트랜잭션에서 여러 주문 애그리거트의 상태를 변경해야 한다.


# 3.3 리포지터리와 애그리거트

- 애그리거트 = 완전한 한 개의 도메인 모델을 표현
    - 객체의 영속성을 처리하는 리포지커리는 애그리거트 단위로 존재한다.
- Order와 OrderLine을 물리적으로 각각 별도의 DB 테이블에 저장한다고 해서 Order가 애그리거트 루트고 OrderLine은 애그리거트에 속하는 구성요소이므로 Order를 위한 리포지터리만 존재한다.
- 새로운 애그리거트를 만들면 저장소에 애그리거트를 영속화하고 애그리거트를 사용하려면 저장소에서 애그리거트를 읽어야 하므로, 리포지터리는 보통 다음의 두 메서드를 기본적으로 제공한다.
    - save()
    - findById()
- 리포지터리를 구현하는 기술의 종류에 따라 애그리거트의 구현도 영향을 받는다.
    - ex) ORM 기술 중 하나인 JPA를 사용하면 데이터베이스 관계형 모델에 객체 도메인 모델을 맞춰야 할 때도 있다.
        - DB 테이블 구조에 맞게 모델을 변경해야 하는데 벨류 타입인 도메인 모델을 @Component(JPA에서 벨류 타입을 매핑할 때 사용하는)가 아닌 @Entity (엔티티를 매핑할 때 사용하는)을 이용해야 할 수도 있다.
- 애그리거트는 개념저긍로 하나이므로 리포지터리는 애그리거트 전체를 저장소에 영속화해야 한다.
    - ex) Order 애그리거트와 관련된 테이블 3개라면 → Order 애그리거트를 저장할 떄 애그리거트 루트와 매핑되는 테이블뿐만 아니라 애그리거트에 속한 모든 구성 요소에 매핑된 테이블에 데이터를 저장해야 한다.

    ```java
    orderRepository.save(order); // 애그리거트 전체를 영속화해야 한다. 
    ```

    ```java
    Order order = orderRepository.findById(orderId); // 리포지터리는 완전한 order를 제공해야 한다. 
    //완전한 애그리거트가 아니면 NullPointerException 문제 발생한다. 
    ```

    - 완전한 애그리거트를 제공하지 않으면 필드나 값이 올바르지 않아 실행도중 NullPointerException 문제 발생할 수 있다.


# 3.4 ID를 이용한 애그리거트 참조

- 한 객체가 다른 객체를 참조하는 것처럼 애그리거트도 다른 애그리거트를 참조한다.
- 애그리거트 관리 주체는 루트이므로 다른 애그리거트를 참조한다는 것은 애그리거트 루트를 참조하는 것이다.
- 애그리거트 간 참조는 필드를 통해 쉽게 구현 가능

    ```java
    public class Order{
    		private Member member;
    }
    ```

- JPA는 ManyToOne, OneToOne과 같은 애너테이션을 사용해서 연관된 객체를 로딩하는 기능을 제공하고 있다. → 필드를 통해 다른 애그리거트를 쉽게 참조 가능

### 문제점

- 편한 탐색 오용
    - 다른 애그리거트의 상태를 쉽게 변경 할 수 있다. → 한 애그리거트 관리 범위 넘어서게 된다.
    - 결합 의존도를 높여서 애그리거트의 변경을 어렵게 만든다.
- 성능에 대한 고민
    - JPA를 참조하면 객체를 지연 로딩과 즉시 로딩의 두 가지 방식으로 로딩할 수 있다.
        - 즉시 로딩 = 단순히 연관된 객체의 데이터를 함께 화면에 보여줘야 하면 조회 성능 유리
        - 지연 로딩 = 애그리거트의 상태를 변경하는 기능을 실행하는 경우에는 불필요한 객체를 함께 로딩할 필요가 없으므로 지연 로딩이 유리할 수 있다.
    - 다양한 경우의 수를 고려해서 연관 매핑과 JPQL , Criteria 쿼리의 로딩 전략을 결정해야 한다.
- 확장 어려움
    - 사용자 수가 증가하면 분산하기 위해 하위 도메인 별로 시스템을 분리한다.
        - 심지어 다른 데이터 저장소를 사용하기도 한다.

- ID를 이용해서 다른 애그리거트를 참조 = 이렇게 3가지 문제를 완화할 때 사용할 수 있음

    ```java
    public class Order{
    		private MemberId memberId;
    }
    ```

    - id로 참조하면 모든 객체가 참조로 연결되지 않고 한 애그리거트에 속한 객체들만 참조로 연결된다.
        - → 경계 명확, 애그리거트 간 물리적 연결 제거 → 모델의 복잡도를 낮춰준다.
        - 구현 복잡도도 낮아진다. 지연 로딩, 즉시 로딩 고민하지 않아도 된다. 그냥 필요하면 ID를 이용해서 참조하면 된다.

    ```java
    public class ChangeOrderService {
    	// 두 개 이상의 애그리거트를 변경 -> 응용 서비스에서 각 애그러그트의 상태를 변경해야 한다.
    	@Transactional
    	public void changeShippingInfo(OrderId id, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr)
    	{
    		Order order = orderRepository.findById(id);
    		if(order == null) throw new OrderNotFoundException();
    		order.shipTo(newShippingInfo);// 주문 정보 변경
    		if(useNewShippingAddrAsMemberAddr)
    		{
    			//Member member = findMember(order.getOrderer());
    			Member member = memberRepository.findById(order.getOrderer().getMemberId());
    			member.changeAddress(newShippingInfo.getAddress());// 회원 정보 변경
    
    		}
    
    	}
    }
    ```

    - 응용 서비스에서 필요한 애그리거트를 로딩하므로 애그리거트 수준에서 지연로딩을 하는 것과 동일한 결과
    - 장점 = 다른 애그리거트에서 상태 변경 불가 + 애그리거트 별 다른 구현 기술 사용 가능 + 각 도메인을 별도 프로세스로 서비스하도록 구현 가능


## 3.4.1 ID를 이용한 참조와 조회 성능

- 다른 애그리거트를 ID로 참조하면 참조하는 여러 애그리거트를 읽을 때 조회 속도가 문제가 될 수 있다.
    - 지연 로딩의 문제와 같이 N+1 문제 발생
        - N+1 조회 문제 = 조회 대상이 N개 일 때 N개를 읽어오는 한 번의 쿼리와 연관된 데이터를 읽어오는 쿼리를 N번 실행한다.
        - 문제 해결하기 위해서는 조인을 사용해야하는데 조인을 사용하는 가장 쉬운 방법은 조회 전용 쿼리를 사용하면 된다.
        - 조회를 위한 별도 DAO를 만들고 조회 메서드에서 조인을 이용해 한 번의 쿼리로 필요한 데이터를 로딩하면 된다.

            ```jsx
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
            ```

          한번의 쿼리로 조회 가능

          *처음 jpa를 사용하면 각 객체 간 모든 연관을 지연 로딩과 즉시 로딩으로 어떻게든 처리하고 싶은 욕구에 사로 잡힌다. 하지만 이것은 실용적이지 않는다. id만 있으면 쿼리를 통해 가능하다.


# 3.5 애그리거트 간 집합 연관

- 1-N 관계는 Set과 같은 컬렉션을 이용해서 표현할 수 있다.

```jsx
public class Category {
    private Set<Product> products;// 다른 애그리거트에 대한 1-N 연관
    
    public List<Product> getProduct(int page, int size){
        List<Product> sortedProducts = sortById(products);
        return sortedProducts.subList((page-1) * size, page*size);
        
    }
}
```

카테고리 별 상품을 조회할 때 product의 개수가 수만 개 정도로 많다면 그것들을 다 조회해서 가져오느라 실행속도가 급격히 느려져 성능 문제가 생긴다.

상품 입장에서 자신이 속한 카테고리를 N-1로 연관지어 구하면 된다.

상품에 카테고리 아이디를 추가하고 그 연관을 이요애서 특정 카테고리에 속한 상품 목록을 구하면 된다.

```jsx
package com.efub.dddstudy.Chap3_애그리거트;

public class Product {
    private CategoryId categoryId;
}
```

```jsx
public class ProductListService {
    public Page<Product> getProductOfCategory(Long categoryId, int page, int size)
    {
        Category category = categoryRepository.findById(categoryId);
        checkCategory(category);
        List<Product> products = productRepository.findByCategoryId(Category.getId(), page, size);
        int totalCount = productRepository.countsByCategoryId(category.getId());
        return new Page(page, size, totalCount, products);
    }
    
}
```

- M-N 연관은 개념적으로 양쪽 애그리거트에 컬렉션으로 연관을 만든다.
    - 예를 들어, 상품 목록을 보여줄 때 모든 카테고리를 상품 정보에 표시하지 않으므로 카테고리에서 상품으로의 집합 연관은 필요하지 않다.
        - → 상품에서 카테고리로의 단방향 M-N 연관만 적용하면 된다.

        ```jsx
        package com.efub.dddstudy.Chap3_애그리거트;
        
        public class Product {
            private CategoryId categoryId;
        }
        ```

    - RDBMS를 이용해서 M-N을 구현하려면 조인 테이블을 사용한다.
    - JPA를 이용하면 다음과 같은 매핑 설정을 사용해서 ID 참조를 이용한 M-N 단방향 연관을 구현할 수 있다.

        ```jsx
        @Entity
        @Table(name = "product")
        public class Product {
            @Embedded
            private ProductId id;
            
            @ElementCollection
            @CollectionTable(name = "product_category", joinColumns = @JoinColomn(name = "product_id"))
            private CategoryId categoryId;
        }
        ```

      이 매핑은 카테고리 ID 목록을 보관하기 위해 밸류 타입의 컬렉션 매핑을 이용했다.

      JPQL 의 member of 연산자를 이용해서 특정 Category에 속한 Product 목록을 구하는 기능을 구현할 수 있다.

        ```jsx
        @Repository
        public class JpaProductRepository implements ProductRepository{
            @PersistenceContext
            private EntityManager entityManager;
            
            @Override
            public List<Product> findByCategoryId(CategoryId catId, int page, int size){
                TypedQuery<Product> query = entityManager.createQuery(
                        "select p from Product p"+
                                "where :catId member of p.categoryIds order by p.id.id desc", 
                        Product.class);
                query.setParameter("catId" , catId);
                query.setFirstResult((page-1) * size);
                query.setMaxResults(size);
                return query.getResultList();
                
            }
            
        }
        ```


# 3.6 애그리거트를 팩토리로 사용하기

- 상품 생성하는 서비스
    - 상품을 생성가능한지 판단하는 코드가 분리 되어있다.
    - → 중요한 도메인 처리 로직이 응용 서비스에 노출되었다.
    - 상품을 등록하려는 가게가 차단된 가게가 아닌 것을 판단하고 → 상품을 등록하는 것은 하나의 도메인 기능인데 이 도메인 기능을 응용 서비스에서 구현하고 있다.
    - 하지만 다음과 같이 store애그리거트에 구현할 수 있다.

        ```jsx
        public class Store {
            public Product createProduct(ProductID newProductId,.. 생략){
                if(isBlocked()) throw new StoreBlockedException();
                return new Product(newProductId, getId());
            }
        }
        ```

      → 상품 애그리거트를 생성하는 팩토리 역할을 한다.

      createProduct()는 팩토리 역할을 하면서도 중요한 도메인 로직을 구현하고 있다.

      팩토리 기능을 구현했으므로 이제 응용 서비스는 팩토리 기능을 이용해서 Product를 생성하면 된다.

        ```java
        public class RegisterProductService {
            public ProductId registerNewProduct(NewProductRequest req)
            {
                Store store = storeRepository.findById(req.getStoreId());
                checkNull(store);
                ProductId id = productRepository.nextId();
                Product product = store.createProduct(id, ..);
                productRepository.save(product);
                return id;
            }
        }
        ```

        - 응용 서비스에서 더 이상 store의 상태를 확인하지 않게 되었따.
        - store가 상품을 생성할 수 있는지(도메인 로직)은 store에서 처리한다.
        - 도메인 로직을 변경해도 store 도메인 영역만 수정하면 되고 응용 계층은 영향을 받지 않는다. 또한 도메인의 응집도도 높아짐
            - → 애그리거트를 팩토리로 사용할 때 얻을 수 있는 장점
    - 애그리거트가 갖고 있는 데이터를 이용해서 다른 애그리거트를 생성해야 한다면 애그리거트에 팩토리 메서드를 구현하는 것을 고려해 보자
    -