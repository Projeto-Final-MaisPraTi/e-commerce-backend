package com.ecommerce.app.service.user;

import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.role.Role;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.model.userRole.UserRole;
import com.ecommerce.app.repository.role.RoleRepository;
import com.ecommerce.app.repository.user.UserRepository;

import com.ecommerce.app.repository.userGroup.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired // faz injeção de dependência automática
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

    public List<UserDTO> getAllUsers(){
        // retorna a lista de usuários convertidos e coletados
        return userRepository
                .findAll() // pega todos os usuários do bd
                .stream() // os usuários são colocados em uma stream
                .map(this::convertToDTO) // cada usuário é convertido e abstraído apenas os dados que compõe o DTO
                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
    }

    public UserDTO getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO, List<String> roles){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setPhone(user.getPhone());
        user.setRole(user.getRole());
        user.setAddress(user.getAddress());
        user.setSales(user.getSales());
        user.setItemCart(user.getItemCart());
        userRepository.save(user);

        List<UserRole> userRoleList = roles.stream().map(roleName -> {
            Optional<Role> possibleRoleName = roleRepository.findByRolename(roleName);
            if(possibleRoleName.isPresent()){
                Role role = possibleRoleName.get();
                return new UserRole(user, role);
            }

            return null;
        }).filter(role -> role != null).collect(Collectors.toList());

        userRoleRepository.saveAll(userRoleList);

        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(user.getPassword());
            user.setPhone(user.getPhone());
            user.setRole(user.getRole());
            user.setAddress(user.getAddress());
            user.setSales(user.getSales());
            user.setItemCart(user.getItemCart());
            userRepository.save(user);

            return convertToDTO(user);
        }

        return null;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setPhone(user.getPhone());
        user.setRole(user.getRole());
        user.setAddress(user.getAddress());
        user.setSales(user.getSales());
        user.setItemCart(user.getItemCart());

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public User getUserWithPermissions(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();
        List<String> permissions = userRoleRepository.findPermissionsByUser(user);
        user.setRole(permissions);

        return user;
    }
}
