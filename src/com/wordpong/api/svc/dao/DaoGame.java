package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoGame {
	Game save(Atomic at, Game game) throws DaoException;

	Game getGame(String gameKeyStr) throws DaoException;

	List<Game> getGamesByInviteeKey(User user) throws DaoException;
}
