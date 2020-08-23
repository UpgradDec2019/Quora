package com.upgrad.quora.api.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
@RequestMapping("/")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/**
	 * 
	 * @param accessToken
	 * @param questionRequest
	 * @return
	 * @throws AuthorizationFailedException
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/question/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionResponse> createQuestion(@RequestHeader("authorization") final String accessToken,
			QuestionRequest questionRequest) throws AuthorizationFailedException {
		QuestionEntity questionEntity = new QuestionEntity();
		questionEntity.setContent(questionRequest.getContent());
		questionEntity = questionService.createQuestion(questionEntity, accessToken);
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setId(questionEntity.getUuid());
		questionResponse.setStatus("QUESTION CREATED");
		return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 * @throws AuthorizationFailedException
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(
			@RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException {
		List<QuestionEntity> questions = questionService.getAllQuestions(accessToken);
		List<QuestionDetailsResponse> questionDetailResponses = new ArrayList<>();
		for (QuestionEntity questionEntity : questions) {
			QuestionDetailsResponse questionDetailResponse = new QuestionDetailsResponse();
			questionDetailResponse.setId(questionEntity.getUuid());
			questionDetailResponse.setContent(questionEntity.getContent());
			questionDetailResponses.add(questionDetailResponse);
		}
		return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailResponses, HttpStatus.OK);
	}

	/**
	 * 
	 * @param accessToken
	 * @param questionId
	 * @param questionEditRequest
	 * @return
	 * @throws AuthorizationFailedException
	 * @throws InvalidQuestionException
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionEditResponse> editQuestion(@RequestHeader("authorization") final String accessToken,
			@PathVariable("questionId") final String questionId, QuestionEditRequest questionEditRequest)
			throws AuthorizationFailedException, InvalidQuestionException {
		QuestionEntity questionEntity = questionService.editQuestion(accessToken, questionId,
				questionEditRequest.getContent());
		QuestionEditResponse questionEditResponse = new QuestionEditResponse();
		questionEditResponse.setId(questionEntity.getUuid());
		questionEditResponse.setStatus("QUESTION EDITED");
		return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * @param accessToken
	 * @param questionId
	 * @return
	 * @throws AuthorizationFailedException
	 * @throws InvalidQuestionException
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}")
	public ResponseEntity<QuestionDeleteResponse> deleteQuestion(
			@RequestHeader("authorization") final String accessToken,
			@PathVariable("questionId") final String questionId)
			throws AuthorizationFailedException, InvalidQuestionException {

		QuestionEntity questionEntity = questionService.deleteQuestion(accessToken, questionId);
		QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse();
		questionDeleteResponse.setId(questionEntity.getUuid());
		questionDeleteResponse.setStatus("QUESTION DELETED");
		return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * @param accessToken
	 * @param userId
	 * @return
	 * @throws AuthorizationFailedException
	 * @throws UserNotFoundException
	 */
	@RequestMapping(method = RequestMethod.GET, path = "question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<QuestionDetailsResponse>> getQuestionByUserId(
			@RequestHeader("authorization") final String accessToken, @PathVariable("userId") String userId)
			throws AuthorizationFailedException, UserNotFoundException {

		List<QuestionEntity> questions = questionService.getAllQuestionsByUser(userId, accessToken);
		List<QuestionDetailsResponse> questionDetailResponses = new ArrayList<>();
		for (QuestionEntity questionEntity : questions) {
			QuestionDetailsResponse questionDetailResponse = new QuestionDetailsResponse();
			questionDetailResponse.setId(questionEntity.getUuid());
			questionDetailResponse.setContent(questionEntity.getContent());
			questionDetailResponses.add(questionDetailResponse);
		}
		return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailResponses, HttpStatus.OK);
	}
}