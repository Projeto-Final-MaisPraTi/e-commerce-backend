package com.ecommerce.app.repository.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.address.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	Optional<Address> findById(Integer userId);
	
}
