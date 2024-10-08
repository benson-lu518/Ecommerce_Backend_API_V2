package com.bensonlu.ecommercebackendapi.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {
    //Json to class
    @NotEmpty  //cant be empty
    private List<CreateOrderByItem> createOrderByItemList;

    public List<CreateOrderByItem> getBuyItemList() {
        return createOrderByItemList;
    }

    public void setBuyItemList(List<CreateOrderByItem> createOrderByItemList) {
        this.createOrderByItemList = createOrderByItemList;
    }
}
