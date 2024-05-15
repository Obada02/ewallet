package com.github.ewallet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "card_user_id_key", columnList = "user_id, id", unique = true)
})
public class Card {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-transaction"
    )
    @SequenceGenerator(
            name = "sequence-transaction",
            sequenceName = "sequence_transaction",
            allocationSize = 5
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String credentials;
    private LocalDateTime expiryDate;
}
