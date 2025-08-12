package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.dto.UserDTO;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.exception.UserNotFoundException;
import com.zoyasplanet.englishapp.mapper.UserMapper;
import com.zoyasplanet.englishapp.repository.UserRepository;
import com.zoyasplanet.englishapp.service.PaymentService;
import com.zoyasplanet.englishapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findByIdWithTasks(id)
                .map(UserMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAllWithTasks().stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findByIdWithTasks(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserMapper.INSTANCE.updateEntity(existingUser, userDTO);
        // Проверяем, был ли изменён пароль, и хешируем, если да
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty() &&
                !passwordEncoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        User savedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDTO createClientAndPayment(UserDTO userDTO, double amount) {
        // Создание пользователя
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хеширование
        User savedUser = userRepository.save(user);

        // Создание DTO для платежа и делегирование PaymentService
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setUserId(savedUser.getId());
        paymentDTO.setAmount(amount);
        paymentDTO.setDueDate(LocalDate.now().withDayOfMonth(7)); // 7-е следующего месяца
        paymentDTO.setStatus(PaymentDTO.PaymentStatus.PENDING);
        paymentService.createPayment(paymentDTO); // Используем существующий метод

        return UserMapper.INSTANCE.toDTO(savedUser);
    }

}
