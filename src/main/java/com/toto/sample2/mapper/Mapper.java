package com.toto.sample2.mapper;

import java.util.List;
import java.util.ArrayList;

public interface Mapper<E, D> {

    E toEntity(D data);

    D toData(E entity);

    default List<E> toEntities(List<D> datas) {
        List<E> entities = new ArrayList<>();
        for (D data : datas) {
            E entity = this.toEntity(data);
            entities.add(entity);
        }
        return entities;
    }

    default List<D> toDatas(List<E> entities) {
        List<D> datas = new ArrayList<>();
        for (E entity : entities) {
            D data = this.toData(entity);
            datas.add(data);
        }
        return datas;
    }

}
