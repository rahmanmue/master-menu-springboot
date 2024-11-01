package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("Select c from Customer c where c.deleted = false")
    List<Customer> getAllCustomer();

    @Query("Select c from Customer c where c.deleted = false and LOWER(c.fullName) LIKE LOWER(CONCAT('%',:customer_name,'%'))")
    List<Customer> getAllCustomerByName(@Param("customer_name") String customer_name);

    @Query("Select c from Customer c where c.deleted = false and c.id = :customer_id")
    Customer findCustomerByDeletedFalse(@Param("customer_id") String customer_id);

}
