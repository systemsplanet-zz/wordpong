package com.wordpong.api.svc;

import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.WPServiceException;

public interface SvcUser {
    User createUser(User user) throws WPServiceException;

    User save(User u) throws WPServiceException;

    User findByEmail(String email) throws WPServiceException;

    User getByKey(User u) throws WPServiceException;

    void purgeExpiredPasswordChangeRequests() throws WPServiceException;

    public String createPasswordChangeRequest(String email) throws WPServiceException;

    public PasswordChangeRequest getPasswordChangeRequest(String randomId, String email) ;

}
