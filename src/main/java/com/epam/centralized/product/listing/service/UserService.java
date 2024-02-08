package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.User;

public interface UserService {

    User findByEmail(String email);

    User updateUserById(User user,Long id);

    boolean checkIfValidOldPassword(User user, String currentPassword);

    void changeUserPassword(User user, String newPassword);
}
