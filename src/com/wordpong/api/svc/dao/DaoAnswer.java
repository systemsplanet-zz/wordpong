package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;

public interface DaoAnswer {
	Answer save(Answer u) throws DaoException;

	List<Answer> getAnswers(User u) throws DaoException;

	Answer getAnswer(String answerKey) throws DaoException;

}
