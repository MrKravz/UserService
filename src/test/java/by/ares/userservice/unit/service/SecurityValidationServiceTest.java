package by.ares.userservice.unit.service;

import by.ares.userservice.service.impl.SecurityValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.lang.reflect.Field;

import static by.ares.userservice.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityValidationServiceTest {

    private SecurityValidationServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        service = new SecurityValidationServiceImpl();
        Field field = SecurityValidationServiceImpl.class.getDeclaredField("internalServiceKey");
        field.setAccessible(true);
        field.set(service, INTERNAL_KEY);
    }

    @Test
    void shouldAllowAccess_whenAdmin() {
        assertDoesNotThrow(() ->
                service.validateAccess(USER_ID, CARD_ID, ADMIN)
        );
    }

    @Test
    void shouldAllowAccess_whenOwner() {
        assertDoesNotThrow(() ->
                service.validateAccess(USER_ID, USER_ID, USER)
        );
    }

    @Test
    void shouldDenyAccess_whenNotOwner() {
        assertThrows(AccessDeniedException.class, () ->
                service.validateAccess(USER_ID, CARD_ID, USER)
        );
    }

    @Test
    void shouldAllowAccess_whenAdminAndInternalService() {
        assertDoesNotThrow(() ->
                service.validateAccess(INTERNAL_KEY)
        );
    }


}
