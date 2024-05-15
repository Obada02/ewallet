package com.github.ewallet.repository;

import com.github.ewallet.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findById(Long id);

    List<Card> findByUserId(Long userId);

    boolean existsByCredentials(String credentials);

    Card getReferenceByCredentials(String credentials);
    List<Card> findByUserId(long userId);

    Optional<Card> findByCredentialsIgnoreCase(String credentials);

    boolean existsByUserIdAndCredentialsIgnoreCase(Long userId, String credentials);
}
