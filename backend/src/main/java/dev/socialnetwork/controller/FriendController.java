package dev.socialnetwork.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.socialnetwork.entity.Account;
import dev.socialnetwork.entity.Friend;
import dev.socialnetwork.exception.AccountNotFoundError;
import dev.socialnetwork.model.FriendStatus;
import dev.socialnetwork.service.AuthService;
import dev.socialnetwork.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final AuthService authService;
    private final FriendService friendService;

    @Autowired
    public FriendController(AuthService authService, FriendService friendService) {
        this.authService = authService;
        this.friendService = friendService;
    }

    record FriendRequest(long friend, String token) {
    }

    @PostMapping("/send")
    public void send(@RequestBody FriendRequest friendRequest) {
        Optional<Account> account =
                Optional.of(authService.getAccountFromToken(friendRequest.token()));
        if (!account.isEmpty()) friendService.saveFriend(account.get(), friendRequest.friend());
        else throw new AccountNotFoundError();
    }

    record MyFriendResponse(long id, LocalDate date, @JsonProperty("my_friend") Account myFriend, FriendStatus status){};

    @PostMapping
    public List<MyFriendResponse> getFriendList(FriendRequest friendRequest) {
        if (Optional.of(authService.getAccountFromToken(friendRequest.token())).isEmpty())
            throw new AccountNotFoundError();

        List<MyFriendResponse> myFriends = friendService.getFriendList(friendRequest.friend()).stream()
                .map(friend -> new MyFriendResponse(friend.getId(), friend.getDate(),
                        friend.getFirstAccount().getId()==friendRequest.friend?friend.getSecondAccount():friend.getFirstAccount(),
                        friend.getStatus()))
                .collect(Collectors.toList());
        return myFriends;
    }

    @PostMapping("/accept")
    public void accept(@RequestBody FriendRequest friendRequest) {
        Optional<Account> account =
                Optional.of(authService.getAccountFromToken(friendRequest.token()));
        if (!account.isEmpty()) friendService.accept(account.get(), friendRequest.friend());
        else throw new AccountNotFoundError();
    }

}
