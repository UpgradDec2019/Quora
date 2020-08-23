package com.upgrad.quora.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AdminService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
@RequestMapping("/")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * @param accessToken
	 * @param userId
	 * @return
	 * @throws AuthorizationFailedException
	 * @throws UserNotFoundException
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDeleteResponse> deleteUser(@RequestHeader("authorization") final String accessToken,
			@PathVariable("userId") String userId) throws AuthorizationFailedException, UserNotFoundException {

		UserEntity userEntity = adminService.deleteUser(userId, accessToken);

		UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(userEntity.getUuid())
				.status("USER SUCCESSFULLY DELETED");

		return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
	}
}