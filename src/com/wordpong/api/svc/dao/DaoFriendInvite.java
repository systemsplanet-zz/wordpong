package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.User;

public interface DaoFriendInvite {

    void inviteFriends(User user, List<String> emails) throws DaoException;;

}
