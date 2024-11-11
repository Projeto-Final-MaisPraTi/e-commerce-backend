package com.ecommerce.app.repository.user;

import com.ecommerce.app.infra.enums.Roles;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.sales.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
//	User findByEmail(String email);
//    Optional<User> findByRoles_Name(String roleName);
//    List<User> findByTypeRole(Roles typeRole);

    boolean existsByEmail(String email);
}
