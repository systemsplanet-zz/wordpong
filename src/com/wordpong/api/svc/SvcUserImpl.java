package com.wordpong.api.svc;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoException;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;

public class SvcUserImpl implements SvcUser {
	private DaoUser daoUser = DaoUserFactory.getUserDao();

	public User save(User u) throws WPServiceException {
		try {
			u = daoUser.save(u);
		} catch (DaoException e) {
			throw new WPServiceException(e.getMessage());
		}
		return u;
	}

	public User findByEmail(String email) throws WPServiceException {
		User result = null;
		try {
			result = daoUser.findByEmail(email);
		} catch (DaoException e) {
			throw new WPServiceException(e.getMessage());
		}
		return result;
	}

}
