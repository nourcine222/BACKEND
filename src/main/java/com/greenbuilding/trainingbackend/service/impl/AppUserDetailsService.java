package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.entity.AppUser;
import com.greenbuilding.trainingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// Adapts AppUser records to Spring Security's UserDetails contract.
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user with its role so Spring Security can build authorities.
        AppUser appUser = userRepository.findWithRoleByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Spring Security expects authorities prefixed with ROLE_ for role checks.
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + appUser.getRole().getName().name())
        );

        return new User(appUser.getLogin(), appUser.getPassword(), authorities);
    }
}
