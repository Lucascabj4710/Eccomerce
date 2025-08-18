package com.eccomerce.category;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class CategoryDto {
    private Long idCategory;
    private String name;
}
