package com.vgilab.geobot.persistence.repositories;

import com.vgilab.geobot.persistence.entity.Coordinates;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ljzhang
 */
public interface CoordinateRepository extends PagingAndSortingRepository<Coordinates, Long>, QueryDslPredicateExecutor<Coordinates>  {

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Coordinates findOne(Long id);

    /**
     *
     * @param sort
     * @return
     */
    @Override
    public Iterable<Coordinates> findAll(Sort sort);

    /**
     *
     * @param pgbl
     * @return
     */
    @Override
    public Page<Coordinates> findAll(Pageable pgbl);

    /**
     *
     * @return
     */
    @Override
    public List<Coordinates> findAll();

    /**
     *
     * @param persisted
     * @return
     */
    @Override
    public Coordinates save(Coordinates persisted);

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(Long id);

    /**
     *
     * @return
     */
    @Override
    public long count();
}
