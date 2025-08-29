package com.example.sofkatransaction.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a " +
            "from Account a " +
            "where a.id=?1 and a.deletedAt is null")
    Optional<Account> findAccountById(Long id);

    @Query("select a " +
            "from Account a " +
            "where a.accountNumber=?1 and a.deletedAt is null")
    Optional<Account> findAccountByAccountNumber(Long id);

    @Query("select a " +
            "from Account a " +
            "left join fetch a.transactions t " +
            "where a.clientId =?1 and t.createdAt between ?2 and ?3 and a.deletedAt is null")
    List<Account> findAccountsExtendedByClientId(Long clientId, LocalDateTime start, LocalDateTime end);
}
