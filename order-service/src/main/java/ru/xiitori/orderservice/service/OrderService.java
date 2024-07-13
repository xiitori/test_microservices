package ru.xiitori.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ru.xiitori.orderservice.dto.OrderLineItemsDTO;
import ru.xiitori.orderservice.dto.OrderRequest;
import ru.xiitori.orderservice.model.Order;
import ru.xiitori.orderservice.model.OrderLineItems;
import ru.xiitori.orderservice.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderLineItems(
                orderRequest.getOrderLineItems().stream().map(dto -> mapToOrderLineItems(dto, order)).toList()
        );

        List<String> scuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        Boolean isStock = webClient.post()
                .uri("http://localhost:8082/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(scuCodes)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(isStock)) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("These items are not available.");
        }
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDTO orderLineItemsDTO, Order order) {
        return OrderLineItems.builder()
                .order(order)
                .skuCode(orderLineItemsDTO.getSkuCode())
                .price(orderLineItemsDTO.getPrice())
                .quantity(orderLineItemsDTO.getQuantity())
                .build();
    }
}
