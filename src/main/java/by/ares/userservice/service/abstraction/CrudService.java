package by.ares.userservice.service.abstraction;

public interface CrudService<R, D, T> {

    D findById(T id);

    T save(R request);

    T update(R request, T id);

    void deleteById(T id);

}
