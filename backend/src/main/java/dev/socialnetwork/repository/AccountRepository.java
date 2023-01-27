package dev.socialnetwork.repository;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.jwt.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    /*    @Query("""
                            SELECT u.id, u.firstname, u.lastname, u.email, u.password FROM account u INNER JOIN password_recovery pr ON u.id = pr.account_id
                            WHERE pr.token = :token
                    """)*/
    Optional<Account> findByPasswordRecoveriesToken(String token);

    /*    @Query("""
                        SELECT u.id, u.firstname, u.lastname, u.email, u.password FROM account u INNER JOIN token t ON u.id = t.account_id
                        WHERE u.id = :id AND t.refresh_token = :refreshToken AND t.expired_at >= :expiredAt
                """)*/
    Optional<Account> findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(Long id, String refreshToken, LocalDateTime expiredAt);
}//findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan
