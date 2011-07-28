package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoGame {
    Game save(Atomic at, Game game) throws DaoException;

    void saveGame(Atomic at, Game g) throws DaoException;

    Game getGame(String gameKeyStr) throws DaoException;

    Game getGame(Atomic at, String gameKeyString) throws DaoException;

    List<Game> getGamesByInviteeKey(User user) throws DaoException;

    List<Game> getGamesByAnswers(List<Answer> as) throws DaoException;

    List<Game> getTheirTurnGames(User user) throws DaoException;

    void cancelGameInvite(Atomic at, Game game) throws DaoException;
}
