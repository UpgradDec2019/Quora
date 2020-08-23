package com.upgrad.quora.service.business;

import com.upgrad.quora.service.entities.QuestionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class QuestionService {
    @PersistenceContext
    private EntityManager entityManager;
    public QuestionEntity createQuestion(QuestionEntity questionEntity){
      entityManager.persist(questionEntity);
      return questionEntity;
    }
}
