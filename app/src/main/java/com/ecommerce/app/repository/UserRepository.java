package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	User findByEmail(String email);
}
