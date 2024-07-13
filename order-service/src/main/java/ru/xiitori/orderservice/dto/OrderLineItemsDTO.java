package ru.xiitori.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Data
@Builder
public class OrderLineItemsDTO {

    private String skuCode;

    private BigDecimal price;

    private int quantity;
}
