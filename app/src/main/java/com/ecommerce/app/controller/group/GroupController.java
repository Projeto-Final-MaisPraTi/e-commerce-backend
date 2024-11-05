//package com.ecommerce.app.controller.group;
//
//import com.ecommerce.app.model.grupo.Group;
//import com.ecommerce.app.repository.grupo.GroupRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/grupos")
//@RequiredArgsConstructor
//public class GroupController {
//
//    private final GroupRepository groupRepository;
//
//    @PostMapping
//    @Transactional
//    @PreAuthorize("hasRole('ADMIN")
//    public ResponseEntity<Group> salvar(@RequestBody Group group){
//        groupRepository.save(group);
//        return ResponseEntity.ok(group);
//    }
//
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<Group>> listar(){
//        return ResponseEntity.ok(groupRepository.findAll());
//    }
//
//}
