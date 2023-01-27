package dev.socialnetwork.service.impl;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.model.Status;
import dev.socialnetwork.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User doesn't exists"));

        Collection<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(account.getEmail(),
                account.getPassword(),
                account.getStatus().equals(Status.ACTIVE),
                account.getStatus().equals(Status.ACTIVE),
                account.getStatus().equals(Status.ACTIVE),
                account.getStatus().equals(Status.ACTIVE),
                authorities);
    }
}
