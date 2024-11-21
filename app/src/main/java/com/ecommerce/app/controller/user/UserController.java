package com.ecommerce.app.controller.user;

import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.service.user.UserService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile() {
        try {
            UserDTO userDTO = userService.getAuthenticatedUserProfile();
            return ResponseEntity.ok(userDTO);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterRequest registerRequest) {
        User user = userService.createUser(registerRequest);
        UserDTO userDTO = userService.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        UserDTO updateUser = userService.updateUser(id, userDTO);
        return updateUser != null ? ResponseEntity.ok(updateUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}