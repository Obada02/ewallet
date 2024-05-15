package com.github.ewallet.repository;

import com.github.ewallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByIban(String iban);

    List<Wallet> findByUserId(Long userId);

    boolean existsByIbanIgnoreCase(String iban);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);

    Wallet getReferenceByIban(String iban);
}