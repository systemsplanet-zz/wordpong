package com.wordpong.test.app.util.db;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.wordpong.app.util.servlet.HtmlUtils;

/**
 * Tests for the class {@link HtmlUtils}
 */
public class FriendsTest {
	static final int MAX_FRIENDS = 500;
	private static final Logger log = Logger.getLogger(FriendsTest.class
			.getName());

	@Before
	public void setUp() throws Exception {
		ObjectifyService.register(UserFriend.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateBatchGetFriends() {
		//Objectify ofy = ObjectifyService.beginTransaction();
		Objectify ofy = ObjectifyService.begin();
		assertTrue(ofy != null);
		UserFriend parent = new UserFriend();
		Key<UserFriend> parentKey = null;
		try {
			assertTrue(parent != null);
			List<Long> friends = new ArrayList<Long>(MAX_FRIENDS);
			// Write list of friends
			long start = System.currentTimeMillis();
			for (int i = 0; i < MAX_FRIENDS; i++) {
				UserFriend u = new UserFriend();
				u.setName("name" + i);
				assertTrue(u != null);
				Key<UserFriend> uk = ofy.put(u);
				assertTrue(uk != null);
				assertTrue(u.getId() != null);
				friends.add(u.getId());
			}
			log.info("added users in " + (System.currentTimeMillis() - start)
					+ " ms");

			// write the parent
			start = System.currentTimeMillis();
			parentKey = ofy.put(parent);
			assertTrue(parentKey != null);
			assertTrue(parent.getId() != null);
			log.info("added parent in " + (System.currentTimeMillis() - start)
					+ " ms");
			//ofy.getTxn().commit();
		} finally {
			//if (ofy.getTxn().isActive())
			//	  ofy.getTxn().rollback();
		}

		ofy = ObjectifyService.begin();
		assertTrue(ofy != null);
		// read the parent
		long start = System.currentTimeMillis();
		UserFriend newParent = ofy.get(parentKey);
		log.info("read parent in " + (System.currentTimeMillis() - start)
				+ " ms");
		assertTrue(newParent.getFriends().size() == parent.getFriends().size());

		// verify friend ids were read
		List<Long> friends = newParent.getFriends();
		for (int i = 0; i < friends.size(); i++) {
			assertTrue(newParent.getFriends().get(i) == parent.getFriends()
					.get(i));
		}

		// Create list of keys to all the friends
		List<Key<UserFriend>> friendKeys = new ArrayList<Key<UserFriend>>();
		for (int i = 0; i < friends.size(); i++) {
			long id = friends.get(i);
			Key<UserFriend> k = new Key<UserFriend>(UserFriend.class, id);
			friendKeys.add(k);
		}

		// batch read all friends at once
		start = System.currentTimeMillis();
		Map<Key<UserFriend>, UserFriend> newFriends = ofy.get(friendKeys);
		log.info("read all friends in " + (System.currentTimeMillis() - start)
				+ " ms");
		assertTrue(newFriends != null);
		for (int i = 0; i < newFriends.size(); i++) {
			assertTrue(newFriends.get(i).getName().startsWith("name"));
		}
	}
}
