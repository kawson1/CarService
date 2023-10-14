package com.example.carservice.Repositories;

import java.util.List;
import java.util.Optional;

/**I
 *
 * @param <E> type of entity
 * @param <K> type of primary key
 */
public interface Repository<E, K> {
    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void delete(E entity);

    void update(E entity);

}
