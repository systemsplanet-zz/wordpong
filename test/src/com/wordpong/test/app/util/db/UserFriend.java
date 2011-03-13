package com.wordpong.test.app.util.db;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Id;


public class UserFriend {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	// unindexed
	private String name;

	// unindexed
	private List<Long> friends = new ArrayList<Long>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getFriends() {
		return friends;
	}

	public void setFriends(List<Long> friends) {
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
