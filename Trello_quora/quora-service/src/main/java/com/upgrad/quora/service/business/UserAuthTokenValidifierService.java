package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.common.EndPointIdentifier;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;

/**
 * Method to provide service for validating a user authentication token through
 * a access token
 */

@Service
public class UserAuthTokenValidifierService implements EndPointIdentifier {

	@Autowired
	UserDao userDao;

	/**
	 * @param authorizationToken the first {@code String} to check if the access is
	 *                           available.
	 * @return true or false
	 */
	boolean userSignOutStatus(String authorizationToken) {
		UserAuthEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
		ZonedDateTime loggedOutStatus = userAuthTokenEntity.getLogoutAt();
		ZonedDateTime loggedInStatus = userAuthTokenEntity.getLoginAt();
		if (loggedOutStatus != null && loggedOutStatus.isAfter(loggedInStatus)) {
			return true;
		} else
			return false;
	}

}
