package by.ares.userservice.service.abstraction;

import by.ares.userservice.model.ActivationStatus;

public interface StatusChangerService<ID> {

    ID changeStatus(ID id, ActivationStatus activationStatus);

}
