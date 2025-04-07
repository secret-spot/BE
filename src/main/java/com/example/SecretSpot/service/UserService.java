package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //return createUserDetails(userRepository.findByEmail(email)
        //        .orElseThrow(() -> new UsernameNotFoundException("사용자 없음. " + email)));
        return null;
    }

    public UserDetails createUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(passwordEncoder.encode("SOCIAL_LOGIN"))
//                .authorities(new SimpleGrantedAuthority(user.getRole().toString()))
//                .build();
        return null;
    }
}
