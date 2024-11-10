package com.ecommerce.app.service.user;

import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.infra.enums.Roles;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.address.Address;
//import com.ecommerce.app.model.role.Role;
import com.ecommerce.app.model.user.User;
//import com.ecommerce.app.repository.role.RoleRepository;
import com.ecommerce.app.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    //@Autowired // faz injeção de dependência automática
    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers(){
        // retorna a lista de usuários convertidos e coletados
        return userRepository
                .findAll() // pega todos os usuários do bd
                .stream() // os usuários são colocados em uma stream
                .map(this::convertToDTO) // cada usuário é convertido e abstraído apenas os dados que compõe o DTO
                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
    }

    public UserDTO getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public User createUser(RegisterRequest registerRequest){
        User user = new User();
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()){
            throw new RuntimeException("Email já registrado!");
        }

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhone(user.getPhone());
//        user.setRole(user.getRole());
        user.setAddress(user.getAddress());
        user.setSales(user.getSales());
        user.setItemCart(user.getItemCart());

        // Buscando ou criando roles
//        List<Role> roles = roleNames.stream()
//            .map(roleName -> roleRepository.findByName(roleName)
//            .orElseGet(() -> {
//                Role newRole = new Role();
//                newRole.setName(roleName);
//                return roleRepository.save(newRole);
//            })
//        ).collect(Collectors.toList());
//
//        user.setRoles(roles);

        user.setTypeRole(Roles.CLIENT);

        return userRepository.save(user);

//        return convertToDTO(user);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(user.getPassword());
            user.setPhone(user.getPhone());
//            user.setRole(user.getRole());
            user.setAddress(user.getAddress());
            user.setSales(user.getSales());
            user.setItemCart(user.getItemCart());
            userRepository.save(user);

            return convertToDTO(user);
        }

        return null;
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
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

        // Obtém as roles diretamente, sem precisar de mapear para String
        Roles roles = user.getTypeRole();

        user.setTypeRole(roles);

        return user;
    }

    private UserDTO convertToDTO(User user){
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        user.setPassword(user.getPassword());
//        user.setPhone(user.getPhone());
////        user.setRole(user.getRole());
//        user.setAddress(user.getAddress());
//        user.setSales(user.getSales());
//        user.setItemCart(user.getItemCart());
//
//        return userDTO;
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getTypeRole()
        );
    }
}
