package dev.socialnetwork.repository;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByFirstAccountAndSecondAccount(Account first, Account second);

    List<Friend> findByFirstAccount(Account account);

    List<Friend> findBySecondAccount(Account account);

    List<Friend> findByFirstAccountOrSecondAccount(Account first, Account second);

    Optional<Friend> findByFirstAccountAndSecondAccount(Account first, Account second);

    @Query(value = """
    select a.id, a.created_date, a.first_account, a.second_account, a.initiator_id, a.status
    from sc_network.friend a
             inner join sc_network.account acc
                        on a.first_account = acc.id
    where a.initiator_id != :first
      and (a.first_account = :first or a.second_account = :first)
      and (a.first_account = :second or a.second_account = :second)
      and a.status = 0
              """, nativeQuery = true)
    Optional<Friend> findToAccept(@Param("first") long first, @Param("second") long second);

    Friend save(Friend friend);
}
