package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;

@Service
public class SigninBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    /**
     * @param  username the first {@code String} username of the user.
     * @param  password the second {@code String} password of the user.
     * @return List of QuestionEntity objects.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        UserAuthEntity userAuthEntity = null;
        UserEntity userEntity = userDao.getUserByUserName(username);
        //check userName not exist
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }
        //encrypt password
        final String encryptedPassword = cryptographyProvider.encrypt(password, userEntity.getSalt());

        //send username and encrypted password to userDao
        userEntity = userDao.authenticateUser(username, encryptedPassword);

        if (userEntity != null) {
			// if userName and password match
            //Geneate authention token
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            userAuthEntity = new UserAuthEntity();
            userAuthEntity.setUserEntity(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthEntity.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthEntity.setLoginAt(now);
            userAuthEntity.setExpiresAt(expiresAt);
            userAuthEntity.setUuid(userEntity.getUuid());
            userDao.createAuthToken(userAuthEntity);
        } else {
            throw new AuthenticationFailedException("ATH-002", "Password Failed");
        }
        return userAuthEntity;
    }
}
