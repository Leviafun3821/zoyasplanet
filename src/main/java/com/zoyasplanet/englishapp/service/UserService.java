package com.zoyasplanet.englishapp.service;

import com.zoyasplanet.englishapp.dto.UserDTO;

import java.util.List;


public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);

    UserDTO createClientAndPayment(UserDTO userDTO, double amount);
}
