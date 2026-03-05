package by.ares.userservice.service.abstraction;

public interface CrudService<REQUEST, RESPONSE, ID> {

    RESPONSE findById(ID id);

    RESPONSE save(REQUEST request);

    RESPONSE update(REQUEST request, ID id);

    void deleteById(ID id);

}
