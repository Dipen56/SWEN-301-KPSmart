package model.user;

/**
 * Clerk class of the KPS, which has a username,
 * password and a boolean to represent manager status.
 *
 * @author Betty
 */

public class Clerk extends model.user.Staff {
	public Clerk(int uid, String name, String pass) {
		super(uid, name, pass);
	}

}

