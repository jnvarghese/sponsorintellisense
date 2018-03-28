package com.sposnor.intellisense.sponsorintellisense.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthToken implements Authentication {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String token;
	
	private final String firstname;
	    
	private final String middlename;
	    
	private final String lastName;

    public AuthToken(String token, String firstname, String middlename, String lastname) {
        this.token = token;
        this.firstname = firstname;
        this.lastName = lastname;
        this.middlename= middlename;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.firstname +" "+ this.lastName;
    }
}