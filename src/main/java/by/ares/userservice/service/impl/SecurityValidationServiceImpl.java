package by.ares.userservice.service.impl;

import by.ares.userservice.model.Role;
import by.ares.userservice.service.SecurityValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class SecurityValidationServiceImpl implements SecurityValidationService {

    @Value("${INTERNAL_SERVICE_KEY:}")
    private String internalServiceKey;

    @Override
    public void validateAccess(Long id, Long userId, String role) {
        if (isAdmin(Role.valueOf(role)) || isOwner(id, userId)) {
            return;
        }
        throw new AccessDeniedException("Access denied");
    }

    @Override
    public void validateAccess(String role) {
        if (isInternalService(role) || isAdmin(Role.valueOf(role))) {
            return;
        }
        throw new AccessDeniedException("Access denied");
    }

    private boolean isOwner(Long id, Long userId) {
        return id.equals(userId);
    }

    private boolean isAdmin(Role role) {
        return role.equals(Role.ADMIN);
    }

    private boolean isInternalService(String role) {
        return role.equals(internalServiceKey);
    }

}
