package dev.socialnetwork.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.socialnetwork.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "password_recovery")
public class PasswordRecovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "token")
    private String token;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public PasswordRecovery(String token) {
        this.token = token;
    }

    public PasswordRecovery() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordRecovery that = (PasswordRecovery) o;

        if (id != that.id) return false;
        if (!token.equals(that.token)) return false;
        return account.equals(that.account);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + token.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}