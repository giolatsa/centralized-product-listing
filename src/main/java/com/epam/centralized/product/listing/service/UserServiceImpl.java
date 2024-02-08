package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.model.enums.UserStatus;
import com.epam.centralized.product.listing.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUserById(User user, Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userRepository.save(user);

    }

    @Override
    public boolean checkIfValidOldPassword(User user, String currentPassword) {

        return bCryptPasswordEncoder.matches(currentPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);

    }

    @Override
    public void createUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRegisterDate(LocalDateTime.now());


        userRepository.save(user);

    }
}
