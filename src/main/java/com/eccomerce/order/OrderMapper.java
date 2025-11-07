package com.eccomerce.order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;


    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDto convertOrderToOrderDto(Order order){

        return modelMapper.map(order, OrderDto.class);
    }


}
