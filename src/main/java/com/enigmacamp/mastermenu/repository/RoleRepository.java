package com.enigmacamp.mastermenu.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.mastermenu.model.entity.Role;

import com.enigmacamp.mastermenu.utils.enums.ERole;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
