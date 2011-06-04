package com.wordpong.api.svc.dao;

import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.Game;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoGameImpl extends DaoBase<Game> implements DaoGame {
	private static final Logger log = Logger.getLogger(DaoGameImpl.class
			.getName());

	public Game save(Atomic at, Game g) throws DaoException {
		if (g == null) {
			throw new DaoException("cant save null game");
		}
		try {
			String action = g.getKey() == null ? "Created" : "Updated";
			Key key = at.put(g);
			log.info(action + " game:" + g + " key:" + key);
		} catch (Exception e) {
			log.warning("unable to save game:" + g);
			throw new DaoException(e.getMessage());
		}
		return g;
	}

}
