package dev.socialnetwork.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import dev.socialnetwork.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_account")
    @JsonProperty("first_account")
    private Account firstAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_account")
    @JsonProperty("second_account")
    private Account secondAccount;

    @Column(name = "text")
    private String text;
    @Column(name = "created_date")
    private LocalDate date;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private MessageStatus messageStatus;

    public enum MessageStatus  {
        ACCEPTED, NOT_ACCEPTED

    }
    public static ChatMessage of(Account firstAccount, Account secondAccount, String text){
        return new ChatMessage(firstAccount, secondAccount, text, LocalDate.now(), MessageStatus.NOT_ACCEPTED);
    }

    private ChatMessage(Account firstAccount, Account secondAccount, String text, LocalDate date, MessageStatus status) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.text = text;
        this.date = date;
        this.messageStatus = status;
    }
    public ChatMessage(){
    }
}
