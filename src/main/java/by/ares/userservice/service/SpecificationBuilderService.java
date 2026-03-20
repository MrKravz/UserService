package by.ares.userservice.service;

import by.ares.userservice.dto.request.SpecificationRequest;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilderService<T> {

    Specification<T> configure(SpecificationRequest specificationRequest);

}
