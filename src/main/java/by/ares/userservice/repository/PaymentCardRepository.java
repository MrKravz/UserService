package by.ares.userservice.repository;

import by.ares.userservice.model.PaymentCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findAllByUserId(Long userId);
    @EntityGraph(attributePaths = {"user"})
    Page<PaymentCard> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM payment_cards WHERE id = :id", nativeQuery = true)
    Optional<PaymentCard> findAnyById(Long id);
}
