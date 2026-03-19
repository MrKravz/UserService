package by.ares.userservice.repository;

import by.ares.userservice.model.PaymentCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    Set<PaymentCard> findAllByUserId(Long userId);
    @EntityGraph(attributePaths = {"user"})
    Page<PaymentCard> findAll(Pageable pageable);

    @Query(value = "SELECT p FROM payment_cards p WHERE p.id = :id", nativeQuery = true)
    Optional<PaymentCard> findAnyById(Long id);
}
