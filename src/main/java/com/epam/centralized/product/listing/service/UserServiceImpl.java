package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.exception.UserNotFoundException;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.model.enums.UserStatus;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public User findByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public User updateUserById(User user, Long id) {
    userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    return userRepository.save(user);
  }

  @Override
  public boolean checkIfValidOldPassword(User user, String currentPassword) {

    return bCryptPasswordEncoder.matches(currentPassword, user.getPassword());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void changeUserPassword(User user, String newPassword) {
    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUser(User user) {

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setUserRole(UserRole.CUSTOMER);
    user.setUserStatus(UserStatus.ACTIVE);
    user.setRegisterDate(LocalDateTime.now());

    userRepository.save(user);
  }
}
