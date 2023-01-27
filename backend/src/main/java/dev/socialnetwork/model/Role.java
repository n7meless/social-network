package dev.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceCreator;


@Getter
@Setter
@Entity
@Table(name = "role", schema = "sc_network")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;

    public static Role of(String name) {
        return new Role( name);
    }

    @PersistenceCreator
    private Role( String name) {
        this.name = name;
    }
}
