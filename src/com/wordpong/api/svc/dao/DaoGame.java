package com.wordpong.api.svc.dao;

import com.wordpong.api.model.Game;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoGame {
	Game save(Game game) throws DaoException;
}
