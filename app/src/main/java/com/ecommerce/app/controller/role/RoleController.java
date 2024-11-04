package com.ecommerce.app.controller.role;

import com.ecommerce.app.model.role.Role;
import com.ecommerce.app.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN")
    public ResponseEntity<Role> salvar(@RequestBody Role role){
        roleRepository.save(role);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> listar(){
        return ResponseEntity.ok(roleRepository.findAll());
    }

}
