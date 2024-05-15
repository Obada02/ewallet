package com.github.ewallet.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"username"})
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-user"
    )
    @SequenceGenerator(
            name = "sequence-user",
            sequenceName = "sequence_user",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Wallet wallet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @PostPersist
    public void createWallet() {
        Wallet wallet = new Wallet();
        wallet.setIban(generateIban());
        wallet.setName("Default Wallet");
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUser(this);
        this.setWallet(wallet);
    }

    private String generateIban() {
        // Implement your IBAN generation logic here
        return "GENERATED_IBAN";
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setUser(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setUser(null);
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void setWallet(Wallet wallet) {
        if (wallet == null) {
            if (this.wallet != null) {
                this.wallet.setUser(null);
            }
        } else {
            wallet.setUser(this);
        }
        this.wallet = wallet;
    }
}
