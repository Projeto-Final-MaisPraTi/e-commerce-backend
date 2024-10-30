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
    
    public List<AddressDTO> getAllAddresses(){
    	
        return addressRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO getAddressById(Long id){
//        Optional<Address> address = addressRepository.findById(id);
//        return address.map(this::convertToDTO).orElse(null);
    	Address address = addressRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
    	
    	return new AddressDTO(address);
    }

    public AddressDTO createAddress(AddressDTO addressDTO){
        Address address = new Address();
        address.setEndereco(address.getEndereco());
        address.setCidade(address.getCidade());
        address.setUf(address.getUf());
        address.setCep(address.getCep());
        address.setEndereco_padrao(addressDTO.getEndereco_padrao());

        User user = new User();
        user.setId(addressDTO.getId());
        address.setUser(user);

        addressRepository.save(address);

        return convertToDTO(address);
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDTO){
        Optional<Address> addressOptional = addressRepository.findById(id);
        if(addressOptional.isPresent()){
            Address address = addressOptional.get();
            address.setId(address.getId());
            address.setEndereco(address.getEndereco());
            address.setCidade(address.getCidade());
            address.setUf(address.getUf());
            address.setCep(address.getCep());
            address.setEndereco_padrao(addressDTO.getEndereco_padrao());

            User user = new User();
            user.setId(addressDTO.getId());
            address.setUser(user);

            addressRepository.save(address);

            return convertToDTO(address);
        }

        return null;
    }

    public void deleteAddress(Long id){
        addressRepository.deleteById(id);
    }

    private AddressDTO convertToDTO(Address address){
        AddressDTO addressDTO = new AddressDTO(address);
        addressDTO.setId(address.getId());
        addressDTO.setEndereco(address.getEndereco());
        addressDTO.setNumero(address.getNumero());
        addressDTO.setCidade(address.getCidade());
        addressDTO.setUf(address.getUf());
        addressDTO.setCep(address.getCep());
        addressDTO.setEndereco_padrao(address.isEndereco_padrao());
        addressDTO.setUser(address.getUser());

        return addressDTO;
    }
}

