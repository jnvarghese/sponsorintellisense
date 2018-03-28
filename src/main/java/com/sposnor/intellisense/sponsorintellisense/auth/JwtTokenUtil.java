package com.sposnor.intellisense.sponsorintellisense.auth;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sposnor.intellisense.sponsorintellisense.common.Constants;
import com.sposnor.intellisense.sponsorintellisense.data.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Claims getClaimsFromToken(String token) {
	    Claims claims;
	    try {	
	        claims = Jwts.parser()
	                .setSigningKey(Constants.SIGNING_KEY)
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (Exception e) {
	        claims = null;
	    }
	    return claims;
	}
	
    public String getUsernameFromToken(String token) {
       // return getClaimFromToken(token, Claims::getSubject);
    	 String username;
    	    try {
    	        final Claims claims = this.getClaimsFromToken(token);
    	        username = claims.getSubject();
    	    } catch (Exception e) {
    	        username = null;
    	    }
    	    return username;
    }

    public Date getExpirationDateFromToken(String token) {
    	Date expirationDate;
 	    try {
 	        final Claims claims = this.getClaimsFromToken(token);
 	       expirationDate = claims.getExpiration();
 	    } catch (Exception e) {
 	    	expirationDate = null;
 	    }
 	    return expirationDate;       
    }
/*
    public  T getClaimFromToken(String token, Function claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }*/
/*
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }*/

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://devglan.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}