package com.sposnor.intellisense.sponsorintellisense.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.auth.AuthToken;
import com.sposnor.intellisense.sponsorintellisense.auth.JwtTokenUtil;
import com.sposnor.intellisense.sponsorintellisense.auth.LoginUser;
import com.sposnor.intellisense.sponsorintellisense.data.model.User;
import com.sposnor.intellisense.sponsorintellisense.mapper.UserMapper;

@RestController
@RequestMapping("/api/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userService;
    
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
    	LOGGER.info(" Generating token. ");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findOne(loginUser.getUsername());
        LOGGER.info(" Setting the token. ");
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token, user.getId(),user.getFirstname(), user.getMiddlename(), user.getLastName()));
    }

}
