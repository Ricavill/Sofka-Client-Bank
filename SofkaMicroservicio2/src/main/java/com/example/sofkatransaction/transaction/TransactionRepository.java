package com.example.sofkatransaction.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(
            value = """
                      select new com.example.sofkatransaction.transaction.TransactionDTO(t, a, :clientName)
                      from Transaction t
                      join t.account a
                      where a.clientId = :clientId
                        and t.createdAt between :start and :end
                        and a.deletedAt is null
                      order by t.createdAt desc
                    """,
            countQuery = """
                      select count(t)
                      from Transaction t
                      join t.account a
                      where a.clientId = :clientId
                        and t.createdAt between :start and :end
                        and a.deletedAt is null
                    """
    )
    Page<TransactionDTO> findAccountsExtendedByClientId(
            @Param("clientId") Long clientId,
            @Param("clientName") String clientName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable);
}
