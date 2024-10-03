package com.ecommerce.app.service;

import com.ecommerce.app.dto.UserDTO;
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
        // retorna a lista de usuários convertidos e coletados
        return userRepository
                .findAll() // pega todos os usuários do bd
                .stream() // os usuários são colocados em uma stream
                .map(this::convertToDTO) // cada usuário é convertido e abstraído apenas os dados que compõe o DTO
                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
    }

    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO){
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());
        user.setTelefone(userDTO.getTelefone());
        userRepository.save(user);

        return convertToDTO(user);
    }

    public UserDTO updateUser(int id, UserDTO userDTO){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setNome(userDTO.getNome());
            user.setEmail(userDTO.getEmail());
            user.setSenha(userDTO.getSenha());
            user.setTelefone(userDTO.getTelefone());
            userRepository.save(user);

            return convertToDTO(user);
        }

        return null;
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
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
