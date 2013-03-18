/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.cookingapp.domain.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

/**
 * The class that represent users.
 */
@SuppressWarnings("unchecked")
public class User implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404494181L;

	/**
	 * For Sorting.
	 */
	@SuppressWarnings("rawtypes")
	public static Comparator<User> ORDER_DISPLAYNAME = new Comparator() {
		@Override
		public int compare(final Object o1, final Object o2) {
			return ((User) o1).getDisplayName().compareTo(
					((User) o2).getDisplayName());
		}
	};

	/**
	 * Id of the user.
	 */
	private String login;

	/**
	 * Display Name of the user.
	 */
	private String displayName;

	/**
	 * True for administrators.
	 */
	private boolean admin;

	/**
	 * The prefered language.
	 */
	private String language;

	/**
	 * Bean constructor.
	 */
	public User() {
		super();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		return login.equals(((User) obj).getLogin());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "User#" + hashCode() + "[login=[" + login + "], displayName=["
				+ displayName + "], admin=[" + admin + "], language=["
				+ language + "]]";
	}

	/**
	 * @return the login of the user.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 */
	public void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            The displayName to set.
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param admin
	 *            The admin to set.
	 */
	public void setAdmin(final boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return Returns the admin.
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(final String language) {
		this.language = language;
	}

	/**
	 * @return the user display language.
	 */
	public String getDisplayLanguage() {
		final Locale locale = new Locale(language);
		return locale.getDisplayLanguage(locale);
	}

}