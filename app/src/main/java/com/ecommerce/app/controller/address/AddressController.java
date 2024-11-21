package com.ecommerce.app.controller.address;

import com.ecommerce.app.dto.address.AddressDTO;
import com.ecommerce.app.service.address.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }

    @GetMapping("/user")
    public ResponseEntity<AddressDTO> getAddressByUser() {
        AddressDTO addressDTO = addressService.getAddressByUser();
        if (addressDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressDTO);
    }

    @PostMapping
    public AddressDTO createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @PutMapping
    public AddressDTO updateAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(addressDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
    }
}