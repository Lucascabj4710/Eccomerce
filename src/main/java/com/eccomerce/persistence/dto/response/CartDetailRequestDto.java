package com.eccomerce.persistence.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CartDetailRequestDto {

    private Long product;
    private Long quantity;
}
