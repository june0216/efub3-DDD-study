package com.efub.dddstudy.Chap9_도메인모델과바운디드컨텍스트;

import com.efub.dddstudy.Chap3_애그리거트.Product;
import com.efub.dddstudy.Chap3_애그리거트.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

//인프라 영역
public class RecSystemClient implements ProductRecommedationService{
	private ProductRepository productRepository;

	@Override
	public List<Product> getRecommendationsOf(ProductId id)
	{
		List<RecommendationItem> items = getRecItems(id.getValue());
		return toProduct(items);//추천 모델을 받아와서 카탈로그 도메인의 Product 모델로 변환하는 작업을 한다.
	}

	private List<RecommendationItem> getRecItems(String itemId){
		return externalRecClient.getRecs(itemId);//외부 추천 시스템을 위한 클라이언트
	}

	private List<Product> toProducts(List<RecommendationItem> items){
		return items.stream()
				.map(item -> toProductId(item.getItemId()))
				.map(prodId -> productRepository.findById(prodId))
				.collect(toList());
	}

	private ProductId toProductId(String itemId){
		return new ProductId(itemId);
	}
}
