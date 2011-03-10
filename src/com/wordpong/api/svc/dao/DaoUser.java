package com.wordpong.api.svc.dao;

import com.wordpong.api.model.User;

public interface DaoUser {
	User save(User u) throws DaoException;
	User findByEmail(String email) throws DaoException;
}
