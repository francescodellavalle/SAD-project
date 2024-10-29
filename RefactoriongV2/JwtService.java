package com.groom.manvsclass.controller;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public boolean isJwtValid(String jwt) {
		try {
			Claims c = Jwts.parser().setSigningKey("mySecretKeyAdmin").parseClaimsJws(jwt).getBody();

			if((new Date()).before(c.getExpiration())) {
				return true;
			}
		} catch(Exception e) {
			System.err.println(e);
		}

		return false;
	}

    // Add other JWT-related methods if necessary
}
