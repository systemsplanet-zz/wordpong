package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Predicate;
import com.wordpong.api.meta.InviteGameMeta;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoInviteGameImpl extends DaoBase<InviteGame> implements
		DaoInviteGame {
	private static final Logger log = Logger.getLogger(DaoInviteGameImpl.class
			.getName());

	@Override
	public List<InviteGame> getGameActivePlayerByInviteeKey(User user)
			throws DaoException {
		List<InviteGame> result = null;
		InviteGameMeta e = InviteGameMeta.get();
		try {
			Key k = user.getKey();
			result = Datastore.query(e).filter(e.activePlayerKey.equal(k))
					.asList();

		} catch (Exception ex) {
			throw new DaoException("Err:" + ex.getMessage());
		}
		return result;
	}

	@Override
	// create a new game for invitee and a new game for inviter
	public void createGames(Atomic at, User invitee, User inviter)
			throws DaoException {
		final String msg = "createGames: invitee:" + invitee + " inviter:"
				+ inviter;
		try {
			InviteGame inviteeGame = new InviteGame();
			Key inviteeKey = invitee.getKey();
			inviteeGame.setActivePlayerKey(inviteeKey);
			inviteeGame.setInviteeKey(invitee.getKey());
			inviteeGame.setInviterKey(inviter.getKey());
			inviteeGame.setInviteeDetails(invitee.getDetails());
			inviteeGame.setInviterDetails(inviter.getDetails());
			at.put(inviteeGame);

			InviteGame inviterGame = new InviteGame();
			Key inviterKey = inviter.getKey();
			inviterGame.setActivePlayerKey(inviterKey);
			inviterGame.setInviteeKey(inviter.getKey());
			inviterGame.setInviterKey(invitee.getKey());
			inviterGame.setInviteeDetails(inviter.getDetails());
			inviterGame.setInviterDetails(invitee.getDetails());
			at.put(inviterGame);
			log.finer(msg);
		} catch (Exception e) {
			log.warning(msg + " err:" + e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void ignoreInvitation(String gameInviteKeyStr) throws DaoException {
		if (gameInviteKeyStr == null)
			throw new DaoException("key cant be null");
		final Key k = KeyFactory.stringToKey(gameInviteKeyStr);
		try {
			final String msg = "ignoreInvitation: keyStr:" + gameInviteKeyStr
					+ " key:" + k;
			Predicate<Atomic> WORK = new Predicate<Atomic>() {
				public boolean apply(Atomic at) {
					boolean result = false;
					try {
						InviteGame fi = get(k);
						fi.setIgnored(true);
						put(fi);
						result = true;
					} catch (Exception e) {
					}
					return result;
				}

				public String toString() {
					return msg;
				}
			};
			Atomic.transact(WORK);
		} catch (Exception e) {
			String m = "ignoreInvitation failed. keyStr:" + gameInviteKeyStr
					+ " Err:" + e.getMessage();
			log.warning(m);
			throw new DaoException(m);
		}
	}

	@Override
	public InviteGame getInviteGame(String inviteGameKeyString)
			throws DaoException {
		InviteGame result = null;
		try {
			if (inviteGameKeyString == null)
				throw new DaoException(
						"getInviteGame inviteGameKeyString cant be null");
			Key k = KeyFactory.stringToKey(inviteGameKeyString);
			result = get(k);
		} catch (Exception e) {
			String m = "getInviteGame failed. keyStr:" + inviteGameKeyString
					+ " Err:" + e.getMessage();
			log.warning(m);
			throw new DaoException(m);
		}
		return result;
	}

	@Override
	public void removeInviteGame(Atomic at, InviteGame inviteGame)
			throws DaoException {
		if (at != null && inviteGame != null) {
			Key k = inviteGame.getKey();
			if (k != null) {
				at.delete(k);
				log.info("removeInviteGame InviteGame:" + k);
			}
		}
	}

	@Override
	public List<InviteGame> getGameInvitesByInviterKey(User user)
			throws DaoException {
		List<InviteGame> result = null;
		InviteGameMeta e = InviteGameMeta.get();

		try {
			Key k = user.getKey();
			result = Datastore.query(e).filter(e.inviterKey.equal(k)).asList();

		} catch (Exception ex) {
			throw new DaoException("getGameInvitesByInviterKey Err:"
					+ ex.getMessage());
		}
		return result;
	}

	@Override
	public void withdrawInvitation(String inviteGameKeyString)
			throws DaoException {
		Key k = KeyFactory.stringToKey(inviteGameKeyString);
		delete(k);
	}
}
