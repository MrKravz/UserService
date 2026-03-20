package by.ares.userservice.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;

public interface StatusChangerService<T> {

    T changeStatus(T id, ActivationStatusRequest activationStatusRequest);

}
