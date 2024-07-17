package com.backend.e_commerce.model.dao;

import com.backend.e_commerce.model.LocalUser;
import com.backend.e_commerce.service.UserService;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocalUserDao extends CrudRepository<LocalUser, Long> {
    Optional<LocalUser> findByUsernameIgnoreCase(String userName);
    Optional<LocalUser> findByEmailIgnoreCase(String email);

}
