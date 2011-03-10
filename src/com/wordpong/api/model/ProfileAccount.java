package com.wordpong.api.model;

import java.io.Serializable;
import java.net.URI;

public class ProfileAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _id;
	private String _email;
	private String _firstName;
	private String _lastName;
	private URI _imageUri;

	public String geId() {
		return _id;
	}

	public void setId(String id) {
		_id = id;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public URI getImageUri() {
		return _imageUri;
	}

	public void setImageUri(URI imageUri) {
		_imageUri = imageUri;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		_email = email;
	}

}
