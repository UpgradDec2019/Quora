package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;

@Service
public class SignoutBusinessService {

	@Autowired
	private UserDao userDao;

	/**
	 * @param accessToken the first {@code String} to signout a user.
	 * @return List of QuestionEntity objects.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public UserAuthEntity signOutService(String accessToken) throws SignOutRestrictedException {
		UserAuthEntity userAuthTokenEntity = null;
		// check user sign in or not
		userAuthTokenEntity = userDao.getUserAuthToken(accessToken);
		if (userAuthTokenEntity != null) {
			final ZonedDateTime now = ZonedDateTime.now();
			userAuthTokenEntity.setLogoutAt(now);
			userAuthTokenEntity = userDao.updateUserLogOut(userAuthTokenEntity);
		} else {
			// if user is not sign in then throws exception
			throw new SignOutRestrictedException("SGR-001", "User is not Signed in");
		}
		return userAuthTokenEntity;
	}
}
