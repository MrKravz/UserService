package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.ActivationStatusRequest;

public interface StatusChangerService<ID> {

    ID changeStatus(ID id, ActivationStatusRequest activationStatusRequest);

}
