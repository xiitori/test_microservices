package ru.xiitori.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xiitori.orderservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
