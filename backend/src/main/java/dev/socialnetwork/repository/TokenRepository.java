package dev.socialnetwork.repository;

import dev.socialnetwork.jwt.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TokenRepository  extends JpaRepository<Token, Long> {
    void deleteByAccount_Id(long id);
}
