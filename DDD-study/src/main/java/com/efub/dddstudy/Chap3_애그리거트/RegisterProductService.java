package com.efub.dddstudy.Chap3_애그리거트;

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
