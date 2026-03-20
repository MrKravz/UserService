package by.ares.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
@SQLRestriction("active = 'ACTIVE'")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    @Enumerated(EnumType.STRING)
    private ActivationStatus active = ActivationStatus.ACTIVE;

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version = 0L;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<PaymentCard> paymentCards = new HashSet<>();

    public void addCard(PaymentCard paymentCard) {
        paymentCards.add(paymentCard);
        paymentCard.setUser(this);
    }

    public void removeCard(PaymentCard paymentCard) {
        paymentCards.remove(paymentCard);
        paymentCard.setUser(null);
    }

}
