package com.ecommerce.app.service.address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.utils.UserContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.address.AddressDTO;
import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.repository.address.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

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

        Optional<Integer> optionalId = UserContextUtils.getAuthenticatedUserId();
        if (optionalId.isEmpty()) {
            throw new RuntimeException("Usuario não autenticado");
        }
        Optional<User> optionalUser = userRepository.findById(optionalId.get());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuario não encontrado");
        }
        User user = optionalUser.get();

        address.setUser(user);

        addressRepository.save(address);

        return convertToDTO(address);
    }

    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));

        address.setEndereco(addressDTO.getEndereco());
        address.setNumero(addressDTO.getNumero());
        address.setCidade(addressDTO.getCidade());
        address.setUf(addressDTO.getUf());
        address.setCep(addressDTO.getCep());

        addressRepository.save(address);

        return convertToDTO(address);
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address);
    }

    public AddressDTO getAddressByUser() {
        Optional<Integer> userId = UserContextUtils.getAuthenticatedUserId();
        if (userId.isEmpty()) {
            throw new RuntimeException("Usuario não esta autenticado");
        }
        Optional<Address> optionalAddress = addressRepository.findByUserId(userId.get());
        if (optionalAddress.isEmpty()) {
            return null;
        }
        Address address = optionalAddress.get();
        AddressDTO addressDTO = new AddressDTO(address);
        return addressDTO;
    }
}