package by.ares.userservice.repository;

import by.ares.userservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = {"paymentCards"})
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"paymentCards"})
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT u FROM users u WHERE u.id = :id", nativeQuery = true)
    Optional<User> findAnyById(Long id);
}
