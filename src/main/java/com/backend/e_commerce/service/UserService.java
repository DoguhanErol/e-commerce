package com.backend.e_commerce.service;

import com.backend.e_commerce.api.model.LoginBody;
import com.backend.e_commerce.api.model.RegistrationBody;
import com.backend.e_commerce.exception.UserAlreadyExistException;
import com.backend.e_commerce.model.LocalUser;
import com.backend.e_commerce.model.dao.LocalUserDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private LocalUserDao localUserDao;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(LocalUserDao localUserDao, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDao = localUserDao;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException{
        if (localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() || localUserDao.findByUsernameIgnoreCase(registrationBody.getUserName()).isPresent()){
            throw new UserAlreadyExistException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUserName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user = localUserDao.save(user);
        return user;
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUserName());
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
