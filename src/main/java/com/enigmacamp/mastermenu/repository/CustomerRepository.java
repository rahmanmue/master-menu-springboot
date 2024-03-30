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

    @Query("Select c from Customer c where c.deleted = false and c.id = :customer_id")
    Customer findCustomerByDeletedFalse(@Param("customer_id") String customer_id);

//    @Query("Delete from Customer c where c.id = :customer_id")
//    void deleteCustomer(@Param("customer_id") String customer_id);

}
