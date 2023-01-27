package dev.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.socialnetwork.jwt.PasswordRecovery;
import dev.socialnetwork.jwt.Token;
import dev.socialnetwork.model.Role;
import dev.socialnetwork.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.jpa.repository.Query;

import java.util.*;
import java.util.function.Predicate;

@Entity
@Getter
@Setter
@Table(name = "account")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "firstname")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "lastname")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Token> tokens = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PasswordRecovery> passwordRecoveries = new HashSet<>();


    public static Account of(String email, String firstName, String lastName, String password, Set<Role> role, Status status) {
        return new Account(null, email, firstName, lastName, password, role, status, Collections.emptyList(), Collections.emptyList());
    }

    @PersistenceCreator
    private Account(Long id, String email, String firstName,
                    String lastName, String password, Set<Role> role, Status status,
                    Collection<Token> tokens, Collection<PasswordRecovery> passwordRecoveries) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = role;
        this.status = status;
        this.tokens.addAll(tokens);
        this.passwordRecoveries.addAll(passwordRecoveries);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    public Boolean removeTokenIf(Predicate<? super Token> predicate) {
        return this.tokens.removeIf(predicate);
    }

    public void addPasswordRecovery(PasswordRecovery passwordRecovery) {
        this.passwordRecoveries.add(passwordRecovery);
    }

    public Boolean removePasswordRecovery(PasswordRecovery passwordRecovery) {
        return this.passwordRecoveries.remove(passwordRecovery);
    }

    public Boolean removePasswordRecoveryIf(Predicate<? super PasswordRecovery> predicate) {
        return this.passwordRecoveries.removeIf(predicate);
    }
}
