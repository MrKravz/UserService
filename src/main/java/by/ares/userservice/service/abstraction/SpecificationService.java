package by.ares.userservice.service.abstraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationService<T ,E> {
    Page<T> findAll(Specification<E> specification, Pageable pageable);
}
