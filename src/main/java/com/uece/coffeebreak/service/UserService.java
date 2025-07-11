package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.User;
import com.uece.coffeebreak.entity.enums.Role;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.UserRepository;
import com.uece.coffeebreak.shared.StockDTO;
import com.uece.coffeebreak.shared.UserDTO;
import com.uece.coffeebreak.shared.UserOrderCountDTO;
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
        List<User> users = repository.findAllUsers();
        return users.stream()
                .map(user -> new ModelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = repository.findUserById(id)
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

    public List<UserOrderCountDTO> countOrdersByUser() {
        List<Object[]> results = repository.countOrdersByUser();
        return results.stream()
                .map(row -> new UserOrderCountDTO(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .toList();
    }

    public List<UserDTO> findClientsOrdersSumGreater(Long id) {
        repository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        List<User> users = repository.findClientsOrdersSumGreater(id);
        return users.stream()
                .map(user -> new ModelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO insert(UserDTO userDTO) {
        if (repository.existsByEmail(userDTO.getEmail())) {
            throw new DatabaseException("E-mail already exists");
        }
        userDTO.setId(null);
        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.CLIENT);
        }
        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.insertUser(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    public void delete(Long id) {
        try {
            repository.findUserById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
            repository.deleteItemsByClientId(id);
            repository.deletePaymentsByClientId(id);
            repository.deleteOrdersByUserId(id);
            repository.deleteUserById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        if (repository.existsByEmail(userDTO.getEmail())) {
            throw new DatabaseException("E-mail already exists");
        }
        User user = repository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(userDTO, user);
        if (!encoder.matches(userDTO.getPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }
        repository.insertUser(user);
        return mapper.map(user, UserDTO.class);
    }

    public boolean hasEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
}
