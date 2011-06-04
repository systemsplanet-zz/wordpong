package com.wordpong.api.svc.dao;

import com.wordpong.api.model.Game;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoGame {
	Game save(Atomic at, Game game) throws DaoException;
}
