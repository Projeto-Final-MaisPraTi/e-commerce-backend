package com.ecommerce.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	Optional<Address> findById(Integer userId);
	
}
