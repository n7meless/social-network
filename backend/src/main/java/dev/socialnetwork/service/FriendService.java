package dev.socialnetwork.service;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.entity.Friend;
import dev.socialnetwork.exception.AccountNotFoundError;
import dev.socialnetwork.model.FriendStatus;
import dev.socialnetwork.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final AccountService accountService;

    @Autowired
    public FriendService(FriendRepository friendRepository, AccountService accountService) {
        this.friendRepository = friendRepository;
        this.accountService = accountService;
    }

    public void saveFriend(Account initiator, Long friendId) {
        Account second = accountService.findById(friendId).orElseThrow(AccountNotFoundError::new);

        Friend friend = new Friend();

        Account first = initiator;
        if (initiator.getId() > friendId) {
            first = second;
            second = initiator;
        }

        if (!friendRepository.existsByFirstAccountAndSecondAccount(initiator, second)) {
            friend.setFirstAccount(first);
            friend.setSecondAccount(second);
            friend.setDate(LocalDate.now());
            friend.setStatus(FriendStatus.SENT);
            friend.setInitiatorId(initiator.getId());
            friendRepository.save(friend);
        }

    }

    public List<Friend> getFriendList(long id){
        Account currAccount = accountService
                .findById(id)
                .orElseThrow(AccountNotFoundError::new);
        return friendRepository
                .findByFirstAccountOrSecondAccount(currAccount, currAccount);
    }
    public void accept(Account first, long friendId){
        Account second = accountService.findById(friendId).orElseThrow(AccountNotFoundError::new);
       Friend friend = friendRepository.findToAccept(first.getId(), second.getId()).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "u not have friend requests")
        );
        System.out.println(friendRepository.findToAccept(first.getId(), second.getId()));
        friend.setStatus(FriendStatus.ACCEPT);
        friendRepository.save(friend);
    }
}
