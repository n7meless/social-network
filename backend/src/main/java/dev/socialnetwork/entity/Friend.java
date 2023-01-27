package dev.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.socialnetwork.model.FriendStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Stack;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_date")
    @JsonProperty("created_date")
    private LocalDate date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_account")
    @JsonProperty("first_account")
    private Account firstAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_account")
    @JsonProperty("second_account")
    private Account secondAccount;

    @Column(name = "initiator_id")
    @JsonProperty("initiator_id")
    private long initiatorId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private FriendStatus status;


    public Friend(Account firstAccount, Account secondAccount, long initiatorId, FriendStatus status, LocalDate date) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.initiatorId = initiatorId;
        this.status = status;
        this.date = date;
    }
}
