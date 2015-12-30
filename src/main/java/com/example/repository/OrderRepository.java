package com.example.repository;

import com.example.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jarvis on 15. 12. 31..
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
