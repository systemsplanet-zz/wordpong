package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.FriendRequest;
import com.wordpong.api.model.User;

public interface DaoFriendRequest {
    
    List<FriendRequest> getFriendRequestsByKey(User user);

}
