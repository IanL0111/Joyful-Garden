package com.joyfulgarden.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.joyfulgarden.model.Members;

public class AuthMembersDetailsService implements UserDetailsService {
	
	@Autowired
	private MembersService mService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username: "+username);
		Members members = mService.findByUsername(username);
		 if (members == null) {
	        	throw new UsernameNotFoundException("找不到用户: " + username);
	        }

	        List<GrantedAuthority> authorities = getAuthorities();
	        
	        return new User(members.getUsername(), members.getPassword(), authorities);
	}

	private List<GrantedAuthority> getAuthorities() {
	
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		return null;
//	}

}
