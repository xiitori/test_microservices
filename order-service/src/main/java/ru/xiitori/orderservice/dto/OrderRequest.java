package ru.xiitori.orderservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequest {
            private List<OrderLineItemsDTO> orderLineItems;
}
