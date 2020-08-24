package com.upgrad.quora.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 
	 * @param userId
	 * @return null
	 */
	public UserEntity getUserById(final String userId) {
		try {
			return entityManager.createNamedQuery("userByUserId", UserEntity.class).setParameter("userId", userId)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	public UserEntity createUser(UserEntity userEntity) {
		entityManager.persist(userEntity);
		return userEntity;
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public UserEntity getUserByUserName(final String userName) {
		try {
			return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public UserEntity getUserByEmail(final String email) {
		try {
			return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public void updateUserEntity(final UserEntity updatedUserEntity) {
		entityManager.merge(updatedUserEntity);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserEntity deleteUser(final String userId) {
		UserEntity deleteUser = getUserById(userId);
		if (deleteUser != null) {
			this.entityManager.remove(deleteUser);
		}
		return deleteUser;
	}
	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public UserAuthEntity getUserAuthToken(final String accessToken) {
		try {
			return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthEntity.class)
					.setParameter("accessToken", accessToken).getSingleResult();
		} catch (NoResultException nre) {

			return null;
		}
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserEntity authenticateUser(final String userName, final String password) {
		try {
			return entityManager.createNamedQuery("authenticateUserQuery", UserEntity.class)
					.setParameter("userName", userName).setParameter("password", password).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param userAuthTokenEntity
	 * @return
	 */
	public UserAuthEntity createAuthToken(final UserAuthEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

	/**
	 * 
	 * @param userAuthTokenEntity
	 * @return
	 */
	public UserAuthEntity updateUserLogOut(final UserAuthEntity userAuthTokenEntity) {
		try {
			return entityManager.merge(userAuthTokenEntity);
		} catch (NoResultException nre) {
			return null;
		}
	}

}