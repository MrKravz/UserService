package by.ares.userservice.service;

import by.ares.userservice.dto.request.SpecificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpecificationService<T> {
    Page<T> findAll(Optional<SpecificationRequest> specificationRequest, Pageable pageable);
}
