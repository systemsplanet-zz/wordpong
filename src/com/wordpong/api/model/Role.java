package com.wordpong.api.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Role implements Serializable {
	public static final String ADMIN = "admin";
	private static final long serialVersionUID = 1L;
	private String name;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public static boolean isAdmin(String role) {
		return role.equals(ADMIN);
	}

	public static boolean hasRole(List<Role> roles, String role) {
		boolean result = false;
		if (roles != null) {
			result = roles.contains(new Role(role));
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && name != null) {
			try {
				return name.equals(((Role) object).getName());
			} catch (Exception exc) {
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
