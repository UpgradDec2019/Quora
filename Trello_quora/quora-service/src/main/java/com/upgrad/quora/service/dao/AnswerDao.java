package com.upgrad.quora.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.upgrad.quora.service.entity.AnswerEntity;

@Repository
public class AnswerDao {

  @PersistenceContext private EntityManager entityManager;

  /**
   * 
   * @param answerEntity
   * @return
   */
  public AnswerEntity createAnswer(AnswerEntity answerEntity) {
    entityManager.persist(answerEntity);
    return answerEntity;
  }

  /**
   * 
   * @param answerId
   * @return
   */
  public AnswerEntity getAnswerById(final String answerId) {
    try {
      return entityManager
          .createNamedQuery("getAnswerById", AnswerEntity.class)
          .setParameter("uuid", answerId)
          .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  /**
   * 
   * @param answerEntity
   */
  public void updateAnswer(AnswerEntity answerEntity) {
    entityManager.merge(answerEntity);
  }

  /**
   * 
   * @param answerId
   * @return
   */
  public AnswerEntity deleteAnswer(final String answerId) {
    AnswerEntity deleteAnswer = getAnswerById(answerId);
    if (deleteAnswer != null) {
      entityManager.remove(deleteAnswer);
    }
    return deleteAnswer;
  }

  /**
   * 
   * @param questionId
   * @return
   */
  public List<AnswerEntity> getAllAnswersToQuestion(final String questionId) {
    return entityManager
        .createNamedQuery("getAllAnswersToQuestion", AnswerEntity.class)
        .setParameter("uuid", questionId)
        .getResultList();
  }
}