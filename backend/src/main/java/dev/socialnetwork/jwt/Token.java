package dev.socialnetwork.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.socialnetwork.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "token")
@ToString
@Setter
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "issue_at")
    private LocalDateTime issueAt;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    public Token(String refreshToken, LocalDateTime issueAt, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.issueAt = issueAt;
        this.expiredAt = expiredAt;
    }

    public Token() {
    }
}
