package com.efub.dddstudy.Chap3_애그리거트;

import org.hibernate.annotations.Table;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Table(name = "product")
public class Product {
    @Embedded
    private ProductId id;

    @ElementCollection
    @CollectionTable(name = "product_category", joinColumns = @JoinColomn(name = "product_id"))
    private CategoryId categoryId;
}
