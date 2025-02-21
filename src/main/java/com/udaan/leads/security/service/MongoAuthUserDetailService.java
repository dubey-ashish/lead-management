package com.udaan.leads.security.service;

import com.udaan.leads.security.entity.SystemUser;
import com.udaan.leads.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Service that loads user details from MongoDB and provides them to Spring Security for authentication and authorization.

@Service
public class MongoAuthUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SystemUser systemUser = userRepository.findUserByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Converting the systemRoles of a SystemUser into GrantedAuthority objects (via SimpleGrantedAuthority (class implementing GrantedAuthority interface)), which are then used for authorization in Spring Security.
        systemUser.getSystemRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.name())));

        //public class User implements UserDetails, CredentialsContainer
        //public User(String username, String password, Collection<? extends GrantedAuthority> authorities)  <----Constructor
        //User class contains few other fields as well
        return new User(systemUser.getUsername(), systemUser.getPassword(), grantedAuthorities);
    }

}


