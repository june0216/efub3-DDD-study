package com.efub.dddstudy.Chap3_애그리거트;

public class Store {
    public Product createProduct(ProductID newProductId, ProductInfo pi){
        if(isBlocked()) throw new StoreBlockedException();
        return new Product(newProductId, getId());
    }
}
