package com.efub.dddstudy.Chap3_애그리거트;

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
