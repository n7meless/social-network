package dev.socialnetwork.service;

import dev.socialnetwork.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(long id);
    Optional<Account> findByEmail(String email);

    List<Account> findAll();
    Account save(Account account);


}
