package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoInviteGame {

	List<InviteGame> getGameActivePlayerByInviteeKey(User user)
			throws DaoException;

	void createGames(Atomic at, User invitee, User inviter) throws DaoException;

	void ignoreInvitation(String gameInviteKeyStr) throws DaoException;

	InviteGame getInviteGame(String inviteGameKeyString) throws DaoException;

	void removeInviteGame(Atomic at, InviteGame inviteGame) throws DaoException;
}
