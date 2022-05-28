package net.hexabrain.hireo.transport.mapper;


public interface BaseMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
