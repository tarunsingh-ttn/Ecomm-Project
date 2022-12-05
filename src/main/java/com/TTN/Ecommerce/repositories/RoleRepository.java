package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


    Role findRoleByAuthority(String role);
}
