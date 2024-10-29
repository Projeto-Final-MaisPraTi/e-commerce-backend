package com.ecommerce.app.service;

import com.ecommerce.app.dto.produtos.UserDTO;
import com.ecommerce.app.exception.BadRequestException;
import com.ecommerce.app.exception.DatabaseOperationException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired // faz injeção de dependência automática
    private UserRepository userRepository;
    public List<UserDTO> getAllUsers(){
        try{
        // retorna a lista de usuários convertidos e coletados
        return userRepository
                .findAll() // pega todos os usuários do bd
                .stream() // os usuários são colocados em uma stream
                .map(this::convertToDTO) // cada usuário é convertido e abstraído apenas os dados que compõe o DTO
                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
        } catch (Exception e) {
            throw new DatabaseOperationException("Error retrieving users");
        }
    }

    public UserDTO getUserById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO){
        if (userDTO.getNome() == null || userDTO.getNome().isEmpty()) {
            throw new BadRequestException("User name cannot be null or empty");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new BadRequestException("User email cannot be null or empty");
        }
        if (userDTO.getSenha() == null || userDTO.getSenha().isEmpty()) {
            throw new BadRequestException("User password cannot be null or empty");
        }

        try{
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());
        user.setTelefone(userDTO.getTelefone());
        userRepository.save(user);

        return convertToDTO(user);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error creating user");
        }
    }

    public UserDTO updateUser(int id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userDTO.getNome() == null || userDTO.getNome().isEmpty()) {
            throw new BadRequestException("User name cannot be null or empty");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new BadRequestException("User email cannot be null or empty");
        }
        if (userDTO.getSenha() == null || userDTO.getSenha().isEmpty()) {
            throw new BadRequestException("User password cannot be null or empty");
        }
        try{
            user.setNome(userDTO.getNome());
            user.setEmail(userDTO.getEmail());
            user.setSenha(userDTO.getSenha());
            user.setTelefone(userDTO.getTelefone());
            userRepository.save(user);

            return convertToDTO(user);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error updating user");
        }
    }

    public void deleteUser(int id){
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        try {
        userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error deleting user");
        }
    }

    private UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNome(user.getNome());
        userDTO.setEmail(user.getEmail());
        userDTO.setSenha(user.getSenha());
        userDTO.setTelefone(user.getTelefone().isEmpty() ? "" : user.getTelefone());

        return userDTO;
    }
}
