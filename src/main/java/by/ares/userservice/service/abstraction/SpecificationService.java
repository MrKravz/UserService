package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.SpecificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecificationService<T> {
    Page<T> findAll(SpecificationRequest specificationRequest, Pageable pageable);
}
