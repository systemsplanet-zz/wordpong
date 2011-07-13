package com.wordpong.app.util.secure;

public class Role {
    public static final String ADMIN = "admin";

    private Role() {
    }

    public static boolean isAdmin(String role) {
        return role.equals(ADMIN);
    }
}
