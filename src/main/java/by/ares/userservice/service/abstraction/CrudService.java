package by.ares.userservice.service.abstraction;

public interface CrudService<REQUEST, RESPONSE, ID> {

    RESPONSE findById(ID id);

    ID save(REQUEST request);

    ID update(REQUEST request, ID id);

    void deleteById(ID id);

}
