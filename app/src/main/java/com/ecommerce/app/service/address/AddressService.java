package com.ecommerce.app.service.address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.address.AddressDTO;
import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.repository.address.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<AddressDTO> getAllAddresses() {
        return addressRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO getAddressById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        return new AddressDTO(address);
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setEndereco(addressDTO.getEndereco());
        address.setNumero(addressDTO.getNumero());
        address.setCidade(addressDTO.getCidade());
        address.setUf(addressDTO.getUf());
        address.setCep(addressDTO.getCep());

        User user = new User();
        user.setId(addressDTO.getUser().getId());
        address.setUser(user);

        addressRepository.save(address);

        return convertToDTO(address);
    }

    public AddressDTO updateAddress(Integer id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));

        address.setEndereco(addressDTO.getEndereco());
        address.setNumero(addressDTO.getNumero());
        address.setCidade(addressDTO.getCidade());
        address.setUf(addressDTO.getUf());
        address.setCep(addressDTO.getCep());

        User user = new User();
        user.setId(addressDTO.getUser().getId());
        address.setUser(user);

        addressRepository.save(address);

        return convertToDTO(address);
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address);
    }
}