package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.User;
import com.uece.coffeebreak.entity.enums.Role;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.UserRepository;
import com.uece.coffeebreak.shared.StockDTO;
import com.uece.coffeebreak.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(user -> new ModelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return new ModelMapper().map(user, UserDTO.class);
    }

    public UserDTO findByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return new ModelMapper().map(user, UserDTO.class);
    }

    public List<UserDTO> findByName(String name) {
        List<User> users = repository.findByNameContaining(name);
        return users.stream()
                .map(user -> new ModelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO insert(UserDTO userDTO) {
        userDTO.setId(null);
        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.CLIENT);
        }
        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user = repository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    public void delete(Long id) {
        try {
            User user = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(userDTO, user);
        if (!userDTO.getPassword().equals(user.getPassword())) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }
        user = repository.save(user);
        return mapper.map(user, UserDTO.class);
    }

    public boolean hasEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
}
