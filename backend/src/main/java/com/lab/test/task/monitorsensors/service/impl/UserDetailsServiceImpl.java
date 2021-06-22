package com.lab.test.task.monitorsensors.service.impl;

import com.lab.test.task.monitorsensors.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
            .map(this::toUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid username " + username));
    }

    private UserDetails toUserDetails(com.lab.test.task.monitorsensors.entity.User user) {
        return User.withUsername(user.getLogin())
            .password(user.getPassword())
            .authorities(user.getRole().getAuthority())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }
}
