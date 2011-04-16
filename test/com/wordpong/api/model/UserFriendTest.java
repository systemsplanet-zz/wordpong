package com.wordpong.api.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserFriendTest extends AppEngineTestCase {

    private UserFriend model = new UserFriend();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
