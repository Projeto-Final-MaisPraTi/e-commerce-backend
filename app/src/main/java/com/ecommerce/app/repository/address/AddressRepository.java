package com.ecommerce.app.repository.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.address.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	Optional<Address> findById(Integer userId);

    @Query(value = "SELECT * FROM addresses WHERE user_id = :userId LIMIT 1", nativeQuery = true)
    Optional<Address> findByUserId(@Param("userId") Integer userId);
}