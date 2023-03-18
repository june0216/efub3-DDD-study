package com.efub.dddstudy.Chap3_애그리거트;

import java.util.List;

public interface ProductRepository {
    public List<Product> findByCategoryId(CategoryId catId, int page, int size);
}
