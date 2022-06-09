package net.hexabrain.hireo.web.common.mapper;


public interface BaseMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
