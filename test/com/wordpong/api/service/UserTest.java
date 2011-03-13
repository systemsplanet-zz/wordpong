package com.wordpong.api.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserTest extends AppEngineTestCase {

    private User service = new User();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
