package by.ares.userservice.mapper;

public interface SimpleMapper<R, D, T> {
    T toModel(R request);
    D toDto(T model);
}
