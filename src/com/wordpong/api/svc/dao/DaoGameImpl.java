package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.api.meta.GameMeta;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
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

	public List<Game> getGamesByInviteeKey(User user) throws DaoException {
		List<Game> result = null;
		GameMeta e = GameMeta.get();
		try {
			Key k = user.getKey();
			result = Datastore.query(e).filter(e.userKey.equal(k)).asList();

		} catch (Exception ex) {
			throw new DaoException("Err:" + ex.getMessage());
		}
		return result;

	}

	@Override
	public Game getGame(String gameKeyStr) throws DaoException {
		Game result = null;
		try {
			if (gameKeyStr == null)
				throw new DaoException("gameKeyStr cant be null");
			Key k = KeyFactory.stringToKey(gameKeyStr);
			result = get(k);
		} catch (Exception e) {
			String m = "getGame failed. keyStr:" + gameKeyStr + " Err:"
					+ e.getMessage();
			log.warning(m);
			throw new DaoException(m);
		}
		return result;
	}

	@Override
	public void finishGame(String gameKeyStr) throws DaoException {
		try {
			if (gameKeyStr == null)
				throw new DaoException("gameKeyStr cant be null");
			Game g = getGame(gameKeyStr);
			g.setCompleted(true);
			put(g);
		} catch (Exception e) {
			String m = "finishGame failed. keyStr:" + gameKeyStr + " Err:"
					+ e.getMessage();
			log.warning(m);
			throw new DaoException(m);
		}
	}
}
