package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("Select o from Order o where o.deleted = false")
    List<Order> getAllOrder();

    // select order by id
    @Query("Select o from Order o where o.deleted = false and o.id = :order_id")
    Order findOrderByDeletedFalse(@Param("order_id") String order_id);

    // select order by customer id desc
    @Query("Select o from Order o where o.deleted = false and o.customer.id = :customer_id order by o.date desc")
    List<Order> findOrderByCustomerIdDesc(@Param("customer_id") String customer_id);

    // Select order by status
    @Query("Select o from Order o where o.deleted = false and o.status = :status")
    List<Order> findOrderByStatus(@Param("status") EOrderStatus status);

}
