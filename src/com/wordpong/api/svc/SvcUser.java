package com.wordpong.api.svc;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;

public interface SvcUser {
    User save(User u) throws WPServiceException;

    User findByEmail(String email) throws WPServiceException;

    void purgeExpiredPasswordChangeRequests();

    public String createPasswordChangeRequest(String email) throws WPServiceException;

    public PasswordChangeRequest getPasswordChangeRequest(String randomId);
}
