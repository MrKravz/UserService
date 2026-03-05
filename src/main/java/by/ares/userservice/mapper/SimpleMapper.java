package by.ares.userservice.mapper;

public interface SimpleMapper<REQUEST, DTO, MODEL> {
    MODEL toModel(REQUEST request);
    DTO toDto(MODEL model);
}
