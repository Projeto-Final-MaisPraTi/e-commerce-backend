package com.ecommerce.app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
//	User findByEmail(String email);
    Optional<User> findByRoles_Name(String roleName);

    boolean existsByEmail(String email);
}
