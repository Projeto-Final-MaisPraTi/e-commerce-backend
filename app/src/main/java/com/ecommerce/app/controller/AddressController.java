package com.ecommerce.app.controller;

import com.ecommerce.app.dto.AddressDTO;
import com.ecommerce.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	@Autowired
    private AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public AddressDTO createAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(id, addressDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
