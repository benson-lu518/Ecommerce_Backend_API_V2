package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> filterByParams(ProductQueryParams productQueryParams) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction(); // Start with an empty conjunction

            if (productQueryParams.getCategory() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("category"), productQueryParams.getCategory()));
            }

            if (productQueryParams.getSearch() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(root.get("productName"), "%" + productQueryParams.getSearch() + "%"));
            }

            return predicates;
        };
    }
}
