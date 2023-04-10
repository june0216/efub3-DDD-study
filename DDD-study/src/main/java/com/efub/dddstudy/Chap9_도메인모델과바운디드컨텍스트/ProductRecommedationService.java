package com.efub.dddstudy.Chap9_도메인모델과바운디드컨텍스트;

public interface ProductRecommedationService {
	List<Product> getRecommendationOf(ProductId id); // 상품 추천 기능을 표현하는 도메인 서비스

}
