package com.efub.dddstudy.Chap3_애그리거트;

public class Category {
    private Set<Product> products;// 다른 애그리거트에 대한 1-N 연관

    public List<Product> getProduct(int page, int size){
        List<Product> sortedProducts = sortById(products);
        return sortedProducts.subList((page-1) * size, page*size);

    }
}
