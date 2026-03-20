package by.ares.userservice.service.impl;

import by.ares.userservice.dto.request.SpecificationRequest;
import by.ares.userservice.model.User;
import by.ares.userservice.service.SpecificationBuilderService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSpecificationBuilderService implements SpecificationBuilderService<User> {

    @Override
    public Specification<User> configure(SpecificationRequest specificationRequest) {
        List<Specification<User>> spec = new ArrayList<>();
        if (specificationRequest.getName() != null) {
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("name"), specificationRequest.getName()));
        }
        if (specificationRequest.getSurname() != null) {
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("surname"), specificationRequest.getSurname()));
        }
        return Specification.allOf(spec);
    }

}
