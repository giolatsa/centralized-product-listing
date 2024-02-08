package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        return user.getPassword().equals(currentPassword);
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);

    }
}
