package com.github.ewallet.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"iban"})
@Table(indexes = {
        @Index(name = "wallet_user_id_iban_key", columnList = "user_id, iban", unique = true),
        @Index(name = "wallet_user_id_name_key", columnList = "user_id, name", unique = true)
})
public class Wallet {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-wallet"
    )
    @SequenceGenerator(
            name = "sequence-wallet",
            sequenceName = "sequence_wallet",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 34, nullable = false, unique = true)
    private String iban;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
