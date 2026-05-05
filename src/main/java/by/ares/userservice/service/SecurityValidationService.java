package by.ares.userservice.service;

public interface SecurityValidationService {
    void validateAccess(Long id, Long userId, String role);
    void validateAccess(String role);
}
