package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.api.meta.GameMeta;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;
import com.wordpong.util.debug.LogUtil;

public class DaoGameImpl extends DaoBase<Game> implements DaoGame {
    private static final Logger log = Logger.getLogger(DaoGameImpl.class.getName());

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
            result = Datastore.query(e).filter(e.inviteeUserKey.equal(k)).asList();

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
            String m = "getGame failed. keyStr:" + gameKeyStr + " Err:" + e.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
        return result;
    }

    @Override
    public List<Game> getGamesByAnswers(List<Answer> as) throws DaoException {
        List<Game> result = new ArrayList<Game>();
        GameMeta e = GameMeta.get();
        for (Answer a : as) {
            try {
                Key k = a.getKey();
                List<Game> games = Datastore.query(e).filter(e.answersKey.equal(k)).asList();
                result.addAll(games);
            } catch (Exception ex) {
                throw new DaoException("getGamesByAnswers answers:" + as + " Err:" + ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<Game> getTheirTurnGames(User user) throws DaoException {
        Set<Key> gameKeys = user.getGameKeys();
        List<Key> gks = new ArrayList<Key>(gameKeys);
        List<Game> result = new ArrayList<Game>();
        try {
            result = get(gks);
        } catch (Exception e) {
            LogUtil.logException("getTheirTurnGames user:" + user, e);
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    public void cancelGameInvite(Atomic at, Game game) throws DaoException {
        if (game != null) {
            Key gameKey = game.getKey();
            if (gameKey != null) {
                at.delete(gameKey);
            }
        }
    }

    @Override
    public Game getGame(Atomic at, String gameKeyString) throws DaoException {
        Key k = KeyFactory.stringToKey(gameKeyString);
        Game result = at.get(Game.class, k);
        return result;
    }

    @Override
    public void saveGame(Atomic at, Game g) throws DaoException {
        at.put(g);
    }

}
