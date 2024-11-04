package com.ecommerce.app.repository.role;

import com.ecommerce.app.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(String groupname);
}
