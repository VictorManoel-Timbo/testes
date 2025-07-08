package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.UserService;
import com.uece.coffeebreak.shared.UserDTO;
import com.uece.coffeebreak.view.model.request.UserRequest;
import com.uece.coffeebreak.view.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserDTO> usersDTO =  service.findAll();
        ModelMapper mapper = new ModelMapper();
        List<UserResponse> response = usersDTO.stream().map(user -> mapper.map(user, UserResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserDTO userDTO = service.findById(id);
        UserResponse response = new ModelMapper().map(userDTO, UserResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponse> findByEmail(@RequestParam(value = "email") String email) {
        UserDTO userDTO = service.findByEmail(email);
        UserResponse response = new ModelMapper().map(userDTO, UserResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam(value = "name") String name) {
        List<UserDTO> userDTO = service.findByName(name);
        ModelMapper mapper = new ModelMapper();
        List<UserResponse> response = userDTO.stream().map(user -> mapper.map(user, UserResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody UserRequest userRequest) {
        UserDTO userDTO = new ModelMapper().map(userRequest, UserDTO.class);
        userDTO = service.insert(userDTO);
        UserResponse response = new ModelMapper().map(userDTO, UserResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id ,@RequestBody UserRequest userRequest) {
        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(userRequest, UserDTO.class);
        userDTO = service.update(id, userDTO);
        UserResponse response = mapper.map(userDTO, UserResponse.class);
        return ResponseEntity.ok().body(response);
    }
}
