package com.backend.e_commerce.service;

import com.backend.e_commerce.api.model.LoginBody;
import com.backend.e_commerce.api.model.RegistrationBody;
import com.backend.e_commerce.exception.EmailFailureException;
import com.backend.e_commerce.exception.UserAlreadyExistException;
import com.backend.e_commerce.exception.UserNotVerifiedException;
import com.backend.e_commerce.model.LocalUser;
import com.backend.e_commerce.model.VerificationToken;
import com.backend.e_commerce.model.dao.LocalUserDao;
import com.backend.e_commerce.model.dao.VerificationTokenDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private LocalUserDao localUserDao;
    private VerificationTokenDao verificationTokenDao;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private  EmailService emailService;

    public UserService(VerificationTokenDao verificationTokenDao, LocalUserDao localUserDao, EncryptionService encryptionService, JWTService jwtService, EmailService emailService) {
        this.verificationTokenDao = verificationTokenDao;
        this.localUserDao = localUserDao;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailureException {
        if (localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() || localUserDao.findByUsernameIgnoreCase(registrationBody.getUserName()).isPresent()){
            throw new UserAlreadyExistException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUserName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);


        user = localUserDao.save(user);
        return user;
    }

    public VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUserName());
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                if (user.getIsEmailVerified()){

                    return jwtService.generateJWT(user);

                }else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 || verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() -(60 * 60 * 1000)));
                    if (resend){
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDao.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }
    @Transactional
    public boolean verifyUser(String token){
    Optional<VerificationToken> opToken = verificationTokenDao.findByToken(token);
    if (opToken.isPresent()){
        VerificationToken verificationToken = opToken.get();
        LocalUser user = verificationToken.getUser();
        if (!user.getIsEmailVerified()){
            user.setIsEmailVerified(true);
            localUserDao.save(user);
            verificationTokenDao.deleteByUser(user);
            return true;
        }
    }
    return false;
    }
}
